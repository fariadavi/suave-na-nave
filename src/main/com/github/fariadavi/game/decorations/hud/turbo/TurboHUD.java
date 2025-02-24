package main.com.github.fariadavi.game.decorations.hud.turbo;

import main.com.github.fariadavi.CanvasGroupComponent;
import main.com.github.fariadavi.CanvasPanel;

public class TurboHUD extends CanvasGroupComponent {

    public static final int WIDTH_TURBO_CHARGE_BAR = 150;
    public static final int HEIGHT_TURBO_CHARGE_BAR = 10;

    private final TurboPlayerTextInfo turboPlayerTextInfo;
    private final TurboChargeBarTextInfo turboChargeBarTextInfo;
    private final TurboChargeBar turboChargeBar;

    public TurboHUD(int x, int y) {
        super(x, y, true);

        this.turboPlayerTextInfo = new TurboPlayerTextInfo();
        this.turboChargeBarTextInfo = new TurboChargeBarTextInfo(x, y);
        this.turboChargeBar = new TurboChargeBar(x, y + 10);

        setComponents(
                this.turboPlayerTextInfo,
                this.turboChargeBarTextInfo,
                this.turboChargeBar
        );
    }

    private TurboStatus getTurboStatus(boolean isTurboActive, double turboChargePercentage) {
        if (isTurboActive)
            return TurboStatus.ACTIVE;

        if (turboChargePercentage >= 1)
            return TurboStatus.READY;

        return TurboStatus.CHARGING;
    }

    public void update(double dt, CanvasPanel canvasPanel) {
        double[] playerPosition = canvasPanel.getPlayerPosition();
        this.turboPlayerTextInfo.setPX(playerPosition[0]);
        this.turboPlayerTextInfo.setPY(playerPosition[1]);

        TurboStatus turboStatus = getTurboStatus(
                canvasPanel.isPlayerTurboActive(),
                canvasPanel.getPlayerTurboChargePercentage()
        );
        if (turboStatus == null)
            System.out.println("dsada");
        this.turboPlayerTextInfo.setTurboStatus(turboStatus);
        this.turboChargeBarTextInfo.setTurboStatus(turboStatus);
        this.turboChargeBar.setTurboStatus(turboStatus);

        this.turboPlayerTextInfo.update(dt, canvasPanel);
        this.turboChargeBar.update(dt, canvasPanel);
    }
}
