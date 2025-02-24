package main.com.github.fariadavi.game.shots;

import main.com.github.fariadavi.CanvasCollidableImageComponent;
import main.com.github.fariadavi.CanvasPanel;
import main.com.github.fariadavi.game.ships.enemy.Enemy;

import static main.com.github.fariadavi.utils.SpriteMappings.SPRITE_PLAYER_SIMPLE_SHOT_PATH;

public class PlayerSimpleShot extends NewShot {

    public PlayerSimpleShot(int x, int y) {
        super(SPRITE_PLAYER_SIMPLE_SHOT_PATH, x, y, true);
        this.projectileSpeed = 512;

        setCollisionInsetX(6);
        setCollisionInsetY(14);
    }

    public void update(double dt, CanvasPanel canvasPanel) {
        if (!this.isActive()) return;

        if (this.isActive() && !this.isVisible())
            this.deactivate();

        Enemy[] enemies = canvasPanel.getEnemyShips();
        CanvasCollidableImageComponent collidedEnemy = this.checkForCollision(enemies);
        if (collidedEnemy != null) {
            this.deactivate();

            Enemy enemy = (Enemy) collidedEnemy;
            enemy.hit(1, canvasPanel);
            canvasPanel.addBountyToScore(enemy);
            return;
        }

        this.moveX(MOVE_DIRECTION_RIGHT, dt, this.projectileSpeed * canvasPanel.getPlayerSpeedMultiplier());
    }
}
