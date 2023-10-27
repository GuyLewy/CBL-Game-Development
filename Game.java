import javax.swing.*;

public class Game {
    public static JFrame gameWindow = new JFrame("Planks n' Plunders");

    public static void main(String[] args) {
        gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameWindow.setExtendedState(JFrame.MAXIMIZED_BOTH);
        gameWindow.setUndecorated(true);
        gameWindow.setVisible(true);
        new MainMenu(gameWindow);
    }
}
