import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Creates a frame with a button that can be pressed to start an instance of the
 * game then closses the frame.
 */
public class MainMenu extends JPanel {

    private JButton startGameButton;
    private JFrame mainWindow;

    /**
     * Sets up the frame to have a button and add an action listener to the button.
     */
    public MainMenu() {
        mainWindow = new JFrame();
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

    /**
     * Checks if start game button is pressed and if it is then the method will
     * create a new instance of the game and close the main menu.
     */
    private class ButtonClickListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == startGameButton) {
                new DisplayGraphics();
                mainWindow.dispose();
            }
        }
    }
}