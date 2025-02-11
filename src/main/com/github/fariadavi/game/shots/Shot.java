package main.com.github.fariadavi.game.shots;

import main.com.github.fariadavi.utils.FileHelper;

import java.awt.Image;
import javax.swing.ImageIcon;

public abstract class Shot {
    protected Image spriteTiro;
    protected int multiplicador, sentido = 1;
    protected double px = -200, py = -200;
    protected boolean ativo = false;

    public Shot(String imagePath, int multiplier) {
        spriteTiro = FileHelper.getImage(imagePath);
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
        px = pxNave + 120;
        py = pyNave + 64;
    }

    public void desativar() {
        ativo = false;
    }

    public boolean ativo() {
        return ativo;
    }
}
