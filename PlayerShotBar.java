import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * PlayerShotBar class used to create a progression bar presenting the time left
 * to another shot.
 * 
 * @author Antoni Nowaczyk
 * @id 1934899
 * @author Guy Lewy
 * @id 1954962 
 */
public class PlayerShotBar extends JPanel implements Drawable {
    private int timePassed;

    LoadingBarSquare[] squares = new LoadingBarSquare[9];
    Color color = new Color(150, 0, 0);
    /*Color[] defaultColors = { new Color(184, 20, 20), new Color(184, 60, 20),
        new Color(184, 100, 20), new Color(184, 140, 20), new Color(180, 180, 20),
        new Color(140, 180, 20), new Color(100, 180, 20), new Color(60, 180, 20),
        new Color(20, 180, 20) }; */

    public int barX = DisplayGraphics.windowDimensions.width
        - DisplayGraphics.blackBorderDimensions.width - 270;
    public int barY = DisplayGraphics.blackBorderDimensions.height + 120;

    BufferedImage barTexture;

    /**
     * Create a shot bar and get a texture.
     */
    public PlayerShotBar() {
        try { 
            barTexture = ImageIO.read(getClass().getResourceAsStream(
                "/textures/menu/shotBar.png"));
        } catch (IOException e) {
            ;
        }
    }
                
    /**
     * Initializes the bar, creates an array of squares.
     */
    public void playerBarSetup(int delay) {
        for (int i = 0; i < 9; i++) {
            LoadingBarSquare nextSquare = new LoadingBarSquare();
            nextSquare.width = 20;
            nextSquare.height = 20;
            nextSquare.xPosition = barX + 17 + (25 * i);
            nextSquare.yPosition = barY + 15;
            nextSquare.time = (int) (delay / 10 * (i + 2));

            squares[i] = nextSquare;
        }
    }

    public void updateBar(int time) {
        timePassed = time;
    }

    /**
     * Updates the shot delay after the player stat upgrade.
     * @param delay new delay time.
     */
    public void updateDelay(int delay) {
        for (int i = 0; i < 9; i++) {
            squares[i].time = (int) (delay) / 10 * (i + 2);
        }
    }

    /**
     * Draws the progress bar with proper colors for each square.
     */
    public void draw(Graphics g) {
        g.drawImage(barTexture, barX, barY, null);
        for (int i = 0; i < 9; i++) {
            LoadingBarSquare next = squares[i];

            if (timePassed >= next.time) {
                next.color = color;
            } else {
                next.color = Color.black;
            }
            next.draw(g);
        }
    }
}
