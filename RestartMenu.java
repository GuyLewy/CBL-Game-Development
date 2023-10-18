import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Creates a frame that displays the score the player acheived that round and a
 * button that can be pressed to start an instance of the game then closses this
 * frame.
 */
public class RestartMenu extends JPanel {

    private JButton restartGameButton;
    private JFrame mainWindow;

    /**
     * Creates a frame that displays the score the player acheived that round and a
     * button.
     * 
     * @param score The player's score
     */
    public RestartMenu(int score) {
        mainWindow = new JFrame();
        String deathMessage = "You scored " + Integer.toString(score) + " points";
        JLabel deathMessageLabel = new JLabel(deathMessage, SwingConstants.CENTER);
        deathMessageLabel.setFont(new Font("Arial", Font.PLAIN, 60));
        deathMessageLabel.setBounds(200, 100, 600, 100);
        mainWindow.add(deathMessageLabel);
        restartGameButton = new JButton("Restart");
        restartGameButton.setFont(new Font("Arial", Font.PLAIN, 40));
        restartGameButton.setBounds(200, 400, 600, 100);
        ButtonClickListener buttonClick = new ButtonClickListener();
        restartGameButton.addActionListener(buttonClick);
        mainWindow.add(restartGameButton);
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainWindow.setSize(1000, 560);
        mainWindow.setLayout(null);
        mainWindow.setVisible(true);
    }

    /**
     * Checks if restart button is pressed and if it is then the method will
     * create a new instance of the game and close the restart menu.
     */
    private class ButtonClickListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == restartGameButton) {
                mainWindow.dispose();
                new DisplayGraphics();
            }
        }
    }
}
