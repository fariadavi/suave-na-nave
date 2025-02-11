package main.com.github.fariadavi.game.ships;

public class RedUFO extends Ship {

    public RedUFO() {
        super(true, 1, "sprites/ships/enemy1.png", randInt(80, 150));
    }

    public void update(double dt) {
        aumentaMult();
        px -= multiplicador * dt;
    }
}
