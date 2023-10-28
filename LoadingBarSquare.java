import java.awt.*;
import javax.swing.*;

/**
 * LoadingBarSquare class used to draw a single square inside the loading bar.
 * @author Antoni Nowaczyk
 * @id 1934899
 * @author Guy Lewy
 * @id 1954962 
 */
public class LoadingBarSquare extends JPanel implements Drawable {
    int width;
    int height;
    int xPosition;
    int yPosition;
    int time;
    Color color;

    /**
     * Draws a square.
     */
    public void draw(Graphics g) {
        g.setColor(color);
        g.fillRect(xPosition, yPosition, width, height);
    }
}
