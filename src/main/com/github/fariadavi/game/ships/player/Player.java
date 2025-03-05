package main.com.github.fariadavi.game.ships.player;

import main.com.github.fariadavi.CanvasCollidableImageComponent;
import main.com.github.fariadavi.CanvasPanel;
import main.com.github.fariadavi.game.ships.Ship;

import java.awt.event.KeyEvent;

import static main.com.github.fariadavi.MainFrame.WINDOW_HEIGHT;
import static main.com.github.fariadavi.MainFrame.WINDOW_WIDTH;
import static main.com.github.fariadavi.utils.SpriteMappings.SPRITE_SHIPS_PLAYER_SPACESHIP_BOOSTED_PATH;
import static main.com.github.fariadavi.utils.SpriteMappings.SPRITE_SHIPS_PLAYER_SPACESHIP_PATH;

public class Player extends Ship {

    public static final double TURBO_RECHARGE_DURATION_SECONDS = 20;
    public static final double TURBO_CONSUME_DURATION_SECONDS = 4;

    public static final int PLAYER_MAX_MISSILE_CHARGES = 3;
    public static final double MISSILE_COOLDOWN_DURATION_SECONDS = 1.5;

    public static final int PLAYER_STARTING_HP = 3;

    private boolean isBoosted;
    private double dtBoost = 0d;
    private double speedMultiplier = 1;

    private double dtInvulnerability = 0d;
    private boolean invulnerable;

    private double dtShooting = 0d;

    private int numMissileCharges = PLAYER_MAX_MISSILE_CHARGES;
    private double dtMissile = MISSILE_COOLDOWN_DURATION_SECONDS;

    public Player(int x, int y) {
        super(SPRITE_SHIPS_PLAYER_SPACESHIP_PATH, PLAYER_STARTING_HP, x, y, 150);
        this.deadAtHP = -1;

        this.setCollisionInsetX(6);
        this.setCollisionInsetY(50);
        this.setCollisionOffsetX(56);
    }

    public double[] getPosition() {
        return new double[]{this.getPX(), this.getPY()};
    }

    public int getHealthPoints() {
        return this.healthPoints;
    }

    public boolean isBoosted() {
        return this.isBoosted;
    }

    public double getTurboChargePercentage() {
        return this.dtBoost / TURBO_RECHARGE_DURATION_SECONDS;
    }

    public double getSpeedMultiplier() {
        return this.speedMultiplier;
    }

    private double getSpeed() {
        return this.baseSpeedX * this.speedMultiplier;
    }

    public boolean isInvulnerable() {
        return this.invulnerable;
    }

    public void toggleInvulnerable() {
        this.invulnerable = !this.invulnerable;
    }

    public int getNumMissileCharges() {
        return this.numMissileCharges;
    }

    public void addMissileCharges(int missileCharges) {
        this.numMissileCharges += missileCharges;
        if (this.numMissileCharges > PLAYER_MAX_MISSILE_CHARGES)
            this.numMissileCharges = PLAYER_MAX_MISSILE_CHARGES;
    }

    public double getMissileCooldownPercentage() {
        return this.dtMissile / MISSILE_COOLDOWN_DURATION_SECONDS;
    }

    @Override
    public boolean checkForCollision(CanvasCollidableImageComponent comp) {
        if (this.invulnerable) return false;

        return super.checkForCollision(comp);
    }

    @Override
    public void hit(int damage, CanvasPanel canvasPanel) {
        super.hit(1, canvasPanel);

        this.toggleInvulnerable();
    }

    public void update(double dt, CanvasPanel canvasPanel) {
        if (this.getHealthPoints() < 0) return;

        handleMovement(dt, canvasPanel);
        handleShooting(dt, canvasPanel);
        handleMissile(dt, canvasPanel);
        handleTurbo(dt, canvasPanel);
        handleInvulnerability(dt, canvasPanel);
    }

