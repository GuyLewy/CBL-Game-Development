import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class MainMenuBoard implements Drawable {
    int boardX = 550;
    int boardY = 250;
    int titleX = 460;
    int titleY = 20;
    int boardWidth = 400;
    int boardHeight = 500;
    int arrowPosition = 0;
    boolean up = false;
    boolean scoreVisible = false;

    BufferedImage boardTexture;
    BufferedImage pointerTexture;
    BufferedImage titleTexture;
    BufferedImage[] digits = new BufferedImage[10];
    public int highScore;
    int[] scoreDigits = new int[3];

    public ScoreMenu score = new ScoreMenu(boardX + 380, boardY + 100);

    public MainMenuBoard() {
        getScore();
        getTextures();
    }

    public void getScore() {
        for (int i = 0; i < 3; i++) {
            scoreDigits[i] = highScore % 10;
            highScore /= 10;
        }
    }

    public void getTextures() {
        try {
            boardTexture = ImageIO.read(getClass().getResourceAsStream(
                "textures/menu/board.png"));
            pointerTexture = ImageIO.read(getClass().getResourceAsStream(
                "textures/menu/pointer.png"));
            titleTexture = ImageIO.read(getClass().getResourceAsStream(
                "textures/menu/title.png"));

            for (int i = 0; i < 10; i++) {
                digits[i] = ImageIO.read(getClass().getResourceAsStream(
                "textures/menu/digits/" + i + ".png"));
            }
            
        } catch (IOException e) {
            ;
        }
    }

    public void draw(Graphics g) {
        g.drawImage(boardTexture, boardX, boardY, null);
        g.drawImage(pointerTexture, boardX + 20, 35 + boardY + arrowPosition * 120, null);
        g.drawImage(titleTexture, titleX, titleY, null);
        if (scoreVisible) {
            score.draw(g);
            for (int i = 0; i < 3; i++) {
                g.drawImage(digits[scoreDigits[2 - i]], score.menuX + 50 + 120 * i, 
                    score.menuY + 170, null);
            }
        }
    }

    public void bounce() {
        if (up) {
            up = false;
            boardY -= 20;
            score.menuY -= 20;

        } else {
            up = true;
            boardY += 20;
            score.menuY += 20;
        }
    }
}
