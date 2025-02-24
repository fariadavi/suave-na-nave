package main.com.github.fariadavi.game.shots;

import main.com.github.fariadavi.CanvasPanel;
import main.com.github.fariadavi.game.ships.player.Player;

import static main.com.github.fariadavi.utils.SpriteMappings.SPRITE_ENEMY_SIMPLE_SHOT_PATH;

public class EnemySimpleShot extends NewShot {

    public EnemySimpleShot(int x, int y) {
        super(SPRITE_ENEMY_SIMPLE_SHOT_PATH, x, y, true);
        this.projectileSpeed = 256;

        setCollisionInsetX(7);
        setCollisionInsetY(9);
    }

    public void update(double dt, CanvasPanel canvasPanel) {
        if (!this.isActive()) return;

        if (this.isActive() && !this.isVisible())
            this.deactivate();

        Player player = canvasPanel.getPlayerShip();
        if (!player.isInvulnerable() && this.checkForCollision(player)) {
            this.deactivate();
            player.hit(1, canvasPanel);

            return;
        }

        this.moveX(MOVE_DIRECTION_LEFT, dt, this.projectileSpeed * canvasPanel.getPlayerSpeedMultiplier());
    }
}
