package main.com.github.fariadavi.game.ships.enemy;

import main.com.github.fariadavi.CanvasPanel;
import main.com.github.fariadavi.game.decorations.hud.EnemyHealthBar;
import main.com.github.fariadavi.game.ships.Ship;
import main.com.github.fariadavi.game.ships.player.Player;

import java.awt.*;

import static main.com.github.fariadavi.MainFrame.WINDOW_HEIGHT;
import static main.com.github.fariadavi.MainFrame.WINDOW_WIDTH;
import static main.com.github.fariadavi.utils.Randomizer.randInt;

public abstract class Enemy extends Ship {

    private EnemyHealthBar enemyHealthBar;

    private double dtShooting;

    public Enemy(String imageResourcePath, int healthPoints, int baseSpeedX) {
        this(imageResourcePath, healthPoints, baseSpeedX, 0, 0);
    }

    public Enemy(String imageResourcePath, int healthPoints, int baseSpeedX, int baseSpeedY, int breadthY) {
        super(imageResourcePath, healthPoints, baseSpeedX, baseSpeedY);

        this.setInitialPosition(
                WINDOW_WIDTH - 1,
                randInt(breadthY, WINDOW_HEIGHT - breadthY - (int) this.getHeight())
        );

        generateHealthBar();
    }

    public void generateHealthBar() {
        this.enemyHealthBar = new EnemyHealthBar(
                this.healthPoints,
                (int) (this.getWidth() - this.getCollisionOffsetX() - this.getCollisionInsetX() * 2)
        );
    }

    public void update(double dt, CanvasPanel canvasPanel) {
        if (!this.isActive()) return;

        if (this.isActive() && !this.isVisible()) {
            this.deactivate();
            return;
        }

        handleShooting(dt, canvasPanel);
        handleCollision(dt, canvasPanel);
        if (!this.isActive()) return;

        this.moveX(MOVE_DIRECTION_LEFT, dt, this.baseSpeedX * canvasPanel.getPlayerSpeedMultiplier());
        this.moveY(MOVE_DIRECTION_DOWN, dt, this.baseSpeedY * canvasPanel.getPlayerSpeedMultiplier());

        this.enemyHealthBar.updateLocation(this);
    }

    private void handleShooting(double dt, CanvasPanel canvasPanel) {
        EnemyType enemyType = EnemyType.getEnemyType(this.getClass());
        if (enemyType == null) return;

        double shootingInterval = enemyType.getShootingInterval();
        if (shootingInterval <= 0) return;

        this.dtShooting += dt;

        if (this.dtShooting < shootingInterval) return;
        canvasPanel.createEnemySimpleShot(this);
        this.dtShooting = 0;
    }

    private void handleCollision(double dt, CanvasPanel canvasPanel) {
        Player player = canvasPanel.getPlayerShip();
        if (!player.isInvulnerable() && this.checkForCollision(player)) {
            this.hit(10, canvasPanel);

            player.hit(1, canvasPanel);
        }
    }

    @Override
    public void hit(int damage, CanvasPanel canvasPanel) {
        super.hit(damage, canvasPanel);
        this.enemyHealthBar.setHealthPoints(this.healthPoints);

        if (this.healthPoints > this.deadAtHP) return;

        EnemyType enemyType = EnemyType.getEnemyType(this.getClass());
        if (enemyType != null && randInt(1, (int) (1 / enemyType.getMissileDropChance())) == 1)
            canvasPanel.dropMissile((int) this.getCollidableStartX(), (int) this.getCollidableStartY());
    }

    @Override
    public void draw(Graphics2D g2d) {
        super.draw(g2d);
        this.enemyHealthBar.draw(g2d);
    }

    @Override
    public void setCollisionInsetX(double collisionInsetX) {
        super.setCollisionInsetX(collisionInsetX);
        generateHealthBar();
    }

    @Override
    public void setCollisionOffsetX(double collisionOffsetX) {
        super.setCollisionOffsetX(collisionOffsetX);
        generateHealthBar();
    }

    @Override
    public void setWidth(double width) {
        super.setWidth(width);
        generateHealthBar();
    }

    @Override
    public void setDimensions(double w, double h) {
        super.setDimensions(w, h);
        generateHealthBar();
    }

    @Override
    public void resetDimensions() {
        super.resetDimensions();
        generateHealthBar();
    }
}
