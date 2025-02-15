package main.com.github.fariadavi.game.decorations.hud;

import main.com.github.fariadavi.CanvasGroupComponent;
import main.com.github.fariadavi.CanvasPanel;
import main.com.github.fariadavi.game.decorations.hud.life.LifeHUD;
import main.com.github.fariadavi.game.decorations.hud.missile.MissileHUD;
import main.com.github.fariadavi.game.decorations.hud.turbo.TurboHUD;

public class HeadsUpDisplay extends CanvasGroupComponent {

    private final LifeHUD lifeHUD;
    private final MissileHUD missileHUD;
    private final TurboHUD turboHUD;

    public HeadsUpDisplay(int maxLives, int maxMissiles) {
        super(true);

        this.lifeHUD = new LifeHUD(20, 20, maxLives);
        this.missileHUD = new MissileHUD(722, 20, maxMissiles);
        this.turboHUD = new TurboHUD(25, 530);

        setComponents(
                this.lifeHUD,
                this.missileHUD,
                this.turboHUD
        );
    }

    public void update(double dt, CanvasPanel canvasPanel) {
        this.lifeHUD.update(dt, canvasPanel);
        this.missileHUD.update(dt, canvasPanel);
        this.turboHUD.update(dt, canvasPanel);
    }
}
