package panel;

import main.Pair;
import main.Sentence;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;

public class SlidingWindow {
    
    TopPanel parent;
    Map<Pair<Integer, String>, List<Sentence>> chapters;
    
    public SlidingWindow(TopPanel parent) {
        this.parent = parent;
        this.chapters = parent.chapters;
        
        init();
    }
    
    public void init() {
        JPanel content = new JPanel();
        content.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        content.setBackground(Color.black);
        
        
    }
}
