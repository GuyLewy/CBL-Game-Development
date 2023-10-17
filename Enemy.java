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

    public BufferedImage texture;

    public int lifePointsLeft = ENEMY_LIFE_POINTS;
    public int enemyX = DisplayGraphics.windowDimensions.width;
    public int enemyY;
    int textureIndex;
    

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

    /**
     * Moves enemy to the left based on its movement speed.
     */
    public void moveEnemy() {
        enemyX -= ENEMY_SPEED;
    }

    public void removeLifePoint() {
        lifePointsLeft--;
    }
}
