import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

/**
 * DisplayGraphics class acts as the main window, implementing all timing logics
 * and drawing functionality as well as well as Swing window creation.
 */
public class DisplayGraphics extends JPanel implements KeyListener, Drawable {
    double difficultyLevel = 1.5;
    double startDifficulty = 0.1;
    double difficultyCoefficient = startDifficulty;
    double dLog = Math.log(1 / startDifficulty - 1);

    Random rand = new Random();

    public static Dimension windowDimensions;
    public static Dimension blackBorderDimensions = new Dimension(0, 0);
    public static double screenSizeMultiplier = 1;
    private ScoreManager scoreManager = new ScoreManager();
    private Player player = new Player();
    private EnemiesArrayList enemies = new EnemiesArrayList();
    private WavesArrayList waves = new WavesArrayList();
    private PlayerShotBar playerBar = new PlayerShotBar();
    private ScoreCounter score = new ScoreCounter();
    private Wallet playerWallet = new Wallet();
    private Dock dock;
    private HealthBar playerHealthBar = new HealthBar(player.playerHealth);
    private Sound sound = new Sound();
    private Sound soundtrack = new Sound();

    private boolean statUpgraded;
    public static boolean gameRunning;
    boolean upPressed = false;
    boolean downPressed = false;
    boolean blockNextShot = false;
    int enemyInitialSpawnDelay = 350;
    int enemySpawnDelayCounter;
    int enemySpawnDelay = enemyInitialSpawnDelay;
    int numberOfEnemiesBound = 1;
    int playerShotDelayCounter = player.playerShotDelay;
    float soundtrackVolume = -5.0f;
    JFrame gameWindow = new JFrame();

    private int[] fireRateUpgradePrices = { 20, 30, 40, 50 };
    private int[] movementSpeedUpgradePrices = { 10, 15, 20, 25 };

    private final int ups = 120; // Updates per second
    private final int fps = 120; // Frames per second
    private int timeInSeconds = 0;

    /**
     * Constructor method to initialize a timer and set the DisplayGraphics object
     * as focusable so that keystrokes can be recorded.
     */
    public DisplayGraphics() {
        this.setLayout(new BorderLayout());
        startGame();
        scoreManager.createScoreFile();
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
     * 
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
        scoreManager.saveScore(score.gameScore);
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
        gameWindow.setExtendedState(JFrame.MAXIMIZED_BOTH);
        gameWindow.setMinimumSize(windowDimensions);
        gameWindow.setResizable(false);
        windowDimensions = Toolkit.getDefaultToolkit().getScreenSize();
        checkScreenAspectRatio();
        gameWindow.add(this);
        dock = new Dock(windowDimensions.height);
        gameWindow.setVisible(true);
        soundtrack.setSoundEffect(3);
        soundtrack.play();
        soundtrack.setVolume(soundtrackVolume);
        soundtrack.loop();
        gameRunning = true;
    }

    void checkScreenAspectRatio() {
        if (windowDimensions.width / (double) windowDimensions.height == 16.0 / 9.0) {
            System.out.println("16:9");
            screenSizeMultiplier = windowDimensions.width / 1920.0;
        } else if (windowDimensions.width / (double) windowDimensions.height > 16.0 / 9.0) {
            blackBorderDimensions.width = (windowDimensions.width
                    - (windowDimensions.height * 9 / 16)) / 2;
            screenSizeMultiplier = windowDimensions.width / 1920.0;
        } else if (windowDimensions.width / (double) windowDimensions.height < 16.0 / 9.0) {
            blackBorderDimensions.height = (windowDimensions.height
                    - (windowDimensions.width * 9 / 16)) / 2;
            screenSizeMultiplier = windowDimensions.height / 1080.0;
        }

        System.out.println(screenSizeMultiplier);
        System.out.println(blackBorderDimensions);
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

        final double timeUPS = 1000000000 / ups;
        final double timeFPS = 1000000000 / fps;

        double deltaUPS = 0;
        double deltaFPS = 0;
        long timer = System.currentTimeMillis();

        while (gameRunning) {
            long currentTime = System.nanoTime();

            deltaUPS += (currentTime - initialTime) / timeUPS;
            deltaFPS += (currentTime - initialTime) / timeFPS;
            secondCounter += (currentTime - initialTime);
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
        updateDC(timeInSeconds); // Update difficulty coefficient; time in minutes.
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
            if (player.playerHealth > 0) {
                playerLoseHealth();
            }
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

        if (waves.waveDelayCounter >= waves.waveDelay) {
            waves.generateWave();
            waves.waveDelayCounter = 0;
        } else {
            waves.waveDelayCounter++;
        }

        waves.updateWaves();
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
        waves.draw(g);
        player.draw(g);
        dock.draw(g);
        enemies.draw(g);
        playerBar.draw(g);
        score.draw(g);
        playerWallet.draw(g);
        playerHealthBar.draw(g);
        this.draw(g);
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
            player.playerProjectiles.addProjectile((int) (player.playerX + 95),
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

        statUpgraded = (!(code == KeyEvent.VK_Z || code == KeyEvent.VK_X || code == KeyEvent.VK_C));

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

    /**
     * Draws the upgrade details on the top of the screen, including the price of
     * the upgrade and what button should be pressed for the upgrade.
     * 
     * @param g Graphics object that the graphics should be added to
     */
    @Override
    public void draw(Graphics g) {
        g.setColor(new Color(0, 0, 0, 255));
        g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 25));
        String fireString;
        String speedString;

        if (player.fireRateUpgrades < fireRateUpgradePrices.length) {
            fireString = "$" + fireRateUpgradePrices[player.fireRateUpgrades];
        } else {
            fireString = "MAX LEVEL";
        }

        if (player.speedUpgrades < movementSpeedUpgradePrices.length) {
            speedString = "$" + movementSpeedUpgradePrices[player.speedUpgrades];
        } else {
            speedString = "MAX LEVEL";
        }

        g.drawString(
                "Firerate (z): %s    Speed (x): %s    Heal (c): $%d".formatted(
                        fireString,
                        speedString,
                        8 * player.healthUpgrades),
                (int) (0.2 * DisplayGraphics.windowDimensions.getWidth()), 30 + blackBorderDimensions.height);

        g.setColor(Color.black);
        // Draws black borders for displays that are tall
        g.fillRect(0, 0, windowDimensions.width, blackBorderDimensions.height);
        g.fillRect(0, windowDimensions.height - blackBorderDimensions.height, windowDimensions.width,
                blackBorderDimensions.height);

        // Draws black borders for displays that are wide
        g.fillRect(0, 0, blackBorderDimensions.width, windowDimensions.height);
        g.fillRect(windowDimensions.width - blackBorderDimensions.width, 0, blackBorderDimensions.width,
                windowDimensions.height);
    }
}
