import java.awt.*;
import javax.swing.*;

/**
 * .
 */
public class DisplayGraphics extends Canvas {

    public static int windowHeight = 0;

    public static void main(String[] args) {
        Player player = new Player();
        JFrame mainWindow = new JFrame();
        mainWindow.add(player);
        mainWindow.setSize(400, 400);
        mainWindow.setVisible(true);
        windowHeight = mainWindow.getBounds().height;
    }
}