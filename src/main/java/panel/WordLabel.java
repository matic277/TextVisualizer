package panel;

import main.Sentence;
import main.UserDictionary.Word;
import main.Utils;
import main.VisualType;

import javax.swing.*;
import java.awt.*;

public class WordLabel extends JLabel {
    
    Sentence parentSentence;
    Word word;
    JComponent parent;
    JPanel containerParent;
    
    public Color CURRENT_COLOR = new Color(0, 0, 0);
    public Color HOVERED_COLOR = new Color(0, 0, 0);
    
    public WordLabel (JComponent parent, JPanel containerParent, Word word) {
        super(" " + word.getSourceText() + " ");
        this.parent = parent;
        this.containerParent = containerParent;
        this.word = word;
        
        CURRENT_COLOR = word.color;
        HOVERED_COLOR = CURRENT_COLOR.brighter();
        
        this.setOpaque(true);
        this.setFont(Utils.getFont(14));
        
        this.setBackground(CURRENT_COLOR);
    }
    
    public WordLabel (Word word) {
        super(" " + word.getSourceText() + " ");
        this.setFont(Utils.getFont(14));
        this.setOpaque(true);
        
        CURRENT_COLOR = word.color;
        HOVERED_COLOR = CURRENT_COLOR.brighter();
        
        this.setBackground(CURRENT_COLOR);
    }
    
    public void setParentSentence(Sentence parent) {
        this.parentSentence = parent;
    }
}
