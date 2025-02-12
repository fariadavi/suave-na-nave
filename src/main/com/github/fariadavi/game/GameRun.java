package main.com.github.fariadavi.game;

import main.com.github.fariadavi.CanvasPanel;
import main.com.github.fariadavi.game.decorations.BgStar;
import main.com.github.fariadavi.game.items.MissileDrop;
import main.com.github.fariadavi.game.ships.*;
import main.com.github.fariadavi.game.shots.MissileShot;
import main.com.github.fariadavi.game.shots.RegularShot;
import main.com.github.fariadavi.utils.FileHelper;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;

import static main.com.github.fariadavi.utils.FileHelper.getImage;
import static main.com.github.fariadavi.utils.SpriteMappings.*;

public class GameRun {

    private Player player;

    private RedUFO[] redUFO = new RedUFO[16];
    private GreenFire[] greenFire = new GreenFire[16];
    private BigBang[] bigBang = new BigBang[16];
    private DeathFish[] deathFish = new DeathFish[16];

    private RegularShot[] tiros = new RegularShot[16];
    private MissileShot[] misseis = new MissileShot[3];

    private MissileDrop[] missilMissileDrop = new MissileDrop[64];

    private BgStar[] estrelas = new BgStar[300];

    private Image gui_vidas, gui_misseis, gameOver;
    //    private boolean chargeMissil = false, pressed = false;
    private double[] deltaInimigos = new double[64];
    private int[] posInimigos = new int[64];
    private int posReturnPadrao = 0, targetMissil = 0, contBlink = 0, tamPontuacao, ptsAsomar = 0, posMenuHighlited = 0;
    private double cargaTurbo = 0, cargaMissil = 0,
            frametimeEstrelas = 0, frametimeMissel = 0, frametimeRespawnRedUFO = 2, frametimeRespawnGreenFire = 3.8,
            frametimeRespawnBigBang = 5.8, frametimeRespawnDeathFish = 7.8, frametimeTurbo = 0, frametimeExplosao = 0,
            frametimeInvulneravel = 10, frametimeSomaPts = 0, frametimeGameOver = 0, frametimeEnter = 2;


    private Font fontePontos = new Font("Verdana", Font.PLAIN, 48);
    private Font fonteTxt = new Font("Verdana", Font.BOLD, 14);

    private boolean CheckBoxCollision(double x1, double y1, double w1, double h1, double x2, double y2, double w2, double h2) {
        return ((x1 < x2 + w2) && (x2 < x1 + w1) && (y1 < y2 + h2) && (y2 < y1 + h1));
    }

    public void start() {
        player.spawn(80, 220);
        for (int i = 0; i < 16; i++) {
            redUFO[i].die();
            greenFire[i].die();
            bigBang[i].die();
            deathFish[i].die();
        }
    }

    public GameRun() {
        player = new Player();
        gui_vidas = getImage(SPRITE_UI_HEALTH_INDICATOR_PATH);
        gui_misseis = getImage(SPRITE_UI_MISSILES_INDICATOR_PATH);
        gameOver = getImage(SPRITE_TEXTS_GAMEOVER_PATH);

        for (int i = 0; i < 16; i++) {
            redUFO[i] = new RedUFO();
            greenFire[i] = new GreenFire();
            bigBang[i] = new BigBang();
            deathFish[i] = new DeathFish();
            tiros[i] = new RegularShot(false);
        }

        for (int i = 0; i < 3; i++)
            misseis[i] = new MissileShot();

        for (int i = 0; i < 64; i++)
            missilMissileDrop[i] = new MissileDrop(SPRITE_ITEMS_DROPPEDMISSILE_PATH);

        // for estrelas load
        for (int i = 0; i < 300; i++) {
            estrelas[i] = new BgStar();
            if (!estrelas[i].ativo()) {
                estrelas[i].ativar();
            }
        }
    }

