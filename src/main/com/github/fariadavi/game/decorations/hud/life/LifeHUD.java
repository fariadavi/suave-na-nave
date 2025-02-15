package main.com.github.fariadavi.game.decorations.hud.life;

import main.com.github.fariadavi.CanvasGroupComponent;
import main.com.github.fariadavi.CanvasPanel;

import static main.com.github.fariadavi.utils.SpriteMappings.SPRITE_UI_LIFE_INDICATOR_PATH;

public class LifeHUD extends CanvasGroupComponent {

    private final int maxLives;

    private final LifeIndicator[] lifeIndicators;

    public LifeHUD(int x, int y, int maxLives) {
        super(x, y, true);

        this.maxLives = maxLives;
        this.lifeIndicators = new LifeIndicator[this.maxLives];
        for (int i = 0; i < lifeIndicators.length; i++) {
            lifeIndicators[i] = new LifeIndicator(
                    SPRITE_UI_LIFE_INDICATOR_PATH,
                    20 + (i * 48),
                    20,
                    true
            );
        }

        setComponents(this.lifeIndicators);
    }

    public void update(double dt, CanvasPanel canvasPanel) {
        boolean updateComponents = false;
        int currentLives = canvasPanel.getPlayerLives();
        if (currentLives >= 0 && currentLives < this.maxLives && this.lifeIndicators[currentLives] != null)
            for (int i = currentLives; i < this.lifeIndicators.length; i++) {
                updateComponents = true;
                this.lifeIndicators[i] = null;
            }

        if (updateComponents)
            setComponents(this.lifeIndicators);
    }
}
