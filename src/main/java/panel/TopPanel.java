package panel;

import main.Pair;
import main.Sentence;
import main.Utils;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;

public class TopPanel extends JScrollPane {
    
    MainPanel parent;
    
    Map<Pair<Integer, String>, List<Sentence>> chapters;
    
    public TopPanel(MainPanel parent, TopBox topBox) {
        super(topBox);
        this.parent = parent;
        this.chapters = parent.getChapters();
        
        topBox.setParent(this);
        topBox.init();
        
        this.setBackground(Color.red);
    }
}
