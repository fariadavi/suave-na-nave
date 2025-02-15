package main.com.github.fariadavi;

import java.awt.*;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Stream;

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

    public void setComponents(CanvasComponent[]... componentArrays) {
        CanvasComponent[] unionArray = new CanvasComponent[0];
        for (CanvasComponent[] componentArray : componentArrays) {
            unionArray = Stream.concat(
                    Arrays.stream(unionArray),
                    Arrays.stream(componentArray)
            ).toArray(CanvasComponent[]::new);
        }

        this.components = unionArray;
    }

    @Override
    public void resetStatus() {
        super.resetStatus();

        for (CanvasComponent component : components) {
            if (component == null) continue;

            component.resetStatus();
        }
    }

    @Override
    public void resetPosition() {
        super.resetPosition();

        for (CanvasComponent component : components) {
            if (component == null) continue;

            component.resetPosition();
        }
    }

    @Override
    public void draw(Graphics2D g2d) {
        if (!super.isActive()) return;

        for (CanvasComponent component : components) {
            if (component == null || !component.isActive()) continue;

            component.draw(g2d);
        }
    }

    @Override
    public boolean isVisible() {
        return Arrays.stream(components)
                .filter(Objects::nonNull)
                .anyMatch(CanvasComponent::isVisible);
    }

    @Override
    public void move(String direction, double dt, int dtMultiplier) {
        super.move(direction, dt, dtMultiplier);

        for (CanvasComponent component : components) {
            if (component == null) continue;

            component.move(direction, dt, dtMultiplier);
        }
    }
}
