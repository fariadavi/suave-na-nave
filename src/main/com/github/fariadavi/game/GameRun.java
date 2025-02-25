package main.com.github.fariadavi.game;

import main.com.github.fariadavi.CanvasGroupComponent;
import main.com.github.fariadavi.CanvasPanel;
import main.com.github.fariadavi.game.decorations.ShipExplosion;
import main.com.github.fariadavi.game.decorations.background.GameBackground;
import main.com.github.fariadavi.game.decorations.hud.HeadsUpDisplay;
import main.com.github.fariadavi.game.items.MissileDrop;
import main.com.github.fariadavi.game.ships.Ship;
import main.com.github.fariadavi.game.ships.enemy.Enemy;
import main.com.github.fariadavi.game.ships.enemy.EnemyType;
import main.com.github.fariadavi.game.ships.player.Player;
import main.com.github.fariadavi.game.shots.EnemySimpleShot;
import main.com.github.fariadavi.game.shots.PlayerMissileShot;
import main.com.github.fariadavi.game.shots.PlayerSimpleShot;

import java.awt.*;

public class GameRun extends CanvasGroupComponent {

    public static final int MAX_HEALTH = 3;
    public static final int MAX_MISSILES = 3;

    private final GameBackground background;
    private final HeadsUpDisplay hud;
    private final Player player;
    private final GameOver gameOver;

    private int score = 0, scoreBuffer = 0;
    private double dtScore = 0d;

    private final EnemyType[] enemyTypes = {
            EnemyType.RED_UFO,
            EnemyType.GREEN_FIRE,
            EnemyType.BIG_BANG,
            EnemyType.DEATH_FISH
    };
    private final Enemy[] enemies = new Enemy[64];
    private final double[] dtEnemies = new double[enemyTypes.length];
    private final ShipExplosion[] explosions = new ShipExplosion[64];

    private final PlayerSimpleShot[] playerSimpleShots = new PlayerSimpleShot[8];
    private final PlayerMissileShot[] playerMissileShots = new PlayerMissileShot[3];

    private final EnemySimpleShot[] enemySimpleShots = new EnemySimpleShot[32];

    private final MissileDrop[] missileDrops = new MissileDrop[16];

    public GameRun() {
        super(false);
        this.background = new GameBackground();
        this.hud = new HeadsUpDisplay(MAX_HEALTH, MAX_MISSILES);
        this.player = new Player(80, 280);
        for (int i = 0; i < this.dtEnemies.length; i++)
            this.dtEnemies[i] = this.enemyTypes[i].getRespawnInterval();
        this.gameOver = new GameOver();
    }

    public int getScore() {
        return this.score;
    }

    public Enemy getEnemyShipByIndex(Integer targetIndex) {
        if (targetIndex == null || targetIndex < 0 || targetIndex >= this.enemies.length) return null;

        return this.enemies[targetIndex];
    }

    public Enemy[] getEnemyShips() {
        return this.enemies;
    }

    public Player getPlayerShip() {
        return this.player;
    }

    public double[] getPlayerPosition() {
        return this.player.getPosition();
    }

    public int getPlayerHealthPoints() {
        return this.player.getHealthPoints();
    }

    public int getPlayerMissileCharges() {
        return this.player.getNumMissileCharges();
    }

    public void addPlayerMissileCharges(int missileCharges) {
        this.player.addMissileCharges(missileCharges);
    }

    public double getPlayerMissileCooldownPercentage() {
        return this.player.getMissileCooldownPercentage();
    }

    public boolean isPlayerTurboActive() {
        return this.player.isBoosted();
    }

    public double getPlayerTurboChargePercentage() {
        return this.player.getTurboChargePercentage();
    }

    public double getPlayerSpeedMultiplier() {
        return this.player.getSpeedMultiplier();
    }

    public void createPlayerSimpleShot() {
        for (int i = 0; i < this.playerSimpleShots.length; i++)
            if (this.playerSimpleShots[i] == null || !this.playerSimpleShots[i].isActive()) {
                this.playerSimpleShots[i] = new PlayerSimpleShot(
                        (int) (this.player.getPX() + this.player.getWidth() - this.player.getCollisionInsetX()),
                        (int) (this.player.getPY() + (this.player.getHeight() / 2))
                );
                break;
            }
    }

    public void createPlayerMissileShot() {
        for (int i = 0; i < this.playerMissileShots.length; i++)
            if (this.playerMissileShots[i] == null || !this.playerMissileShots[i].isActive()) {
                this.playerMissileShots[i] = new PlayerMissileShot(
                        (int) (this.player.getCollidableEndX()),
                        (int) (this.player.getPY() + (this.player.getHeight() / 2)),
                        getNearestEnemyToPlayer()
                );
                break;
            }
    }

    public void createEnemySimpleShot(Enemy enemy) {
        for (int i = 0; i < this.enemySimpleShots.length; i++)
            if (this.enemySimpleShots[i] == null || !this.enemySimpleShots[i].isActive()) {
                this.enemySimpleShots[i] = new EnemySimpleShot(
                        (int) enemy.getCollidableStartX(),
                        (int) (enemy.getPY() + (enemy.getHeight() / 2))
                );
                break;
            }
    }