    public void update(double dt, CanvasPanel canvasPanel) {
        frametimeSomaPts += dt;
        if (ptsAsomar > 0 && frametimeSomaPts > 0.02) {
            player.addPts();
            ptsAsomar--;
            frametimeSomaPts = 0;
        }
        if (player.getVidas() > -1) {
            player.update(dt, canvasPanel);
            frametimeTurbo += dt;
            frametimeEstrelas += dt;
            frametimeMissel += dt;

            posReturnPadrao = 0;

            frametimeRespawnRedUFO += dt;
            if (player.getPontos() > 1000)
                frametimeRespawnDeathFish += dt;
            if (player.getPontos() > 420)
                frametimeRespawnBigBang += dt;
            if (player.getPontos() > 100)
                frametimeRespawnGreenFire += dt;


            if (ptsAsomar > 0 && frametimeSomaPts > 0.02) {
                player.addPts();
                ptsAsomar--;
                frametimeSomaPts = 0;
            }

            if (player.getInvulneravel()) {                                     //BLINK/INVULNERAVEL QDO MORRE
                frametimeInvulneravel += dt;
                if (contBlink >= 16 && frametimeInvulneravel > 10.5) {
                    frametimeInvulneravel = 20;
                    contBlink = 0;
                    player.setBlink(false);
                    player.setInvulneravel(false);
                    player.setAlive(true);
                } else if (frametimeInvulneravel > 20.1 && contBlink % 2 != 0 && contBlink < 16) {
                    player.setBlink(false);
                    frametimeInvulneravel = 10;
                    contBlink++;
                } else if (frametimeInvulneravel > 10.1 && contBlink % 2 == 0 && contBlink < 16) {
                    player.setBlink(true);
                    frametimeInvulneravel = 20;
                    contBlink++;
                }
            }
            for (int i = 0; i < 16; i++) {
                redUFO[i].setTurbo(player.getTurbo(), player.getTempoTurbo());
                greenFire[i].setTurbo(player.getTurbo(), player.getTempoTurbo());
                bigBang[i].setTurbo(player.getTurbo(), player.getTempoTurbo());
                deathFish[i].setTurbo(player.getTurbo(), player.getTempoTurbo());
            }

            if (!player.getTurbo() && frametimeTurbo > 0.05 && cargaTurbo < 147) {   //CARGA TURBO
                cargaTurbo += 0.37;
                frametimeTurbo = 0;
            } else if (player.getTurbo() && frametimeTurbo > 0.01) {
                cargaTurbo -= 0.37;
                frametimeTurbo = 0;
            }

            if (player.getNumMisseis() > 0 && player.getNumMisseis() <= 3) {          //CARGA MISSEL
                if (player.getMissil()) {
                    cargaMissil = 0;
                } else if (frametimeMissel > 0.01 && cargaMissil < 50) {
                    cargaMissil += 0.36;
                    frametimeMissel = 0;
                }
            }

            // for estrelas
            for (int i = 0; i < 300; i++) {
                if (frametimeEstrelas > 0.01) {
                    if (!estrelas[i].ativo()) {
                        estrelas[i].ativar();
                        frametimeEstrelas = 0;
                        break;
                    }
                }
                if (estrelas[i].ativo()) {
                    estrelas[i].update(dt, player.getTurbo());
                    if (estrelas[i].getPX() < 0)
                        estrelas[i].desativar();
                }
            }
            // for Tiros Simples
            for (int i = 0; i < 16; i++) {
                if (player.getTiro()) {
                    if (!tiros[i].ativo()) {
                        tiros[i].ativar(player.getPX(), player.getPY());
                        break;
                    }
                }
                if (tiros[i].ativo()) {
                    tiros[i].update(dt);
                    for (int j = 0; j < 16; j++) {
                        if (CheckBoxCollision(tiros[i].getPX(), tiros[i].getPY(), 20, 8, redUFO[j].getPX() + 8, redUFO[j].getPY() + 8, 48, 48) && redUFO[j].isAlive()) {
                            tiros[i].desativar();
                            redUFO[j].hit();
                            if (!redUFO[j].isAlive())
                                ptsAsomar += 10;
                        } else if (CheckBoxCollision(tiros[i].getPX(), tiros[i].getPY(), 20, 8, greenFire[j].getPX(), greenFire[j].getPY() + 8, 48, 64) && greenFire[j].isAlive()) {
                            tiros[i].desativar();
                            greenFire[j].hit();
                            if (!greenFire[j].isAlive())
                                ptsAsomar += 50;
                        } else if (CheckBoxCollision(tiros[i].getPX(), tiros[i].getPY(), 20, 8, bigBang[j].getPX(), bigBang[j].getPY() + 8, 48, 64) && bigBang[j].isAlive()) {
                            tiros[i].desativar();
                            bigBang[j].hit();
                            if (!bigBang[j].isAlive())
                                ptsAsomar += 100;
                        } else if (CheckBoxCollision(tiros[i].getPX(), tiros[i].getPY(), 20, 8, deathFish[j].getPX(), deathFish[j].getPY() + 8, 48, 64) && deathFish[j].isAlive()) {
                            tiros[i].desativar();
                            deathFish[j].hit();
                            if (!deathFish[j].isAlive())
                                ptsAsomar += 200;
                        } else if (tiros[i].getPX() > 799) {
                            tiros[i].desativar();
                        }
                    }
                }
            }
            //for Misseis
            for (int i = 0, j = 16, k = 32, l = 48; i < 16; i++, j++, k++, l++) {
                deltaInimigos[i] = redUFO[i].setDeltaHip(redUFO[i].getPX() + 8, player.getPX() + 128, redUFO[i].getPY() + 32, player.getPY() + 64);
                deltaInimigos[j] = greenFire[i].setDeltaHip(greenFire[i].getPX(), player.getPX() + 128, greenFire[i].getPY() + 32, player.getPY() + 64);
                deltaInimigos[k] = bigBang[i].setDeltaHip(bigBang[i].getPX(), player.getPX() + 128, bigBang[i].getPY() + 32, player.getPY() + 64);
                deltaInimigos[l] = deathFish[i].setDeltaHip(deathFish[i].getPX(), player.getPX() + 128, deathFish[i].getPY() + 32, player.getPY() + 64);
                posInimigos[i] = i;
                posInimigos[j] = j;
                posInimigos[k] = k;
                posInimigos[l] = l;
            }
            for (int i = 0; i < 3; i++) {
                if (player.getMissil()) {
                    targetMissil = player.target(deltaInimigos, posInimigos, posReturnPadrao);
                    if (player.getNumMisseis() > 0) {
                        if (!misseis[i].ativo()) {
                            player.setNumMisseis(-1);
                            misseis[i].ativar(player.getPX(), player.getPY());
                            break;
                        }
                    }
                }
                if (misseis[i].ativo()) {
                    while (targetMissil < 16 && !redUFO[targetMissil].isAlive() ||
                            targetMissil > 15 && targetMissil < 32 && !greenFire[targetMissil - 16].isAlive() ||
                            targetMissil > 31 && targetMissil < 48 && !bigBang[targetMissil - 32].isAlive() ||
                            targetMissil > 47 && targetMissil < 64 && !deathFish[targetMissil - 48].isAlive()) {
                        posReturnPadrao++;
                        targetMissil = player.target(deltaInimigos, posInimigos, posReturnPadrao);
                    }
                    if (targetMissil < 16) {
                        redUFO[targetMissil].setDX(redUFO[targetMissil].getPX() + 8, misseis[i].getPX() + 22);
                        redUFO[targetMissil].setDY(redUFO[targetMissil].getPY() + 32, misseis[i].getPY() + 6);
                        misseis[i].setMult(redUFO[targetMissil].getDX(), redUFO[targetMissil].getDY());
                    } else if (targetMissil < 32) {
                        greenFire[targetMissil - 16].setDX(greenFire[targetMissil - 16].getPX(), misseis[i].getPX() + 22);
                        greenFire[targetMissil - 16].setDY(greenFire[targetMissil - 16].getPY() + 32, misseis[i].getPY() + 6);
                        misseis[i].setMult(greenFire[targetMissil - 16].getDX(), greenFire[targetMissil - 16].getDY());
                    } else if (targetMissil < 48) {
                        bigBang[targetMissil - 32].setDX(bigBang[targetMissil - 32].getPX() + 2, misseis[i].getPX() + 22);
                        bigBang[targetMissil - 32].setDY(bigBang[targetMissil - 32].getPY() + 32, misseis[i].getPY() + 6);
                        misseis[i].setMult(bigBang[targetMissil - 32].getDX(), bigBang[targetMissil - 32].getDY());
                    } else if (targetMissil < 64) {
                        deathFish[targetMissil - 48].setDX(deathFish[targetMissil - 48].getPX() + 2, misseis[i].getPX() + 22);
                        deathFish[targetMissil - 48].setDY(deathFish[targetMissil - 48].getPY() + 32, misseis[i].getPY() + 6);
                        misseis[i].setMult(deathFish[targetMissil - 48].getDX(), deathFish[targetMissil - 48].getDY());
                    }
                    misseis[i].update(dt);
                    for (int j = 0; j < 16; j++) {
                        if (CheckBoxCollision(misseis[i].getPX(), misseis[i].getPY(), 22, 14, redUFO[j].getPX() + 8, redUFO[j].getPY() + 8, 48, 48) && redUFO[j].isAlive()) {
                            misseis[i].desativar();
                            redUFO[j].die();
                            ptsAsomar += 10;
                        } else if (CheckBoxCollision(misseis[i].getPX(), misseis[i].getPY(), 22, 14, greenFire[j].getPX(), greenFire[j].getPY() + 8, 60, 48) && greenFire[j].isAlive()) {
                            misseis[i].desativar();
                            greenFire[j].die();
                            ptsAsomar += 50;
                        } else if (CheckBoxCollision(misseis[i].getPX(), misseis[i].getPY(), 22, 14, bigBang[j].getPX(), bigBang[j].getPY() + 8, 60, 48) && bigBang[j].isAlive()) {
                            misseis[i].desativar();
                            bigBang[j].die();
                            ptsAsomar += 100;
                        } else if (CheckBoxCollision(misseis[i].getPX(), misseis[i].getPY(), 22, 14, deathFish[j].getPX(), deathFish[j].getPY() + 8, 60, 48) && deathFish[j].isAlive()) {
                            misseis[i].desativar();
                            deathFish[j].die();
                            ptsAsomar += 200;
                        }
                    }
                    if (misseis[i].getPX() > 799 || misseis[i].getPY() > 599) {
                        misseis[i].desativar();
                    }
                }
            }
            // for Drop
            for (int i = 0; i < 16; i++) {
                for (int j = 0; j < 64; j++) {
                    if (redUFO[i].getDropMissil()) {
                        if (!missilMissileDrop[j].ativo()) {
                            missilMissileDrop[j].ativar(redUFO[i].getPX(), redUFO[i].getPY());
                            redUFO[i].setDropMissil(false);
                        }
                    }
                    if (greenFire[i].getDropMissil()) {
                        if (!missilMissileDrop[j].ativo()) {
                            missilMissileDrop[j].ativar(greenFire[i].getPX(), greenFire[i].getPY());
                            greenFire[i].setDropMissil(false);
                        }
                    }
                    if (bigBang[i].getDropMissil()) {
                        if (!missilMissileDrop[j].ativo()) {
                            missilMissileDrop[j].ativar(bigBang[i].getPX(), bigBang[i].getPY());
                            bigBang[i].setDropMissil(false);
                        }
                    }
                    if (deathFish[i].getDropMissil()) {
                        if (!missilMissileDrop[j].ativo()) {
                            missilMissileDrop[j].ativar(deathFish[i].getPX(), deathFish[i].getPY());
                            deathFish[i].setDropMissil(false);
                        }
                    }
                    if (CheckBoxCollision(player.getPX() + 62, player.getPY() + 50, 60, 30, missilMissileDrop[j].getPX() + 16, missilMissileDrop[j].getPY() + 16, 32, 32) && missilMissileDrop[j].ativo() && player.getNumMisseis() < 3) {
                        player.setNumMisseis(1);
                        missilMissileDrop[j].desativar();
                    }
                    missilMissileDrop[j].update(dt);
                }
            }
            //for RedUFOs
            for (int i = 0; i < 16; i++) {
                if (frametimeRespawnRedUFO > 1.5) {
                    if (!redUFO[i].isAlive()) {
                        int py = RedUFO.randInt(-8, 516);
                        redUFO[i].spawn(800, py);
                        frametimeRespawnRedUFO = 0;
                        break;
                    }
                }
                if (redUFO[i].isAlive()) {
                    redUFO[i].update(dt);
                    if (CheckBoxCollision(player.getPX() + 62, player.getPY() + 50, 60, 30, redUFO[i].getPX() + 8, redUFO[i].getPY() + 8, 48, 48) && !player.getInvulneravel()) {
                        redUFO[i].die();
                        player.hit();
                    }
                    if (redUFO[i].getPX() < -64) {
                        redUFO[i].die();
                    }
                }
                for (int j = 0; j < 16; j++) {                                     // EXPLOSAO NAVE INIMIGA
                    if (redUFO[i].getExplodirBool()) {
                        frametimeExplosao += dt;
                        if (frametimeExplosao > 1 && redUFO[i].getExplodir() < 15) {
                            redUFO[i].incrementaExplodir();
                            frametimeExplosao = 0;
                        } else if (redUFO[i].getExplodir() >= 15) {
                            frametimeExplosao = 0;
                            redUFO[i].setExplodir(0);
                            redUFO[i].setExplodirBool(false);
                            break;
                        }
                    }
                }
            }
            //for GreenFire
            for (int i = 0; i < 16; i++) {
                if (frametimeRespawnGreenFire > 4) {
                    if (!greenFire[i].isAlive()) {
                        int py = GreenFire.randInt(-8, 516);
                        greenFire[i].spawn(800, py);
                        frametimeRespawnGreenFire = 0;
                        break;
                    }
                }
                greenFire[i].update(dt);
                if (greenFire[i].isAlive()) {
                    if (CheckBoxCollision(player.getPX() + 62, player.getPY() + 50, 60, 30, greenFire[i].getPX() + 2, greenFire[i].getPY() + 8, 60, 48) && !player.getInvulneravel()) {
                        greenFire[i].die();
                        player.hit();
                    }
                    if (greenFire[i].getPX() < -64) {
                        greenFire[i].die();
                    }
                }
                for (int j = 0; j < 16; j++) {                                     // EXPLOSAO NAVE INIMIGA
                    if (CheckBoxCollision(player.getPX() + 62, player.getPY() + 50, 60, 30, greenFire[i].getTiroInimPX(j) + 4, greenFire[i].getTiroInimPY(j), 24, 6) && !player.getInvulneravel()) {
                        greenFire[i].playerHit(j);
                        player.hit();
                    }
                    if (greenFire[i].getExplodirBool()) {
                        frametimeExplosao += dt;
                        if (frametimeExplosao > 1 && greenFire[i].getExplodir() < 15) {
                            greenFire[i].incrementaExplodir();
                            frametimeExplosao = 0;
                        } else if (greenFire[i].getExplodir() >= 15) {
                            greenFire[i].setExplodir(0);
                            greenFire[i].setExplodirBool(false);
                            break;
                        }
                    }
                }
            }
            //for BigBang
            for (int i = 0; i < 16; i++) {
                if (frametimeRespawnBigBang > 6) {
                    if (!bigBang[i].isAlive()) {
                        int py = BigBang.randInt(92, 416);
                        bigBang[i].spawn(800, py);
                        bigBang[i].setPYinic(py);
                        frametimeRespawnBigBang = 0;
                        break;
                    }
                }
                if (bigBang[i].isAlive()) {
                    bigBang[i].update(dt);
                    if (CheckBoxCollision(player.getPX() + 62, player.getPY() + 50, 60, 30, bigBang[i].getPX() + 8, bigBang[i].getPY() + 8, 48, 48) && !player.getInvulneravel()) {
                        bigBang[i].die();
                        player.hit();
                    }
                    if (bigBang[i].getPX() < -64) {
                        bigBang[i].die();
                    }
                }
                for (int j = 0; j < 16; j++) {                                     // EXPLOSAO NAVE INIMIGA
                    if (bigBang[i].getExplodirBool()) {
                        frametimeExplosao += dt;
                        if (frametimeExplosao > 1 && bigBang[i].getExplodir() < 15) {
                            bigBang[i].incrementaExplodir();
                            frametimeExplosao = 0;
                        } else if (bigBang[i].getExplodir() >= 15) {
                            bigBang[i].setExplodir(0);
                            bigBang[i].setExplodirBool(false);
                            break;
                        }
                    }
                }
            }
            //for DeathFish
            for (int i = 0; i < 16; i++) {
                if (frametimeRespawnDeathFish > 8) {
                    if (!deathFish[i].isAlive()) {
                        int py = DeathFish.randInt(0, 330);
                        deathFish[i].spawn(800, py);
                        deathFish[i].zeraIt();
                        frametimeRespawnDeathFish = 0;
                        break;
                    }
                }
                deathFish[i].update(dt);
                if (deathFish[i].isAlive()) {
                    if (CheckBoxCollision(player.getPX() + 62, player.getPY() + 50, 60, 30, deathFish[i].getPX() + 8, deathFish[i].getPY() + 8, 48, 48) && !player.getInvulneravel()) {
                        deathFish[i].die();
                        player.hit();
                    }
                    if (deathFish[i].getPX() < -64) {
                        deathFish[i].die();
                    }
                }
                for (int j = 0; j < 16; j++) {                                     // EXPLOSAO NAVE INIMIGA
                    if (CheckBoxCollision(player.getPX() + 62, player.getPY() + 50, 60, 30, deathFish[i].getTiroInimPX(j) + 4, deathFish[i].getTiroInimPY(j), 24, 6) && !player.getInvulneravel()) {
                        deathFish[i].playerHit(j);
                        player.hit();
                    }
                    if (deathFish[i].getExplodirBool()) {
                        frametimeExplosao += dt;
                        if (frametimeExplosao > 1 && deathFish[i].getExplodir() < 15) {
                            deathFish[i].incrementaExplodir();
                            frametimeExplosao = 0;
                        } else if (deathFish[i].getExplodir() >= 15) {
                            deathFish[i].setExplodir(0);
                            deathFish[i].setExplodirBool(false);
                            break;
                        }
                    }
                }
            }
        } else {
            frametimeGameOver += dt;
            if (frametimeGameOver >= 1 && frametimeGameOver < 1 + dt) {
                String nomeEntrado = null;
//                testee = FileHelper.le();
                if (player.getPontos() > FileHelper.le()) {
                    while (nomeEntrado == null || nomeEntrado.equals("")) {
                        nomeEntrado = JOptionPane.showInputDialog(null, "Insira o seu nome", "High Score", JOptionPane.PLAIN_MESSAGE);
                        if (nomeEntrado == null || nomeEntrado.equals("")) {
                            JOptionPane.showMessageDialog(null, "Entre um nome");
                        }
                    }
                    FileHelper.escreve(nomeEntrado, player.getPontos());
//                        recordes=true;
                }

                canvasPanel.returnToTitleScreen();
            }
        }
    }

    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        for (int i = 0; i < 300; i++) {                                             // DESENHA ESTRELAS
            if (estrelas[i].ativo())
                estrelas[i].draw(g2d);
        }
        for (int i = 0; i < 16; i++) {                                               // DESENHA TIROS SIMPLES PLAYER
            if (tiros[i].ativo())
                tiros[i].draw(g2d);
            //g2d.draw(new Rectangle2D.Double(tiros[i].getPX(), tiros[i].getPY(), 20, 8));
        }
        for (int i = 0; i < 3; i++) {                                               // DESENHA TIROS SIMPLES PLAYER
            if (misseis[i].ativo())
                misseis[i].draw(g2d);
            //            g2d.draw(new Rectangle2D.Double(misseis[i].getPX(), misseis[i].getPY(), 22, 14));
        }

