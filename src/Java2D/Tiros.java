package Java2D;

import java.awt.Image;
import javax.swing.ImageIcon;

public abstract class Tiros {
    protected Image spriteTiro;
    protected int multiplicador, sentido = 1;
    protected double px=-200, py=-200;
    protected boolean ativo = false;
    
    public Tiros (String imagePath, int multiplier) {
        spriteTiro=new ImageIcon(this.getClass().getResource(imagePath)).getImage();
        multiplicador = multiplier;
    }
    
    public double getPX() {
        return px;
    }
    
    public double getPY() {
        return py;
    }
    
    public void ativar(double pxNave, double pyNave) {
        ativo = true;       
        px = pxNave+120;
        py = pyNave+64;     
    }
    
    public void desativar() {
        ativo = false;
    }
    
    public boolean ativo() {
        return ativo;
    }
}