    private Integer getNearestEnemyToPlayer() {
        double playerCenterX = this.player.getCollidableCenterX();
        double playerCenterY = this.player.getCollidableCenterY();

        Integer nearestEnemyIndex = null;
        Double nearestEnemyDistance = null;
        for (int i = 0; i < this.enemies.length; i++) {
            Enemy enemy = this.enemies[i];
            if (enemy == null || !enemy.isActive()) continue;

            double deltaX = Math.abs(enemy.getCollidableCenterX() - playerCenterX);
            double deltaY = Math.abs(enemy.getCollidableCenterY() - playerCenterY);
            double deltaHip = Math.sqrt(deltaX * deltaX + deltaY * deltaY);

            if (nearestEnemyDistance == null || deltaHip < nearestEnemyDistance) {
                nearestEnemyIndex = i;
                nearestEnemyDistance = deltaHip;
            }
        }

        return nearestEnemyIndex;
    }

    public void addBountyToScore(Enemy collidedEnemy) {
        for (EnemyType enemyType : this.enemyTypes)
            if (collidedEnemy.getClass().equals(enemyType.getEnemyClass()))
                this.scoreBuffer += enemyType.getBounty();
    }

    public void explodeShip(Ship ship) {
        for (int i = 0; i < this.explosions.length; i++)
            if (this.explosions[i] == null || !this.explosions[i].isActive()) {
                this.explosions[i] = new ShipExplosion((int) ship.getPX(), (int) ship.getPY());
                break;
            }
    }

    public void dropMissile(int x, int y) {
        for (int i = 0; i < this.missileDrops.length; i++)
            if (this.missileDrops[i] == null || !this.missileDrops[i].isActive()) {
                this.missileDrops[i] = new MissileDrop(x, y);
                break;
            }
    }

    public void update(double dt, CanvasPanel canvasPanel) {
        if (!this.isActive()) return;

        this.hud.update(dt, canvasPanel);
        for (ShipExplosion explosion : this.explosions) {
            if (explosion != null)
                explosion.update(dt, canvasPanel);
        }

        if (this.dtScore < 0.02)
            this.dtScore += dt;
        else if (this.scoreBuffer > 0) {
            this.score++;
            this.scoreBuffer--;
            this.dtScore = 0;
        }

        this.gameOver.update(dt, canvasPanel);
        if (this.player.getHealthPoints() < 0) {
            if (!this.gameOver.isActive())
                this.gameOver.activate();

            return;
        }

        this.background.update(dt, canvasPanel);
        this.player.update(dt, canvasPanel);

        for (Enemy enemy : this.enemies)
            if (enemy != null)
                enemy.update(dt, canvasPanel);

        for (EnemySimpleShot enemySimpleShot : this.enemySimpleShots)
            if (enemySimpleShot != null)
                enemySimpleShot.update(dt, canvasPanel);

        for (PlayerSimpleShot playerSimpleShot : this.playerSimpleShots)
            if (playerSimpleShot != null)
                playerSimpleShot.update(dt, canvasPanel);

        for (PlayerMissileShot playerMissileShot : this.playerMissileShots)
            if (playerMissileShot != null)
                playerMissileShot.update(dt, canvasPanel);

        for (MissileDrop missileDrop : this.missileDrops)
            if (missileDrop != null)
                missileDrop.update(dt, canvasPanel);

        for (int i = 0; i < this.enemyTypes.length; i++) {
            if (this.score < this.enemyTypes[i].getRespawnThreshold())
                continue;

            this.dtEnemies[i] += dt;
            if (this.dtEnemies[i] < enemyTypes[i].getRespawnInterval())
                continue;

            this.dtEnemies[i] = 0;
            for (int j = 0; j < this.enemies.length; j++)
                if (this.enemies[j] == null || !this.enemies[j].isActive()) {
                    try {
                        this.enemies[j] = this.enemyTypes[i].getEnemyClass().newInstance();
                    } catch (InstantiationException | IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }

                    break;
                }
        }
    }

    @Override
    public void draw(Graphics2D g2d) {
        if (!this.isActive()) return;

        this.background.draw(g2d);

        for (Enemy enemy : this.enemies)
            if (enemy != null)
                enemy.draw(g2d);
//        for (int i = 0; i < this.enemies.length; i++) {   // DEBUG ENEMIES
//            Enemy enemy = this.enemies[i];
//            if (enemy != null) {
//                g2d.setColor(Color.GREEN);
//                g2d.setFont(new Font("Arial", Font.BOLD, 36));
//                g2d.drawString(String.valueOf(i), (int) enemy.getCollidableStartX(), (int) enemy.getCollidableEndY());
//            }
//        }

        for (ShipExplosion explosion : this.explosions)
            if (explosion != null)
                explosion.draw(g2d);

        for (EnemySimpleShot enemySimpleShot : this.enemySimpleShots)
            if (enemySimpleShot != null)
                enemySimpleShot.draw(g2d);

        for (PlayerSimpleShot playerSimpleShot : this.playerSimpleShots)
            if (playerSimpleShot != null)
                playerSimpleShot.draw(g2d);

        for (PlayerMissileShot playerMissileShot : this.playerMissileShots)
            if (playerMissileShot != null)
                playerMissileShot.draw(g2d);

        for (MissileDrop missileDrop : this.missileDrops)
            if (missileDrop != null)
                missileDrop.draw(g2d);

        this.player.draw(g2d);

        this.hud.draw(g2d);
        this.gameOver.draw(g2d);
    }
}
