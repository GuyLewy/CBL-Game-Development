import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

/**
 * DisplayGraphics class acts as the main window, implementing all timing logics
 * and drawing functionality as well as well as Swing window creation.
 */
public class DisplayGraphics extends JPanel implements KeyListener {

    public static Rectangle windowDimensions;
    private Player player = new Player();
    private ProjectilesArrayList playerProjectiles = new ProjectilesArrayList();
    private EnemiesArrayList enemies = new EnemiesArrayList();
    private PlayerShotBar playerBar = new PlayerShotBar();
    private ScoreCounter score = new ScoreCounter();
    private Wallet playerWallet = new Wallet();
    private HealthBar playerHealthBar = new HealthBar(player.playerHealth);
    private Sound sound = new Sound();
    private Sound soundtrack = new Sound();

    private boolean statUpgraded;
    public static boolean gameRunning;
    boolean upPressed = false;
    boolean downPressed = false;
    boolean blockNextShot = false;
    int enemySpawnDelayCounter;
    int enemySpawnDelay = 80;
    int playerShotDelayCounter = player.playerShotDelay;
    float soundtrackVolume = -15.0f;
    JFrame gameWindow = new JFrame();

    private int fireRateUpgradePrices[] = { 20, 30, 40, 50 };
    private int movementSpeedUpgradePrices[] = { 10, 15, 20, 25 };

    private final int UPS = 120; // Updates per second
    private final int FPS = 120; // Frames per second

    /**
     * Constructor method to initialize a timer and set the DisplayGraphics object
     * as focusable so that keystrokes can be recorded.
     */
    public DisplayGraphics() {
        startGame();
        addKeyListener(this);
        setFocusable(true);
        requestFocus();
        setFocusTraversalKeysEnabled(false);
        playerBar.playerBarSetup(player.playerShotDelay);
        Thread gameLoopThread = new Thread(this::startGameLoop);
        gameLoopThread.start();
    }

    /**
     * Iterates across the enemiesArrayList and checks if any of their projectiles
     * hit the player.
     * 
     * @param enemiesArrayList array list containing all of the enemies in the game
     */
    public void checkEnemyProjectiles(EnemiesArrayList enemiesArrayList) {
        ArrayList<Enemy> enemies = enemiesArrayList.enemies;
        for (int i = 0; i < enemies.size(); i++) {
            ProjectilesArrayList nextProjectileList = enemies.get(i).enemyProjectiles;
            if (nextProjectileList.areBulletsHitting(player.playerX, player.playerY,
                    player.playerWidth, player.playerHeight)) {
                playerLoseHealth();
            }
        }
    }

    /**
     * Lowers the players health and plays a sound when run, additionally it checks
     * if the player's health goes to 0 and if it does then it ends the game.
     */
    public void playerLoseHealth() {
        sound.setSoundEffect(2);
        sound.play();
        player.playerHealth--;
        if (player.playerHealth <= 0) {
            endGame();
        }
    }

    /**
     * Run at the end of a game, deletes all enemies, resets
     * player positon and opens up the restart menu.
     */
    public void endGame() {
        gameRunning = false;
        soundtrack.stop();
        new RestartMenu(score.gameScore);
        gameWindow.setVisible(false);
        player.playerX = 0;
        player.playerY = 0;
        score.gameScore = 0;
        enemies.deleteAllEnemies();
    }

    /**
     * Sets up the window and starts music to the game.
     */
    public void startGame() {
        gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameWindow.setSize(2000, 1000);
        windowDimensions = gameWindow.getBounds();
        gameWindow.add(this);
        gameWindow.setVisible(true);
        soundtrack.setSoundEffect(3);
        soundtrack.play();
        soundtrack.setVolume(soundtrackVolume);
        soundtrack.loop();
        gameRunning = true;
    }

