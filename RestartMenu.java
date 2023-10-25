import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * Creates a frame that displays the score the player acheived that round and a
 * button that can be pressed to start an instance of the game then closses this
 * frame.
 */
public class RestartMenu extends MainMenu {

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

    public void restart() {
        menuWindow.dispose();
        background.stop();
        new MainMenu();
    }
}
