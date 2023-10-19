import java.awt.*;

/**
 * A MoneyDropText is a short String which appears after an enemy was killed.
 * It takes the form: "+X$", where X is the ammount of money gained from a particular enemy.
 * The MoneyDropText stays on the screen for a particular ammount of time, slowly moving up
 * and decreasing its opacity.
 * Then it disappears.
 */
public class MoneyDropText implements Drawable {
    String moneyString;
    public int textX;
    public int textY;
    public int timeLeftOnScreen;
    public int opacity;

    /**
     * Creating a new MoneyDropText with maximal opacity and specified.:
     * @param ammountOfMoney .
     * @param x - x position.
     * @param y - starting y position.
     */
    public MoneyDropText(int ammountOfMoney, int x, int y, int time) {
        moneyString = "+" + Integer.toString(ammountOfMoney) + "$";
        textX = x;
        textY = y;
        timeLeftOnScreen = time;
        opacity = 255;
    }

    /**
     * Drawing MoneyDropText.
    */
    public void draw(Graphics g) {
        Font  f2  = new Font(Font.SANS_SERIF,  Font.BOLD, 25);
        g.setColor(new Color(175, 140, 45, opacity));
        g.setFont(f2);
        g.drawString(moneyString, textX, textY);
    }
}
