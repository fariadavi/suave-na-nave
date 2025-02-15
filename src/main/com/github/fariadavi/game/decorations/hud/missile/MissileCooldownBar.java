package main.com.github.fariadavi.game.decorations.hud.missile;

import main.com.github.fariadavi.CanvasComponent;
import main.com.github.fariadavi.CanvasPanel;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class MissileCooldownBar extends CanvasComponent {

    public static final double WIDTH_MISSILE_COOLDOWN_BAR = 50;
    public static final int HEIGHT_MISSILE_COOLDOWN_BAR = 5;

    private double missileCooldownPercentage;

    protected MissileCooldownBar() {
        super(true);

        this.missileCooldownPercentage = 0;
    }

    public void update(double dt, CanvasPanel canvasPanel) {
        double[] playerPosition = canvasPanel.getPlayerPosition();
        this.setPX(playerPosition[0] + 65);
        this.setPY(playerPosition[1] + 35);

        this.missileCooldownPercentage = canvasPanel.getPlayerMissileCooldownPercentage();
    }

    @Override
    public boolean isVisible() {
        return true;
    }

    @Override
    public void draw(Graphics2D g2d) {
        if (this.missileCooldownPercentage > 0 && this.missileCooldownPercentage < 1) {
            g2d.draw(new Rectangle2D.Double(
                    this.getPX(),
                    this.getPY(),
                    WIDTH_MISSILE_COOLDOWN_BAR,
                    HEIGHT_MISSILE_COOLDOWN_BAR
            ));
            g2d.setPaint(new GradientPaint(
                    0,
                    (int) this.getPY() + 1,
                    Color.cyan,
                    0,
                    (int) this.getPY() + HEIGHT_MISSILE_COOLDOWN_BAR - 1,
                    Color.black
            ));
            g2d.fill(new Rectangle2D.Double(
                    (int) this.getPX() + 1,
                    (int) this.getPY() + 1,
                    WIDTH_MISSILE_COOLDOWN_BAR * this.missileCooldownPercentage - 1,
                    HEIGHT_MISSILE_COOLDOWN_BAR - 1
            ));
        }
    }
}
