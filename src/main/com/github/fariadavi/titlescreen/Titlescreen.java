package main.com.github.fariadavi.titlescreen;

import main.com.github.fariadavi.CanvasPanel;

import java.awt.*;
import java.awt.event.MouseEvent;

import static main.com.github.fariadavi.utils.FileHelper.getImage;
import static main.com.github.fariadavi.utils.SpriteMappings.*;

public class Titlescreen {

    private MockPlayer mockPlayer;
    private Menu menu;
    private Highscore recorde;

    private Font fontePontos = new Font("Verdana", Font.PLAIN, 48);

    private Image imgCreditos, title, newGame, records, quit;

    private Image[] backgroundMenu = new Image[5];

    private boolean creditos = false, recordes = false;
    private double[] pxBackGroundMenu = {0, 290, 580, 870, 1160};

    private double titleImgPY = 20, imgCreditosPY = 600, frametimeCreditos = 0;

    public Titlescreen() {
        title = getImage(SPRITE_TEXTS_GAMETITLE_PATH);
        mockPlayer = new MockPlayer();
        menu = new Menu();
        recorde = new Highscore();

        imgCreditos = getImage(SPRITE_TEXTS_CREDITS_DEVLIST_PATH);

        for (int i = 0; i < 5; i++){
            String bg_sprite_path = SPRITE_BG_TITLESCREEN_PATH
                    .replaceFirst("%i%", String.valueOf(i + 1));
            backgroundMenu[i] = getImage(bg_sprite_path);
        }
    }

    private void resetPositions() {
        titleImgPY = 20;
        menu.resetMenuPosition();
    }

    public void update(double dt, CanvasPanel canvasPanel) {
        for (int i = 0; i < 5; i++) {
            pxBackGroundMenu[i] -= 70 * dt;
            if (pxBackGroundMenu[i] < -290) {
                pxBackGroundMenu[i] = 1159;
            }
        }

        menu.update(dt, canvasPanel);

        if (recordes) {
            menu.move("RIGHT", 320);

            titleImgPY -= 170 * dt;
            recorde.ativar();
            recorde.update(dt);
        }

        if (creditos) {
            menu.move("RIGHT", 200);

            titleImgPY -= 170 * dt;
            imgCreditosPY -= 200 * dt;
            if (imgCreditosPY < -300) {
                imgCreditosPY = 600;
                creditos = false;
                resetPositions();
            }
        }
    }

    public void draw(Graphics2D g2d) {
        g2d.drawImage(backgroundMenu[0], (int) pxBackGroundMenu[0], 0, null);
        g2d.drawImage(backgroundMenu[1], (int) pxBackGroundMenu[1], 0, null);
        g2d.drawImage(backgroundMenu[2], (int) pxBackGroundMenu[2], 0, null);
        g2d.drawImage(backgroundMenu[3], (int) pxBackGroundMenu[3], 0, null);
        g2d.drawImage(backgroundMenu[4], (int) pxBackGroundMenu[4], 0, null);
        g2d.drawImage(title, 796 / 2 - 600 / 2, (int) titleImgPY, null);
        g2d.drawImage(imgCreditos, 796 / 2 - 373 / 2, (int) imgCreditosPY, null);
        g2d.setFont(fontePontos);
        g2d.setColor(Color.red);
//            g2d.drawString(testee + " " + recordes + " " + start, 10, 100);
//                    g2d.drawString(logoImageRect.getMinY() + " " + logoImageRect.getMaxY() + " " +newGame.getHeight(this), 10, 115);
//        player.draw(g2d);
        recorde.draw(g2d);
        menu.draw(g2d);
    }

    public void show() {
        resetPositions();
        pxBackGroundMenu[0] = 0;
        pxBackGroundMenu[1] = 290;
        pxBackGroundMenu[2] = 580;
        pxBackGroundMenu[3] = 870;
        pxBackGroundMenu[4] = 1160;
    }

    public void handleMouseClick(MouseEvent e, CanvasPanel canvasPanel) {
        String menuAction = menu.checkForMenuClick(e.getX(), e.getY());
        if (menuAction == null)
            return;

        triggerMenuAction(menuAction, canvasPanel);
    }

    public void triggerMenuAction(String menuAction, CanvasPanel canvasPanel) {
        if (menuAction == null)
            return;

        switch (menuAction) {
            case "START":
                canvasPanel.newGame();
            case "HIGHSCORES":
                recordes = true;
                break;
            case "CREDITS":
                creditos = true;
                break;
            case "EXIT":
                System.exit(0);
            default:
        }
    }
}
