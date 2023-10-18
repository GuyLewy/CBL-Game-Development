import java.awt.*;
import javax.swing.*;

/**
 * Class used to count and display the score on the screen.
 */
public class ScoreCounter extends JPanel implements Drawable {
    Font f2 = new Font(Font.SANS_SERIF, Font.BOLD, 25);
    int gameScore = 0;
    int numberOfEnemiesBefore = 0;
    int numberOfEnemies = 0;

    /**
     * Update the score.
     */
    public void updateScore(EnemiesArrayList enemies) {
        if (enemies.enemyKilled) {
            gameScore++;
        }
    }

    /**
     * Displays the score.
     */
    public void draw(Graphics g) {
        g.setColor(Color.black);
        g.setFont(f2);
        g.drawString("Score: %d".formatted(gameScore),
                (int) (0.64 * DisplayGraphics.windowDimensions.getWidth()), 65);
    }
}
