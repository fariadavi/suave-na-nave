package main.com.github.fariadavi.game.ships.enemy;

import main.com.github.fariadavi.CanvasPanel;

import static main.com.github.fariadavi.utils.Randomizer.randInt;
import static main.com.github.fariadavi.utils.SpriteMappings.SPRITE_SHIPS_ENEMY_4_SPACESHIP_PATH;

public class DeathFish extends Enemy {

    public static final int BREADTH_Y_DEATH_FISH = 100;

    public DeathFish() {
        super(SPRITE_SHIPS_ENEMY_4_SPACESHIP_PATH,
                5,
                randInt(60, 100),
                0,
                BREADTH_Y_DEATH_FISH
        );

        setCollisionInsetX(3);
        setCollisionInsetY(9);
    }

    @Override
    public void update(double dt, CanvasPanel canvasPanel) {
        super.update(dt, canvasPanel);
        this.setPY(BREADTH_Y_DEATH_FISH * Math.sin(this.getPX() * this.baseSpeedX / 4000) + this.getInitialPY());
    }
}
