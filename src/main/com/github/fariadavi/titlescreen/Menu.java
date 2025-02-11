package main.com.github.fariadavi.titlescreen;

import main.com.github.fariadavi.CanvasPanel;

import java.awt.*;
import java.awt.event.KeyEvent;

import static main.com.github.fariadavi.utils.FileHelper.getImage;
import static main.com.github.fariadavi.utils.SpriteMappings.*;

public class Menu {
    private final String[] menuOptions = {
            "NEW_GAME", "HIGHSCORES", "CREDITS", "EXIT"
    };
    private final Rectangle[] menuRect = new Rectangle[4];
    private int highlightedMenuOption = 0;

    private final Image[] menuImages = new Image[8];
    private final int[] initialMenuImgPX = {
            796 / 4 * 3 - 278 / 2,
            796 / 4 * 3 - 262 / 2,
            796 / 4 * 3 - 242 / 2,
            796 / 4 * 3 - 150 / 2
    };
    private final double[] menuImgPX = {
            initialMenuImgPX[0],
            initialMenuImgPX[1],
            initialMenuImgPX[2],
            initialMenuImgPX[3],
    };
    private final int[] initialMenuImgPY = {
            250, 310, 370, 430
    };
    private final double[] menuImgPY = {
            initialMenuImgPY[0],
            initialMenuImgPY[1],
            initialMenuImgPY[2],
            initialMenuImgPY[3],
    };

    private boolean isActive = true;
    private boolean changingMenuOption = false;

    private String move = null;
    private int dtMultiplier = 0;

    public Menu() {
        menuImages[0] = getImage(SPRITE_MENUOPTION_NEWGAME_PATH);
        menuImages[1] = getImage(SPRITE_MENUOPTION_HIGHSCORES_PATH);
        menuImages[2] = getImage(SPRITE_MENUOPTION_CREDITS_PATH);
        menuImages[3] = getImage(SPRITE_MENUOPTION_QUITGAME_PATH);
        menuImages[4] = getImage(SPRITE_MENUOPTION_HOVER_NEWGAME_PATH);
        menuImages[5] = getImage(SPRITE_MENUOPTION_HOVER_HIGHSCORES_PATH);
        menuImages[6] = getImage(SPRITE_MENUOPTION_HOVER_CREDITS_PATH);
        menuImages[7] = getImage(SPRITE_MENUOPTION_HOVER_QUITGAME_PATH);

        for (int i = 0; i < menuRect.length; i++)
            menuRect[i] = new Rectangle(
                    initialMenuImgPX[i],
                    initialMenuImgPY[i],
                    menuImages[i].getWidth(null),
                    menuImages[i].getHeight(null)
            );
    }

    public void move(String direction, int multiplier) {
        move = direction;
        dtMultiplier = multiplier;
    }

    public void resetMenuPosition() {
        isActive = true;
        move = null;
        dtMultiplier = 0;

        for (int i = 0; i < menuImgPX.length; i++) {
            menuImgPX[i] = initialMenuImgPX[i];
            menuImgPY[i] = initialMenuImgPY[i];
        }
    }

    public String checkForMenuClick(int clickPX, int clickPY) {
        if (!isActive) return null;

        for (int i = 0; i < menuRect.length; i++) {
            if (menuRect[i].contains(clickPX, clickPY))
                return menuOptions[i];
        }

        return null;
    }

    public void update(double dt, CanvasPanel canvasPanel) {
        if (changingMenuOption &&
                !canvasPanel.key_states[KeyEvent.VK_DOWN] &&
                !canvasPanel.key_states[KeyEvent.VK_UP]) {
            changingMenuOption = false;

        } else if (!changingMenuOption &&
                canvasPanel.key_states[KeyEvent.VK_UP] &&
                highlightedMenuOption > 0) {
            highlightedMenuOption--;
            changingMenuOption = true;

        } else if (!changingMenuOption &&
                canvasPanel.key_states[KeyEvent.VK_DOWN] &&
                highlightedMenuOption < menuOptions.length - 1) {
            highlightedMenuOption++;
            changingMenuOption = true;
        }

        if (isActive && canvasPanel.key_states[KeyEvent.VK_ENTER]) {
            isActive = false;
            canvasPanel.titlescreen.triggerMenuAction(
                    menuOptions[highlightedMenuOption],
                    canvasPanel
            );
        }

        if (move != null) {
            for (int i = 0; i < 4; i++)
                switch (move) {
                    case "LEFT":
                        menuImgPX[i] -= (dtMultiplier * dt);
                        break;
                    case "RIGHT":
                        menuImgPX[i] += (dtMultiplier * dt);
                        break;
                    case "UP":
                        menuImgPY[i] -= (dtMultiplier * dt);
                        break;
                    case "DOWN":
                        menuImgPY[i] += (dtMultiplier * dt);
                        break;
                    default:
                }
        }
    }

    public void draw(Graphics2D g2d) {
        for (int i = 0; i < 4; i++) {
            boolean isHighlighted = highlightedMenuOption == i;
            g2d.drawImage(
                    menuImages[i + (isHighlighted ? 4 : 0)],
                    (int) menuImgPX[i],
                    (int) menuImgPY[i],
                    null
            );
        }
    }
}
