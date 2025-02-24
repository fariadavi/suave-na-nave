package main.com.github.fariadavi;

import java.awt.*;

public abstract class CanvasComponent {

    public static final double MOVE_DIRECTION_LEFT = -1d;
    public static final double MOVE_DIRECTION_RIGHT = 1d;
    public static final double MOVE_DIRECTION_UP = -1d;
    public static final double MOVE_DIRECTION_DOWN = 1d;

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

        setInitialPosition(x, y);
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

    protected void setInitialPosition(int x, int y) {
        this.initialPX = x;
        this.initialPY = y;
        this.pX = this.initialPX;
        this.pY = this.initialPY;
    }

    public double getInitialPY() {
        return initialPY;
    }

    public double getInitialPX() {
        return initialPX;
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

    public void moveX(double direction, double dt, double dtMultiplier) {
        this.setPX(this.getPX() + direction * dtMultiplier * dt);
    }

    public void moveY(double direction, double dt, double dtMultiplier) {
        this.setPY(this.getPY() + direction * dtMultiplier * dt);
    }
}
