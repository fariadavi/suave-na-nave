package main.com.github.fariadavi;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class MainFrame extends JFrame {

    public MainFrame() {
        setTitle("Suave na Nave");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(new CanvasPanel());
        setSize(800, 600);
        setLocationRelativeTo(null);
        setExtendedState(MAXIMIZED_BOTH);
        setResizable(false);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainFrame().setVisible(true));
    }
}
