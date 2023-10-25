import java.awt.*;

public class Dock implements Drawable {
    int dockHeight;

    Dock(int height) {
        dockHeight = height;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(new Color(107, 58, 41));
        g.fillRect(0, 50, 100, dockHeight - 50);

        for (int i = 1; i <= 30; i++) {
            g.setColor(Color.black);
            g.fillRect(0, dockHeight * i / 30 + 50, 100, 3);
            if (i % 2 == 1) {
                g.setColor(new Color(79, 44, 28));
                g.fillOval(90, (dockHeight * (i - 1) / 30) - 10 + 50, 20, 20);
            }
        }

    }
}
