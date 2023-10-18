import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * .
 */

public class Projectile extends JPanel implements Drawable {
    public static final int RANGE = DisplayGraphics.windowDimensions.width;
    public static final int PROJECTILE_WIDTH = 15;
    public static final int PROJECTILE_HEIGHT = 15;

    BufferedImage bullet;

    int projectileX;
    int projectileY;
    public static final int PROJECTILE_SPEED = 1;
    boolean disappear = false;

    Projectile() {
        getProjectileImage();
    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2D = (Graphics2D) g;
        g2D.drawImage(bullet, null, projectileX, projectileY);
    }

    
    /**
     * Get the projectile image.
     */
    public void getProjectileImage() {
        try {
            bullet = ImageIO.read(getClass()
            .getResourceAsStream("textures/projectiles/Bullet.png"));
        } catch (IOException e) {
            ;
        }
    }

    /**
     * .
     */
    public void moveProjectile(int projectileSpeed) {
        if (projectileX < RANGE && projectileX > 0) {
            projectileX += projectileSpeed;
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
