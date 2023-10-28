import java.awt.*;

/**
 * Class that draws the dock onto the screen given the parameters of the
 * height of the dock.
 * 
 * @author Antoni Nowaczyk
 * @id 1934899
 * @author Guy Lewy
 * @id 1954962
 */
public class Dock implements Drawable {
    int dockHeight;

    Dock(int height) {
        dockHeight = height - 2 * DisplayGraphics.blackBorderDimensions.height;
    }

    /**
     * Draws the dock lines onto the dock, purely visual.
     * 
     * @param g        graphics object that is drawn onto
     * @param numLines number of lines to draw
     */
    private void drawDockLines(Graphics g, int numLines) {
        for (int i = 1; i <= numLines; i++) {
            g.setColor(Color.black);
            g.fillRect(0, dockHeight * i / numLines + 50 
                + DisplayGraphics.blackBorderDimensions.height, 
                (int) (100 * DisplayGraphics.screenSizeMultiplier), 3);
        }
    }

    /**
     * Draws the dock posts onto the dock, purely visual.
     * 
     * @param g        graphics object that is drawn onto
     * @param numPosts number of posts to draw
     */
    private void drawDockPosts(Graphics g, int numPosts) {
        for (int i = 1; i <= numPosts; i++) {
            g.setColor(new Color(79, 44, 28));
            g.fillOval((int) (90 * DisplayGraphics.screenSizeMultiplier),
                    (((dockHeight - 20) * (i - 1) / numPosts) - 10 + 50
                            + DisplayGraphics.blackBorderDimensions.height),
                    (int) (20 * DisplayGraphics.screenSizeMultiplier),
                    (int) (20 * DisplayGraphics.screenSizeMultiplier));
        }
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(new Color(107, 58, 41));
        g.fillRect(0, 50 + DisplayGraphics.blackBorderDimensions.height,
                (int) (100 * DisplayGraphics.screenSizeMultiplier), dockHeight - 50);

        drawDockLines(g, 30);
        drawDockPosts(g, 12);

    }
}