    private void handleMovement(double dt, CanvasPanel canvasPanel) {
        if ((canvasPanel.isKeyPressed(KeyEvent.VK_RIGHT) || canvasPanel.isKeyPressed(KeyEvent.VK_D)) &&
                this.getPX() < WINDOW_WIDTH - (this.getWidth() - this.getCollisionInsetX()))
            this.moveX(MOVE_DIRECTION_RIGHT, dt, getSpeed());

        else if ((canvasPanel.isKeyPressed(KeyEvent.VK_LEFT) || canvasPanel.isKeyPressed(KeyEvent.VK_A)) &&
                this.getPX() > (getCollisionOffsetX() + getCollisionInsetX()) * -1)
            this.moveX(MOVE_DIRECTION_LEFT, dt, getSpeed());

        if ((canvasPanel.isKeyPressed(KeyEvent.VK_UP) || canvasPanel.isKeyPressed(KeyEvent.VK_W)) &&
                this.getPY() > (getCollisionOffsetY() + getCollisionInsetY()) * -1)
            this.moveY(MOVE_DIRECTION_UP, dt, getSpeed());

        else if ((canvasPanel.isKeyPressed(KeyEvent.VK_DOWN) || canvasPanel.isKeyPressed(KeyEvent.VK_S)) &&
                this.getPY() < WINDOW_HEIGHT - (this.getHeight() - this.getCollisionInsetY()))
            this.moveY(MOVE_DIRECTION_DOWN, dt, getSpeed());
    }

    private void handleShooting(double dt, CanvasPanel canvasPanel) {
        if (this.dtShooting < .5d) {
            this.dtShooting += dt;
            return;
        }

        if (canvasPanel.isKeyPressed(KeyEvent.VK_SPACE)) {
            canvasPanel.createPlayerSimpleShot();
            this.dtShooting = 0;
        }
    }

    private void handleMissile(double dt, CanvasPanel canvasPanel) {
        if (this.numMissileCharges <= 0) return;

        if (this.dtMissile < MISSILE_COOLDOWN_DURATION_SECONDS) {
            this.dtMissile += dt;
            return;
        }

        if (canvasPanel.isKeyPressed(KeyEvent.VK_CONTROL)) {
            canvasPanel.createPlayerMissileShot();
            this.dtMissile = 0;
            this.numMissileCharges--;
        }
    }

    private void handleTurbo(double dt, CanvasPanel canvasPanel) {
        if (!this.isBoosted) {
            if (this.dtBoost < TURBO_RECHARGE_DURATION_SECONDS)
                this.dtBoost += dt;
            else if (this.dtBoost > TURBO_RECHARGE_DURATION_SECONDS)
                this.dtBoost = TURBO_RECHARGE_DURATION_SECONDS;
            else if (canvasPanel.isKeyPressed(KeyEvent.VK_SHIFT)) {
                this.speedMultiplier = 2;
                this.isBoosted = true;
                this.setComponentImage(SPRITE_SHIPS_PLAYER_SPACESHIP_BOOSTED_PATH);
            }
        } else {
            if (this.dtBoost > 0) {
                double consumeMultiplier = TURBO_RECHARGE_DURATION_SECONDS / TURBO_CONSUME_DURATION_SECONDS;
                this.dtBoost -= dt * consumeMultiplier;
            } else {
                this.dtBoost = 0;
                this.speedMultiplier = 1;
                this.isBoosted = false;
                this.setComponentImage(SPRITE_SHIPS_PLAYER_SPACESHIP_PATH);
            }
        }
    }

    private void handleInvulnerability(double dt, CanvasPanel canvasPanel) {
        if (!this.invulnerable) return;

        dtInvulnerability += dt;
        if (dtInvulnerability > 2) {
            dtInvulnerability = 0;
            this.toggleInvulnerable();
            this.activate();
            return;
        }

        if ((int) (dtInvulnerability * 10) % 2 == 0)
            this.deactivate();
        else
            this.activate();
    }
}
