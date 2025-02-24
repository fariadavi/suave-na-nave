package main.com.github.fariadavi.game.ships.enemy;

public enum EnemyType {

    RED_UFO(RedUFO.class, 10, 0, 1.5d, 0, 0.1),
    GREEN_FIRE(GreenFire.class, 50, 3, 4d, 100, 0.2),
    BIG_BANG(BigBang.class, 100, 0, 6d, 420, 0.3),
    DEATH_FISH(DeathFish.class, 200, 1.5, 8d, 1000, 0.4);

    private final Class<? extends Enemy> enemyClass;
    private final int bounty;
    private final double shootingInterval;
    private final double respawnInterval;
    private final double respawnThreshold;
    private final double missileDropChance;

    EnemyType(
            Class<? extends Enemy> enemyClass,
            int bounty,
            double shootingInterval,
            double respawnInterval,
            int respawnThreshold,
            double missileDropChance
    ) {
        this.enemyClass = enemyClass;
        this.bounty = bounty;
        this.shootingInterval = shootingInterval;
        this.respawnInterval = respawnInterval;
        this.respawnThreshold = respawnThreshold;
        this.missileDropChance = missileDropChance;
    }

    public Class<? extends Enemy> getEnemyClass() {
        return this.enemyClass;
    }

    public int getBounty() {
        return this.bounty;
    }

    public double getShootingInterval() {
        return shootingInterval;
    }

    public double getRespawnInterval() {
        return this.respawnInterval;
    }

    public double getRespawnThreshold() {
        return this.respawnThreshold;
    }

    public double getMissileDropChance() {
        return this.missileDropChance;
    }

    public static EnemyType getEnemyType(Class<? extends Enemy> enemyClass) {
        for (EnemyType enemyType : values())
            if (enemyClass.equals(enemyType.enemyClass))
                return enemyType;

        return null;
    }
}