        g2d.setColor(Color.WHITE);                                              // BRANCO

        for (int i = 0; i < 64; i++)
            missilMissileDrop[i].draw(g2d);

        for (int i = 0; i < 16; i++) {                                           // DESENHA RedUFOS
            redUFO[i].draw(g2d);
            greenFire[i].draw(g2d);
            bigBang[i].draw(g2d);
            deathFish[i].draw(g2d);
            for (int j = 0, w = (int) bigBang[i].getPX() + 6; j < bigBang[i].getHP(); j++, w += 20) {
                g2d.setPaint(new Color(102, 0, 0));
                g2d.fill(new Rectangle2D.Double(w, (int) bigBang[i].getPY() + 62, 18, 5));
            }
            for (int j = 0, w = (int) deathFish[i].getPX() + 2; j < deathFish[i].getHP(); j++, w += 12) {
                g2d.setPaint(new Color(102, 0, 0));
                g2d.fill(new Rectangle2D.Double(w, (int) deathFish[i].getPY() + 62, 10, 5));
            }
            //                for(int j = 0; j<4; j++) {
            //                    if(tirosGreenFire[i][j].ativo() && greenFire[i].isAlive())
            //                        tirosGreenFire[i][j].draw(g2d);
            //        }
            //            g2d.draw(new Rectangle2D.Double((int)deathFish[i].getPX()+2, (int)bigBang[i].getPY()+8, 60, 48));
            //                g2d.setColor(Color.white);
            //            g2d.drawString(String.valueOf(i), (int)redUFO[i].getPX(), (int)redUFO[i].getPY());
            //            g2d.drawString(String.valueOf(i+16), (int)greenFire[i].getPX(), (int)greenFire[i].getPY());
        }
        if (!player.getBlink())
            player.draw(g2d);                                                       // DESENHA NAVE PLAYER
        //g2d.drawString(String.valueOf(contBlink), (int)player.getPX()+50, (int)player.getPY()+50);
        //        g2d.drawString(, 200, 80);
        //        for(int i=0, h2=80; i<16; i++, h2+=15) {
        //            for (int j=0, h1=700; j<16; j++, h1+=15) {
        //        g2d.draw(new Rectangle2D.Double(player.getPX()+62, player.getPY()+50, 60, 30);