    /**
     * Sets up a game loop timer with discrete FPS (frames per second) and UPS
     * (updates per second) values so that the game works equally as fast on
     * different hardware as well as saving on some resources by not drawing to the
     * screen every update.
     */
    public void startGameLoop() {

        long initialTime = System.nanoTime();
        final double timeUPS = 1000000000 / UPS;
        final double timeFPS = 1000000000 / FPS;
        double deltaUPS = 0;
        double deltaFPS = 0;
        long timer = System.currentTimeMillis();

        while (gameRunning) {
            long currentTime = System.nanoTime();

            deltaUPS += (currentTime - initialTime) / timeUPS;
            deltaFPS += (currentTime - initialTime) / timeFPS;
            initialTime = currentTime;

            if (deltaUPS >= 1) {
                // Update all game logic
                updateGame();
                deltaUPS--;
            }

            if (deltaFPS >= 1) {
                // Update all sprites and graphics
                repaint();
                deltaFPS--;
            }

            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
            }

        }
    }

    /**
     * Method that handles all of the logic of the game including player movement,
     * enemy and player collisions as well as damage and awarding money for
     * killing an enemy.
     */
    private void updateGame() {
        player.move(upPressed, downPressed);
        playerProjectiles.moveProjectiles(5);
        if (playerProjectiles.bulletInTarget) {
            sound.setSoundEffect(1);
            sound.play();
            playerProjectiles.bulletInTarget = false;
        }

        int playerDamage = enemies.updateEnemies(playerProjectiles, playerWallet,
                player.playerX, player.playerY, player.playerWidth, player.playerHeight);

        for (int i = 0; i < playerDamage; i++) {
            playerLoseHealth();
        }
        checkEnemyProjectiles(enemies);

        if (enemySpawnDelayCounter >= enemySpawnDelay) {
            enemies.generateEnemy(0);
            enemySpawnDelayCounter = 0;
        }

        if (playerShotDelayCounter >= player.playerShotDelay) {
            blockNextShot = false;
        } else {
            playerShotDelayCounter++;
        }

        enemySpawnDelayCounter++;

        score.updateScore(enemies);
        playerBar.updateBar(playerShotDelayCounter);
        playerHealthBar.updateHealtBar(player.playerHealth);
    }

    /**
     * The method used to draw all graphics objects and elements onto the screen.
     * 
     * @param g a Graphics object that is painted to the screen
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.setBackground(new Color(95, 175, 250));
        player.draw(g);
        playerProjectiles.draw(g);
        enemies.draw(g);
        playerBar.draw(g);
        score.draw(g);
        playerWallet.draw(g);
        enemies.drawEnemyProjectiles(g);
        enemies.drawMoneyDropTexts(g);
        playerHealthBar.draw(g);
    }

    /**
     * Method to determine whether a key on the keyboard was pressed and get the
     * keycode of the key, it is used to control the actions that the player
     * chooses.
     * 
     * @param e a KeyEvent object that is used to determine the key that was pressed
     */
    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_UP) {
            upPressed = true;
        } else if (code == KeyEvent.VK_DOWN) {
            downPressed = true;
        }

        if (code == KeyEvent.VK_SPACE && !blockNextShot) {
            sound.setSoundEffect(0);
            sound.play();
            playerProjectiles.addProjectile((int) (player.playerX + 95),
                    (int) (player.playerY + 72));
            blockNextShot = true;
            playerShotDelayCounter = 0;
        }

        if (code == KeyEvent.VK_Z && !statUpgraded
                && player.fireRateUpgrades <= fireRateUpgradePrices.length
                && playerWallet.money >= fireRateUpgradePrices[player.fireRateUpgrades]) {
            playerWallet.money -= fireRateUpgradePrices[player.fireRateUpgrades];
            player.upgradeStat(1);
            statUpgraded = true;
        } else if (code == KeyEvent.VK_X && !statUpgraded
                && player.speedUpgrades <= movementSpeedUpgradePrices.length
                && playerWallet.money >= movementSpeedUpgradePrices[player.speedUpgrades]) {
            playerWallet.money -= movementSpeedUpgradePrices[player.speedUpgrades];
            player.upgradeStat(2);
            statUpgraded = true;
        } else if (code == KeyEvent.VK_C && !statUpgraded
                && playerWallet.money >= 8 * player.healthUpgrades) {
            playerWallet.money -= 8 * player.healthUpgrades;
            player.upgradeStat(3);
            statUpgraded = true;
        }
    }

    /**
     * Method used to determen whether a key on the keyboard was released and get
     * the keykoad of the key, this is used to determine when the player wants to
     * stop moving.
     * 
     * @param e A KeyEvent used to determine what key is released
     */
    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_UP) {
            upPressed = false;
        } else if (code == KeyEvent.VK_DOWN) {
            downPressed = false;
        }

        if (code == KeyEvent.VK_Z || code == KeyEvent.VK_X || code == KeyEvent.VK_C) {
            statUpgraded = false;
        }

    }

    /**
     * This method is not being used however must be defined in order to apease the
     * implementation of KeyListener.
     * 
     * @param e a KeyEvent object that can be used to determine the typed key
     */
    @Override
    public void keyTyped(KeyEvent e) {
    }
}
