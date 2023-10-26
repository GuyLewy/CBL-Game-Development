import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class ScoreManager {
    File highScoreF = new File("highScore.txt");
    public int highScore;

    public void createScoreFile() {
        try {
            if (!highScoreF.createNewFile()) {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error accured");
        }
    }

    public int getHighScore() {
        highScore = 0;
        try {
            Scanner fs = new Scanner(highScoreF);
            highScore = fs.nextInt();
            fs.close();
        } catch (FileNotFoundException e) {
            createScoreFile();
        }
        return highScore;
    }

    public void saveScore(int score) {
        try {
            FileWriter fw = new FileWriter("highScore.txt");
            if (score > highScore) {
                fw.write(Integer.toString(score));
            }
            fw.close();
        } catch (IOException e) {
            ;
        }
        
    }
}
