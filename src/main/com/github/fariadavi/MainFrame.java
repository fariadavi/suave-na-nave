package main.com.github.fariadavi;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    public static final int WINDOW_WIDTH = 800;
    public static final int WINDOW_HEIGHT = 600;

    public MainFrame() {
        setTitle("Suave na Nave");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(new CanvasPanel());
//        setUndecorated(true);
//        setResizable(true);
        setResizable(false);
        setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        pack();

        setExtendedState(MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainFrame().setVisible(true));
    }
}
