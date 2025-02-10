package Java2D;

public class RedUFO extends Nave{
    
    public RedUFO() {
        super(true, 1, "sprite_naves/enemy1.png", randInt(80,150));
    }
    
    public void update(double dt) {
        aumentaMult();
        px-=multiplicador*dt;    
    }
}
