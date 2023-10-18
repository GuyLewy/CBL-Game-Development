public class EnemyTank extends Enemy implements Drawable {

    EnemyTank(int yPos) {
        super(yPos);
        lifePointsLeft = 3;
        enemySpeed = 2;
        projectileDelay = 300;
    }
