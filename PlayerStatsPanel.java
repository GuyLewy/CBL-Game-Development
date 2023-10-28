import java.awt.*;
import java.util.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class PlayerStatsPanel implements Drawable {
    static int squareSize = 20;
    static int panelHeight = 125;
    static int panelWidht = 270;
    static int panelX;
    static int panelY;

    public int speedLevel = 0;
    public int attackLevel = 0;
    public int currentSpeedCost = 10;
    public int currentAttackCost = 20;
    public int currentHealCost = 8;

    ArrayList<LoadingBarSquare> playerSpeedBar = new ArrayList<LoadingBarSquare>();
    ArrayList<LoadingBarSquare> attackSpeedBar = new ArrayList<LoadingBarSquare>();

    BufferedImage statsBar;
    BufferedImage attackIcon;
    BufferedImage speedIcon;

    public PlayerStatsPanel(int maxSpeed, int maxAttackSpeed, int x, int y) {
        panelX = x - panelWidht + DisplayGraphics.blackBorderDimensions.width;
        panelY = y + DisplayGraphics.blackBorderDimensions.height;

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
        int priceTagX = panelX + 160;
        int priceTagY = panelY + 40;

        String speedCost = "";
        String attackCost = "";

        g.drawImage(statsBar, panelX, panelY, null);
        g.drawImage(speedIcon, panelX + 20, panelY + 48, null);
        g.drawImage(attackIcon, panelX + 20, panelY + 78, null);

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

        if (currentSpeedCost == 9999) {
            speedCost = "MAX";
        } else {
            speedCost = "(x) " + Integer.toString(currentSpeedCost) + "$";
        }

        if (currentAttackCost == 9999) {
            attackCost = "MAX";
        } else {
            attackCost = "(z) " + Integer.toString(currentAttackCost) + "$";
        }

        g.setColor(new Color(175, 140, 45));
        g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 25));

        g.drawString("(c) " + Integer.toString(currentHealCost) + "$", priceTagX, priceTagY);
        g.drawString(speedCost, priceTagX, priceTagY + 30);
        g.drawString(attackCost, priceTagX, priceTagY + 60);
    }

    public void updateUpdatesCosts(int movementCost, int attackCost, int healCost) {
        currentAttackCost = attackCost;
        currentSpeedCost = movementCost;
        currentHealCost = healCost;
    }
}
