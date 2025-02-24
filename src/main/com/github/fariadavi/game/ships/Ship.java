package main.com.github.fariadavi.game.ships;

import main.com.github.fariadavi.CanvasCollidableImageComponent;
import main.com.github.fariadavi.CanvasPanel;

public class Ship extends CanvasCollidableImageComponent {

    protected int healthPoints;
    protected int deadAtHP = 0;

    protected double baseSpeedX;
    protected double baseSpeedY;

    public Ship(String imageResourcePath, int healthPoints, double baseSpeedX) {
        super(imageResourcePath, true);
        this.healthPoints = healthPoints;
        this.baseSpeedX = baseSpeedX;
        this.baseSpeedY = 0;
    }

    public Ship(String imageResourcePath, int healthPoints, double baseSpeedX, double baseSpeedY) {
        this(imageResourcePath, healthPoints, baseSpeedX);
        this.baseSpeedY = baseSpeedY;
    }

    public Ship(String imageResourcePath, int healthPoints, int x, int y, double baseSpeedX) {
        super(imageResourcePath, x, y, true);
        this.healthPoints = healthPoints;
        this.baseSpeedX = baseSpeedX;
        this.baseSpeedY = 0;
    }

    public Ship(String imageResourcePath, int healthPoints, int x, int y, double baseSpeedX, double baseSpeedY) {
        this(imageResourcePath, healthPoints, x, y, baseSpeedX);
        this.baseSpeedY = baseSpeedY;
    }

    public void hit(int damage, CanvasPanel canvasPanel) {
        this.healthPoints -= damage;

        if (this.healthPoints <= this.deadAtHP) {
            this.deactivate();
            canvasPanel.explodeShip(this);
        }
    }
}
