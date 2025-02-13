package main.com.github.fariadavi;

import main.com.github.fariadavi.game.GameRun;
import main.com.github.fariadavi.titlescreen.TitleScreen;
import main.com.github.fariadavi.utils.InputManager;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CanvasPanel extends JPanel implements Runnable {

    private InputManager inputManager;

    public boolean isKeyPressed(int keyCode) {
        return this.inputManager.isKeyPressed(keyCode);
    }

    public void clearKeyPress(int keyCode) {
        this.inputManager.clearKeyState(keyCode);
    }

    public boolean isMouseClicked() {
        return this.inputManager.isMouseClicked();
    }

    public int[] getClickPosition() {
        return this.inputManager.getClickPosition();
    }

    public void clearMouseClick() {
        this.inputManager.clearClickPosition();
    }

    public ScoreManager scoreManager;

    public TitleScreen titlescreen;
    public GameRun gameRun;

    public CanvasPanel() {
        setDoubleBuffered(true);
        setFocusable(true);
        load();
        new Thread(this).start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    @Override
    public void run() {
        double btime, dtime = 0;
        btime = System.currentTimeMillis();
        while (true) {
            try {
                update(dtime / 1000);
            } catch (IOException ex) {
                Logger.getLogger(CanvasPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
            repaint();
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
            dtime = (System.currentTimeMillis() - btime);
            btime = System.currentTimeMillis();
        }
    }

    private void load() {
        setBackground(Color.BLACK);

        this.inputManager = new InputManager();
        addKeyListener(this.inputManager.getKeyboardAdapter());
        addMouseListener(this.inputManager.getMouseAdapter());

        this.scoreManager = new ScoreManager();

        this.titlescreen = new TitleScreen();
    }

    public void showScoreboard() {
        HighScore[] highScores = this.scoreManager.readScoreBoardFile();
        this.titlescreen.showScoreboard(highScores);
    }

    public void showCredits() {
        this.titlescreen.showCredits();
    }

    public void resetTitleScreen() {
        this.titlescreen.reset();
    }

    public void startNewGameRun() {
        this.titlescreen = null;
        this.gameRun = new GameRun();
    }

    public void finishGameRun() {
        this.titlescreen = new TitleScreen();
        this.gameRun = null;
    }

    public double getPlayerSpeed() {
        return this.gameRun.getPlayerSpeed();
    }

    public int getLowestHighScore() {
        return this.scoreManager.getLowestScoreFromFile();
    }

    public void addHighScore(String playerName, int score) {
        this.scoreManager.addScoreToFile(new HighScore(playerName, score));
    }

    private void update(double dt) throws IOException {
        if (this.titlescreen != null) {
            this.titlescreen.update(dt, this);
            return;
        }

        if (this.gameRun != null) {
            this.gameRun.update(dt, this);
            return;
        }
    }

    private void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        if (this.titlescreen != null) {
            this.titlescreen.draw(g2d);
            return;
        }

        if (this.gameRun != null) {
            this.gameRun.draw(g2d);
            return;
        }
    }
}
