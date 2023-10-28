import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * A wave class used to store a single wave object.
 * @author Antoni Nowaczyk
 * @id 1934899
 * @author Guy Lewy
 * @id 1954962 
 */
public class Wave implements Drawable {
    int waveX;
    int waveY;
    int timeLeft;
    int textureIndex = 0;
    BufferedImage texture;

    /**
     * Create a wave with a given parameters.
     * @param x x position.
     * @param y y position.
     * @param lifeTime time the wave should be visible.
     */
    Wave(int x, int y, int lifeTime) {
        waveX = x;
        waveY = y;
        timeLeft = lifeTime;
    }

    public void draw(Graphics g) {
        g.drawImage(texture, waveX, waveY, null);
    }

}