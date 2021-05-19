package main;

import window.MainWindow;

import javax.swing.*;
import java.awt.*;

public class Main {
    
    public static void main(String[] args) {
        String[] fonts =
                GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
    
        for ( int i = 0; i < fonts.length; i++ )
        {
            System.out.println(fonts[i]);
        }
        
        FileReader reader = new FileReader("./texts/SherlockHolmes-AStudyInScarlet.txt");
        reader.readFile();
        
        TextProcessor textProc = new TextProcessor(reader.getChaptersMap());
        textProc.processText();
        
        SwingUtilities.invokeLater(() -> {
            new MainWindow(textProc.getProcessedChapters());
        });
    }
}
