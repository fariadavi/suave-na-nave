package main.com.github.fariadavi.titlescreen.components;

import main.com.github.fariadavi.CanvasGroupComponent;
import main.com.github.fariadavi.CanvasPanel;
import main.com.github.fariadavi.utils.FileHelper;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;

public class Scoreboard extends CanvasGroupComponent {

    private final String[] names = new String[10];
    private final int[] scores = new int[10];

    private final Rectangle closeScoreboard;

    public Scoreboard(int x, int y) {
        super(x, y, false);

        closeScoreboard = new Rectangle(x + 120, y + 400, 110, 40);
    }

    @Override
    public void activate() {
        super.activate();

        String[] fileNames = FileHelper.getNomes();
        for (int i = 0; i < fileNames.length; i++)
            names[i] = fileNames[i];

        int[] fileScores = FileHelper.getPts();
        for (int i = 0; i < fileScores.length; i++)
            scores[i] = fileScores[i];
    }

    public void update(double dt, CanvasPanel canvasPanel) {
        if (!this.isActive()) return;

        if (this.getPY() > 80) {
            this.move(MOVE_DIRECTION_UP, dt, 180);
            closeScoreboard.setLocation((int) this.getPX() + 120, (int) this.getPY() + 400);
            return;
        }

        if (canvasPanel.isKeyPressed(KeyEvent.VK_ENTER)) {
            canvasPanel.clearKeyPress(KeyEvent.VK_ENTER);

            canvasPanel.returnToTitleScreen();
        }

        if (canvasPanel.isMouseClicked() &&
                canvasPanel.getClickedRectangleIndex(closeScoreboard) == 0) {
            canvasPanel.clearMouseClick();

            canvasPanel.returnToTitleScreen();
        }
    }

    public void draw(Graphics2D g2d) {
        if (!super.isActive()) return;

        double px = super.getPX();
        double py = super.getPY();
        g2d.setPaint(new GradientPaint((float) px, (float) py, Color.black, (float) px, (float) py + 400, Color.lightGray));
        g2d.fill(new RoundRectangle2D.Double(px, py, 350, 420, 50, 50));
        g2d.setColor(Color.black);
        g2d.draw(new RoundRectangle2D.Double(px, py, 350, 420, 50, 50));
        g2d.setColor(new Color(254, 233, 0));
        g2d.fill(new Rectangle2D.Double(px + 120, py + 400, 110, 40));
        g2d.setColor(new Color(254, 233, 0, 250));
        g2d.setFont(new Font("Tahoma", Font.BOLD, 36));
        g2d.drawString("HIGH SCORES", (int) px + 48, (int) py + 70);
        g2d.setFont(new Font("Tahoma", Font.BOLD, 24));
        for (int i = 0, h = (int) py + 110; i < 10; i++, h += 30) {
            g2d.drawString(names[i] + " ", (int) px + 80, h);
            g2d.drawString(" " + scores[i], (int) px + 230, h);
        }
        g2d.setColor(Color.black);
        g2d.draw(new Rectangle2D.Double(px + 120, py + 400, 110, 40));
        g2d.drawString("CLOSE", (int) px + 136, (int) py + 430);
    }
}
