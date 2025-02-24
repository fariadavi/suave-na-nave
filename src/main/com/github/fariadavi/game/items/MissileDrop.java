package main.com.github.fariadavi.game.items;

import main.com.github.fariadavi.CanvasCollidableImageComponent;
import main.com.github.fariadavi.CanvasPanel;

import static main.com.github.fariadavi.game.ships.player.Player.PLAYER_MAX_MISSILE_CHARGES;
import static main.com.github.fariadavi.utils.SpriteMappings.SPRITE_DROP_MISSILE_PATH;

public class MissileDrop extends CanvasCollidableImageComponent {

    public MissileDrop(int x, int y) {
        super(SPRITE_DROP_MISSILE_PATH, x, y, true);
    }

    public void update(double dt, CanvasPanel canvasPanel) {
        if (!this.isActive()) return;

        this.moveX(MOVE_DIRECTION_LEFT, dt, 64 * canvasPanel.getPlayerSpeedMultiplier());

        if (canvasPanel.getPlayerMissileCharges() < PLAYER_MAX_MISSILE_CHARGES
                && this.checkForCollision(canvasPanel.getPlayerShip())) {
            this.deactivate();
            canvasPanel.addPlayerMissileCharges(1);
        }
    }
}
