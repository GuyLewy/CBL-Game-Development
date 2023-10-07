import java.awt.*;
import javax.swing.*;

/**
 * .
 */
public class Player extends JPanel implements Drawable {
    public static final int MOVEMENT_SPEED = 2;

    public int playerY = 100;
    public Projectile playerProjectiles;

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.black);
        g.fillRect(100, playerY, 100, 100);
    }

    /**
     * .
     */
    public void move(boolean upPressed, boolean downPressed) {
        if (upPressed && playerY > 0) {
            playerY -= MOVEMENT_SPEED;
        }
        if (downPressed && playerY + 135 < DisplayGraphics.windowDimensions.height) {
            playerY += MOVEMENT_SPEED;
        }
    }
}