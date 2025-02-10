package Java2D;

import java.awt.Graphics2D;

public class GreenFire extends Nave {
    private int shot;
    private double frametime;
    private boolean playerHit = false;
    private TiroSimples[] tirosGF = new TiroSimples[16];
        
    public GreenFire() {
        super(true, 1, "sprite_naves/enemy2.png", randInt(60,100));
        for(int i=0; i<16; i++)
            tirosGF[i] = new TiroSimples(true); 
    }

    public double getTiroInimPX(int i) {
        return tirosGF[i].getPX();
    }
    
    public double getTiroInimPY(int i) {
        return tirosGF[i].getPY();
    }
    
    public void playerHit(int h) {
        playerHit = true;
        shot = h;
    }
    
    public void update(double dt) {
        if(alive) {
            frametime+=dt;
            aumentaMult();
            px-=multiplicador*dt;  
        }
        for(int i=0; i<16; i++) {
            if(frametime>1.8 && alive) {
                if (!tirosGF[i].ativo()) {
                    tirosGF[i].ativar(px-128, py-32);
                    frametime=0;
                    break;
                }
            }
            if(tirosGF[i].ativo())
                tirosGF[i].update(dt);
            if(tirosGF[i].getPX()<-50)
                tirosGF[i].desativar();
        }
        if(playerHit)
                tirosGF[shot].desativar();
        playerHit=false;
    }
    
    @Override
    public void draw(Graphics2D g2d) {
        for(int i=0; i<16; i++) {
            if(tirosGF[i].ativo())
                tirosGF[i].draw(g2d);
        }
        if(alive)
            g2d.drawImage(spriteNave, (int)px, (int)py, null); 
        else if (explodirBool)
            g2d.drawImage(explosao[explodir], (int)px, (int)py, null);
    }
}