import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import javax.swing.*;

/**
 * Enemy class used to create new enemies, includes all of their details such as
 * speed and position as well as the sprite to display.
 */
public class Enemy extends JPanel implements Drawable {
    static final int ENEMY_WIDTH = 128;
    static final int ENEMY_HEIGHT = 128;

    int enemySpeed;
    int projectileDelay;

    Random rand = new Random();

    public BufferedImage texture;

    int lifePointsLeft = 1;
    int enemyX = DisplayGraphics.windowDimensions.width;
    int enemyY;
    int textureIndex;
    int moneyCarried;
    int projectileDelayCounter;

    public ProjectilesArrayList enemyProjectiles = new ProjectilesArrayList();

    /**
     * Initialize the enemy with y position and the image.
     */
    Enemy(int yPos) {
        lifePointsLeft = 1;
        projectileDelay = 200;
        enemySpeed = 4;
        moneyCarried = rand.nextInt(3) + 1;
        enemyY = yPos;

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
        enemyX -= this.enemySpeed;
    }

    public void removeLifePoint() {
        lifePointsLeft--;
    }

    /**
     * Iterates across all projectiles in the enemyProjectiles arrayList and moves
     * them all a certain value to the left.
     */
    public void moveProjectiles() {
        for (var i = 0; i < enemyProjectiles.projectiles.size(); i++) {
            enemyProjectiles.projectiles.get(i).moveProjectile(-10);
        }
    }

    /**
     * Iterates the projectileDelayCounter while the counter is smaller than the
     * delay otherwise it adds another projectile to the enemyProjectiles array list
     * at the position of the enemy cannon and resets the counter.
     */
    public void shoot() {
        if (projectileDelayCounter >= projectileDelay) {
            enemyProjectiles.addProjectile(enemyX, enemyY + 50);
            projectileDelayCounter = 0;
        } else {
            projectileDelayCounter++;
        }
    }

    /**
     * Checks wether the enemy is colliding with the player through the use of
     * Axis-Aligned Bound Box collision detection, if colliding it will remove all
     * of the enemy's life points.
     * 
     * @param playerX      The x position of the player as an int
     * @param playerY      The y position of the player as an int
     * @param playerWidth  The width in the x direction of the player as an int
     * @param playerHeight The height in the y direction of the player as an int
     * @return a boolean value of true or fules wether the player collides with the
     *         enemy
     */
    public boolean checkPlayerCollision(int playerX, int playerY,
            int playerWidth, int playerHeight) {
        if (enemyX <= playerX + playerWidth && enemyX + ENEMY_WIDTH >= playerX + ENEMY_HEIGHT
                && enemyY >= playerY && enemyY <= playerY + playerHeight) {
            lifePointsLeft = 0;
            return true;
        } else {
            return false;
        }
    }
}
