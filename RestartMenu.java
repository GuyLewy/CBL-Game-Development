import java.awt.event.*;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * Creates a frame that displays the score the player acheived that round and a
 * button that can be pressed to start an instance of the game then closses this
 * frame.
 */
public class RestartMenu extends MainMenu {

    /**
     * Constructor that sets up the restart menu.
     * 
     * @param score The score of the most recent game.
     */
    public RestartMenu(int score) {
        board.highScore = score;
        board.getScore();
        board.boardTexture = null;
        board.titleTexture = null;
        board.scoreVisible = true;
        board.score.menuX = board.boardX;
        board.score.menuY = board.boardY;
        try {
            board.score.texture = ImageIO.read(getClass().getResourceAsStream(
                    "textures/menu/restartBoard.png"));
        } catch (IOException e) {
            ;
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_SPACE) {
            restart();
        }
    }

    /**
     * When restarting the game the restart menu window is disposed of and a new
     * instance of the main menu is created.
     */
    public void restart() {
        menuWindow.dispose();
        background.stop();
        new MainMenu();
    }
}
