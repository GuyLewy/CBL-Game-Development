import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * Projectile class includes all of the projectile details such as speed and
 * postion and handles drawing projectiles with an interface.
 * 
 * @author Antoni Nowaczyk
 * @id 1934899
 * @author Guy Lewy
 * @id 1954962 
 */
public class Projectile extends JPanel implements Drawable {
    public static final int RANGE = DisplayGraphics.windowDimensions.width;
    public static final int PROJECTILE_WIDTH = 15;
    public static final int PROJECTILE_HEIGHT = 15;

    BufferedImage bullet;

    int projectileX;
    int projectileY;
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
     * Moves all the projectiles. A method called every game update.
     */
    public void moveProjectile(int projectileSpeed) {
        if (projectileX < RANGE && projectileX > 0) {
            projectileX += projectileSpeed;
        } else {
            disappear = true;
        }
    }

    /**
     * Assigns a position to the projectile.
     * @param x x position.
     * @param y y position.
     */
    public void givePosition(int x, int y) {
        projectileX = x;
        projectileY = y;
    }
}
