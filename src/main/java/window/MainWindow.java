package window;

import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.FlatLightLaf;
import main.*;
import panel.BottomPanel;
import panel.MainPanel;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class MainWindow {
    
    protected JFrame frame;
    protected MainPanel mainPanel;
    
    Map<Pair<Integer, String>, List<Sentence>> chapters;
    
    public MainWindow(Map<Pair<Integer, String>, List<Sentence>> chapters) {
        this.chapters = chapters;
        
        frame = new JFrame();
        frame.setSize(new Dimension(Utils.INITIAL_WINDOW_WIDTH, Utils.INITIAL_WINDOW_HEIGHT));
        frame.setPreferredSize(new Dimension(Utils.INITIAL_WINDOW_WIDTH, Utils.INITIAL_WINDOW_HEIGHT));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
        
        FlatLightLaf.install();
        UIManager.put("Button.arc", 15 );
        UIManager.put("Component.arc", 30 );
        UIManager.put("Component.focusWidth", 1);
        UIManager.put("TextComponent.arc", 50 );
        
        mainPanel = new MainPanel(this);
        
        frame.add(mainPanel);
        frame.pack();
    }
    
    public void onNewTextImport(JTextField field) {
       // regular text
        String file = field.getText();
        FileReader reader = new FileReader(file);
        try {
            reader.readFile();
            TextProcessor textProc = new TextProcessor(reader.getChaptersMap());
            textProc.processText();
            mainPanel.onNewTextImport(textProc.getProcessedChapters());
        }
        catch (Exception e) {
            System.out.println("Error processing file: \"" + file + "\".");
            e.printStackTrace();
            Color defaultClr = field.getBackground();
            field.setBackground(Utils.RED);
            Utils.sleep(1000);
            field.setBackground(defaultClr);
        }

        
        // tweets
//        String file = field.getText();
//        FileReaderDate reader = new FileReaderDate(file);
//        try {
//            reader.readFile();
//            TextProcessorDate textProc = new TextProcessorDate(reader.getChaptersMap());
//            textProc.processText();
//            mainPanel.onNewTextImport(textProc.getProcessedChapters());
//        }
//        catch (Exception e) {
//            System.out.println("Error processing file: \"" + file + "\".");
//            e.printStackTrace();
//            Color defaultClr = field.getBackground();
//            field.setBackground(Utils.RED);
//            Utils.sleep(1000);
//            field.setBackground(defaultClr);
//        }
    }
    
    public MainPanel getMainPanel() { return this.mainPanel; }
    
    public Map<Pair<Integer, String>, List<Sentence>> getChapters() {
        return this.chapters;
    }
}
