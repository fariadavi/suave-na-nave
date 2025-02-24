package main.com.github.fariadavi.game.ships.enemy;

import static main.com.github.fariadavi.utils.Randomizer.randInt;
import static main.com.github.fariadavi.utils.SpriteMappings.SPRITE_SHIPS_ENEMY_2_SPACESHIP_PATH;

public class GreenFire extends Enemy {

    public GreenFire() {
        super(SPRITE_SHIPS_ENEMY_2_SPACESHIP_PATH, 1, randInt(60, 100));

        setCollisionInsetX(3);
        setCollisionInsetY(8);
    }
}