        //        g2d.draw(new Rectangle2D.Double(player.getPX()+62, player.getPY()+50, 60, 30));
        //        g2d.draw(new Rectangle2D.Double(missilDrop[i].getPX()+16, missilDrop[i].getPY()+16, 32, 32));
        //                g2d.drawString(i + " " + greenFire[i].frametime + " ", 10, h2);
        //
        //               g2d.drawString(deathFish[i].getIt() + " " + deathFish[i].getSentido(), 10, h2);
        //                g2d.drawString("pY" + i + ": " + misseis[i].getPY(), 10, h2);
        //                if (misseis[i].ativo()) {
        //                    g2d.drawString("dX  : " + redUFO[i].getDX(), (int)(misseis[i].getPX()+(redUFO[i].getPX()+8-misseis[i].getPX()+22)/2-20), (int)misseis[i].getPY());
        //                    g2d.drawString("dY  : " + redUFO[i].getDY(), (int)redUFO[i].getPX(), (int)(misseis[i].getPY()+(redUFO[i].getPY()-misseis[i].getPY())/2+15));
        //                    g2d.draw(new Line2D.Double(misseis[i].getPX()+22, misseis[i].getPY()+6, redUFO[i].getPX(), misseis[i].getPY()+6));
        //                    g2d.draw(new Line2D.Double(redUFO[i].getPX(), redUFO[i].getPY()+32, redUFO[i].getPX(), misseis[i].getPY()+6));
        ////////////                if(redUFO[j].isAlive())
        ////////////                    g2d.draw(new Line2D.Double(player.getPX()+128, player.getPY()+64, redUFO[j].getPX(), redUFO[j].getPY()+32));
        ////////////                if(greenFire[j].isAlive())
        ////////////                    g2d.draw(new Line2D.Double(player.getPX()+128, player.getPY()+64, greenFire[j].getPX(), greenFire[j].getPY()+32));
        ////(int)(player.getPX()+50), (int)(player.getPY()+50), (int)((player.getPX()+50)+(redUFO[i].getPX()+8)-(misseis[i].getPX()+22)), (int)((player.getPY()+50)+(redUFO[i].getPY()+8)-(misseis[i].getPY())));
        ////                    g2d.draw("dY  : " + ((redUFO[i].getPY()+8)-(misseis[i].getPY())), 10, h2);
        //                }
        //            }
        //        }

