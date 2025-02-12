package main.com.github.fariadavi;

import java.awt.*;
import java.util.Arrays;

public class CanvasGroupComponent extends CanvasComponent {

    private CanvasComponent[] components;

    public CanvasGroupComponent(boolean isActive, CanvasComponent... components) {
        super(isActive);

        this.components = components;
    }

    public CanvasGroupComponent(int x, int y, boolean isActive, CanvasComponent... components) {
        super(x, y, isActive);

        this.components = components;
    }

    public CanvasComponent[] getComponents() {
        return components;
    }

    public void setComponents(CanvasComponent... components) {
        this.components = components;
    }

    @Override
    public void resetStatus() {
        super.resetStatus();

        for (CanvasComponent component : components)
            component.resetStatus();
    }

    @Override
    public void resetPosition() {
        super.resetPosition();

        for (CanvasComponent component : components)
            component.resetPosition();
    }

    @Override
    public void draw(Graphics2D g2d) {
        if (!super.isActive()) return;

        for (CanvasComponent component : components)
            component.draw(g2d);
    }

    @Override
    public boolean isVisible() {
        return Arrays.stream(components).anyMatch(CanvasComponent::isVisible);
    }

    @Override
    public void move(String direction, double dt, int dtMultiplier) {
        super.move(direction, dt, dtMultiplier);

        for (CanvasComponent component : components) {
            component.move(direction, dt, dtMultiplier);
        }
    }
}
