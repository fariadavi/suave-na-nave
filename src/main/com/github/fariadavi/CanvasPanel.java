package main.com.github.fariadavi;

import main.com.github.fariadavi.game.GameRun;
import main.com.github.fariadavi.titlescreen.Titlescreen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CanvasPanel extends JPanel implements Runnable {

    public final CanvasPanel canvasPanel = this;

    public boolean[] key_states = new boolean[256];

    public Titlescreen titlescreen;
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

    private class KeyboardAdapter extends KeyAdapter {
        @Override
        public void keyReleased(KeyEvent e) {
            key_states[e.getKeyCode()] = false;
        }

        @Override
        public void keyPressed(KeyEvent e) {
            key_states[e.getKeyCode()] = true;
        }
    }

    private class MouseAdapter implements MouseListener {
        //where initialization occurs:
        //Register for mouse events on blankArea and the panel.
        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
//            if (!start) {
//                if (rectNewGame.contains(e.getX(), e.getY()))
//                    System.out.println("ENTROU");
//                else if (rectRecords.contains(e.getX(), e.getY()))
//                    System.out.println("ENTROU");
//                else if (rectCredits.contains(e.getX(), e.getY()))
//                    System.out.println("ENTROU");
//                else if (rectQuit.contains(e.getX(), e.getY()))
//                    System.out.println("ENTROU");
//            }
        }

        @Override
        public void mouseExited(MouseEvent e) {
//            if (!start) {
//                if (rectNewGame.contains(e.getX(), e.getY()))
//                    System.out.println("ENTROU");
//                else if (rectRecords.contains(e.getX(), e.getY()))
//                    System.out.println("ENTROU");
//                else if (rectCredits.contains(e.getX(), e.getY()))
//                    System.out.println("ENTROU");
//                else if (rectQuit.contains(e.getX(), e.getY()))
//                    System.out.println("ENTROU");
//            }
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            if (!start) {
                titlescreen.handleMouseClick(e, canvasPanel);
            }
        }
    }

    private void load() {
        setBackground(Color.BLACK);
        addKeyListener(new KeyboardAdapter());
        addMouseListener(new MouseAdapter());

        titlescreen = new Titlescreen();
        gameRun = new GameRun();
    }

    public void goBack() {
        start = false;
        titlescreen.show();
//        gameRun.resetPlayer();
//        player.setPos(80, 280);
    }

    public void newGame() {
        start = true;
        gameRun.start();
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
