package panel;

import main.Pair;
import main.Sentence;
import main.Utils;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;

public class LeftPanel extends JScrollPane {
    
    BottomPanel parent;
    TextBox textBox;
    
    Map<Pair<Integer, String>, List<Sentence>> chapters;
    
    public LeftPanel(BottomPanel parent, TextBox content) {
        super(content);
        this.parent = parent;
        this.chapters = parent.parent.getChapters();
        this.textBox = content;
        this.textBox.setParent(this);
    }
    
    public void onSentenceClick(Sentence clickedSentence) {
        textBox.onSentenceClick(clickedSentence);
    }
    
    public void onSentenceHover(List<SentenceLabel> hoveredSentences) {
        textBox.onSentenceHover(hoveredSentences);
    }
}
