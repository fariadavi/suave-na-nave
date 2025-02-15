package main.com.github.fariadavi.game.decorations.hud.turbo;

import main.com.github.fariadavi.CanvasComponent;

import java.awt.*;

import static main.com.github.fariadavi.game.decorations.hud.turbo.TurboHUD.WIDTH_TURBO_CHARGE_BAR;

public class TurboChargeBarTextInfo extends CanvasComponent {

    private TurboStatus turboStatus;

    protected TurboChargeBarTextInfo(int x, int y) {
        super(x, y, true);
    }

    public void setTurboStatus(TurboStatus turboStatus) {
        this.turboStatus = turboStatus;
    }

    @Override
    public void draw(Graphics2D g2d) {
        if (this.turboStatus == null) return;

        g2d.setFont(new Font("Verdana", Font.BOLD, 14));

        String turboChargeText = "TURBO " + this.turboStatus;
        int turboChargeTextWidth = g2d.getFontMetrics().stringWidth(turboChargeText);

        g2d.setColor(this.turboStatus.getColor());
        g2d.drawString(
                turboChargeText,
                (int) this.getPX() + (WIDTH_TURBO_CHARGE_BAR - turboChargeTextWidth) / 2,
                (int) this.getPY()
        );
    }

    @Override
    public boolean isVisible() {
        return true;
    }
}
