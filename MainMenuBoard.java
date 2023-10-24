import java.awt.*;

public class MainMenuBoard implements Drawable {
    int boardX = 550;
    int boardY = 100;
    int boardWidth = 400;
    int boardHeight = 500;
    int arrowPosition = 0;
    boolean up = false;

    public void draw(Graphics g) {
        g.setColor(Color.black);
        g.fillRect(boardX, boardY, boardWidth, boardHeight);
        g.setColor(Color.blue);
        g.fillRect(boardX + 5, boardY + 5, boardWidth - 10, 80);
        g.setColor(Color.white);
        g.fillRect(boardX + 30, boardY + (arrowPosition + 1) * 100 + 20, 40, 40);
        g.setColor(Color.red);
        for (int i = 0; i < 4; i++) {
            g.fillRect(boardX + 100, boardY + (i + 1) * 100, boardWidth - 120, 80);
        }
    }

    public void bounce() {
        if (up) {
            up = false;
            boardY -= 20;
        } else {
            up = true;
            boardY += 20;
        }
    }
}
