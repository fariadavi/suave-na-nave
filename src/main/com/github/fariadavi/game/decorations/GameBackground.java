package main.com.github.fariadavi.game.decorations;

import main.com.github.fariadavi.CanvasGroupComponent;
import main.com.github.fariadavi.CanvasPanel;

import java.util.Arrays;
import java.util.Comparator;

public class GameBackground extends CanvasGroupComponent {

    public static final int NUM_BG_STARS = 300;

    private final BgStar[] stars = new BgStar[NUM_BG_STARS];

    public GameBackground() {
        super(true);

        for (int i = 0; i < this.stars.length; i++)
            this.stars[i] = new BgStar();

//        reorderStarArrayByColor();
        setComponents(this.stars);
    }

    private void reorderStarArrayByProximity() {
        Arrays.sort(this.stars, Comparator.comparing(BgStar::getColor));
    }

    public void update(double dt, CanvasPanel canvasPanel) {
        if (!this.isActive()) return;

//        if star size based on proximity get implemented, reordering has to be done
//        boolean reorder = false;
        for (BgStar star : this.stars) {
            star.update(dt, canvasPanel);
            if (!star.isVisible()) {
                star.respawn();
//                reorder = true;
            }
        }

//        if (reorder)
//            reorderStarArrayByColor();
    }
}
