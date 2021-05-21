package panel;

import main.Pair;
import main.Sentence;
import main.Utils;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;

public class TopPanel extends JPanel {
    
    MainPanel parent;
    
    JPanel controlPanel;
    ChaptersPanel chaptersPanel;
    
    Map<Pair<Integer, String>, List<Sentence>> chapters;
    
    public TopPanel(MainPanel parent) {
        this.parent = parent;
        this.chapters = parent.getChapters();
        
        controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        controlPanel.add(new JLabel("CONTENT"));
        
        chaptersPanel = new ChaptersPanel(this);
        
        this.setLayout(new BorderLayout());
        this.add(controlPanel, BorderLayout.NORTH);
        this.add(chaptersPanel, BorderLayout.CENTER);
    }
}
