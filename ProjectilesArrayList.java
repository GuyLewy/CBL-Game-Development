import java.awt.*;
import java.util.*;

/**
 * .
 */
public class ProjectilesArrayList implements Drawable {
    public ArrayList<Projectile> projectiles = new ArrayList<Projectile>();

    int projectileSpeed;

    public boolean bulletInTarget = false;

    public ProjectilesArrayList(int speed) {
        projectileSpeed = speed;
    }

    /**
     * .
     */
    public void addProjectile(int projectileX, int projectileY) {
        Projectile p = new Projectile();
        p.givePosition(projectileX, projectileY);
        projectiles.add(p);
    }

    /**
     * .
     */
    public void moveProjectiles() {
        for (int i = 0; i < projectiles.size(); i++) {
            Projectile next = projectiles.get(i);
            next.moveProjectile(projectileSpeed);
        }
    }

    /**
     * .
     * @param x .
     * @param y .
     * @param targetWidth .
     * @param targetHeight .
     * @return .
     */
    public boolean areBulletsHitting(int x, int y, int targetWidth, int targetHeight) {
        for (int i = 0; i < projectiles.size(); i++) {
            Projectile next = projectiles.get(i);
            if (next.projectileX < x + targetWidth && next.projectileX > x && next.projectileY > y
                    && next.projectileY < y + targetHeight) {
                next.disappear = true;
                bulletInTarget = true;
                return true;
            }
        }
        return false;
    }

    /**
     * .
     */
    public void draw(Graphics g) {
        for (int i = 0; i < projectiles.size(); i++) {
            Projectile next = projectiles.get(i);
            if (next.disappear) {
                projectiles.remove(i);
            } else {
                next.draw(g);
            }
        }
    }
}
