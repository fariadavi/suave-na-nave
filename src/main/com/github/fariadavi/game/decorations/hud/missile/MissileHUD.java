package main.com.github.fariadavi.game.decorations.hud.missile;

import main.com.github.fariadavi.CanvasComponent;
import main.com.github.fariadavi.CanvasGroupComponent;
import main.com.github.fariadavi.CanvasPanel;

import static main.com.github.fariadavi.utils.SpriteMappings.SPRITE_UI_MISSILE_INDICATOR_PATH;

public class MissileHUD extends CanvasGroupComponent {

    private final int maxMissiles;

    private final MissileIndicator[] missileIndicators;
    private final MissileCooldownBar cooldownBar;

    public MissileHUD(int x, int y, int maxMissiles) {
        super(x, y, true);

        this.maxMissiles = maxMissiles;
        this.missileIndicators = new MissileIndicator[this.maxMissiles];
        for (int i = 0; i < this.missileIndicators.length; i++) {
            this.missileIndicators[i] = createMissileIndicator(i);
        }

        this.cooldownBar = new MissileCooldownBar();
        setComponents(this.missileIndicators, new CanvasComponent[]{this.cooldownBar});
    }

    public void update(double dt, CanvasPanel canvasPanel) {
        this.cooldownBar.update(dt, canvasPanel);

        boolean updateComponents = false;
        int missileCharges = canvasPanel.getPlayerMissileCharges();
        if (missileCharges >= 0 && missileCharges < this.maxMissiles && this.missileIndicators[missileCharges] != null) {
            for (int i = missileCharges; i < this.missileIndicators.length; i++) {
                updateComponents = true;
                this.missileIndicators[i] = null;
            }
        } else if (missileCharges > 0 && missileCharges <= this.maxMissiles && this.missileIndicators[missileCharges - 1] == null)
            for (int i = missileCharges - 1; i >= 0; i--) {
                updateComponents = true;
                this.missileIndicators[i] = createMissileIndicator(i);
            }

        if (updateComponents)
            setComponents(this.missileIndicators, new CanvasComponent[]{this.cooldownBar});
    }

    private MissileIndicator createMissileIndicator(int missileNumber) {
        return new MissileIndicator(
                SPRITE_UI_MISSILE_INDICATOR_PATH,
                (int) this.getPX() - (missileNumber * 48),
                (int) this.getPY(),
                true
        );
    }
}
