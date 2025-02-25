package main.com.github.fariadavi.game.shots;

import main.com.github.fariadavi.CanvasCollidableImageComponent;

public abstract class NewShot extends CanvasCollidableImageComponent {

    protected double projectileSpeed;

    public NewShot(String imageResourcePath, int x, int y, boolean isActive) {
        super(imageResourcePath, x, y, isActive);
    }
}
