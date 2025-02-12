package main.com.github.fariadavi.utils;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class InputManager {

    public static final int MAX_KEYCODE = 256;

    private final boolean[] keyStates = new boolean[MAX_KEYCODE];
    private final int[] clickPosition = new int[2];

    private class KeyboardAdapter extends KeyAdapter {
        @Override
        public void keyReleased(KeyEvent e) {
            if (e.getKeyCode() >= MAX_KEYCODE) return;

            clearKeyState(e.getKeyCode());
        }

        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() >= MAX_KEYCODE) return;

            keyStates[e.getKeyCode()] = true;
        }
    }

    public KeyAdapter getKeyboardAdapter() {
        return new KeyboardAdapter();
    }

    public boolean isKeyPressed(int keyCode) {
        if (keyCode >= MAX_KEYCODE) return false;

        return keyStates[keyCode];
    }

    public void clearKeyState(int keyCode) {
        if (keyCode >= MAX_KEYCODE) return;

        keyStates[keyCode] = false;
    }

    private class MouseAdapter implements MouseListener {

        public MouseAdapter() {

        }

        @Override
        public void mousePressed(MouseEvent e) {
            clickPosition[0] = e.getX();
            clickPosition[1] = e.getY();
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            clickPosition[0] = 0;
            clickPosition[1] = 0;
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }

        @Override
        public void mouseClicked(MouseEvent e) {
        }
    }

    public MouseListener getMouseAdapter() {
        return new MouseAdapter();
    }

    public boolean isMouseClicked() {
        return clickPosition[0] > 0 || clickPosition[1] > 0;
    }

    public int[] getClickPosition() {
        return clickPosition;
    }

    public void clearClickPosition() {
        clickPosition[0] = 0;
        clickPosition[1] = 0;
    }

    public int getClickedRectangleIndex(Rectangle... rectangles) {
        for (int i = 0; i < rectangles.length; i++) {
            if (rectangles[i].contains(clickPosition[0], clickPosition[1]))
                return i;
        }

        return -1;
    }
}
