package main.com.github.fariadavi.game.items;

import main.com.github.fariadavi.utils.FileHelper;

import javax.swing.*;
import java.awt.*;

public class MissileDrop {
    private Image dropImg;
    public double frametime, px, py;
    public boolean ativo;

    public MissileDrop(String imagePath) {
        dropImg = FileHelper.getImage(imagePath);
    }

    public void ativar(double pxNave, double pyNave) {
        ativo = true;
    px = pxNave;
        py = pyNave;
    }

    public void desativar() {
        ativo = false;
        frametime = 0;
    }

    public boolean ativo() {
        return ativo;
    }

    public double getPX() {
        return px;
    }

    public double getPY() {
        return py;
    }

    public void update(double dt) {
        if (ativo)
            frametime += dt;
        if (frametime > 80)
            desativar();
        px -= 5 * dt;
    }

    public void draw(Graphics2D g2d) {
        if (ativo)
            g2d.drawImage(dropImg, (int) px + 16, (int) py + 16, null);
    }
}
