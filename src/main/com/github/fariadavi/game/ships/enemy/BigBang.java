package main.com.github.fariadavi.game.ships.enemy;

import main.com.github.fariadavi.CanvasPanel;

import static main.com.github.fariadavi.utils.Randomizer.randBoolean;
import static main.com.github.fariadavi.utils.Randomizer.randInt;
import static main.com.github.fariadavi.utils.SpriteMappings.SPRITE_SHIPS_ENEMY_3_SPACESHIP_PATH;

public class BigBang extends Enemy {

    public static final int BREADTH_Y_BIG_BANG = 60;

    public BigBang() {
        super(SPRITE_SHIPS_ENEMY_3_SPACESHIP_PATH,
                3,
                randInt(100, 140),
                randInt(100, 140) * (randBoolean() ? 1 : -1),
                BREADTH_Y_BIG_BANG
        );

        setCollisionInsetX(3);
        setCollisionInsetY(9);
    }

    @Override
    public void update(double dt, CanvasPanel canvasPanel) {
        if (this.baseSpeedY > 0 && this.getPY() > this.getInitialPY() + BREADTH_Y_BIG_BANG ||
                this.baseSpeedY < 0 && this.getPY() < this.getInitialPY() - BREADTH_Y_BIG_BANG)
            this.baseSpeedY *= -1;

        super.update(dt, canvasPanel);
    }
}
