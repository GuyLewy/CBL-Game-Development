import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * .
 */
public class DisplayGraphics extends JPanel implements KeyListener {

    public static Rectangle windowDimensions;
    private Player player = new Player();
    private Enemy enemy = new Enemy();
    private ProjectilesArrayList projectiles = new ProjectilesArrayList();
    boolean upPressed = false;
    boolean downPressed = false;
    boolean blockNextShot = false;

    /**
     * .
     */
    public DisplayGraphics() {
        new Timer(5, new TimerListener()).start();
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
    }

    /**
     * .
     */
    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_UP) {
            upPressed = true;
        } else if (code == KeyEvent.VK_DOWN) {
            downPressed = true;
        } else if (code == KeyEvent.VK_SPACE && !blockNextShot) {
            projectiles.setPosition(100 + 50, player.playerY + 50);
            projectiles.addProjectile();
            blockNextShot = true;
        }
    }

    /**
     * .
     */
    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_UP) {
            upPressed = false;
        } else if (code == KeyEvent.VK_DOWN) {
            downPressed = false;
        } else if (code == KeyEvent.VK_SPACE) {
            blockNextShot = false;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        player.draw(g);
        enemy.draw(g);
        projectiles.draw(g);
    }

    private class TimerListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            player.move(upPressed, downPressed);
            projectiles.moveProjectiles();
            repaint();
        }
    }

    public static void main(String[] args) {
        JFrame mainWindow = new JFrame();
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainWindow.setSize(400, 400);
        windowDimensions = mainWindow.getBounds();
        DisplayGraphics graphics = new DisplayGraphics();
        mainWindow.add(graphics);
        mainWindow.setVisible(true);
    }
}