package main.com.github.fariadavi;

import java.awt.*;

import static main.com.github.fariadavi.MainFrame.WINDOW_HEIGHT;
import static main.com.github.fariadavi.MainFrame.WINDOW_WIDTH;
import static main.com.github.fariadavi.utils.FileHelper.getImage;

public class CanvasImageComponent extends CanvasComponent {

    private Image componentImage;

    public static final double ALIGNMENT_START = 0d;
    public static final double ALIGNMENT_CENTER = 0.5d;
    public static final double ALIGNMENT_END = 1d;
    private double alignmentX;
    private double alignmentY;

    private double width;
    private double height;
    private double initialWidth;
    private double initialHeight;

    public CanvasImageComponent(String imageResourcePath, boolean isActive) {
        super(isActive);

        this.alignmentX = ALIGNMENT_START;
        this.alignmentY = ALIGNMENT_START;

        this.componentImage = getImage(imageResourcePath);
        setInitialDimensions(this.componentImage.getWidth(null), this.componentImage.getHeight(null));
    }

    public CanvasImageComponent(String imageResourcePath, int x, int y, boolean isActive) {
        super(x, y, isActive);

        this.alignmentX = ALIGNMENT_START;
        this.alignmentY = ALIGNMENT_START;

        this.componentImage = getImage(imageResourcePath);
        setInitialDimensions(componentImage.getWidth(null), componentImage.getHeight(null));
    }

    public Image getComponentImage() {
        return componentImage;
    }

    public void setComponentImage(Image componentImage) {
        this.componentImage = componentImage;
    }

    public void setComponentImage(String imageResourcePath) {
        this.componentImage = getImage(imageResourcePath);
        setDimensions(this.componentImage.getWidth(null), this.componentImage.getHeight(null));
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }


    public void setInitialDimensions(double w, double h) {
        this.width = w;
        this.height = h;
        this.initialWidth = this.width;
        this.initialHeight = this.height;
    }

    public void setDimensions(double w, double h) {
        this.width = w;
        this.height = h;
    }

    public void resetDimensions() {
        this.width = this.initialWidth;
        this.height = this.initialHeight;
    }

    public double getAlignmentX() {
        return alignmentX;
    }

    public double getAlignmentY() {
        return alignmentY;
    }

    public void setAlignmentX(double newAlignment) {
        this.alignmentX = newAlignment;
    }

    public void setAlignmentY(double newAlignment) {
        this.alignmentY = newAlignment;
    }

    public double getVisibleX() {
        return this.getPX() - this.getWidth() * this.getAlignmentX();
    }

    public double getVisibleY() {
        return this.getPY() - this.getHeight() * this.getAlignmentY();
    }

    public void reset() {
        super.reset();
        this.resetDimensions();
    }

    @Override
    public boolean isVisible() {
        double startX = getVisibleX();
        double endX = startX + this.getWidth();
        double startY = getVisibleY();
        double endY = startY + this.getHeight();

        return endX > 0 && endY > 0 && startX < WINDOW_WIDTH && startY < WINDOW_HEIGHT;
    }

    @Override
    public void draw(Graphics2D g2d) {
        if (!this.isActive()) return;

        g2d.drawImage(componentImage, (int) getVisibleX(), (int) getVisibleY(), null);
//        g2d.draw(new Rectangle((int) getVisibleX(), (int) getVisibleY(), (int) getWidth(), (int) getHeight()));
    }
}
