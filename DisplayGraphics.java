import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

/**
 * DisplayGraphics class acts as the main window, implementing all timing logics
 * and drawing functionality as well as well as Swing window creation.
 */
public class DisplayGraphics extends JPanel implements KeyListener {

    double difficultyLevel = 1;
    double startDifficulty = 0.1; 
    double difficultyCoefficient = startDifficulty;
    double dLog = Math.log(1 / startDifficulty - 1);

    Random rand = new Random();

    public static Rectangle windowDimensions;
    private Player player = new Player();
    private EnemiesArrayList enemies = new EnemiesArrayList();
    private PlayerShotBar playerBar = new PlayerShotBar();
    private ScoreCounter score = new ScoreCounter();
    private Wallet playerWallet = new Wallet();
    private HealthBar playerHealthBar = new HealthBar(player.playerHealth);
    private Sound sound = new Sound();
    private Sound soundtrack = new Sound();
    public static boolean gameRunning;
    boolean upPressed = false;
    boolean downPressed = false;
    boolean blockNextShot = false;
    int enemyInitialSpawnDelay = 350;
    int enemySpawnDelayCounter;
    int enemySpawnDelay = enemyInitialSpawnDelay;
    int numberOfEnemiesBound = 1;
    int playerShotDelayCounter = player.playerShotDelay;
    float soundtrackVolume = -25.0f;
    JFrame gameWindow = new JFrame();
    // Timer timer = new Timer(5, new TimerListener());

    private final int UPS = 120; // Updates per second
    private final int FPS = 60; // Frames per second
    private int timeInSeconds = 0;

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
     * Updates the difficulty of the game.
     * @param timeInMinutes is a time passed from the beggining of the game.
     */
    void updateDC(double timeInSeconds) {
        difficultyCoefficient = 1 / (1 + Math.pow(Math.E, 
            -difficultyLevel * timeInSeconds / 60 + dLog));
        enemySpawnDelay = (int) (enemyInitialSpawnDelay
            - 150 * difficultyCoefficient);
        numberOfEnemiesBound = (int) (difficultyCoefficient * 2.1) + 1;
    }

    /**
     * Iterates across the enemiesArrayList and checks if any of their projectiles
     * hit the player.
     * 
     * @param enemiesArrayList array list containing all of the enemies in the game
     */
    public void checkEnemyProjectiles(EnemiesArrayList enemiesArrayList) {
        if (enemiesArrayList.enemiesProjectiles.areBulletsHitting(player.playerX, player.playerY,
                player.playerWidth, player.playerHeight)) {
            playerLoseHealth();
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
        long secondCounter = 0;
        final double timeUPS = 1000000000 / UPS;
        final double timeFPS = 1000000000 / FPS;
        double deltaUPS = 0;
        double deltaFPS = 0;
        long timer = System.currentTimeMillis();

        while (gameRunning) {
            long currentTime = System.nanoTime();

            deltaUPS += (currentTime - initialTime) / timeUPS;
            deltaFPS += (currentTime - initialTime) / timeFPS;
            secondCounter +=  (currentTime - initialTime);
            initialTime = currentTime;
            
            if (secondCounter >= 1000000000) {
                timeInSeconds++;
                secondCounter = 0;
            }

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
        updateDC(timeInSeconds); //Update difficulty coefficient; time in minutes.
        player.move(upPressed, downPressed);
        player.playerProjectiles.moveProjectiles();
        checkEnemyProjectiles(enemies);
        if (player.playerProjectiles.bulletInTarget) {
            sound.setSoundEffect(1);
            sound.play();
            player.playerProjectiles.bulletInTarget = false;
        }

        int playerDamage = enemies.updateEnemies(player.playerProjectiles, playerWallet,
                player.playerX, player.playerY, player.playerWidth, player.playerHeight, 
                (int) (100 * difficultyCoefficient));

        for (int i = 0; i < playerDamage; i++) {
            playerLoseHealth();
        }

        enemySpawnDelayCounter++;
        
        if (enemySpawnDelayCounter >= enemySpawnDelay) {
            for (int i = 0; i < rand.nextInt(numberOfEnemiesBound) + 1; i++) {
                enemies.generateEnemy();
            }
            enemySpawnDelayCounter = 0;
        }


        if (playerShotDelayCounter >= player.playerShotDelay) {
            blockNextShot = false;
        } else {
            playerShotDelayCounter++;
        }

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
        enemies.draw(g);
        playerBar.draw(g);
        score.draw(g);
        playerWallet.draw(g);
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
        } else if (code == KeyEvent.VK_SPACE && !blockNextShot) {
            sound.setSoundEffect(0);
            sound.play();
            player.playerProjectiles.addProjectile((int) (player.playerX + 95),
                    (int) (player.playerY + 72));
            blockNextShot = true;
            playerShotDelayCounter = 0;
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
