import java.awt.*;
import javax.swing.*;

/**
 * .
 */

public class Projectile extends JPanel implements Drawable {
    public static final int RANGE = DisplayGraphics.windowDimensions.width;
    public static final int PROJECTILE_WIDTH = 10;
    public static final int PROJECTILE_HEIGHT = 10;

    int projectileX;
    int projectileY;
    public static final int PROJECTILE_SPEED = 5;
    boolean disappear = false;

    @Override
    public void draw(Graphics g) {
        g.setColor(new Color(77, 46, 8));
        g.fillRect(projectileX, projectileY, PROJECTILE_WIDTH, PROJECTILE_HEIGHT);
        
    }

    /**
     * .
     */
    public void moveProjectile() {
        if (projectileX < RANGE) {
            projectileX += PROJECTILE_SPEED;
        } else {
            disappear = true;
        }
    }

    /**
     * .
     */
    public void givePosition(int x, int y) {
        projectileX = x;
        projectileY = y;
    }
}
