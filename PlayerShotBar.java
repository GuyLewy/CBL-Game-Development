import java.awt.*;
import javax.swing.*;

/**
 * PlayerShotBar class used to create a progression bar presenting the time left to another shot.
 */
public class PlayerShotBar extends JPanel implements Drawable {
    private int timePassed;

    LoadingBarSquare[] squares = new LoadingBarSquare[9];
    Color[] defaultColors = {new Color(184, 20, 20), new Color(184, 60, 20),
        new Color(184, 100, 20), new Color(184, 140, 20), new Color(180, 180, 20),
        new Color(140, 180, 20), new Color(100, 180, 20), new Color(60, 180, 20), 
        new Color(20, 180, 20)};

    /**
     * Initializes the bar, creates an array of squares.
     */
    public void playerBarSetup(int delay) {
        for (int i = 0; i < 9; i++) {
            LoadingBarSquare nextSquare = new LoadingBarSquare();
            nextSquare.width = 20;
            nextSquare.height = 20;
            nextSquare.xPosition = (int) 
                (0.645 * DisplayGraphics.windowDimensions.getWidth()
                + 25 * i);
            nextSquare.yPosition = 15;
            nextSquare.time = (int) (delay / 10 * (i + 2));

            squares[i] = nextSquare;
        }
    }


    public void updateBar(int time) {
        timePassed = time;
    }

    /**
     * Draws the progress bar with proper colors for each square.
     */
    public void draw(Graphics g) {
        g.setColor(new Color(90, 90, 90));
        g.fillRect((int) (0.64 * DisplayGraphics.windowDimensions.getWidth()),
            10, 240, 30);
        for (int i = 0; i < 9; i++) {
            LoadingBarSquare next = squares[i];

            if (timePassed >= next.time) {
                next.color = defaultColors[i];
            } else {
                next.color = Color.black;
            }
            next.draw(g);
        }
    }
}
