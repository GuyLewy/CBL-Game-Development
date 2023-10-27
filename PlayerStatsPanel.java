import java.awt.*;
import java.util.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class PlayerStatsPanel implements Drawable {
    static int squareSize = 20;
    static int panelHeight = 125;
    static int panelWidht = 175;
    static int panelX;
    static int panelY;

    public int speedLevel = 0;
    public int attackLevel = 0;

    ArrayList<LoadingBarSquare> playerSpeedBar = new ArrayList<LoadingBarSquare>();
    ArrayList<LoadingBarSquare> attackSpeedBar = new ArrayList<LoadingBarSquare>();

    BufferedImage statsBar;
    BufferedImage attackIcon;
    BufferedImage speedIcon;

    public PlayerStatsPanel(int maxSpeed, int maxAttackSpeed, int x, int y) {
        panelX = x - panelWidht;
        panelY = y;

        try {
            statsBar = ImageIO.read(getClass().getResourceAsStream("/textures/menu/statsBar.png"));
            attackIcon = ImageIO.read(getClass().getResourceAsStream(
                    "/textures/menu/attackSpeed.png"));
            speedIcon = ImageIO.read(getClass().getResourceAsStream(
                    "/textures/menu/movementSpeed.png"));
        } catch (IOException e) {
            ;
        }

        for (int i = 0; i < maxSpeed; i++) {
            LoadingBarSquare next = new LoadingBarSquare();
            next.height = squareSize;
            next.width = squareSize;
            next.color = Color.black;
            next.yPosition = panelY + 50;
            next.xPosition = panelX + 60 + (squareSize + 5) * i;
            playerSpeedBar.add(next);
        }

        for (int i = 0; i < maxAttackSpeed; i++) {
            LoadingBarSquare next = new LoadingBarSquare();
            next.height = squareSize;
            next.width = squareSize;
            next.color = Color.black;
            next.yPosition = panelY + 80;
            next.xPosition = panelX + 60 + (squareSize + 5) * i;
            attackSpeedBar.add(next);
        }
    }

    public void draw(Graphics g) {
        g.drawImage(statsBar, panelX + DisplayGraphics.blackBorderDimensions.width,
                panelY + DisplayGraphics.blackBorderDimensions.height, null);
        g.drawImage(speedIcon, panelX + 20 + DisplayGraphics.blackBorderDimensions.width,
                panelY + 48 + DisplayGraphics.blackBorderDimensions.height, null);
        g.drawImage(attackIcon, panelX + 20 + DisplayGraphics.blackBorderDimensions.width,
                panelY + 78 + DisplayGraphics.blackBorderDimensions.height, null);

        for (int i = 0; i < playerSpeedBar.size(); i++) {
            if (speedLevel - i > 0) {
                playerSpeedBar.get(i).color = new Color(0, 117, 214);
            }
            playerSpeedBar.get(i).draw(g);
        }

        for (int i = 0; i < attackSpeedBar.size(); i++) {
            if (attackLevel - i > 0) {
                attackSpeedBar.get(i).color = new Color(223, 200, 0);
            }
            attackSpeedBar.get(i).draw(g);
        }
    }
}
