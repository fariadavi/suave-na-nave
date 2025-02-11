package main.com.github.fariadavi.titlescreen;

import main.com.github.fariadavi.utils.FileHelper;

import java.awt.*;

public class MockPlayer {

    private Image playerShip;

    public MockPlayer() {
        playerShip = FileHelper.getImage("sprites/ships/spaceship.png");
    }
}
