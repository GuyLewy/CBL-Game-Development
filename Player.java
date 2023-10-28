import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * Enemy class includes all of the player details such as speed and position as
 * well as the sprite to display.
 * 
 * @author Antoni Nowaczyk
 * @id 1934899
 * @author Guy Lewy
 * @id 1954962 
 */
public class Player implements Drawable {
    public static final int BASE_MOVEMENT_SPEED = 3;
    public static final int PLAYER_MAX_HEALTH = 8;
    double screenSizeMultiplier = DisplayGraphics.screenSizeMultiplier;
    public final int playerWidth = (int) (100 * screenSizeMultiplier);
    public final int playerHeight = (int) (128 * screenSizeMultiplier);
    public int speedUpgrades = 0;
    public int fireRateUpgrades = 0;
    public int healthUpgrades = 1;
    public boolean wantsToShoot = false;

    int playerHealth = PLAYER_MAX_HEALTH;
    private int movementSpeed = BASE_MOVEMENT_SPEED;

    public int playerY = (int) (100 + DisplayGraphics.blackBorderDimensions.height
            * screenSizeMultiplier);

    public int playerX = (int) (100 + DisplayGraphics.blackBorderDimensions.width
            * screenSizeMultiplier);

    public int playerShotDelay = 70;
    public int playerDirection = 0;
    public ProjectilesArrayList playerProjectiles = new ProjectilesArrayList(5);

    BufferedImage playerUp;
    BufferedImage playerDown;

    public int barX = DisplayGraphics.windowDimensions.width
            - DisplayGraphics.blackBorderDimensions.width;
    public int barY = 5 + DisplayGraphics.blackBorderDimensions.height;
    public HealthBar playerHealthBar;
    public PlayerShotBar playerBar = new PlayerShotBar();
    public PlayerStatsPanel stats;

    /**
     * Constructor for the player class, creates a new healthbar and stats panel.
     */
    public Player() {
        getPlayerImage();
        stats = new PlayerStatsPanel(4, 4, barX, barY);
        playerHealthBar = new HealthBar(playerHealth, barX - 95, barY);
    }

    /**
     * Loads player textures.
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
        stats.draw(g);
        playerBar.draw(g);
        playerHealthBar.draw(g);

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
                playerBar.updateDelay(playerShotDelay);
                fireRateUpgrades++;
                stats.attackLevel++;
                break;

            case 2:
                movementSpeed++;
                speedUpgrades++;
                stats.speedLevel++;
                break;

            case 3:
                playerHealth += 2;

                if (playerHealth > PLAYER_MAX_HEALTH) {
                    playerHealth = PLAYER_MAX_HEALTH;
                }

                healthUpgrades++;
                break;
        }
    }

    /**
     * Update the player shot delay bar so that it maxes out at the players new
     * delay.
     * 
     * @param playerShotDelayCounter new player shot delay
     */
    public void updatePlayerBars(int playerShotDelayCounter) {
        playerBar.updateBar(playerShotDelayCounter);
        playerHealthBar.updateHealtBar(playerHealth);
    }
}