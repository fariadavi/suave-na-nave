package main.com.github.fariadavi.game.ships;

import main.com.github.fariadavi.CanvasPanel;
import main.com.github.fariadavi.utils.FileHelper;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;

import static main.com.github.fariadavi.utils.SpriteMappings.SPRITES_SHIPS_PLAYER_SPACESHIP_BOOSTED_PATH;
import static main.com.github.fariadavi.utils.SpriteMappings.SPRITES_SHIPS_PLAYER_SPACESHIP_PATH;

public class Player extends Ship {

    public static final double TURBO_RECHARGE_DURATION_SECONDS = 20;
    public static final double TURBO_CONSUME_DURATION_SECONDS = 4;

    public static final double MISSILE_COOLDOWN_DURATION_SECONDS = 1.5;

    private Image spriteNaveBoosted;
    double frametimeTiro = 0, frametimeMissil = MISSILE_COOLDOWN_DURATION_SECONDS;
    private int pontos = 0, misseis = 3;
    private boolean missilAtivo, tiroAtivo;

    public Player() {
        super(false, 4, SPRITES_SHIPS_PLAYER_SPACESHIP_PATH, 150);
        spriteNaveBoosted = FileHelper.getImage(SPRITES_SHIPS_PLAYER_SPACESHIP_BOOSTED_PATH);
        px = 80;
        py = 280;
        spawn(px, py);
    }

    public double getMissileCooldownPercentage() {
        return this.frametimeMissil / MISSILE_COOLDOWN_DURATION_SECONDS;
    }

    public double getTurboChargePercentage() {
        return this.tempoTurbo / TURBO_RECHARGE_DURATION_SECONDS;
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

    public int getScore() {
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

    public void update(double dt, CanvasPanel canvasPanel) {
        frametimeTiro += dt;
        tiroAtivo = false;

        if (misseis > 0 && misseis <= 3) {
            if (missilAtivo)
                missilAtivo = false;

            if (frametimeMissil < MISSILE_COOLDOWN_DURATION_SECONDS) {
                frametimeMissil += dt;
            } else if (frametimeMissil > MISSILE_COOLDOWN_DURATION_SECONDS) {
                frametimeMissil = MISSILE_COOLDOWN_DURATION_SECONDS;
            } else if (canvasPanel.isKeyPressed(KeyEvent.VK_B)) {
                frametimeMissil = 0;
                missilAtivo = true;
            }
        }

        if (!turbo) {
            if (tempoTurbo < TURBO_RECHARGE_DURATION_SECONDS)
                tempoTurbo += dt;
            else if (tempoTurbo > TURBO_RECHARGE_DURATION_SECONDS)
                tempoTurbo = TURBO_RECHARGE_DURATION_SECONDS;
            else if (canvasPanel.isKeyPressed(KeyEvent.VK_SHIFT)) {
                multiplicador *= 2.2;
                setTurbo(true, tempoTurbo);
            }
        } else {
            if (tempoTurbo > 0) {
                double consumeMultiplier = TURBO_RECHARGE_DURATION_SECONDS / TURBO_CONSUME_DURATION_SECONDS;
                tempoTurbo -= dt * consumeMultiplier;
            } else {
                tempoTurbo = 0;
                multiplicador = multiplicadorInicial;
                setTurbo(false, tempoTurbo);
            }
        }

        if (canvasPanel.isKeyPressed(KeyEvent.VK_V) && frametimeTiro > 0.5) {
            tiroAtivo = true;
            frametimeTiro = 0;
        }
        if (canvasPanel.isKeyPressed(KeyEvent.VK_RIGHT) && px < 670) {
            px += multiplicador * dt;
        } else if (canvasPanel.isKeyPressed(KeyEvent.VK_LEFT) && px > -62) {
            px -= multiplicador * dt;
        }
        if (canvasPanel.isKeyPressed(KeyEvent.VK_UP) && py > -50) {
            py -= multiplicador * dt;
        } else if (canvasPanel.isKeyPressed(KeyEvent.VK_DOWN) && py < 490) {
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
