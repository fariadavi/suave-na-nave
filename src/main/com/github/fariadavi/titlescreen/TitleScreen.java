package main.com.github.fariadavi.titlescreen;

import main.com.github.fariadavi.CanvasGroupComponent;
import main.com.github.fariadavi.CanvasPanel;
import main.com.github.fariadavi.HighScore;
import main.com.github.fariadavi.titlescreen.components.*;

public class TitleScreen extends CanvasGroupComponent {

    private final Background background;
    private final Title title;
    private final MockPlayer mockPlayer;
    private final Menu menu;
    private final Scoreboard scoreboard;
    private final Credits credits;

    public TitleScreen() {
        super(true);

        background = new Background();
        title = new Title(796 / 2, 40);
        mockPlayer = new MockPlayer(80, 280);
        menu = new Menu(796 / 4 * 3, 290);
        scoreboard = new Scoreboard(365, 610);
        credits = new Credits(796 / 2 - 373 / 2, 600);

        setComponents(background, title, mockPlayer, menu, scoreboard, credits);
    }

    public void showScoreboard(HighScore... updatedScoreList) {
        scoreboard.updateHighScores(updatedScoreList);
        scoreboard.activate();
    }

    public void showCredits() {
        credits.activate();
    }

    public void update(double dt, CanvasPanel canvasPanel) {
        if (!this.isActive()) return;

        background.update(dt, canvasPanel);

        if (scoreboard.isActive()) {
            scoreboard.update(dt, canvasPanel);

            if (menu.isVisible())
                menu.moveX(MOVE_DIRECTION_RIGHT, dt, 320);
            else
                menu.deactivate();

            if (title.isVisible())
                title.moveY(MOVE_DIRECTION_UP, dt, 170);
            else
                title.deactivate();

            return;
        }

        if (credits.isActive()) {
            credits.update(dt, canvasPanel);

            if (menu.isVisible())
                menu.moveX(MOVE_DIRECTION_RIGHT, dt, 200);
            else
                menu.deactivate();

            if (title.isVisible())
                title.moveY(MOVE_DIRECTION_UP, dt, 170);
            else
                title.deactivate();

            return;
        }

        menu.update(dt, canvasPanel);
    }
}
