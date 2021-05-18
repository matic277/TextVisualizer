package window;

import main.Pair;
import main.Sentence;
import panel.BottomPanel;
import panel.MainPanel;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;

public class MainWindow {
    
    protected JFrame frame;
    protected MainPanel mainPanel;
    
    protected Dimension windowSize;
    
    final Color bgColor = Color.white;
    
    Map<Pair<Integer, String>, List<Sentence>> chapters;
    
    public MainWindow(Dimension windowSize, Map<Pair<Integer, String>, List<Sentence>> chapters) {
        this.windowSize = windowSize;
        this.chapters = chapters;
        
        frame = new JFrame();
        frame.setSize(windowSize);
        frame.setPreferredSize(windowSize);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
        
        try { UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel"); }
        catch (Exception e) {e.printStackTrace();}
        
        Dimension mainSize = new Dimension(windowSize.width, windowSize.height * 2/3);
        Dimension bottomSize = new Dimension(windowSize.width, windowSize.height - mainSize.height);
        mainPanel = new MainPanel(mainSize, this);
        
        frame.add(mainPanel);
        frame.pack();
    }
    
    public Map<Pair<Integer, String>, List<Sentence>> getChapters() {
        return this.chapters;
    }
}
