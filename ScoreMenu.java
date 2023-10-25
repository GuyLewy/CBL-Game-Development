import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ScoreMenu implements Drawable {
    public int menuX;
    public int menuY;

    public BufferedImage texture;

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
