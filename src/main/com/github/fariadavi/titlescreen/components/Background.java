package main.com.github.fariadavi.titlescreen.components;

import main.com.github.fariadavi.CanvasGroupComponent;
import main.com.github.fariadavi.CanvasImageComponent;
import main.com.github.fariadavi.CanvasPanel;

import static main.com.github.fariadavi.utils.SpriteMappings.SPRITE_BG_TITLESCREEN_PATH;

public class Background extends CanvasGroupComponent {

    private final CanvasImageComponent[] bgImages = new CanvasImageComponent[5];

    public Background() {
        super(true);

        for (int i = 0; i < 5; i++) {
            String bgSpritePath = SPRITE_BG_TITLESCREEN_PATH
                    .replaceFirst("%i%", String.valueOf(i + 1));
            bgImages[i] = new CanvasImageComponent(bgSpritePath, i * 290, 0, true);
        }

        this.setComponents(bgImages);
    }

    public void update(double dt, CanvasPanel canvasPanel) {
        for (int i = 0; i < 5; i++) {
            bgImages[i].moveX(MOVE_DIRECTION_LEFT, dt, 70);
            if (bgImages[i].getPX() < -290)
                bgImages[i].setPX(bgImages[i].getPX() + 1450);
        }
    }

    @Override
    public void resetPosition() { }
}
