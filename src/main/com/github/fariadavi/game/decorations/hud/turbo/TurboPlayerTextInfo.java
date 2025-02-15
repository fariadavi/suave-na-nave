package main.com.github.fariadavi.game.decorations.hud.turbo;

import main.com.github.fariadavi.CanvasComponent;
import main.com.github.fariadavi.CanvasPanel;

import java.awt.*;

public class TurboPlayerTextInfo extends CanvasComponent {

    public static final int TURBO_STATUS_READY_TEXT_DURATION_SECONDS = 1;

    private TurboStatus turboStatus;
    private double dtStatusReady;

    protected TurboPlayerTextInfo() {
        super(true);
    }

    public void setTurboStatus(TurboStatus turboStatus) {
        this.turboStatus = turboStatus;
    }

    public void update(double dt, CanvasPanel canvasPanel) {
        if (this.turboStatus == null) return;

        if (this.turboStatus == TurboStatus.READY) {
            if (this.dtStatusReady < TURBO_STATUS_READY_TEXT_DURATION_SECONDS)
                this.dtStatusReady += dt;

        } else if (this.dtStatusReady > 0) {
            this.dtStatusReady = 0;
        }
    }

    @Override
    public void draw(Graphics2D g2d) {
        if (this.turboStatus != TurboStatus.READY || this.dtStatusReady > TURBO_STATUS_READY_TEXT_DURATION_SECONDS)
            return;

        g2d.setFont(new Font("Verdana", Font.BOLD, 14));
        g2d.setColor(this.turboStatus.getColor());
        g2d.drawString(
                "TURBO READY!",
                (int) this.getPX() + 40,
                (int) this.getPY() + 95
        );
    }

    @Override
    public boolean isVisible() {
        return true;
    }
}
