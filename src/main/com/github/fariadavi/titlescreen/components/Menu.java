package main.com.github.fariadavi.titlescreen.components;

import main.com.github.fariadavi.CanvasGroupComponent;
import main.com.github.fariadavi.CanvasPanel;

import java.awt.event.KeyEvent;

import static main.com.github.fariadavi.utils.SpriteMappings.*;

public class Menu extends CanvasGroupComponent {

    public static final String ACTION_NEWRUN = "NEW_GAME";
    public static final String ACTION_HIGHSCORES = "HIGHSCORES";
    public static final String ACTION_CREDITS = "CREDITS";
    public static final String ACTION_QUITGAME = "EXIT";
    public final String[][] menuOptionsInfo = {
            {ACTION_NEWRUN, SPRITE_MENUOPTION_NEWGAME_PATH},
            {ACTION_HIGHSCORES, SPRITE_MENUOPTION_HIGHSCORES_PATH},
            {ACTION_CREDITS, SPRITE_MENUOPTION_CREDITS_PATH},
            {ACTION_QUITGAME, SPRITE_MENUOPTION_QUITGAME_PATH}
    };

    private final MenuOption[] menuOptions = new MenuOption[4];
    private int highlightedMenuOption = 0;

    public Menu(int x, int y) {
        super(x, y, true);

        for (int i = 0; i < menuOptionsInfo.length; i++) {
            menuOptions[i] = new MenuOption(
                    menuOptionsInfo[i][0],
                    menuOptionsInfo[i][1],
                    x,
                    y + (i * 60),
                    true,
                    i == highlightedMenuOption
            );
        }

        this.setComponents(menuOptions);
    }

    public void update(double dt, CanvasPanel canvasPanel) {
        if (!super.isActive()) return;

        if (canvasPanel.isKeyPressed(KeyEvent.VK_UP) && highlightedMenuOption > 0) {
            canvasPanel.clearKeyPress(KeyEvent.VK_UP);

            menuOptions[highlightedMenuOption].setHighlighted(false);
            menuOptions[highlightedMenuOption - 1].setHighlighted(true);
            highlightedMenuOption--;

        } else if (canvasPanel.isKeyPressed(KeyEvent.VK_DOWN) && highlightedMenuOption < menuOptions.length - 1) {
            canvasPanel.clearKeyPress(KeyEvent.VK_DOWN);

            menuOptions[highlightedMenuOption].setHighlighted(false);
            menuOptions[highlightedMenuOption + 1].setHighlighted(true);
            highlightedMenuOption++;
        }

        if (canvasPanel.isKeyPressed(KeyEvent.VK_ENTER)) {
            canvasPanel.clearKeyPress(KeyEvent.VK_ENTER);

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
