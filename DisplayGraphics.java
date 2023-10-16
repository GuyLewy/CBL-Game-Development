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
    public static boolean gameRunning;
    boolean upPressed = false;
    boolean downPressed = false;
    boolean blockNextShot = false;
    int enemySpawnDelayCounter;
    int enemySpawnDelay = 65;
    int playerShotDelayCounter = player.playerShotDelay;
    JFrame gameWindow = new JFrame();
    Timer timer = new Timer(5, new TimerListener());

    /**
     * Constructor method to initialize a timer and set the DisplayGraphics object
     * as focusable so that keystrokes can be recorded.
     */
    public DisplayGraphics() {
        startGame();
        gameRunning = true;
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        playerBar.playerBarSetup(player.playerShotDelay);
        timer.start();
    }

    /**
     * .
     * 
     * @param enemiesArrayList .
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

    public void playerLoseHealth() {
        player.playerHealth--;
        if (player.playerHealth <= 0) {
            endGame();
        }
    }

    public void endGame() {
        gameRunning = false;
        new RestartMenu(score.gameScore);
        gameWindow.setVisible(false);
        player.playerHealth = 3;
        player.playerX = 0;
        player.playerY = 0;
        score.gameScore = 0;
        enemies.deleteAllEnemies();
        timer.stop();
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
            playerProjectiles.addProjectile((int) (player.playerX + 100),
                    (int) (player.playerY + 65));
            blockNextShot = true;
            playerShotDelayCounter = 0;
        }
    }

    /**
     * Method used to determen whether a key on the keyboard was released and get
     * the
     * keykoad of the key, this is used to determine when the player wants to stop
     * moving.
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

    /**
     * The method used to draw all graphics objects and elements onto the screen.
     * 
     * @param g a Graphics object that is painted to the screen
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        player.draw(g);
        playerProjectiles.draw(g);
        enemies.draw(g);
        playerBar.draw(g);
        score.draw(g);
        enemies.drawEnemyProjectiles(g);
    }

    /**
     * A class that is used to determine when a certain amount of time has passed in
     * order to update positions and redraw objects.
     */
    private class TimerListener implements ActionListener {

        /**
         * The method that updates after the time has passed, game logic, player
         * movement and enemy spawning is all handled in this method.
         * 
         * @param e an ActionEvent object used to determine the event
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            if (gameRunning) {

                player.move(upPressed, downPressed);
                playerProjectiles.moveProjectiles(5);
                int playerDamage = enemies.updateEnemies(playerProjectiles, player.playerX,
                        player.playerY, player.playerWidth, player.playerHeight);
                for (int i = 0; i < playerDamage; i++) {
                    playerLoseHealth();
                }
                checkEnemyProjectiles(enemies);

                if (enemySpawnDelayCounter >= enemySpawnDelay) {
                    enemies.generateEnemy(0, 0);
                    enemySpawnDelayCounter = 0;
                }

                if (playerShotDelayCounter >= player.playerShotDelay) {
                    blockNextShot = false;
                } else {
                    playerShotDelayCounter++;
                }

                enemySpawnDelayCounter++;

                playerBar.updateBar(playerShotDelayCounter);
                score.updateScore(enemies);

                repaint();
            }
        }
    }

    public void startGame() {
        gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameWindow.setSize(2000, 1000);
        windowDimensions = gameWindow.getBounds();
        gameWindow.add(this);
        gameWindow.setVisible(true);
    }
}