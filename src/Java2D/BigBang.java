package Java2D;

public class BigBang extends Nave {    
    private double pyInic;
    
    public BigBang() {
        
        super(true, 3, "sprite_naves/enemy3.png", randInt(100,140));
    }
    
    public void setPYinic(int PYsorteado) {
        pyInic = PYsorteado;
    }
    
    public void update(double dt) {
        aumentaMult();
        
        if(py<pyInic-100)
            sentido = 1;
        else if(py>pyInic+100)
            sentido = -1;
        
        px-=multiplicador*dt; 
        py+=multiplicador*dt*sentido; 
    }
}
