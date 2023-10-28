import java.awt.*;
import java.util.*;

/**
 * ProjectilesArrayList, used to update projectiles and create new projectiles.
 * 
 * @author Antoni Nowaczyk
 * @id 1934899
 * @author Guy Lewy
 * @id 1954962 
 */
public class ProjectilesArrayList implements Drawable {
    public ArrayList<Projectile> projectiles = new ArrayList<Projectile>();

    int projectileSpeed;

    public boolean bulletInTarget = false;

    public ProjectilesArrayList(int speed) {
        projectileSpeed = speed;
    }

    /**
     * Add a new enemy at a given position.
     */
    public void addProjectile(int projectileX, int projectileY) {
        Projectile p = new Projectile();
        p.givePosition(projectileX, projectileY);
        projectiles.add(p);
    }

    /**
     * Move every projectile. Method called every update.
     */
    public void moveProjectiles() {
        for (int i = 0; i < projectiles.size(); i++) {
            Projectile next = projectiles.get(i);
            next.moveProjectile(projectileSpeed);
        }
    }

    /**
     * A method checks, if any of the prejectile hits a given target.
     * 
     * @param x x position of a target.
     * @param y y position of a target.
     * @param targetWidth width of a target.
     * @param targetHeight height of a target.
     * @return returns true if a target is hit.
     */
    public boolean areBulletsHitting(int x, int y, int targetWidth, int targetHeight) {
        for (int i = 0; i < projectiles.size(); i++) {
            Projectile next = projectiles.get(i);
            if ((x - next.projectileX) * (x - next.projectileX + targetWidth) < 0 
                && next.projectileY > y && next.projectileY < y + targetHeight) {
                next.disappear = true;
                bulletInTarget = true;
                return true;
            }
        }
        return false;
    }

    /**
     * Draw every projectile.
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
