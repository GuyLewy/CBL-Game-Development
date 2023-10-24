import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * Creates a frame with a button that can be pressed to start an instance of the
 * game then closses the frame.
 */
public class MainMenu extends JPanel {

    private JButton startGameButton;
    private JFrame mainWindow;
    private ImageIcon startButtonIcon;

    Sound sound = new Sound();

    /**
     * Sets up the frame to have a button and add an action listener to the button.
     */
    public MainMenu() {
        mainWindow = new JFrame();
        getButtonImages();
        startGameButton = new JButton(startButtonIcon);
        startGameButton.setBounds(450, 400, 600, 100);
        startGameButton.setBorderPainted(false);
        ButtonClickListener buttonClick = new ButtonClickListener();
        startGameButton.addActionListener(buttonClick);
        mainWindow.add(startGameButton);
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainWindow.setSize(2000, 1000);
        mainWindow.setLayout(null);
        mainWindow.setVisible(true);
        mainWindow.getContentPane().setBackground(new Color(86, 63, 53));
        sound.setSoundEffect(4);
        sound.play();
        sound.loop();
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
                sound.stop();
                sound.setSoundEffect(5);
                sound.play();
                new DisplayGraphics();
                mainWindow.dispose();
            }
        }
    }

    private void getButtonImages() {
        try {
            startButtonIcon = new ImageIcon(ImageIO
            .read(getClass().getResourceAsStream("textures/buttons/startGameButton.png")));
        } catch (IOException e) {
            ;
        }
    }
}