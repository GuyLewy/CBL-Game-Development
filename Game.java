import javax.swing.*;

public class Game {
    public static JFrame gameWindow = new JFrame("Planks n' Plunders");

    public static void main(String[] args) {
        gameWindow.setSize(2000, 1000);
        gameWindow.setBounds(0, 0, 2000, 1000);
        new MainMenu(gameWindow);
    }
}
