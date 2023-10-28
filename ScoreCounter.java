import java.awt.*;
import javax.swing.*;

/**
 * Class used to count and display the score on the screen.
 * 
 * @author Antoni Nowaczyk
 * @id 1934899
 * @author Guy Lewy
 * @id 1954962 
 */
public class ScoreCounter extends JPanel implements Drawable {
    Font f2 = new Font(Font.SANS_SERIF, Font.BOLD, 30);
    int gameScore = 0;

    /**
     * Update the score.
     */
    public void updateScore(EnemiesArrayList enemies) {
        gameScore += enemies.enemiesKilled;
        enemies.enemiesKilled = 0;
    }

    /**
     * Displays the score.
     */
    public void draw(Graphics g) {
        g.setColor(Color.black);
        g.setFont(f2);
        g.drawString("Score: %d".formatted(gameScore),
            DisplayGraphics.windowDimensions.width
             - DisplayGraphics.blackBorderDimensions.width - 480,
            DisplayGraphics.blackBorderDimensions.height + 55);
    }
}
