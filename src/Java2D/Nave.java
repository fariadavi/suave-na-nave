package Java2D;

import java.awt.Graphics2D;
import java.awt.Image;
import javax.swing.ImageIcon;

public abstract class Nave {
    protected Image spriteNave, missilDrop;
    protected int explodir=0, hp, hpInic, multiplicador, multiplicadorInicial;
    protected double dHip, dX, dY, px=-2000, py=-2000, sentido=1, tempoTurbo=0;
    protected boolean alive=false, aumentado=false, blink, dropavel, dropMissil, explodirBool=false, invulneravel=false, turbo=false;
    protected Image[] explosao = new Image[16];
    
    public Nave(boolean drop, int healthPoints, String imagePath, int mult) {
        spriteNave = new ImageIcon(this.getClass().getResource(imagePath)).getImage();
        multiplicadorInicial = mult;
        multiplicador = multiplicadorInicial;
        dropavel = drop;
        hpInic = healthPoints;
        hp = hpInic;
        missilDrop = new ImageIcon(this.getClass().getResource("sprite_tiros/missil3.png")).getImage();
        for(int i=0; i<16; i++)
            explosao[i] = new ImageIcon(this.getClass().getResource("sprite_explosao/exp"+(i+1)+".png")).getImage();
    }
    
    public static int randInt(int min, int max) {
        java.util.Random rand = new java.util.Random();
        int randomNum = rand.nextInt((max - min) + 1) + min;
        return randomNum;
    }   
    
    public void setAlive(boolean bool) {
        alive = bool;
    }
    public boolean isAlive() {
        return alive;
    }
    
    public void setExplodir(int p){
        explodir=p;
    }
    public int getExplodir() {
        return explodir;
    }
    public void incrementaExplodir() {
        explodir++;
    }
    
    public void setExplodirBool(boolean bool) {
        explodirBool = bool;
    }
    public boolean getExplodirBool(){
        return explodirBool;
    }
    
    public void hit() {
        hp--;
        if(dropavel && hp ==0) {
            die();
        } else if(!dropavel)
            die();
    }
    public void spawn(double newPX, double newPY) {
        alive = true;
        hp = hpInic;
        px = newPX;
        py = newPY;
    }
    public void die() {        
        explodirBool = true;
        alive = false;
        if(dropavel) {
            hp=0;
            if(randInt(1,10)==2)
                dropMissil = true;
        }            
        else {
            invulneravel = true;
            blink = false;
            dropMissil = false;
        }
    }
    
    public void setDropMissil(boolean bool) {
        dropMissil=bool;
    }
    public boolean getDropMissil() {
        return dropMissil;
    }
    
    public double getPX(){
        return px;
    }
    public double getPY(){
        return py;
    }   
    
    public void setDX(double x1, double x2) {
        dX = x1-x2;
    }
    public void setDY(double y1, double y2) {
        dY = y1-y2;
    }
    public double setDeltaHip(double xInim, double xPlayer, double yInim, double yPlayer) {
        double deltaX = xInim - xPlayer;
        double deltaY = yInim - yPlayer;                                                                
        if(deltaX < 0)
            deltaX*=-1;
        if(deltaY < 0)
            deltaY*=-1;
        dHip = Math.sqrt(deltaX*deltaX + deltaY*deltaY);
        return dHip;
    }
    public double getDX() {
        return dX;
    }
    public double getDY() {
        return dY;
    }
    public int getmult() {
        return multiplicador;
    }
    
    public double getTempoTurbo() {
        return tempoTurbo;
    }
    public void aumentaMult() {
        if(turbo && !aumentado) {
            multiplicador*=2.2;
            aumentado = true;
        } else if (!turbo) {
            multiplicador=multiplicadorInicial;
            aumentado = false;
        }
    }
    
    public void setTurbo(boolean bool, double tT) {
        turbo=bool;
        tempoTurbo = tT;
    }
    
    public void draw(Graphics2D g2d) {
        if(alive)
            g2d.drawImage(spriteNave, (int)px, (int)py, null); 
        else if (explodirBool)
            g2d.drawImage(explosao[explodir], (int)px, (int)py, null);
    }
}
