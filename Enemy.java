import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import javax.swing.*;

/**
 * Enemy class used to create new enemies, includes all of their details such as
 * speed and position as well as the sprite to display.
 */
public class Enemy extends JPanel implements Drawable {
    public int enemyWidth = 128;
    public int enemyHeight = 128;

    int enemySpeed;
    int projectileDelay;

    Random rand = new Random();

    public BufferedImage texture;
    public int enemyType = 1;
    int lifePointsLeft = 1;
    int enemyX = DisplayGraphics.windowDimensions.width;
    int enemyY;
    int textureIndex;
    int moneyCarried;
    int projectileDelayCounter;
    int projectileSpeed = -7;
    boolean doesShoot = true;

    //public ProjectilesArrayList enemyProjectiles = new ProjectilesArrayList();


    /**
     * Initialize the enemy with y position and the image.
     */
    Enemy(int yPos) {
        lifePointsLeft = 1;
        projectileDelay = 200;
        enemySpeed = 3;
        moneyCarried = rand.nextInt(3) + 1; // Set bound to the max value that should be given
        enemyY = yPos;

    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2D = (Graphics2D) g;
        g2D.drawImage(texture, null, enemyX, enemyY);
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
        if (enemyX <= playerX + playerWidth && enemyX + enemyWidth >= playerX + enemyHeight
                && enemyY >= playerY && enemyY <= playerY + playerHeight) {
            lifePointsLeft = 0;
            return true;
        } else {
            return false;
        }
    }
}
