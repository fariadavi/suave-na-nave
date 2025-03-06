package main.com.github.fariadavi.titlescreen.components;

import main.com.github.fariadavi.CanvasGroupComponent;
import main.com.github.fariadavi.CanvasPanel;

import java.awt.event.KeyEvent;

import static main.com.github.fariadavi.MainFrame.isEmbedded;
import static main.com.github.fariadavi.utils.SpriteMappings.*;

public class Menu extends CanvasGroupComponent {

    public static final String ACTION_NEWRUN = "NEW_GAME";
    public static final String ACTION_HIGHSCORES = "HIGHSCORES";
    public static final String ACTION_CREDITS = "CREDITS";
    public static final String ACTION_QUITGAME = "EXIT";
    public final String[][] menuOptionsInfo =
            isEmbedded ?
                    new String[][]{
                            {ACTION_NEWRUN, SPRITE_MENUOPTION_NEWGAME_PATH},
                            {ACTION_HIGHSCORES, SPRITE_MENUOPTION_HIGHSCORES_PATH},
                            {ACTION_CREDITS, SPRITE_MENUOPTION_CREDITS_PATH}
                    } :
                    new String[][]{
                            {ACTION_NEWRUN, SPRITE_MENUOPTION_NEWGAME_PATH},
                            {ACTION_HIGHSCORES, SPRITE_MENUOPTION_HIGHSCORES_PATH},
                            {ACTION_CREDITS, SPRITE_MENUOPTION_CREDITS_PATH},
                            {ACTION_QUITGAME, SPRITE_MENUOPTION_QUITGAME_PATH}
                    };

    private final MenuOption[] menuOptions = new MenuOption[menuOptionsInfo.length];
    private int highlightedMenuOption = 0;

    public Menu(int x, int y) {
        super(x, y, true);

        for (int i = 0; i < menuOptionsInfo.length; i++) {
            menuOptions[i] = new MenuOption(
                    menuOptionsInfo[i][0],
                    menuOptionsInfo[i][1],
                    x,
                    y + (isEmbedded ? 15 : 0) + (i * 60 + (isEmbedded ? 15 : 0)),
                    true,
                    i == highlightedMenuOption
            );
        }

        this.setComponents(menuOptions);
    }

    public void update(double dt, CanvasPanel canvasPanel) {
        if (!super.isActive()) return;

        if (highlightedMenuOption > 0 && (
                canvasPanel.isKeyPressed(KeyEvent.VK_UP) || canvasPanel.isKeyPressed(KeyEvent.VK_W)
        )) {
            canvasPanel.clearKeyPress(KeyEvent.VK_UP);
            canvasPanel.clearKeyPress(KeyEvent.VK_W);

            menuOptions[highlightedMenuOption].setHighlighted(false);
            menuOptions[highlightedMenuOption - 1].setHighlighted(true);
            highlightedMenuOption--;

        } else if (highlightedMenuOption < menuOptions.length - 1 && (
                canvasPanel.isKeyPressed(KeyEvent.VK_DOWN) || canvasPanel.isKeyPressed(KeyEvent.VK_S)
        )) {
            canvasPanel.clearKeyPress(KeyEvent.VK_DOWN);
            canvasPanel.clearKeyPress(KeyEvent.VK_S);

            menuOptions[highlightedMenuOption].setHighlighted(false);
            menuOptions[highlightedMenuOption + 1].setHighlighted(true);
            highlightedMenuOption++;
        }

        if (canvasPanel.isKeyPressed(KeyEvent.VK_ENTER) || canvasPanel.isKeyPressed(KeyEvent.VK_SPACE)) {
            canvasPanel.clearKeyPress(KeyEvent.VK_ENTER);
            canvasPanel.clearKeyPress(KeyEvent.VK_SPACE);

            menuOptions[highlightedMenuOption].executeAction(canvasPanel);
        }

        if (canvasPanel.isMouseClicked()) {
            int[] clickPosition = canvasPanel.getClickPosition();
            for (MenuOption menuOption : menuOptions) {
                if (menuOption.checkClicked(clickPosition[0], clickPosition[1])) {
                    menuOption.executeAction(canvasPanel);
                    break;
                }
            }
            canvasPanel.clearMouseClick();
        }
    }
}
