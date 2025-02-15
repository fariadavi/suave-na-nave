package main.com.github.fariadavi.game.decorations.background;

import main.com.github.fariadavi.CanvasComponent;
import main.com.github.fariadavi.CanvasPanel;

import java.awt.*;
import java.awt.geom.Rectangle2D;

import static main.com.github.fariadavi.MainFrame.WINDOW_HEIGHT;
import static main.com.github.fariadavi.MainFrame.WINDOW_WIDTH;
import static main.com.github.fariadavi.utils.Randomizer.randInt;

public class BgStar extends CanvasComponent {

    public static final int MIN_COLOR_VALUE = 40;
    public static final int MAX_COLOR_VALUE = 255;

    public static final int WIDTH = 3;
    public static final int HEIGHT = 2;

    private int color;

    public BgStar() {
        super(randInt(0, WINDOW_WIDTH), randInt(0, WINDOW_HEIGHT), true);
        this.color = randInt(MIN_COLOR_VALUE, MAX_COLOR_VALUE);
    }

    public int getColor() {
        return this.color;
    }

    public void respawn() {
        this.color = randInt(MIN_COLOR_VALUE, MAX_COLOR_VALUE);
        this.setPX(WINDOW_WIDTH - 1);
        this.setPY(randInt(0, WINDOW_HEIGHT));
    }

    private double getProximityMultiplier() {
//        return Math.pow((this.color / 100d) + 1d, 2d);
        return 1;
    }

    @Override
    public boolean isVisible() {
        double proximityMultiplier = getProximityMultiplier();

        double starWidth = WIDTH * proximityMultiplier;
        double starHeight = HEIGHT * proximityMultiplier;

        double startX = this.getPX();
        double endX = startX + starWidth;
        double startY = this.getPY();
        double endY = startY + starHeight;

        return endX > 0 && endY > 0 && startX < WINDOW_WIDTH && startY < WINDOW_HEIGHT;
    }

    public void update(double dt, CanvasPanel canvasPanel) {
        if (!this.isActive()) return;

        this.move(MOVE_DIRECTION_LEFT, dt, (int) (this.color * 3 * canvasPanel.getPlayerSpeed()));
    }

    public void draw(Graphics2D g2d) {
        if (!this.isActive()) return;

        g2d.setColor(new Color(this.color, this.color, this.color));

        double proximityMultiplier = getProximityMultiplier();
        double starWidth = WIDTH * proximityMultiplier;
        double starHeight = HEIGHT * proximityMultiplier;
//        g2d.fill(new RoundRectangle2D.Double(
        g2d.fill(new Rectangle2D.Double(
                (int) this.getPX(),
                (int) this.getPY(),
                starWidth,
//                starHeight,
//                starWidth,
                starHeight
        ));
    }
}
