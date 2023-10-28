import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;
import javax.imageio.ImageIO;

/**
 * WavesArrayList, used to update waves and create new waves.
 * 
 * @author Antoni Nowaczyk
 * @id 1934899
 * @author Guy Lewy
 * @id 1954962
 */
public class WavesArrayList implements Drawable {
    public ArrayList<Wave> waves = new ArrayList<Wave>();
    public int waveDelay = 100;
    public int waveDelayCounter = 0;
    public int textureIndex = 0;
    static int animationRate = 20;
    int waveAnimationCounter = 0;
    int waveSpeed = -2;

    int waveLifeTime = 7 * animationRate;

    Random rand = new Random();

    BufferedImage[] textures = new BufferedImage[7];

    /**
     * Constructor for the waves array list that gets the textures from png files.
     */
    public WavesArrayList() {
        try {
            for (int i = 1; i < 8; i++) {
                String path = "textures/waves/wave1/wave1_" + i + ".png";
                textures[i - 1] = ImageIO.read(getClass().getResourceAsStream(path));
            }

        } catch (IOException e) {
            ;
        }
    }

    /**
     * Creates a new wave with a random x and y positon in the screen and adds it to
     * the waves array list.
     */
    public void generateWave() {
        int xPos = rand.nextInt(DisplayGraphics.windowDimensions.width
                - 100 - DisplayGraphics.blackBorderDimensions.width)
                + DisplayGraphics.blackBorderDimensions.width;
        int yPos = rand.nextInt(DisplayGraphics.windowDimensions.height
                - 50 - DisplayGraphics.blackBorderDimensions.height)
                + DisplayGraphics.blackBorderDimensions.height;

        Wave next = new Wave(xPos, yPos, waveLifeTime);
        waves.add(next);
    }

    /**
     * Going through four enemy textures changing to the next one
     * every 30 calls of updateTextures method.
     */
    public void updateTextures() {
        if (waveAnimationCounter < animationRate) {
            waveAnimationCounter++;
        } else {
            waveAnimationCounter = 0;
            for (int i = 0; i < waves.size(); i++) {
                waves.get(i).textureIndex++;
                waves.get(i).textureIndex %= 7;
            }
        }
    }

    /**
     * Moves the wave left based on its movement speed.
     */
    public void moveWaves() {
        for (int i = 0; i < waves.size(); i++) {
            waves.get(i).waveX += waveSpeed;
        }
    }

    /**
     * Check if the wave shoud despawn if not, update its texture and move it.
     */
    public void updateWaves() {
        checkWaves();
        updateTextures();
        moveWaves();
    }

    /**
     * Determines if the wave has been around long enough to complete its cycle if
     * it has it removes it if not it subtracts one from its time left.
     */
    public void checkWaves() {
        for (int i = 0; i < waves.size(); i++) {
            waves.get(i).timeLeft--;
            if (waves.get(i).timeLeft <= 0) {
                waves.remove(i);
            }
        }
    }

    /**
     * Draws the wave to the screen.
     * 
     * @param g graphics object for the wave to be drawn to
     */
    public void draw(Graphics g) {
        for (int i = 0; i < waves.size(); i++) {
            Wave next = waves.get(i);
            next.texture = textures[next.textureIndex];
            next.draw(g);
        }
    }
}
