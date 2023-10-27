import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Creates a frame with a button that can be pressed to start an instance of the
 * game then closses the frame.
 * 
 * @author Guy Lewy
 * @author Antoni Nowaczyk
 */

public class MainMenu extends JPanel implements KeyListener {
    private boolean menuRunning;
    private ScoreManager scoreManager = new ScoreManager();
    public JFrame menuWindow = new JFrame();
    public MainMenuBoard board;

    public static Dimension windowDimensions;

    int menuBounceDelay = 74;
    int menuBounceCounter = 5;

    public int highScore;

    private final int ups = 120; // Updates per second
    private final int fps = 60; // Frames per second

    Sound sound = new Sound();
    Sound background = new Sound();

    /**
     * Sets up the frame to have a button and add an action listener to the button.
     */
    public MainMenu() {
        scoreManager.createScoreFile();
        highScore = scoreManager.getHighScore();
        setMenu();
        board = new MainMenuBoard(highScore, windowDimensions.width, windowDimensions.height);
        addKeyListener(this);
        setFocusable(true);
        requestFocus();
        setFocusTraversalKeysEnabled(false);
        Thread menuLoopThread = new Thread(this::startMenuLoop);
        menuLoopThread.start();
    }

    /**
     * Starts a clock that updates the menu fps times per second.
     */
    public void startMenuLoop() {
        long initialTime = System.nanoTime();
        long secondCounter = 0;
        final double timeUPS = 1000000000 / ups;
        final double timeFPS = 1000000000 / fps;
        double deltaUPS = 0;
        double deltaFPS = 0;
        long timer = System.currentTimeMillis();

        while (menuRunning) {
            long currentTime = System.nanoTime();

            deltaUPS += (currentTime - initialTime) / timeUPS;
            deltaFPS += (currentTime - initialTime) / timeFPS;
            secondCounter += (currentTime - initialTime);
            initialTime = currentTime;

            if (secondCounter >= 1000000000) {
                secondCounter = 0;
            }

            if (deltaUPS >= 1) {
                // Update all game logic
                updateMenu();
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

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.setBackground(new Color(95, 175, 250));
        if (board != null) {
            board.draw(g);
        }
    }

    /**
     * Moves the menu up and down with the beat of the music.
     */
    public void updateMenu() {
        if (menuBounceCounter >= menuBounceDelay) {
            menuBounceCounter = 0;
            board.bounce();
        } else {
            menuBounceCounter++;
        }
    }

    /**
     * Initialize the menu including makingthe frame and playing the music.
     */
    public void setMenu() {
        menuWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menuWindow.setExtendedState(JFrame.MAXIMIZED_BOTH);
        menuWindow.setMinimumSize(windowDimensions);
        menuWindow.setUndecorated(true);
        menuWindow.add(this);
        windowDimensions = Toolkit.getDefaultToolkit().getScreenSize();
        setMaximumSize(windowDimensions);
        menuWindow.setVisible(true);
        background.setSoundEffect(4);
        background.play();
        background.loop();
        menuRunning = true;
    }

    public static void main(String[] args) {
        new MainMenu();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_UP) {
            board.arrowPosition--;
        } else if (code == KeyEvent.VK_DOWN) {
            board.arrowPosition++;
        }
        board.arrowPosition %= 3;
        if (board.arrowPosition < 0) {
            board.arrowPosition = 2;
        }

        if (code == KeyEvent.VK_SPACE) {
            sound.setSoundEffect(5);
            sound.play();
            if (board.scoreVisible) {
                board.scoreVisible = false;
            } else {
                if (board.arrowPosition == 0) {
                    background.stop();
                    startGame();
                } else if (board.arrowPosition == 1) {
                    board.scoreVisible = true;
                } else if (board.arrowPosition == 2) {
                    menuWindow.dispose();
                    System.exit(0);
                }
            }

        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        ;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        ;
    }

    /**
     * Create a new DisplayGraphics object and dispose of the menu window.
     */
    public void startGame() {
        new DisplayGraphics();
        menuWindow.dispose();
    }
}