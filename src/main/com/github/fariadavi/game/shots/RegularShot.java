package main.com.github.fariadavi.game.shots;

import java.awt.Graphics2D;
import java.awt.Image;
import javax.swing.ImageIcon;

public class RegularShot extends Shot {
    private Image tiroSimplesInim;
    private boolean tiroInim;

    public RegularShot(boolean tiroInimigo) {
        super("sprite_tiros/shot_regular.png", 200);
        tiroInim = tiroInimigo;
        tiroSimplesInim = new ImageIcon(this.getClass().getResource("sprite_tiros/enemy_shot.png")).getImage();
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
