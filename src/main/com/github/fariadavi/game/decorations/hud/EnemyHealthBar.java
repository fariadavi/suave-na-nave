package main.com.github.fariadavi.game.decorations.hud;

import main.com.github.fariadavi.CanvasComponent;
import main.com.github.fariadavi.game.ships.enemy.Enemy;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class EnemyHealthBar extends CanvasComponent {

    public static final Color HEALTH_BAR_COLOR = new Color(102, 0, 0);
    public static final int HEALTH_BAR_UNIT_GAP = 2;
    public static final int HEALTH_BAR_OFFSET_TOP = 4;

    private int healthPoints;
    private final int healthBarWidth;
    private final int healthBarUnitWidth;

    public EnemyHealthBar(int healthPoints, int healthBarWidth) {
        super(true);

        this.healthPoints = healthPoints > 1 ? healthPoints : 0;
        this.healthBarWidth = healthBarWidth;
        this.healthBarUnitWidth =
                healthPoints > 1
                        ? (healthBarWidth - (HEALTH_BAR_UNIT_GAP * (healthPoints - 1))) / healthPoints
                        : 0;
    }

    public void setHealthPoints(int healthPoints) {
        this.healthPoints = healthPoints;
    }

    public void updateLocation(Enemy enemy) {
        setPX(enemy.getPX() + enemy.getCollisionOffsetX() + enemy.getCollisionInsetX());
        setPY(enemy.getPY() + enemy.getHeight() - enemy.getCollisionInsetY() + HEALTH_BAR_OFFSET_TOP);
    }

    @Override
    public void draw(Graphics2D g2d) {
        if (healthPoints <= 0) return;

        g2d.setPaint(HEALTH_BAR_COLOR);
        for (int i = 0; i < this.healthPoints; i++) {
            g2d.fill(new Rectangle2D.Double(
                    this.getPX() + i * (healthBarUnitWidth + HEALTH_BAR_UNIT_GAP),
                    (int) this.getPY(),
                    healthBarUnitWidth,
                    (int) (healthBarWidth / 10d))
            );
        }
    }

    @Override
    public boolean isVisible() {
        return true;
    }
}
