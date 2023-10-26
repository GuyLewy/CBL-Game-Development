import java.awt.*;
import javax.swing.*;

/**
 * LoadingBarSquare class used to draw a single square inside the loading bar.
 */
public class LoadingBarSquare extends JPanel implements Drawable {
    int width;
    int height;
    int xPosition;
    int yPosition;
    int time;
    Color color;

    /**
     * .
     */
    public void draw(Graphics g) {
        g.setColor(color);
        g.fillRect(xPosition, yPosition + DisplayGraphics.blackBorderDimensions.height, width, height);
    }
}
