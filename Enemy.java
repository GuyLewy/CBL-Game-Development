import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;

/**
 * Enemy class used to create new enemies, includes all of their details such as
 * speed and position as well as the sprite to display.
 */
public class Enemy extends JPanel implements Drawable {
    public static final int ENEMY_WIDTH = 128;
    public static final int ENEMY_HEIGHT = 128;
    public static final int ENEMY_LIFE_POINTS = 1;
    static final int ENEMY_SPEED = 4;
    static final int PROJECTILE_DELAY = 200;

    public BufferedImage texture;

    public int lifePointsLeft = ENEMY_LIFE_POINTS;
    public int enemyX = DisplayGraphics.windowDimensions.width;
    public int enemyY;
    int textureIndex;
    int projectileDelayCounter = 0;
  
    public ProjectilesArrayList enemyProjectiles = new ProjectilesArrayList();

    /**
     * Initialize the enemy with y position and the image.
     */
    Enemy(int yPos) {
        this.enemyY = yPos;
    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2D = (Graphics2D) g;
        g2D.drawImage(texture, null, enemyX, enemyY);
    }

    public void drawProjectiles(Graphics g) {
        enemyProjectiles.draw(g);
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
        } else {
            projectileDelayCounter++;
        }
    }

    public boolean checkPlayerCollision(int playerX, int playerY, int playerWidth, int playerHeight) {
        if (enemyX <= playerX + playerWidth && enemyX + ENEMY_WIDTH >= playerX + ENEMY_HEIGHT
                && enemyY >= playerY && enemyY <= playerY + playerHeight) {
            lifePointsLeft = 0;
            return true;
        } else {
            return false;
        }
    }
}
