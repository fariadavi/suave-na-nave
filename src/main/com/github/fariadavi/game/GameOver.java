package main.com.github.fariadavi.game;

import main.com.github.fariadavi.CanvasImageComponent;
import main.com.github.fariadavi.CanvasPanel;

import javax.swing.*;
import java.awt.*;

import static main.com.github.fariadavi.MainFrame.WINDOW_HEIGHT;
import static main.com.github.fariadavi.MainFrame.WINDOW_WIDTH;
import static main.com.github.fariadavi.utils.SpriteMappings.SPRITE_TEXTS_GAMEOVER_PATH;

public class GameOver extends CanvasImageComponent {

    private double dtGameOver = 0;

    public GameOver() {
        super(SPRITE_TEXTS_GAMEOVER_PATH, WINDOW_WIDTH / 2, WINDOW_HEIGHT / 2, false);
        setAlignmentX(ALIGNMENT_CENTER);
        setAlignmentY(ALIGNMENT_CENTER);
    }

    public void update(double dt, CanvasPanel canvasPanel) {
        if (!this.isActive()) return;

        this.dtGameOver += dt;
        if (this.dtGameOver >= 2 && this.dtGameOver < 2 + dt) {
            String playerName = null;
            int score = canvasPanel.getPlayerScore();
            if (score > canvasPanel.getLowestHighScore()) {
                while (playerName == null || playerName.isEmpty()) {
                    playerName = JOptionPane.showInputDialog(
                            null,
                            "Insira o seu nome",
                            "High Score",
                            JOptionPane.PLAIN_MESSAGE
                    );
                    if (playerName == null || playerName.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Entre um nome");
                    }
                }

                canvasPanel.addHighScore(playerName, score);
            }

            canvasPanel.finishGameRun();
        }
    }
}
