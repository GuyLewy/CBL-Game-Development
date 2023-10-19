import java.awt.*;

/**
 * A Wallet class to store money in.
 */
public class Wallet implements Drawable {
    Font  f2  = new Font(Font.SANS_SERIF,  Font.BOLD, 25);
    public int money;

    public Wallet() {
        money = 0;
    }

    /**
     * Drawing the money counter.
     */
    public void draw(Graphics g) {
        g.setColor(new Color(175, 140, 45));
        g.setFont(f2);
        g.drawString("Money: %d".formatted(money), 
            (int) (0.64 * DisplayGraphics.windowDimensions.getWidth()), 90);
    }
}
