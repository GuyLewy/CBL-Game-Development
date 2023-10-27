import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * Enemy class includes all of the player details such as speed and position as
 * well as the sprite to display.
 */
public class Player implements Drawable {
    public static final int BASE_MOVEMENT_SPEED = 5;
    public static final int PLAYER_MAX_HEALTH = 6;
    double screenSizeMultiplier = DisplayGraphics.screenSizeMultiplier;
    public final int playerWidth = (int) (100 * screenSizeMultiplier);
    public final int playerHeight = (int) (128 * screenSizeMultiplier);;
    public int speedUpgrades;
    public int fireRateUpgrades;
    public int healthUpgrades = 1;

    int playerHealth = PLAYER_MAX_HEALTH;
    private int movementSpeed = BASE_MOVEMENT_SPEED;

    public int playerShotDelay = 60;
    public int playerY = (int) (100 + DisplayGraphics.blackBorderDimensions.height * screenSizeMultiplier);
    public int playerX = (int) (100 + DisplayGraphics.blackBorderDimensions.width * screenSizeMultiplier);
    public int playerDirection = 0;
    public ProjectilesArrayList playerProjectiles = new ProjectilesArrayList(5);

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

    @Override
    public void draw(Graphics g) {
        Graphics2D g2D = (Graphics2D) g;
        if (playerDirection == 1) {
            g2D.drawImage(playerUp, null, playerX, playerY);
        } else {
            g2D.drawImage(playerDown, null, playerX, playerY);
        }
        playerProjectiles.draw(g);
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
            if (playerY > 20 + DisplayGraphics.blackBorderDimensions.height) {
                playerY -= (int) (screenSizeMultiplier * movementSpeed);
            }
        }
        if (downPressed) {
            playerDirection = 0;
            if (playerY + playerHeight < DisplayGraphics.windowDimensions.height
                    - DisplayGraphics.blackBorderDimensions.height) {
                playerY += (int) (screenSizeMultiplier * movementSpeed);
            }
        }
    }

    /**
     * Method used to upgrade the abilities of the player depending on the requested
     * ability. Note this method does not check if user has enough money nor does it
     * remove the money from the user's wallet, this must be done seperately when
     * calling this method.
     * 
     * @param stat integer that determines what stat is being upgraded
     */
    public void upgradeStat(int stat) {

        switch (stat) {
            default:
                break;

            case 1:
                this.playerShotDelay -= 10;
                fireRateUpgrades++;
                break;

            case 2:
                this.movementSpeed++;
                speedUpgrades++;
                break;

            case 3:
                this.playerHealth += 2;

                if (playerHealth > PLAYER_MAX_HEALTH) {
                    playerHealth = PLAYER_MAX_HEALTH;
                }

                healthUpgrades++;
                break;

        }

    }
}