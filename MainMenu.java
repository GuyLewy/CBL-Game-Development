import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Creates a frame with a button that can be pressed to start an instance of the
 * game then closses the frame.
 * 
 * @author Antoni Nowaczyk
 * @id 1934899
 * @author Guy Lewy
 * @id 1954962 
 */

public class MainMenu extends JPanel implements KeyListener {
    private boolean menuRunning;
    private ScoreManager scoreManager = new ScoreManager();
    public JFrame menuWindow;
    public MainMenuBoard board;

    public static Dimension windowDimensions;

    int menuBounceDelay = 74;
    int menuBounceCounter = 7;

    public int highScore;
    boolean showTutorial = false;
    long timer;

    private final int ups = 120; // Updates per second
    private final int fps = 60; // Frames per second

    Sound sound = new Sound();
    Sound background = new Sound();

    String[] tutorialStringArray = {
        "Move up and down with the Up and Down keys",
        "Shoot with space, defeat the enemies before they reach the dock and avoid their shots",
        "Upgrade your ship's movement speed, fire rate and heal it with z, x, and c respectively",
        "Press esc to pause the game",
        "PRESS SPACE TO RETURN TO MENU"
    };

    /**
     * Sets up the frame to have a button and add an action listener to the button.
     */
    public MainMenu(JFrame frame) {
        menuWindow = frame;
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
        timer = System.currentTimeMillis();

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
        if (board != null && !showTutorial) {
            board.draw(g);
        } else if (timer > 1000) {
            for (int i = 0; i < tutorialStringArray.length; i++) {
                drawTutorialString(g, tutorialStringArray[i], 100 + 50 * i);
            }
        }
    }

    /**
     * Draws the tutorial string to the screen centering the text horizontally.
     * 
     * @param g graphics object that is being drawn to
     * @param text text to be added to the graphics object
     * @param y y position of the text on the screen
     */
    void drawTutorialString(Graphics g, String text, int y) {
        Font tutorialFont = new Font(null, Font.BOLD, 24);
        FontMetrics metrics = g.getFontMetrics(tutorialFont);
        g.setFont(tutorialFont);
        g.drawString(text, (windowDimensions.width - metrics.stringWidth(text)) / 2, y);
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
        menuWindow.add(this);
        windowDimensions = Toolkit.getDefaultToolkit().getScreenSize();
        setMaximumSize(windowDimensions);
        menuWindow.setVisible(true);
        background.setSoundEffect(4);
        background.play();
        background.loop();
        menuRunning = true;
    }

    void showTutorial() {
        showTutorial = true;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_UP) {
            board.arrowPosition--;
        } else if (code == KeyEvent.VK_DOWN) {
            board.arrowPosition++;
        }
        board.arrowPosition %= 4;
        if (board.arrowPosition < 0) {
            board.arrowPosition = 3;
        }

        if (code == KeyEvent.VK_SPACE) {
            sound.setSoundEffect(5);
            sound.play();

            if (showTutorial) {
                showTutorial = false;
                return;
            }

            if (board.arrowPosition == 0) {
                background.stop();
                startGame();
            } else if (board.arrowPosition == 1) {
                board.scoreVisible = !board.scoreVisible;
            } else if (board.arrowPosition == 2) {
                board.scoreVisible = false;
                showTutorial();
            } else if (board.arrowPosition == 3) {
                menuWindow.dispose();
                System.exit(0);
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
        menuWindow.remove(this);
        new DisplayGraphics(menuWindow);
    }
}