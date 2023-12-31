/**
 * Scout enemy inherits from enemy and overrides shoot() while increasing
 * movement speed.
 * 
 * @author Antoni Nowaczyk
 * @id 1934899
 * @author Guy Lewy
 * @id 1954962
 */
public class EnemyScout extends Enemy {

    /**
     * Sets the y position of the enemy and sets it's life points and speed.
     * 
     * @param yPos Integer representing the y position at which the enemy will be
     *             drawn
     */
    EnemyScout(int yPos) {
        super(yPos);
        enemyType = 2;
        lifePointsLeft = 1;
        enemySpeed = 4;
        moneyCarried = rand.nextInt(1) + 1; // Set bound to the max value that should be given
        doesShoot = false;
    }
}