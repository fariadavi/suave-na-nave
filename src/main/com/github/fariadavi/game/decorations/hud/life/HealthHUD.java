package main.com.github.fariadavi.game.decorations.hud.life;

import main.com.github.fariadavi.CanvasGroupComponent;
import main.com.github.fariadavi.CanvasPanel;

import static main.com.github.fariadavi.utils.SpriteMappings.SPRITE_UI_LIFE_INDICATOR_PATH;

public class HealthHUD extends CanvasGroupComponent {

    private final int maxHP;

    private final HealthPointIndicator[] healthPointIndicators;

    public HealthHUD(int x, int y, int maxHP) {
        super(x, y, true);

        this.maxHP = maxHP;
        this.healthPointIndicators = new HealthPointIndicator[this.maxHP];
        for (int i = 0; i < healthPointIndicators.length; i++) {
            healthPointIndicators[i] = new HealthPointIndicator(
                    SPRITE_UI_LIFE_INDICATOR_PATH,
                    20 + (i * 48),
                    20,
                    true
            );
        }

        setComponents(this.healthPointIndicators);
    }

    public void update(double dt, CanvasPanel canvasPanel) {
        boolean updateComponents = false;
        int currentHP = canvasPanel.getPlayerHealthPoints();
        if (currentHP >= 0 && currentHP < this.maxHP && this.healthPointIndicators[currentHP] != null)
            for (int i = currentHP; i < this.healthPointIndicators.length; i++) {
                updateComponents = true;
                this.healthPointIndicators[i] = null;
            }

        if (updateComponents)
            setComponents(this.healthPointIndicators);
    }
}
