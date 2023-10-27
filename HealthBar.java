import java.awt.*;
import java.util.*;

/**
 * Health bar used to show life point left.
 */
public class HealthBar implements Drawable {
    private int maxHealth;
    int barX;
    int barY;

    ArrayList<HealthHeart> healthBar = new ArrayList<HealthHeart>();

    /**
     * Create a new health bar.
     * 
     * @param maxPlayerHealth used to determine the ammount of hearts.
     */
    public HealthBar(int maxPlayerHealth, int x, int y) {
        barX = x;
        barY = y;
        maxHealth = maxPlayerHealth;

        for (int i = 0; i < maxHealth / 2; i++) {
            HealthHeart next = new HealthHeart((barX + 15) + 35 * i, barY + 15);
            healthBar.add(next);
        }
    }

    /**
     * Based on the number of health point left, fill, half-fill or leave empty
     * hearts.
     * 
     * @param healthPoints number of health point left.
     */
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

    /**
     * Draw the healthBar.
     */
    public void draw(Graphics g) {
        for (int i = 0; i < healthBar.size(); i++) {
            healthBar.get(i).draw(g);
        }
    }
}
