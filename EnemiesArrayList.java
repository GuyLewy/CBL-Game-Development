import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * EnemyArrayList, used to go through all available enemies and update them.
 */
public class EnemiesArrayList {

    public ArrayList<Enemy> enemies = new ArrayList<Enemy>();
    Random random = new Random();

    /**
     * A method to create a new enemy then add it to the ArrayList of all existing
     * enemies.
     * 
     * @param xPos      the initial x position of the enemy
     * @param enemyType an integer that determines the characteristics of the enemy
     *                  such as health, speed and damage
     */
    public void generateEnemy(int xPos, int enemyType) {
        int yPos = random.nextInt(DisplayGraphics.windowDimensions.height - 200) + 50;
        Enemy newEnemy = new Enemy(yPos);
        enemies.add(newEnemy);
    }

    /**
     * A method to add all enemies to a graphics object so that they can be painted
     * to the screen.
     * 
     * @param g graphics object for enemy to be added to
     */
    public void draw(Graphics g) {
        for (var i = 0; i < enemies.size(); i++) {
            enemies.get(i).draw(g);
        }
    }

    public void drawEnemyProjectiles(Graphics g) {
        for (var i = 0; i < enemies.size(); i++) {
            enemies.get(i).drawProjectiles(g);
        }
    }

    /**
     * A method that iterates accross all eneemies and moves them all to the left
     * based on their movement speed, aditionally it despawns them if they hit the
     * edge of the screen.
     */
    public void moveEnemies() {
        for (var i = 0; i < enemies.size(); i++) {
            if (enemies.get(i).enemyX > 0) {
                enemies.get(i).moveEnemy();
            } else {
                enemies.remove(i);
            }
        }
    }

    public void handleEnemyProjectiles() {
        for (var i = 0; i < enemies.size(); i++) {
            enemies.get(i).shoot();
            enemies.get(i).moveProjectiles();

        }
    }
}
