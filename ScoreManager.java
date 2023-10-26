import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * Handles all of the score related functions such as reading and writing the
 * high score.
 * 
 * @author Guy Lewy
 * @author Antoni Nowaczyk
 */
public class ScoreManager {
    File highScoreF = new File("highScore.txt");
    public int highScore;

    /**
     * Creates a new file called highScore.txt.
     */
    public void createScoreFile() {
        try {
            highScoreF.createNewFile();
        } catch (IOException e) {
            System.out.println("An error accured");
        }
    }

    /**
     * Read the high score form the highScore.txt file. If the file does not exist
     * then it calls the createScoreFile method. If the file is empty or does not
     * have an int then it defaults to 0.
     * 
     * @return the high score
     */
    public int getHighScore() {
        highScore = 0;
        try {
            Scanner fs = new Scanner(highScoreF);
            if (fs.hasNextInt()) {
                highScore = fs.nextInt();
            }
            fs.close();
        } catch (FileNotFoundException e) {
            createScoreFile();
        }

        return highScore;
    }

    /**
     * Writes the high score to the highScore.txt file if the score is higher than
     * the high score.
     * 
     * @param score The score of the most recent game
     */
    public void saveScore(int score) {
        try {
            if (score > highScore) {
                FileWriter fw = new FileWriter("highScore.txt");
                fw.write(Integer.toString(score));
                fw.close();
            }
        } catch (IOException e) {
            ;
        }

    }
}
