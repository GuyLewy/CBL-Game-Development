import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

/**
 * Enemy class includes all of the player details such as speed and position as
 * well as the sprite to display.
 */
public class Player implements Drawable {
    public static final int MOVEMENT_SPEED = 5;
    public final int playerWidth = 128;
    public final int playerHeight = 128;
    int playerHealth = 3;

    public int playerShotDelay = 60;
    public int playerY = 100;
    public int playerX = 100;
    public int playerDirection = 0;
    public Projectile playerProjectiles;

    BufferedImage playerUp;
    BufferedImage playerDown;

    public Player() {
        getPlayerImage();
    }

    /**
     * .
     */
    public void getPlayerImage() {
        try {
            playerDown = ImageIO.read(getClass()
            .getResourceAsStream("textures/player/player2.png"));
            playerUp = ImageIO.read(getClass()
            .getResourceAsStream("textures/player/player1.png"));
        } catch (IOException e) {
            ;
        }
    }

    /**
     * .
     * 
     * @param enemiesArrayList .
     */
    public void checkProjectiles(EnemiesArrayList enemiesArrayList) {
        ArrayList<Enemy> enemies = enemiesArrayList.enemies;
        for (int i = 0; i < enemies.size(); i++) {
            ProjectilesArrayList nextProjectileList = enemies.get(i).enemyProjectiles;
            if (nextProjectileList.areBulletsHitting(playerX, playerY, playerWidth, playerHeight)) {
                loseHealth();
            }

        }

    }

    /**
     *.
     */
    public void loseHealth() {
        playerHealth--;
        if (playerHealth <= 0) {
            DisplayGraphics.endGame();
        }
    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2D = (Graphics2D) g;
        if (playerDirection == 1) {
            g2D.drawImage(playerUp, null, playerX, playerY);
        } else {
            g2D.drawImage(playerDown, null, playerX, playerY);
        }
    }

    /**
     * A method to move the player up and down as long as they are not at the edge
     * of the screen.
     * 
     * @param upPressed   a boolean stating whether the up arrow is pressed
     * @param downPressed a boolean stating whether the down arrow is pressed
     */
    public void move(boolean upPressed, boolean downPressed) {
        if (upPressed) {
            playerDirection = 1;
            if (playerY > 0) {
                playerY -= MOVEMENT_SPEED;
            }
        }
        if (downPressed) {
            playerDirection = 0;
            if (playerY + 2 * playerHeight < DisplayGraphics
                .windowDimensions.height) {
                playerY += MOVEMENT_SPEED;
            }
        } 
        
    }
}