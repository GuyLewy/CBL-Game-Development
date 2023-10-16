import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class RestartMenu extends JPanel {

    private JButton restartGameButton;
    private JFrame mainWindow;

    public RestartMenu(int score) {
        mainWindow = new JFrame();
        restartGameButton = new JButton(Integer.toString(score));
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

    private class ButtonClickListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == restartGameButton) {
                mainWindow.dispose();
                new DisplayGraphics();
            }
        }
    }
}
