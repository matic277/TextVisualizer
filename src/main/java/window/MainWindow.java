package window;

import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.FlatLightLaf;
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
    
        FlatLightLaf.install();
        UIManager.put("Button.arc", 15 );
        UIManager.put("Component.arc", 30 );
        UIManager.put("Component.focusWidth", 1);
        UIManager.put("TextComponent.arc", 50 );
        
        mainPanel = new MainPanel(this);
        
        frame.add(mainPanel);
        frame.pack();
    }
    
    public MainPanel getMainPanel() { return this.mainPanel; }
    
    public Map<Pair<Integer, String>, List<Sentence>> getChapters() {
        return this.chapters;
    }
}
