/**
 * Scout enemy inherits from enemy and overrides shoot() while increasing
 * movement speed.
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
        lifePointsLeft = 1;
        enemySpeed = 6;
        moneyCarried = rand.nextInt(1) + 1; // Set bound to the max value that should be given
    }

    /**
     * Removes the scout's ability to shoot.
     */
    @Override
    public void shoot() {
    }
}