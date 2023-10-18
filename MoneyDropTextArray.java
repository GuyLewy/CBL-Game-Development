import java.awt.*;
import java.util.*;

/**
 * Array stores all visible MoneyDropTexts.
 */
public class MoneyDropTextArray implements Drawable {
    ArrayList<MoneyDropText> texts = new ArrayList<MoneyDropText>(); 
    
    /**
     * Move each MoneyDropText one pixel up, decrease its opacity and timeLeftOnScreen.
     * If the time is left - remove.
     */
    public void updateTexts() {
        for (int i = 0; i < texts.size(); i++) {
            MoneyDropText next = texts.get(i);
            next.textY--;
            next.timeLeftOnScreen--;
            next.opacity -= 5;;

            if (next.timeLeftOnScreen <= 0) {
                texts.remove(i);
            }
        }
    }

    /**
     * Draw all MoneyDropTexts.
     */
    public void draw(Graphics g) {
        for (int i = 0; i < texts.size(); i++) {
            MoneyDropText next = texts.get(i);
            next.draw(g);
        }
    }
}
