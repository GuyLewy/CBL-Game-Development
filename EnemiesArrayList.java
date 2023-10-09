import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * EnemyArrayList, used to go through all available enemies and update them.
 */
public class EnemiesArrayList {
    public boolean enemyKilled = false;
    public ArrayList<Enemy> enemies = new ArrayList<Enemy>();
    Random random = new Random();
    int height = Enemy.ENEMY_HEIGHT;

    /**
     * A method to create a new enemy then add it to the ArrayList of all existing
     * enemies.
     * 
     * @param xPos      the initial x position of the enemy
     * @param enemyType an integer that determines the characteristics of the enemy
     *                  such as health, speed and damage
     */
    public void generateEnemy(int xPos, int enemyType) {
        int yPos = random.nextInt(DisplayGraphics.windowDimensions.height - 400) + 75;
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

    /**
     * A method checks if any of the enemies is hit by a projectile,
     * has no life points left and then moves the enemies.
     */
    public void updateEnemies(ProjectilesArrayList projectiles) {
        enemyKilled = false;
        checkProjectiles(projectiles);
        checkLifePoints();
        moveEnemies();
    }

    /**
     * A method checks, if any of the projectiles in ArrayList..
     * @param projectiles is hit by any of the projectiles.
     */
    public void checkProjectiles(ProjectilesArrayList projectiles) {
        for (int i = 0; i < enemies.size(); i++) {
            Enemy next = enemies.get(i);
            if (projectiles.areBulletsHitting(next.enemyX, next.enemyY, height)) {
                next.removeLifePoint();
            }
        }
    }

    /**
     * Method checks, if any of the enemies has no life points left.
     * In that case, it removes that enemy.
     */
    public void checkLifePoints() {
        for (int i = 0; i < enemies.size(); i++) {
            Enemy next = enemies.get(i);

            if (next.lifePointsLeft <= 0) {
                enemies.remove(i);
                enemyKilled = true;
            }
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
}
