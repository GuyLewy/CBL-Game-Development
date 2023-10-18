import java.awt.*;
import java.awt.image.BufferedImage;
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

    public BufferedImage texture;

    int lifePointsLeft = 1;
    int enemyX = DisplayGraphics.windowDimensions.width;
    int enemyY;
    int textureIndex;
    int projectileDelayCounter = 0;

    public ProjectilesArrayList enemyProjectiles = new ProjectilesArrayList();

    /**
     * Initialize the enemy with y position and the image.
     */
    Enemy(int yPos) {
        this.lifePointsLeft = 1;
        this.projectileDelay = 200;
        this.enemySpeed = 4;

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
        enemyX -= this.enemySpeed;
    }

    public void removeLifePoint() {
        lifePointsLeft--;
    }

    /**
     * .
     */
    public void moveProjectiles() {
        for (var i = 0; i < enemyProjectiles.projectiles.size(); i++) {
            enemyProjectiles.projectiles.get(i).moveProjectile(-10);
        }
    }

    /**
     * .
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
     * .
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
