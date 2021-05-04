package main;

import window.MainWindow;

import javax.swing.*;
import java.awt.*;

public class Main {
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MainWindow(new Dimension(800, 600));
        });
    }
}
