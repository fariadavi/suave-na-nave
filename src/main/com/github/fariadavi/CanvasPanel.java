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

    private boolean start = false;

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
        addKeyListener(inputManager.getKeyboardAdapter());
        addMouseListener(inputManager.getMouseAdapter());

        this.scoreManager = new ScoreManager();

        this.titlescreen = new TitleScreen();
        this.gameRun = new GameRun();
    }

    public void startNewRun() {
        start = true;
        gameRun.start();
    }

    public void showScoreboard() {
        if (start) return;

        HighScore[] highScores = scoreManager.readScoreBoardFile();
        titlescreen.showScoreboard(highScores);
    }

    public void showCredits() {
        if (start) return;

        titlescreen.showCredits();
    }

    public void returnToTitleScreen() {
        if (start) {
            start = false;
            titlescreen.reset();
        } else {
            titlescreen.reset();
        }
//        gameRun.resetPlayer();
//        player.setPos(80, 280);
    }

    public int getLowestHighScore() {
        return scoreManager.getLowestScoreFromFile();
    }

    public void addHighScore(String playerName, int score) {
        scoreManager.addScoreToFile(new HighScore(playerName, score));
    }

    private void update(double dt) throws IOException {
        if (start) {
            gameRun.update(dt, this);
        } else {
            titlescreen.update(dt, this);
        }
    }

    private void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        if (start) {
            gameRun.draw(g2d);
        } else {
            titlescreen.draw(g2d);
        }
    }
}
