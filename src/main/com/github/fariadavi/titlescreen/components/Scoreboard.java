package main.com.github.fariadavi.titlescreen.components;

import main.com.github.fariadavi.CanvasGroupComponent;
import main.com.github.fariadavi.CanvasPanel;
import main.com.github.fariadavi.HighScore;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;

import static main.com.github.fariadavi.ScoreManager.NUM_HIGHSCORES;

public class Scoreboard extends CanvasGroupComponent {

    private final HighScore[] highScores = new HighScore[NUM_HIGHSCORES];

    public Scoreboard(int x, int y) {
        super(x, y, false);
    }

    public void updateHighScores(HighScore... updatedScoreList) {
        for (int i = 0; i < this.highScores.length; i++) {
            if (i >= updatedScoreList.length || updatedScoreList[i] == null) {
                this.highScores[i] = null;
                continue;
            }

            this.highScores[i] = updatedScoreList[i];
        }
    }

    public void update(double dt, CanvasPanel canvasPanel) {
        if (!this.isActive()) return;

        if (this.getPY() > 80) {
            this.moveY(MOVE_DIRECTION_UP, dt, 180);
            return;
        }

        if (canvasPanel.isKeyPressed(KeyEvent.VK_ENTER) || canvasPanel.isKeyPressed(KeyEvent.VK_SPACE)) {
            canvasPanel.clearKeyPress(KeyEvent.VK_ENTER);
            canvasPanel.clearKeyPress(KeyEvent.VK_SPACE);

            canvasPanel.resetTitleScreen();
        }

        if (canvasPanel.isMouseClicked()) {
            int[] clickPosition = canvasPanel.getClickPosition();
            boolean clickedClose = new Rectangle(
                    (int) this.getPX() + 120,
                    (int) this.getPY() + 400,
                    110,
                    40
            ).contains(clickPosition[0], clickPosition[1]);

            if (clickedClose)
                canvasPanel.resetTitleScreen();

            canvasPanel.clearMouseClick();
        }
    }

    public void draw(Graphics2D g2d) {
        if (!this.isActive()) return;

        double px = this.getPX();
        double py = this.getPY();
        g2d.setPaint(new GradientPaint((float) px, (float) py, Color.black, (float) px, (float) py + 400, Color.lightGray));
        g2d.fill(new RoundRectangle2D.Double(px, py, 350, 420, 50, 50));
        g2d.setColor(Color.black);
        g2d.draw(new RoundRectangle2D.Double(px, py, 350, 420, 50, 50));
        g2d.setColor(new Color(254, 233, 0));
        g2d.fill(new Rectangle2D.Double(px + 120, py + 400, 110, 40));
        g2d.setColor(new Color(254, 233, 0, 250));
        g2d.setFont(new Font("Tahoma", Font.BOLD, 36));
        g2d.drawString("HIGH SCORES", (int) px + 56, (int) py + 70);
        g2d.setFont(new Font("Tahoma", Font.BOLD, 24));

        for (int i = 0; i < this.highScores.length; i++) {
            if (this.highScores[i] == null) continue;

            int x = (int) (px + 70);
            int y = (int) (py + 110 + 30 * i);
            String str = String.valueOf(i + 1);
            FontMetrics fm = g2d.getFontMetrics();
            g2d.drawString(str, x - fm.stringWidth(str), y);

            String name = this.highScores[i].getPlayerName();
            if (name.length() > 10)
                name = name.substring(0, 10) + "...";
            g2d.drawString(name, x + 10, y);

            String score = String.valueOf(this.highScores[i].getScore());
            g2d.drawString(score, x + 230 - fm.stringWidth(score), y);
        }

        g2d.setColor(Color.black);
        g2d.draw(new Rectangle2D.Double(px + 120, py + 400, 110, 40));
        g2d.drawString("CLOSE", (int) px + 136, (int) py + 430);
    }
}
