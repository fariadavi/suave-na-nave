package Java2D;

import java.awt.Graphics2D;

public class DeathFish extends Nave {    
    private boolean playerHit = false;
    private int iteracao=0, shot;
    private double frametime=0, frametimeSenoide=0;
    private TiroSimples[] tirosDF = new TiroSimples[16];   
    
    public DeathFish() {
        super(true, 5, "sprite_naves/enemy4.png", randInt(60,100));
        for(int i=0; i<16; i++)
            tirosDF[i] = new TiroSimples(true); 
    }
    
    public double getTiroInimPX(int i) {
        return tirosDF[i].getPX();
    }
    
    public double getTiroInimPY(int i) {
        return tirosDF[i].getPY();
    }
    
    public void playerHit(int h) {
        playerHit = true;
        shot = h;
    }
    
    public void zeraIt() {
        iteracao=0;
    }
    
    public void update(double dt) {
        if(alive) {
            frametime+=dt;
            frametimeSenoide+=dt;
            if(frametimeSenoide>0.2) {
                sentido = Math.sin(0.3*iteracao);
                iteracao++;
                frametimeSenoide=0;
            }
            if(iteracao==20)
                iteracao=-1;
            aumentaMult();
            px-=multiplicador*dt; 
            py+=120*dt*sentido; 
        }
        for(int i=0; i<16; i++) {
            if(frametime>1.5 && alive) {
                if (!tirosDF[i].ativo()) {
                    tirosDF[i].ativar(px-128, py-32);
                    frametime=0;
                    break;
                }
            }
            if(tirosDF[i].ativo())
                tirosDF[i].update(dt);
            if(tirosDF[i].getPX()<-25)
                tirosDF[i].desativar();
        }
        if(playerHit)
                tirosDF[shot].desativar();
        playerHit=false;
    }
    
    @Override
    public void draw(Graphics2D g2d) {
        for(int i=0; i<16; i++) {
            if(tirosDF[i].ativo())
                tirosDF[i].draw(g2d);
        }
        if(alive)
            g2d.drawImage(spriteNave, (int)px, (int)py, null); 
        else if (explodirBool)
            g2d.drawImage(explosao[explodir], (int)px, (int)py, null);
    }
}
