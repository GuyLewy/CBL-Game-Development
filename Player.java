import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * Enemy class includes all of the player details such as speed and position as
 * well as the sprite to display.
 */
public class Player implements Drawable {
    public static final int MOVEMENT_SPEED = 5;
    public final int playerWidth = 100;
    public final int playerHeight = 120;
    int playerHealth = 3;

    public int playerShotDelay = 40;
    public int playerY = 100;
    public int playerX = 100;
    public Projectile playerProjectiles;

    public BufferedImage player;

    public Player() {
        getPlayerImage();
    }

    /**
     * .
     */
    public void getPlayerImage() {
        try {
            player = ImageIO.read(getClass().getResourceAsStream("/player/player.png"));
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

    public void loseHealth() {
        playerHealth--;
        if (playerHealth <= 0) {
            DisplayGraphics.endGame();
        }
    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2D = (Graphics2D) g;
        g2D.drawImage(player, null, playerX, playerY);
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