import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * Class deals with all of the drawing of the main menu board.
 * 
 * @author Guy Lewy
 * @author Antoni Nowaczyk
 */
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

    public ScoreMenu score;

    /**
     * Constructor for the main menu board that gets the textures from png files and
     * the high score from the previous game.
     * 
     * @param inputScore the score recieved from the previous game
     */
    public MainMenuBoard(int inputScore, int screenWidth, int screenHeight) {
        highScore = inputScore;
        boardX = screenWidth / 2 - boardWidth / 2;
        boardY = screenHeight * 2 / 3 - boardHeight / 2;
        titleX = boardX - 90;
        titleY = boardY - boardHeight / 2;
        score = new ScoreMenu(boardX + 380, boardY + 100);
        highScore = score;
        getScore();
        getTextures();
    }

    /**
     * Sets the digits of the high scores and puts them in an array.
     */
    public void getScore() {
        for (int i = 0; i < 3; i++) {
            scoreDigits[i] = highScore % 10;
            highScore /= 10;
        }
    }

    /**
     * Gets the textures from png files.
     */
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

    /**
     * Draws the main menu board to the screen changing the arrow position depending
     * on the menu pointer position.
     * 
     * @param g graphics object for the board to be drawn to.
     */
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

    /**
     * Bounces the board up and down depending on the value of the up boolean.
     */
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
