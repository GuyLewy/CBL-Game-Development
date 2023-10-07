import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * .
 */
public class Enemy extends JPanel implements ActionListener, Drawable {

    public int enemyX = DisplayGraphics.windowDimensions.width;
    public int enemyY = 0;

    Timer t = new Timer(5, this);

    public Enemy() {
        t.start();
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.red);
        g.fillRect(enemyX, enemyY, 100, 100);
    }

    public void actionPerformed(ActionEvent e) {
        enemyX -= Player.MOVEMENT_SPEED;
    }

    // public void paint(Graphics g) {
    // super.paintComponent(g);
    // g.setColor(Color.red);
    // g.fillRect(enemyX, enemyY, 100, 100);
    // }
}
