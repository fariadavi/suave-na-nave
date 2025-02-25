package main.com.github.fariadavi;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class MainFrame extends JFrame {

    public static final int WINDOW_WIDTH = 800;
    public static final int WINDOW_HEIGHT = 600;

    public static boolean isEmbedded = false;

    public MainFrame(String[] args) {
        setTitle("Suave na Nave");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(new CanvasPanel());

//        setResizable(true);
        setResizable(false);
        if (isEmbedded)
            setUndecorated(true);

        setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        pack();

        setExtendedState(MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        if (Arrays.asList(args).contains("--embedded"))
            isEmbedded = true;

        SwingUtilities.invokeLater(() -> new MainFrame(args).setVisible(true));
    }
}
