import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

/**
 * Creates a frame with a button that can be pressed to start an instance of the
 * game then closses the frame.
 */
public class MainMenu extends JPanel implements KeyListener {
    private boolean menuRunning;
    private JFrame menuWindow = new JFrame();
    private MainMenuBoard board = new MainMenuBoard();

    public static Rectangle windowDimensions;

    int menuBounceDelay = 73;
    int menuBounceCounter = 17;

    private final int UPS = 120; // Updates per second
    private final int FPS = 60; // Frames per second

    Sound sound = new Sound();

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
        sound.setSoundEffect(4);
        sound.play();
        sound.loop();
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
        board.arrowPosition %= 4;
        if (board.arrowPosition < 0) {
            board.arrowPosition = 3;
        }

        if (code == KeyEvent.VK_SPACE) {
            sound.setSoundEffect(5);
            sound.play();
        }
    }

    public void keyReleased(KeyEvent e) {
        ;
    }

    public void keyTyped(KeyEvent e) {
        ;
    }
    /**
     * Checks if start game button is pressed and if it is then the method will
     * create a new instance of the game and close the main menu.
     */
    /*private class ButtonClickListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == startGameButton) {
                sound.stop();
                sound.setSoundEffect(5);
                sound.play();
                new DisplayGraphics();
                mainWindow.dispose();
            }
        }
    } */
}