package main.com.github.fariadavi.titlescreen.components;

import main.com.github.fariadavi.CanvasImageComponent;
import main.com.github.fariadavi.CanvasPanel;

import static main.com.github.fariadavi.utils.SpriteMappings.SPRITE_TEXTS_CREDITS_DEVLIST_PATH;

public class Credits extends CanvasImageComponent {

    public Credits(int x, int y) {
        super(SPRITE_TEXTS_CREDITS_DEVLIST_PATH, x, y, false);
    }

    public void update(double dt, CanvasPanel canvasPanel) {
        if (!this.isActive()) return;

        this.move(MOVE_DIRECTION_UP, dt, 200);

        if (!this.isVisible())
            canvasPanel.returnToTitleScreen();
    }
}
