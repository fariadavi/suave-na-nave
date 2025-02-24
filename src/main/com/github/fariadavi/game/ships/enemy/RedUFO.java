package main.com.github.fariadavi.game.ships.enemy;

import static main.com.github.fariadavi.utils.Randomizer.randInt;
import static main.com.github.fariadavi.utils.SpriteMappings.SPRITE_SHIPS_ENEMY_1_SPACESHIP_PATH;

public class RedUFO extends Enemy {

    public RedUFO() {
        super(SPRITE_SHIPS_ENEMY_1_SPACESHIP_PATH, 1, randInt(80, 150));

        setCollisionInsetX(8);
        setCollisionInsetY(8);
    }
}
