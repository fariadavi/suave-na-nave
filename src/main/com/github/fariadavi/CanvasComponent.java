package main.com.github.fariadavi;

import java.awt.*;

public abstract class CanvasComponent {

    public static final String MOVE_DIRECTION_LEFT = "LEFT";
    public static final String MOVE_DIRECTION_RIGHT = "RIGHT";
    public static final String MOVE_DIRECTION_UP = "UP";
    public static final String MOVE_DIRECTION_DOWN = "DOWN";

    private boolean isActive;
    private final boolean initialActive;

    private double pX;
    private double pY;
    private double initialPX;
    private double initialPY;

    protected CanvasComponent(boolean isActive) {
        this.isActive = isActive;
        this.initialActive = this.isActive;
    }

    protected CanvasComponent(int x, int y, boolean isActive) {
        this(isActive);

        this.pX = x;
        this.pY = y;
        this.initialPX = this.pX;
        this.initialPY = this.pY;
    }

    public boolean isActive() {
        return this.isActive;
    }

    public void resetStatus() {
        this.isActive = this.initialActive;
    }

    public void activate() {
        isActive = true;
    }

    public void deactivate() {
        isActive = false;
    }

    public double getPX() {
        return this.pX;
    }

    public void setPX(double pX) {
        this.pX = pX;
    }

    public double getPY() {
        return this.pY;
    }

    public void setPY(double pY) {
        this.pY = pY;
    }

    public void resetPosition() {
        this.pX = this.initialPX;
        this.pY = this.initialPY;
    }

    public void reset() {
        this.resetStatus();
        this.resetPosition();
    }

    public abstract void draw(Graphics2D g2d);

    public abstract boolean isVisible();

    public void move(String direction, double dt, int dtMultiplier) {
        double currentPX = this.getPX();
        double currentPY = this.getPY();
        double newPX = currentPX;
        double newPY = currentPY;

        switch (direction) {
            case MOVE_DIRECTION_LEFT:
                newPX -= (dtMultiplier * dt);
                break;
            case MOVE_DIRECTION_RIGHT:
                newPX += (dtMultiplier * dt);
                break;
            case MOVE_DIRECTION_UP:
                newPY -= (dtMultiplier * dt);
                break;
            case MOVE_DIRECTION_DOWN:
                newPY += (dtMultiplier * dt);
                break;
            default:
        }

        this.setPX(newPX);
        this.setPY(newPY);
    }
}
