import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MainMenu extends JPanel {

    private static JButton startGameButton;

    public MainMenu() {
        JFrame mainWindow = new JFrame();
        startGameButton = new JButton("Start");
        startGameButton.setFont(new Font("Arial", Font.PLAIN, 40));
        startGameButton.setBounds(200, 400, 600, 100);
        ButtonClickListener buttonClick = new ButtonClickListener();
        startGameButton.addActionListener(buttonClick);
        mainWindow.add(startGameButton);
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainWindow.setSize(1000, 560);
        mainWindow.setLayout(null);
        mainWindow.setVisible(true);
    }

    public static void main(String[] args) {
        new MainMenu();
    }

    private class ButtonClickListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == startGameButton) {
            }
        }
    }
}