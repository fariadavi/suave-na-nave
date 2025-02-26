package main.com.github.fariadavi.game.shots;

import main.com.github.fariadavi.CanvasPanel;
import main.com.github.fariadavi.game.ships.enemy.Enemy;

import static main.com.github.fariadavi.utils.SpriteMappings.SPRITE_PLAYER_MISSILE_2_PATH;

public class PlayerMissileShot extends Shot {

    private final Integer targetIndex;

    public PlayerMissileShot(int x, int y, Integer targetIndex) {
        super(SPRITE_PLAYER_MISSILE_2_PATH, x, y, true);
        this.targetIndex = targetIndex;
        this.projectileSpeed = 512;

        setCollisionInsetX(5);
        setCollisionInsetY(10);
    }

    public void update(double dt, CanvasPanel canvasPanel) {
        if (!this.isActive()) return;

        if (this.isActive() && !this.isVisible())
            this.deactivate();

        Enemy enemy = canvasPanel.getEnemyShipByIndex(targetIndex);
        if (enemy != null && this.checkForCollision(enemy)) {
            this.deactivate();

            enemy.hit(10, canvasPanel);
            canvasPanel.addBountyToScore(enemy);
            return;
        }

        if (enemy == null) {
            this.moveX(MOVE_DIRECTION_RIGHT, dt, this.projectileSpeed * canvasPanel.getPlayerSpeedMultiplier());
            return;
        }

        double deltaX = Math.abs(enemy.getCollidableCenterX() - this.getCollidableCenterX());
        double deltaY = Math.abs(enemy.getCollidableCenterY() - this.getCollidableCenterY());
        double deltaHip = Math.sqrt(deltaX * deltaX + deltaY * deltaY);

        double directionX = enemy.getCollidableCenterX() > this.getCollidableCenterX()
                ? MOVE_DIRECTION_RIGHT
                : MOVE_DIRECTION_LEFT;
        if (directionX == MOVE_DIRECTION_RIGHT)
            setComponentImage(SPRITE_PLAYER_MISSILE_2_PATH);
        else
            setComponentImage(SPRITE_PLAYER_MISSILE_2_PATH.replace(".png", "_flipped.png"));

        this.moveX(
                directionX,
                dt,
                ((deltaX / deltaHip) * this.projectileSpeed) * canvasPanel.getPlayerSpeedMultiplier()
        );
        this.moveY(
                enemy.getCollidableCenterY() > this.getCollidableCenterY()
                        ? MOVE_DIRECTION_DOWN
                        : MOVE_DIRECTION_UP,
                dt,
                ((deltaY / deltaHip) * this.projectileSpeed) * canvasPanel.getPlayerSpeedMultiplier()
        );
    }
}
