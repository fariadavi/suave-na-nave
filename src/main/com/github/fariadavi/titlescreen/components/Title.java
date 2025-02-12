package main.com.github.fariadavi.titlescreen.components;

import main.com.github.fariadavi.CanvasImageComponent;

import static main.com.github.fariadavi.utils.SpriteMappings.SPRITE_TEXTS_GAMETITLE_PATH;

public class Title extends CanvasImageComponent {

    public Title(int x, int y) {
        super(SPRITE_TEXTS_GAMETITLE_PATH, x, y, true);
        setAlignmentX(ALIGNMENT_CENTER);
    }
}
