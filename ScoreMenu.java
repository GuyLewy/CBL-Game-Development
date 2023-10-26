import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * Generates the score menu and displays it.
 * 
 * @author Guy Lewy
 * @author Antoni Nowaczyk
 */
public class ScoreMenu implements Drawable {
    public int menuX;
    public int menuY;

    public BufferedImage texture;

    /**
     * Constructor for the score menu that gets the image from a png file.
     * 
     * @param x x position of the menu opn the screen
     * @param y y position of the menu on the screen
     */
    public ScoreMenu(int x, int y) {
        menuX = x;
        menuY = y;

        try {
            texture = ImageIO.read(getClass().getResourceAsStream(
                    "textures/menu/scoreMenu.png"));
        } catch (IOException e) {
            ;
        }
    }

    public void draw(Graphics g) {
        g.drawImage(texture, menuX, menuY, null);
    }

}
