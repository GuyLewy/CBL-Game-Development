import java.awt.*;
import java.util.*;

/**
 * .
 */
public class MoneyDropTextArray implements Drawable {
    ArrayList<MoneyDropText> texts = new ArrayList<MoneyDropText>(); 
    
    /**
     * .
     */
    public void updateTexts() {
        for (int i = 0; i < texts.size(); i++) {
            MoneyDropText next = texts.get(i);
            next.textY--;
            next.timeLeftOnScreen--;
            next.opacity--;;

            if (next.timeLeftOnScreen <= 0) {
                texts.remove(i);
            }
        }
    }

    /**
     * .
     */
    public void draw(Graphics g) {
        for (int i = 0; i < texts.size(); i++) {
            MoneyDropText next = texts.get(i);
            next.draw(g);
        }
    }
}
