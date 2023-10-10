import java.awt.*;
import javax.swing.*;

/**
 * Enemy class used to create new enemies, includes all of their details such as
 * speed and position as well as the sprite to display.
 */
public class Enemy extends JPanel implements Drawable {
    public static final int ENEMY_WIDTH = 100;
    public static final int ENEMY_HEIGHT = 100;
    public static final int ENEMY_LIFE_POINTS = 1;
    static final int ENEMY_SPEED = 4;

    public int lifePointsLeft = ENEMY_LIFE_POINTS;
    public int enemyX = DisplayGraphics.windowDimensions.width;
    public int enemyY;
    static final int PROJECTILE_DELAY = 200;
    int projectileDelayCounter = 0;
    public ProjectilesArrayList enemyProjectiles = new ProjectilesArrayList();

    Enemy(int yPos) {
        this.enemyY = yPos;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.red);
        g.fillRect(this.enemyX, this.enemyY, ENEMY_WIDTH, ENEMY_HEIGHT);
    }

    public void drawProjectiles(Graphics g) {
        enemyProjectiles.draw(g);
        System.out.println("drawing enemy projectiles");
    }

    /**
     * Moves enemy to the left based on its movement speed.
     */
    public void moveEnemy() {
        enemyX -= ENEMY_SPEED;
    }

    public void removeLifePoint() {
        lifePointsLeft--;
    }

    public void moveProjectiles() {
        for (var i = 0; i < enemyProjectiles.projectiles.size(); i++) {
            enemyProjectiles.projectiles.get(i).moveProjectile(-10);
        }
    }

    public void shoot() {
        if (projectileDelayCounter >= PROJECTILE_DELAY) {
            enemyProjectiles.addProjectile(enemyX, enemyY + 50);
            projectileDelayCounter = 0;
            System.out.println("enemy shoots");
        } else {
            projectileDelayCounter++;
        }
    }


}
