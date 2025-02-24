package main.com.github.fariadavi.game.decorations.hud;

import main.com.github.fariadavi.CanvasGroupComponent;
import main.com.github.fariadavi.CanvasPanel;
import main.com.github.fariadavi.game.decorations.hud.life.HealthHUD;
import main.com.github.fariadavi.game.decorations.hud.missile.MissileHUD;
import main.com.github.fariadavi.game.decorations.hud.score.ScoreHUD;
import main.com.github.fariadavi.game.decorations.hud.turbo.TurboHUD;

import static main.com.github.fariadavi.MainFrame.WINDOW_HEIGHT;
import static main.com.github.fariadavi.MainFrame.WINDOW_WIDTH;

public class HeadsUpDisplay extends CanvasGroupComponent {

    private final HealthHUD healthHUD;
    private final MissileHUD missileHUD;
    private final TurboHUD turboHUD;
    private final ScoreHUD score;

    public HeadsUpDisplay(int maxLives, int maxMissiles) {
        super(true);

        this.healthHUD = new HealthHUD(20, 20, maxLives);
        this.missileHUD = new MissileHUD(WINDOW_WIDTH - 20, 20, maxMissiles);
        this.turboHUD = new TurboHUD(20, WINDOW_HEIGHT - 40);
        this.score = new ScoreHUD(WINDOW_WIDTH / 2, 60);

        setComponents(
                this.healthHUD,
                this.missileHUD,
                this.turboHUD,
                this.score
        );
    }

    public void update(double dt, CanvasPanel canvasPanel) {
        this.healthHUD.update(dt, canvasPanel);
        this.missileHUD.update(dt, canvasPanel);
        this.turboHUD.update(dt, canvasPanel);
        this.score.update(dt, canvasPanel);
    }
}
