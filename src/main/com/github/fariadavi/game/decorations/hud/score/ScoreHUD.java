package main.com.github.fariadavi.game.decorations.hud.score;

import main.com.github.fariadavi.CanvasComponent;
import main.com.github.fariadavi.CanvasPanel;

import java.awt.*;

public class ScoreHUD extends CanvasComponent {

    private int playerScore;

    private final Font scoreFont = new Font("Verdana", Font.PLAIN, 48);

    public ScoreHUD(int x, int y) {
        super(x, y, true);
    }

    public void update(double dt, CanvasPanel canvasPanel) {
        this.playerScore = canvasPanel.getPlayerScore();
    }

    @Override
    public void draw(Graphics2D g2d) {
        g2d.setFont(this.scoreFont);
        g2d.setColor(Color.WHITE);

        int scoreWidth = g2d.getFontMetrics().stringWidth(String.valueOf(this.playerScore));
        g2d.drawString(
                String.valueOf(this.playerScore),
                (int) this.getPX() - scoreWidth / 2,
                (int) this.getPY()
        );
    }

    @Override
    public boolean isVisible() {
        return true;
    }
}
