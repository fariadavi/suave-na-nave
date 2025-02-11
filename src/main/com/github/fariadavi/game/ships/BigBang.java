package main.com.github.fariadavi.game.ships;

public class BigBang extends Ship {

    public static final String SPRITE_PATH = "sprites/ships/enemy3.png";

    private double pyInic;

    public BigBang() {
        super(true, 3, SPRITE_PATH, randInt(100, 140));
    }

    public void setPYinic(int PYsorteado) {
        pyInic = PYsorteado;
    }

    public void update(double dt) {
        aumentaMult();

        if (py < pyInic - 100)
            sentido = 1;
        else if (py > pyInic + 100)
            sentido = -1;

        px -= multiplicador * dt;
        py += multiplicador * dt * sentido;
    }
}
