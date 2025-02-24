package main.com.github.fariadavi;

public abstract class CanvasCollidableImageComponent extends CanvasImageComponent {

    private double collisionInsetX;
    private double collisionInsetY;

    private double collisionOffsetX;
    private double collisionOffsetY;

    public CanvasCollidableImageComponent(String imageResourcePath, boolean isActive) {
        super(imageResourcePath, isActive);
    }

    public CanvasCollidableImageComponent(String imageResourcePath, int x, int y, boolean isActive) {
        super(imageResourcePath, x, y, isActive);
    }

    public double getCollisionInsetX() {
        return collisionInsetX;
    }

    public void setCollisionInsetX(double collisionInsetX) {
        this.collisionInsetX = collisionInsetX;
    }

    public double getCollisionInsetY() {
        return collisionInsetY;
    }

    public void setCollisionInsetY(double collisionInsetY) {
        this.collisionInsetY = collisionInsetY;
    }

    public double getCollisionOffsetX() {
        return collisionOffsetX;
    }

    public void setCollisionOffsetX(double collisionOffsetX) {
        this.collisionOffsetX = collisionOffsetX;
    }

    public double getCollisionOffsetY() {
        return collisionOffsetY;
    }

    public void setCollisionOffsetY(double collisionOffsetY) {
        this.collisionOffsetY = collisionOffsetY;
    }

    public double getCollidableStartX() {
        return this.getPX() + this.getCollisionOffsetX() + this.getCollisionInsetX();
    }

    public double getCollidableEndX() {
        return this.getPX() + this.getWidth() - this.getCollisionInsetX();
    }

    public double getCollidableStartY() {
        return this.getPY() + this.getCollisionOffsetY() + this.getCollisionInsetY();
    }

    public double getCollidableEndY() {
        return this.getPY() + this.getHeight() - this.getCollisionInsetY();
    }

    public double getCollidableCenterX() {
        return this.getPX() + this.getCollisionOffsetX() + (this.getWidth() - this.getCollisionOffsetX()) / 2;
    }

    public double getCollidableCenterY() {
        return this.getPY() + this.getCollisionOffsetY() + (this.getHeight() - this.getCollisionOffsetY()) / 2;
    }

    public boolean checkForCollision(CanvasCollidableImageComponent comp) {
        if (comp == null || !comp.isActive()) return false;

        return this.getCollidableStartX() < comp.getCollidableEndX() &&
                this.getCollidableEndX() > comp.getCollidableStartX() &&
                this.getCollidableStartY() < comp.getCollidableEndY() &&
                this.getCollidableEndY() > comp.getCollidableStartY();
    }

    public CanvasCollidableImageComponent checkForCollision(CanvasCollidableImageComponent... comp) {
        for (CanvasCollidableImageComponent component : comp)
            if (checkForCollision(component))
                return component;

        return null;
    }

//    @Override
//    public void draw(Graphics2D g2d) {
//        g2d.setColor(Color.WHITE);
//        super.draw(g2d);
//        g2d.setColor(Color.RED);
//        g2d.draw(new Rectangle(
//                (int) (this.getCollidableStartX()),
//                (int) (this.getCollidableStartY()),
//                (int) getWidth() - (int) this.getCollisionOffsetX() - (int) getCollisionInsetX() * 2,
//                (int) getHeight() - (int) this.getCollisionOffsetY() - (int) getCollisionInsetY() * 2
//        ));
//    }
}
