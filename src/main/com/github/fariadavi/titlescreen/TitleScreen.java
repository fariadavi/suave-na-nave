package main.com.github.fariadavi.titlescreen;

import main.com.github.fariadavi.CanvasGroupComponent;
import main.com.github.fariadavi.CanvasPanel;
import main.com.github.fariadavi.titlescreen.components.Menu;
import main.com.github.fariadavi.titlescreen.components.*;

import java.awt.*;

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
        title = new Title(796 / 2, 20);
        mockPlayer = new MockPlayer(80, 280);
        menu = new Menu(796 / 4 * 3, 250);
        scoreboard = new Scoreboard(365, 610);
        credits = new Credits(796 / 2 - 373 / 2, 600);

        setComponents(background, title, mockPlayer, menu, scoreboard, credits);
    }

    public void showScoreboard() {
        scoreboard.activate();
    }

    public void showCredits() {
        credits.activate();
    }

    public void update(double dt, CanvasPanel canvasPanel) {
        if (!super.isActive()) return;

        background.update(dt, canvasPanel);
        menu.update(dt, canvasPanel);
        scoreboard.update(dt, canvasPanel);
        credits.update(dt, canvasPanel);

        if (scoreboard.isActive()) {
            if (menu.isVisible())
                menu.move(MOVE_DIRECTION_RIGHT, dt, 320);
            else
                menu.deactivate();

            if (title.isVisible())
                title.move(MOVE_DIRECTION_UP, dt, 170);
            else
                title.deactivate();
        }

        if (credits.isActive()) {
            if (menu.isVisible())
                menu.move(MOVE_DIRECTION_RIGHT, dt, 200);
            else
                menu.deactivate();

            if (title.isVisible())
                title.move(MOVE_DIRECTION_UP, dt, 170);
            else
                title.deactivate();
        }
    }
}
