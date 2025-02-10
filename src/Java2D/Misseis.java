package Java2D;

import java.awt.Graphics2D;

public class Misseis extends Tiros {
    private double multX, multY, quadHip;
    
    public Misseis() {
        super("sprite_tiros/missil3.png", 800);
    }
    
    public void setMult(double dX, double dY) {
        quadHip = dX*dX+dY*dY;
        if(dY>=0)
            multY = (dY*dY)/quadHip;
        else
            multY = (dY*dY)/quadHip*-1;
        if(dX>=0)
            multX = (dX*dX)/quadHip;
        else
            multX = (dX*dX)/quadHip*-1;
    }
    public double getMultX() {
        return multX;
    }    
    public double getMultY() {
        return multY;
    }
    public double getHip() {
        return quadHip;
    }
    
    public void update(double dt) { 
        px+=multX*multiplicador*dt;
        py+=multY*multiplicador*dt;
    }
    
    public void draw(Graphics2D g2d) {
        g2d.drawImage(spriteTiro, (int)px-5, (int)py-8, null);  
    }
}
