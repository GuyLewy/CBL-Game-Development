import java.awt.*;
import java.util.*;

/**
 * .
 */
public class ProjectilesArrayList implements Drawable {
    public ArrayList<Projectile> projectiles = new ArrayList<Projectile>();

    public int projectileX;
    public int projectileY;

    /**
     * .
     */
    public void addProjectile() {
        Projectile p = new Projectile();
        p.givePosition(projectileX, projectileY);
        projectiles.add(p);
    }

    /**
     * .
     */
    public void setPosition(int x, int y) {
        projectileX = x;
        projectileY = y;
    }

    /**
     * .
     */
    public void moveProjectiles() {
        for (int i = 0; i < projectiles.size(); i++) {
            Projectile next = projectiles.get(i);
            next.moveProjectile();
        }
    }

    /**.
     * 
     */
    public boolean areBulletsHitting(int x, int y, int enemyHeight) {
        for (int i = 0; i < projectiles.size(); i++) {
            Projectile next = projectiles.get(i);
            if (x - next.projectileX <= 0 &&  next.projectileY - y >= 0
                && y + enemyHeight - next.projectileY >= 0) {
                next.disappear = true;
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
