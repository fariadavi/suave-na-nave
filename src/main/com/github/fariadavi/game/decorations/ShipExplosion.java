package main.com.github.fariadavi.game.decorations;

import main.com.github.fariadavi.CanvasImageComponent;
import main.com.github.fariadavi.CanvasPanel;

import static main.com.github.fariadavi.utils.SpriteMappings.SPRITE_EXPLOSION_PATTERN_PATH;

public class ShipExplosion extends CanvasImageComponent {

    private static final int INITIAL_EXPLOSION_IMAGE_INDEX = 1;
    private static final int FINAL_EXPLOSION_IMAGE_INDEX = 16;
    private int explosionIndex = INITIAL_EXPLOSION_IMAGE_INDEX;

    private double dtExplosion;

    public ShipExplosion(int x, int y) {
        super(
                SPRITE_EXPLOSION_PATTERN_PATH.replace("%i%", String.valueOf(INITIAL_EXPLOSION_IMAGE_INDEX)),
                x,
                y,
                true
        );
    }

    public void update(double dt, CanvasPanel canvasPanel) {
        if (!this.isActive()) return;

        this.moveX(MOVE_DIRECTION_LEFT, dt, 64 * canvasPanel.getPlayerSpeedMultiplier());

        dtExplosion += dt;
        if (dtExplosion < .05)
            return;

        if (this.explosionIndex >= FINAL_EXPLOSION_IMAGE_INDEX) {
            this.deactivate();
            return;
        }

        this.setComponentImage(SPRITE_EXPLOSION_PATTERN_PATH.replace("%i%", String.valueOf(++explosionIndex)));
        dtExplosion = 0;

    }
}
