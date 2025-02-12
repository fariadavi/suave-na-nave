package main.com.github.fariadavi.titlescreen.components;

import main.com.github.fariadavi.CanvasImageComponent;

import static main.com.github.fariadavi.utils.SpriteMappings.SPRITES_SHIPS_PLAYER_SPACESHIP_PATH;

public class MockPlayer extends CanvasImageComponent {

    public MockPlayer(int x, int y) {
        super(SPRITES_SHIPS_PLAYER_SPACESHIP_PATH, x, y, true);
    }
}
