import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import javax.imageio.ImageIO;

/**
 * EnemyArrayList, used to go through all available enemies and update them.
 * 
 * @author Antoni Nowaczyk
 * @id 1934899
 * @author Guy Lewy
 * @id 1954962
 */
public class EnemiesArrayList {
    public ArrayList<Enemy> enemies = new ArrayList<Enemy>();
    public ProjectilesArrayList enemiesProjectiles = new ProjectilesArrayList(-7);
    private MoneyDropTextArray moneyDropTexts = new MoneyDropTextArray();
    static final int MONEY_TEXT_DURATION = 50;
    public static int animationRate = 30;

    Random random = new Random();

    public int enemiesKilled = 0;
    public int randomBound;
    int enemyAnimationCounter = 0;
    int textureIndex = 0;
    int[] bounderies = new int[] { 30, 60 };

    BufferedImage[] textures = new BufferedImage[12];

    Random rand = new Random();

    /**
     * Initialize the array list with the enemies textures.
     */
    public EnemiesArrayList() {
        try {
            for (int i = 1; i < 13; i++) {
                String path = "";
                if (i <= 4) {
                    path = "textures/enemies/pirateShip1/pirateShip1_" + i + ".png";
                } else if (i <= 8) {
                    path = "textures/enemies/pirateShip2/pirateShip2_" + (i - 4) + ".png";
                } else {
                    path = "textures/enemies/pirateShip3/pirateShip3_" + (i - 8) + ".png";
                }
                textures[i - 1] = ImageIO.read(getClass().getResourceAsStream(path));
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    /**
     * A method to create a new enemy then add it to the ArrayList of all existing
     * enemies. The enemy type and position is determined randomly.
     */
    public void generateEnemy() {
        int yPos = random.nextInt(DisplayGraphics.windowDimensions.height - 400) + 75;
        int enemyRand = random.nextInt(randomBound);
        Enemy newEnemy;

        if (enemyRand < bounderies[0]) {
            newEnemy = new Enemy(yPos);
        } else if (enemyRand < bounderies[1]) {
            newEnemy = new EnemyScout(yPos);
        } else {
            newEnemy = new EnemyTank(yPos);
        }

        enemies.add(newEnemy);
    }

    public void addEnemy(Enemy enemy) {
        enemies.add(enemy);
    }

    /**
     * A method to add all enemies to a graphics object so that they can be painted
     * to the screen.
     * 
     * @param g graphics object for enemy to be added to
     */
    public void draw(Graphics g) {
        for (var i = 0; i < enemies.size(); i++) {
            if (enemies.get(i).enemyType == 1) {
                enemies.get(i).texture = textures[textureIndex];
            } else if (enemies.get(i).enemyType == 2) {
                enemies.get(i).texture = textures[textureIndex + 4];
            } else if (enemies.get(i).enemyType == 3) {
                enemies.get(i).texture = textures[textureIndex + 8];
            }
            enemies.get(i).draw(g);
        }
        enemiesProjectiles.draw(g);
        moneyDropTexts.draw(g);
    }

    /**
     * A method checks if any of the enemies is hit by a projectile,
     * has no life points left and then moves the enemies.
     */
    public int updateEnemies(ProjectilesArrayList projectiles, Wallet wallet,
            int playerX, int playerY, int playerWidth, int playerHeight, int bound) {

        checkProjectiles(projectiles);
        manageDamage(wallet);
        moneyDropTexts.updateTexts();
        handleEnemyProjectiles();
        boolean enemyHitDock = moveEnemies();
        updateTextures();
        randomBound = bound;
        if (enemyHitDock) {
            return (Player.PLAYER_MAX_HEALTH);
        } else {
            return checkPlayerCollisions(playerX, playerY, playerWidth, playerHeight);
        }
    }

    /**
     * Going through four enemy textures changing to the next one
     * every 30 calls of updateTextures method.
     */
    public void updateTextures() {
        if (enemyAnimationCounter < animationRate) {
            enemyAnimationCounter++;
        } else {
            enemyAnimationCounter = 0;
            textureIndex++; // Change to the next texture.
            textureIndex %= 4; // If index is equal to four, go back to the index 0.
        }
    }

    /**
     * A method checks, if any of the projectiles in ArrayList..
     * 
     * @param projectiles is hit by any of the projectiles.
     */
    public void checkProjectiles(ProjectilesArrayList projectiles) {
        for (int i = 0; i < enemies.size(); i++) {
            Enemy next = enemies.get(i);
            if (projectiles.areBulletsHitting(next.enemyX, next.enemyY,
                    next.enemyWidth, next.enemyHeight)) {
                next.removeLifePoint();
            }
        }
    }

    /**
     * Method checks, if any of the enemies has no life points left.
     * In that case, it creates a related MoneyDropText, adds money to the wallet
     * and removes that enemy.
     */
    public void manageDamage(Wallet wallet) {
        for (int i = 0; i < enemies.size(); i++) {
            Enemy next = enemies.get(i);

            if (next.lifePointsLeft <= 0) {
                MoneyDropText nextText = new MoneyDropText(next.moneyCarried,
                        next.enemyX, next.enemyY, MONEY_TEXT_DURATION);

                wallet.money += next.moneyCarried;
                moneyDropTexts.texts.add(nextText);

                enemies.remove(i);
                enemiesKilled++;
            }
        }
    }

    /**
     * A method that iterates accross all eneemies and moves them all to the left
     * based on their movement speed, aditionally it despawns them if they hit the
     * edge of the screen.
     */
    public boolean moveEnemies() {
        for (var i = 0; i < enemies.size(); i++) {
            if (enemies.get(i).enemyX > (int) ((50 + DisplayGraphics.blackBorderDimensions.width)
                    * DisplayGraphics.screenSizeMultiplier)) {
                enemies.get(i).moveEnemy();
            } else {
                enemies.remove(i);
                return true;
            }
        }

        return false;
    }

    /**
     * Iterate accross all of the enemies and check wether they should shoot
     * (handled in the shoot() method) and move their projectiles.
     */
    public void handleEnemyProjectiles() {
        shoot();
        moveProjectiles();
    }

    /**
     * Shoots projectiles from enemies that are set to shoot and have a delay
     * counter that has reached the delay time. Adds the projectile to the
     * enemiesProjectiles ArrayList.
     */
    public void shoot() {
        for (var i = 0; i < enemies.size(); i++) {
            Enemy next = enemies.get(i);
            if (next.projectileDelayCounter >= next.projectileDelay && next.doesShoot) {
                enemiesProjectiles.addProjectile(next.enemyX, next.enemyY + 50);
                next.projectileDelayCounter = 0;
            } else if (next.projectileDelayCounter < next.projectileDelay) {
                next.projectileDelayCounter++;
            }
        }
    }

    /**
     * Moves the projectiles of the enemies and removes them if they go off the
     * screen.
     */
    public void moveProjectiles() {
        enemiesProjectiles.moveProjectiles();
        for (var i = 0; i < enemiesProjectiles.projectiles.size(); i++) {
            Projectile next = enemiesProjectiles.projectiles.get(i);
            if (next.projectileX <= 0) {
                enemiesProjectiles.projectiles.remove(i);
            }
        }
    }

    /**
     * Iterates across all of the enemies and checks if the player is hit by any of
     * the enemy projectiles.
     */
    public int checkPlayerCollisions(int playerX, int playerY,
            int playerWidth, int playerHeight) {
        int playerCollisionCount = 0;
        for (var i = 0; i < enemies.size(); i++) {
            if (enemies.get(i).checkPlayerCollision(playerX, playerY, playerWidth, playerHeight)) {
                playerCollisionCount++;
            }
        }
        return playerCollisionCount;
    }

    /**
     * Iterates across all of the enemies and lowers their life points to zero, this
     * should be run when the game is restarted or if the player has an ability that
     * kills all of the enemies on screen.
     */
    public void deleteAllEnemies() {
        for (var i = 0; i < enemies.size(); i++) {
            enemies.get(i).lifePointsLeft = 0;
        }
    }
}
