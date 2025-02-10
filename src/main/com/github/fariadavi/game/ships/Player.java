package main.com.github.fariadavi.game.ships;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;

public class Player extends Ship {
    private Image spriteNaveBoosted;
    double frametimeTiro = 0, frametimeMissil = 0, frametimePontos = 0;
    private int pontos = 0, misseis = 3;
    private boolean missilAtivo, tiroAtivo;
    public boolean[] key_states = new boolean[256];

    public Player() {
        super(false, 4, "sprite_naves/spaceship.png", 150);
        spriteNaveBoosted = new ImageIcon(this.getClass().getResource("sprite_naves/spaceship-boosted.png")).getImage();
        px = 80;
        py = 280;
        spawn(px, py);
    }

    public void setPos(double posx, double posy) {
        px = posx;
        px = posy;
    }

    public void setBlink(boolean bool) {
        blink = bool;
    }

    public boolean getBlink() {
        return blink;
    }

    public void setInvulneravel(boolean bool) {
        invulneravel = bool;
    }

    public boolean getInvulneravel() {
        return invulneravel;
    }

    public void addPts() {
        pontos++;
    }

    public int getPontos() {
        return pontos;
    }

    public int getVidas() {
        return hp - 1;
    }

    public void setNumMisseis(int x) {
        misseis += x;
    }

    public int getNumMisseis() {
        return misseis;
    }

    public boolean getTurbo() {
        return turbo;
    }

    public boolean getTiro() {
        return tiroAtivo;
    }

    public boolean getMissil() {
        return missilAtivo;
    }

    public int target(double[] delta, int[] posicao, int posReturn) {
        boolean trocado = true;
        double tempDelta;
        int tempPos;
        int count = 0;
        while (trocado) {
            trocado = false;
            count++;
            for (int i = 0; i < 64 - count; i++) {
                if (delta[i] > delta[i + 1]) {
                    tempDelta = delta[i];
                    delta[i] = delta[i + 1];
                    delta[i + 1] = tempDelta;
                    tempPos = posicao[i];
                    posicao[i] = posicao[i + 1];
                    posicao[i + 1] = tempPos;
                    trocado = true;
                }
            }
        }
        return posicao[posReturn];
    }

    public void update(double dt) {
        frametimeTiro += dt;
        frametimeMissil += dt;
        tempoTurbo += dt;
        tiroAtivo = false;
        missilAtivo = false;

        if (turbo && tempoTurbo > 4) {
            tempoTurbo = 0;
            multiplicador = multiplicadorInicial;
            ;
            setTurbo(false, tempoTurbo);
        }

        if ((key_states[KeyEvent.VK_SHIFT])) {
            if (tempoTurbo > 20) {
                tempoTurbo = 0;
                multiplicador *= 2.2;
                setTurbo(true, tempoTurbo);
            }
        }

        if (key_states[KeyEvent.VK_V] && frametimeTiro > 0.5) {
            tiroAtivo = true;
            frametimeTiro = 0;
        }
        if (key_states[KeyEvent.VK_B] && frametimeMissil > 1.5) {
            missilAtivo = true;
            frametimeMissil = 0;
        }
        if ((key_states[KeyEvent.VK_RIGHT]) && (px < 670)) {
            px += multiplicador * dt;
        } else if ((key_states[KeyEvent.VK_LEFT]) && (px > -62)) {
            px -= multiplicador * dt;
        }
        if ((key_states[KeyEvent.VK_UP]) && (py > -50)) {
            py -= multiplicador * dt;
        } else if ((key_states[KeyEvent.VK_DOWN]) && (py < 490)) {
            py += multiplicador * dt;
        }
    }

    @Override
    public void draw(Graphics2D g2d) {
        if (turbo)
            g2d.drawImage(spriteNaveBoosted, (int) px, (int) py, null);
        else
            g2d.drawImage(spriteNave, (int) px, (int) py, null);
    }
}
