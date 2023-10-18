import java.awt.*;
import java.util.*;

public class HealthBar implements Drawable {
    private int maxHealth;

    ArrayList<HealthHeart> healthBar = new ArrayList<HealthHeart>();

    public HealthBar(int maxPlayerHealth) {
        maxHealth = maxPlayerHealth;

        for (int i = 0; i < maxHealth / 2; i++) {
            HealthHeart next = new HealthHeart(5 + 35 * i, 5);
            healthBar.add(next);
        }
    }
    
    public void updateHealtBar(int healthPoints) {
        for (int i = 0; i < healthBar.size(); i++) {
            if (i <= healthPoints / 2 - 1) {
                healthBar.get(i).heartState = 2;
            } else {
                if (healthPoints % 2 == 0) {
                    healthBar.get(i).heartState = 0;
                } else if (i - 1 == healthPoints / 2 - 1) {
                    healthBar.get(i).heartState = 1;
                } else {
                    healthBar.get(i).heartState = 0;
                }

            }
        }
    }
    
    public void draw(Graphics g) {
        for (int i = 0; i < healthBar.size(); i++) {
            healthBar.get(i).draw(g);
        }
    }
}
