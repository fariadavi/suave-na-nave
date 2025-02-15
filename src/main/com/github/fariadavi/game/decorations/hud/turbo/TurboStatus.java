package main.com.github.fariadavi.game.decorations.hud.turbo;

import java.awt.*;

public enum TurboStatus {
    ACTIVE(Color.ORANGE),
    READY(Color.GREEN),
    CHARGING(Color.YELLOW);

    private final Color color;

    TurboStatus(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return this.color;
    }
}
