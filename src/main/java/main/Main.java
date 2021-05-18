package main;

import window.MainWindow;

import javax.swing.*;
import java.awt.*;

public class Main {
    
    public static void main(String[] args) {
        FileReader reader = new FileReader("./texts/SherlockHolmes-AStudyInScarlet.txt");
        reader.readFile();
        
        TextProcessor textProc = new TextProcessor(reader.getChaptersMap());
        textProc.processText();
        
        SwingUtilities.invokeLater(() -> {
            new MainWindow(new Dimension(800, 600), textProc.getProcessedChapters());
        });
    }
}
