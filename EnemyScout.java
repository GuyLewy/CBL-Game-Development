import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;

public class EnemyScout extends Enemy implements Drawable {

    EnemyScout(int yPos) {
        super(yPos);
        lifePointsLeft = 1;
        enemySpeed = 6;
    }

    @Override
    public void shoot() {
    }
}