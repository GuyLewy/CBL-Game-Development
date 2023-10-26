import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;
import javax.imageio.ImageIO;

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

    public void generateWave() {
        int xPos = rand
                .nextInt(DisplayGraphics.windowDimensions.width - 100 - DisplayGraphics.blackBorderDimensions.width)
                + DisplayGraphics.blackBorderDimensions.width;
        int yPos = rand
                .nextInt(DisplayGraphics.windowDimensions.height - 50 - DisplayGraphics.blackBorderDimensions.height)
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

    public void moveWaves() {
        for (int i = 0; i < waves.size(); i++) {
            waves.get(i).waveX += waveSpeed;
        }
    }

    public void updateWaves() {
        checkWaves();
        updateTextures();
        moveWaves();
    }

    public void checkWaves() {
        for (int i = 0; i < waves.size(); i++) {
            waves.get(i).timeLeft--;
            if (waves.get(i).timeLeft <= 0) {
                waves.remove(i);
            }
        }
    }

    public void draw(Graphics g) {
        for (int i = 0; i < waves.size(); i++) {
            Wave next = waves.get(i);
            next.texture = textures[next.textureIndex];
            next.draw(g);
        }
    }
}
