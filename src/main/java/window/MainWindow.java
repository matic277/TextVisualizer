package window;

import main.Pair;
import main.Sentence;
import main.Utils;
import panel.BottomPanel;
import panel.MainPanel;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;

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
        
        try { UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel"); }
        catch (Exception e) {e.printStackTrace();}
        
        mainPanel = new MainPanel(this);
        
        frame.add(mainPanel);
        frame.pack();
    }
    
    public MainPanel getMainPanel() { return this.mainPanel; }
    
    public Map<Pair<Integer, String>, List<Sentence>> getChapters() {
        return this.chapters;
    }
}
