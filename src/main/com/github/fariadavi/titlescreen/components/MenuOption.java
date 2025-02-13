package main.com.github.fariadavi.titlescreen.components;

import main.com.github.fariadavi.CanvasImageComponent;
import main.com.github.fariadavi.CanvasPanel;

import java.awt.*;

import static main.com.github.fariadavi.titlescreen.components.Menu.*;
import static main.com.github.fariadavi.utils.FileHelper.getImage;

public class MenuOption extends CanvasImageComponent {

    private final String menuAction;
    private final Image alternateImage;
    private boolean isHighlighted;

    public MenuOption(
            String optionAction,
            String imageResourcePath,
            int x,
            int y,
            boolean isActive,
            boolean isHighlighted
    ) {
        super(imageResourcePath, x, y, isActive);
        this.menuAction = optionAction;
        this.isHighlighted = isHighlighted;

        String alternateImagePath = imageResourcePath.replaceFirst("\\.png", "_selected.png");
        this.alternateImage = getImage(alternateImagePath);
        setAlignmentX(ALIGNMENT_CENTER);
    }

    public boolean isHighlighted() {
        return this.isHighlighted;
    }

    public void setHighlighted(boolean highlighted) {
        this.isHighlighted = highlighted;
    }

    public void executeAction(CanvasPanel canvasPanel) {
        switch (menuAction) {
            case ACTION_NEWRUN:
                canvasPanel.startNewGameRun();
                break;
            case ACTION_HIGHSCORES:
                canvasPanel.showScoreboard();
                break;
            case ACTION_CREDITS:
                canvasPanel.showCredits();
                break;
            case ACTION_QUITGAME:
                System.exit(0);
            default:
        }
    }

    @Override
    public void draw(Graphics2D g2d) {
        g2d.drawImage(
                this.isHighlighted ? this.alternateImage : this.getComponentImage(),
                (int) this.getVisibleX(),
                (int) this.getVisibleY(),
                null
        );
    }

    public boolean checkClicked(int x, int y) {
        return new Rectangle(
                (int) this.getVisibleX(),
                (int) this.getVisibleY(),
                (int) this.getWidth(),
                (int) this.getHeight()
        ).contains(x, y);
    }
}
