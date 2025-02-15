package main.com.github.fariadavi.game.decorations.hud.turbo;

import main.com.github.fariadavi.CanvasComponent;
import main.com.github.fariadavi.CanvasPanel;

import java.awt.*;
import java.awt.geom.Rectangle2D;

import static main.com.github.fariadavi.game.decorations.hud.turbo.TurboHUD.HEIGHT_TURBO_CHARGE_BAR;
import static main.com.github.fariadavi.game.decorations.hud.turbo.TurboHUD.WIDTH_TURBO_CHARGE_BAR;

public class TurboChargeBar extends CanvasComponent {

    private TurboStatus turboStatus;

    private double turboChargePercentage;

    protected TurboChargeBar(int x, int y) {
        super(x, y, true);

        this.turboChargePercentage = 0;
    }

    public void setTurboStatus(TurboStatus turboStatus) {
        this.turboStatus = turboStatus;
    }

    public void update(double dt, CanvasPanel canvasPanel) {
        this.turboChargePercentage = canvasPanel.getPlayerTurboChargePercentage();
    }

    @Override
    public void draw(Graphics2D g2d) {
        g2d.setColor(Color.white);
        g2d.draw(new Rectangle2D.Double(
                this.getPX(),
                this.getPY(),
                WIDTH_TURBO_CHARGE_BAR,
                HEIGHT_TURBO_CHARGE_BAR
        ));
        g2d.setPaint(new GradientPaint(
                0,
                (int) this.getPY() + 1,
                this.turboStatus.getColor(),
                0,
                (int) this.getPY() + HEIGHT_TURBO_CHARGE_BAR - 1,
                Color.black
        ));
        g2d.fill(new Rectangle2D.Double(
                this.getPX() + 1,
                this.getPY() + 1,
                WIDTH_TURBO_CHARGE_BAR * turboChargePercentage - 1,
                HEIGHT_TURBO_CHARGE_BAR - 1
        ));
    }

    @Override
    public boolean isVisible() {
        return true;
    }
}
