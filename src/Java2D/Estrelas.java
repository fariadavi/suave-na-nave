package Java2D;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

public class Estrelas {
    private double px, py;
    private boolean ativo;
    private int cor, mult;
    
    public Estrelas(){
        px=randInt(0,799);
        py=randInt(0,599);
    }
    
    public static int randInt(int min, int max) {
        java.util.Random rand = new java.util.Random();
        int randomNum = rand.nextInt((max - min) + 1) + min;
        return randomNum;
    }   
    
    public boolean ativo() {
        return ativo;
    }
    public void ativar() {
        cor=randInt(40,255);
        ativo=true;                
    }
    public void desativar() {
        ativo=false;
        px=810;
        py=randInt(0,599);
    }
    public double getPX(){
        return px;
    }
    public double getPY() {
        return py;
    }
    
    public void update(double dt, boolean turbo) {
        mult=cor*3;
        if(turbo)
            mult*=2.2;
        px-=mult*dt;
    }
    
    public void draw(Graphics2D g2d) {
        g2d.setColor(new Color(cor, cor, cor));
        g2d.fill(new Rectangle2D.Double((int)px, (int)py, 3, 2));
    }
}
