import java.awt.*;
import javax.swing.*;

/**
 * Enemy class used to create new enemies, includes all of their details such as
 * speed and position as well as the sprite to display.
 */
public class Enemy extends JPanel implements Drawable {

    static final int ENEMY_SPEED = 5;
    public int enemyX = DisplayGraphics.windowDimensions.width;
    public int enemyY;

    Enemy(int yPos) {
        this.enemyY = yPos;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.red);
        g.fillRect(this.enemyX, this.enemyY, 100, 100);
    }

    /**
     * Moves enemy to the left based on its movement speed.
     */
    public void moveEnemy() {
        enemyX -= ENEMY_SPEED;
    }
}