        for (int i = 0, px = 20; i < player.getVidas(); i++, px += 48)                 //DESENHA INTERFACE VIDAS
            g2d.drawImage(gui_vidas, px, 20, null);
        for (int i = 0, px = 722; i < player.getNumMisseis(); i++, px -= 48)             //DESENHA INTERFACE MISSEIS
            g2d.drawImage(gui_misseis, px, 20, null);
        if (cargaMissil < 50 && cargaMissil > 0) {
            //            g2d.draw(new Rectangle2D.Double((int)player.getPX()+65, (int)player.getPY()+85, 50, 5));
            g2d.setPaint(new GradientPaint(0, (int) player.getPY() + 86, Color.cyan, 0, (int) player.getPY() + 89, Color.black));
            g2d.fill(new Rectangle2D.Double((int) player.getPX() + 66, (int) player.getPY() + 86, cargaMissil, 3));
        }


        g2d.setFont(fonteTxt);
        if (player.getTempoTurbo() > 20 && player.getTempoTurbo() < 20.5) {           //DESENHA INTERFACE TURBO
            g2d.setPaint(new GradientPaint(0, 541, Color.green, 0, 549, Color.black));
            g2d.drawString("TURBO AVAILABLE", 25, 530);
            g2d.drawString("TURBO ON!", (int) player.getPX() + 50, (int) player.getPY() + 95);
        } else if (player.getTempoTurbo() > 20) {
            g2d.setPaint(new GradientPaint(0, 541, Color.green, 0, 549, Color.black));
            g2d.drawString("TURBO AVAILABLE", 25, 530);
        } else if (player.getTurbo()) {
            g2d.setPaint(new GradientPaint(0, 541, Color.orange, 0, 549, Color.black));
            g2d.drawString("TURBO ACTIVATED", 25, 530);
        } else {
            g2d.setPaint(new GradientPaint(0, 541, Color.yellow, 0, 549, Color.black));
            g2d.drawString("TURBO CHARGING", 25, 530);
        }
        g2d.fill(new Rectangle2D.Double(26, 541, cargaTurbo, 8));
        g2d.setColor(Color.white);
        g2d.draw(new Rectangle2D.Double(25, 540, 148, 10));


        g2d.setFont(fontePontos);                                               //DESENHA PONTUAÇÃO
        g2d.setColor(Color.WHITE);

        tamPontuacao = g2d.getFontMetrics().stringWidth(String.valueOf(player.getPontos()));
        g2d.drawString(String.valueOf(player.getPontos()), 796 / 2 - tamPontuacao / 2, 60);
//            g2d.drawString(testee + " " + recordes + " " + start, 10, 100);

        if (player.getVidas() < 0)                                               //DESENHA GAME OVER
            g2d.drawImage(gameOver, 796 / 2 - 600 / 2, 570 / 2 - 110 / 2, null);
    }
}
