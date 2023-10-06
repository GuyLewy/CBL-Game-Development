import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*;

/**
 * .
 */
public class Player extends JPanel implements KeyListener, ActionListener {
    public static final int MOVEMENT_SPEED = 1;

    int playerY = 100;
    boolean upPressed = false;
    boolean downPressed = false;

    Timer t = new Timer(5, this);

    /**
     * .
     */
    public Player() {
        t.start();
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
    }

    /**
     * .
     */
    public void actionPerformed(ActionEvent e) {
        repaint();
        if (upPressed && playerY > 0) {
            playerY -= MOVEMENT_SPEED;
        }
        if (downPressed && playerY + 135 < DisplayGraphics.windowHeight) {
            playerY += MOVEMENT_SPEED;
        }
    }

    /**
     * .
     */
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_UP) {
            upPressed = true;
        } else if (code == KeyEvent.VK_DOWN) {
            downPressed = true;
        }
    }

    /**
     * .
     */
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_UP) {
            upPressed = false;
        } else if (code == KeyEvent.VK_DOWN) {
            downPressed = false;
        }
    }

    /**
     * .
     */

    public void paint(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.black);
        g.fillRect(100, playerY, 100, 100);
    }

    public void keyTyped(KeyEvent e) {
    }
}