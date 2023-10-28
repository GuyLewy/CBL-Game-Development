import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * A Wallet class to store money in.
 * 
 * @author Guy Lewy
 * @author Antoni Nowaczyk
 */
public class Wallet implements Drawable {
    BufferedImage boardTexture;
    Font f2 = new Font(Font.SANS_SERIF, Font.BOLD, 30);
    public int money;
    public int walletX = DisplayGraphics.windowDimensions.width
         - DisplayGraphics.blackBorderDimensions.width - 500;
    public int walletY = DisplayGraphics.blackBorderDimensions.height + 5;

    public Wallet() {
        money = 200;

        try { 
            boardTexture = ImageIO.read(getClass().getResourceAsStream(
                "/textures/menu/scoreBoard.png"));
        } catch (IOException e) {
            ;
        }
    }

    /**
     * Drawing the money counter.
     */
    public void draw(Graphics g) {
        g.setColor(new Color(175, 140, 45));
        g.setFont(f2);
        g.drawImage(boardTexture, walletX, walletY, null);
        g.drawString("Money: %d".formatted(money), walletX + 20, walletY + 90);
    }
}
