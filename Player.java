import java.awt.*;
import javax.swing.*;

/**
 * Enemy class includes all of the player details such as speed and position as
 * well as the sprite to display.
 */
public class Player extends JPanel implements Drawable {
    public static final int MOVEMENT_SPEED = 5;
    public final int playerWidth = 100;
    public final int playerHeight = 100;

    public int playerShotDelay = 35;
    public int playerY = 100;
    public int playerX = 100;
    public Projectile playerProjectiles;

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.black);
        g.fillRect(playerX, playerY, playerWidth, playerHeight);
    }

    /**
     * A method to move the player up and down as long as they are not at the edge
     * of the screen.
     * 
     * @param upPressed   a boolean stating whether the up arrow is pressed
     * @param downPressed a boolean stating whether the down arrow is pressed
     */
    public void move(boolean upPressed, boolean downPressed) {
        if (upPressed && playerY > 0) {
            playerY -= MOVEMENT_SPEED;
        }
        if (downPressed && playerY + 135 < DisplayGraphics.windowDimensions.height) {
            playerY += MOVEMENT_SPEED;
        }
    }
}