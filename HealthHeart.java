import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

class HealthHeart implements Drawable {
    //private static int HEART_WIDTH = 32;
    //private static int HEART_HEIGHT = 32;
    private int heartX;
    private int heartY;

    BufferedImage fullHeart;
    BufferedImage halfHeart;
    BufferedImage emptyHeart;

    public int heartState = 2; //2 - full; 1 - half; 0 - empty

    /**
     * Create a heart at coordinates.
     * @param x .
     * @param y .
     */
    public HealthHeart(int x, int y) {
        heartX = x;
        heartY = y;
        getImages();
    }

    /**
     * Read heart textures.
     */
    public void getImages() {
        try {
            fullHeart = ImageIO.read(getClass()
            .getResourceAsStream("textures/heart/fullHeart.png"));
            halfHeart = ImageIO.read(getClass()
            .getResourceAsStream("textures/heart/halfHeart.png"));
            emptyHeart = ImageIO.read(getClass()
            .getResourceAsStream("textures/heart/emptyHeart.png"));
        } catch (IOException e) {
            ;
        }
    }

    /**
     * Draw a heart in correct state.
     */
    public void draw(Graphics g) {
        if (heartState == 2) {
            g.drawImage(fullHeart, heartX, heartY, null);
        } else if (heartState == 1) {
            g.drawImage(halfHeart, heartX, heartY, null);
        } else {
            g.drawImage(emptyHeart, heartX, heartY, null);
        }
    }
}