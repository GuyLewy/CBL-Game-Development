import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;

/**
 * Creates a frame with a button that can be pressed to start an instance of the
 * game then closses the frame.
 */
public class MainMenu extends JPanel implements KeyListener {
    private boolean menuRunning;
    JFrame menuWindow = new JFrame();
    public MainMenuBoard board = new MainMenuBoard();

    public static Rectangle windowDimensions;

    int menuBounceDelay = 74;
    int menuBounceCounter = 1;

    public int highScore;

    private final int UPS = 120; // Updates per second
    private final int FPS = 60; // Frames per second

    Sound sound = new Sound();
    Sound background = new Sound();

    /**
     * Sets up the frame to have a button and add an action listener to the button.
     */
    public MainMenu() {
        setMenu();
        addKeyListener(this);
        setFocusable(true);
        requestFocus();
        setFocusTraversalKeysEnabled(false);
        Thread menuLoopThread = new Thread(this::startMenuLoop);
        menuLoopThread.start();
    }

    public void startMenuLoop() {
        long initialTime = System.nanoTime();
        long secondCounter = 0;
        final double timeUPS = 1000000000 / UPS;
        final double timeFPS = 1000000000 / FPS;
        double deltaUPS = 0;
        double deltaFPS = 0;
        long timer = System.currentTimeMillis();

        while (menuRunning) {
            long currentTime = System.nanoTime();

            deltaUPS += (currentTime - initialTime) / timeUPS;
            deltaFPS += (currentTime - initialTime) / timeFPS;
            secondCounter +=  (currentTime - initialTime);
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
        board.draw(g);
    }

    public void updateMenu() {
        if (menuBounceCounter >= menuBounceDelay) {
            menuBounceCounter = 0;
            board.bounce();
        } else {
            menuBounceCounter++;
        }
    }

    public void setMenu() {
        menuWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menuWindow.setSize(2000, 1000);
        menuWindow.add(this);
        windowDimensions = menuWindow.getBounds();
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

    public void keyReleased(KeyEvent e) {
        ;
    }

    public void keyTyped(KeyEvent e) {
        ;
    }

    public void startGame() {
        new DisplayGraphics();
        menuWindow.dispose();
    }
}