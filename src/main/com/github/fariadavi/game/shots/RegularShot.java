package main.com.github.fariadavi.game.shots;

import main.com.github.fariadavi.utils.FileHelper;

import java.awt.Graphics2D;
import java.awt.Image;
import javax.swing.ImageIcon;

public class RegularShot extends Shot {
    private Image tiroSimplesInim;
    private boolean tiroInim;

    public RegularShot(boolean tiroInimigo) {
        super("sprites/shots/shot_regular.png", 200);
        tiroInim = tiroInimigo;
        tiroSimplesInim = FileHelper.getImage("sprites/shots/enemy_shot.png");
    }

    public void setOrientacao(int sent) {
        sentido = sent;
    }

    public void update(double dt) {
        if (tiroInim)
            sentido = -1;
        else
            sentido = 1;
        px += multiplicador * dt * sentido;
    }

    public void draw(Graphics2D g2d) {
        //g2d.setColor(Color.RED);
        //g2d.fill(new Rectangle2D.Double((int)px, (int)py, 8, 4));
        if (tiroInim)
            g2d.drawImage(tiroSimplesInim, (int) px, (int) py - 9, null);
        else
            g2d.drawImage(spriteTiro, (int) px, (int) py - 9, null);

    }
}
