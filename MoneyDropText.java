import java.awt.*;

/**
 * .
 */
public class MoneyDropText implements Drawable {
    String moneyString;
    public int textX;
    public int textY;
    public int timeLeftOnScreen;
    public int opacity;

    /**
     * .
     * @param ammountOfMoney .
     * @param x .
     * @param y .
     */
    public MoneyDropText(int ammountOfMoney, int x, int y, int time) {
        moneyString = "+" + Integer.toString(ammountOfMoney) + "$";
        textX = x;
        textY = y;
        timeLeftOnScreen = time;
        opacity = 255;
    }

    /**
     * .
    */
    public void draw(Graphics g) {
        Font  f2  = new Font(Font.SANS_SERIF,  Font.BOLD, 25);
        g.setColor(new Color(200, 160, 50, opacity));
        g.setFont(f2);
        g.drawString(moneyString, textX, textY);
    }
}
