import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import javax.swing.*;

public class Wave implements Drawable {
    int waveX;
    int waveY;
    int timeLeft;
    int textureIndex = 0;
    BufferedImage texture;

    Wave(int x, int y, int lifeTime) {
        waveX = x;
        waveY = y;
        timeLeft = lifeTime;
    }

    public void draw(Graphics g) {
        g.drawImage(texture, waveX, waveY, null);
    }

}