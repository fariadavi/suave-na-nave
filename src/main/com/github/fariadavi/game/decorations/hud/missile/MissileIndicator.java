package main.com.github.fariadavi.game.decorations.hud.missile;

import main.com.github.fariadavi.CanvasImageComponent;

public class MissileIndicator extends CanvasImageComponent {

    public MissileIndicator(String imageResourcePath, int x, int y, boolean isActive) {
        super(imageResourcePath, x, y, isActive);

        setAlignmentX(ALIGNMENT_END);
    }
}
