/**
 * Tank enemy inherits from enemy increasing health while decreasing helalth and
 * projectile delay.
 */
public class EnemyTank extends Enemy {

    /**
     * Sets the y position of the enemy and sets it's life points and speed.
     * 
     * @param yPos Integer representing the y position at which the enemy will be
     *             drawn
     */
    EnemyTank(int yPos) {
        super(yPos);
        lifePointsLeft = 3;
        enemySpeed = 2;
        projectileDelay = 300;
        moneyCarried = rand.nextInt(5) + 1; // Set bound to the max value that should be given
    }
}