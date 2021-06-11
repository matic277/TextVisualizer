package panel;

import main.Sentence;
import main.UserDictionary.ColorChangeObserver;
import main.UserDictionary.Word;
import main.Utils;
import main.VisualType;

import javax.swing.*;
import java.awt.*;

public class WordLabel extends JLabel implements ColorChangeObserver {
    
    Word word;
    Sentence parentSentence;
    JComponent parent;
    
    private static final int BORDER_THICKNESS = 2;
    
    public Color CURRENT_COLOR = new Color(0, 0, 0);
    public Color HOVERED_COLOR = new Color(0, 0, 0);
    
    public WordLabel (JPanel parent, Word word) {
        super(" " + word.getSourceText() + " ");
        this.parent = parent;
        this.word = word;
        
        word.addObserver(this);
        
        CURRENT_COLOR = word.getColor();
        HOVERED_COLOR = CURRENT_COLOR.brighter();
        this.setBackground(CURRENT_COLOR);
        this.setOpaque(true);
        
        this.setBorder(BorderFactory.createMatteBorder(BORDER_THICKNESS, BORDER_THICKNESS, BORDER_THICKNESS, BORDER_THICKNESS, CURRENT_COLOR.darker()));
    
        this.setFont(Utils.getFont(14));
    }
    
    public void setParentSentence(Sentence parent) {
        this.parentSentence = parent;
    }
    
    @Override
    public void onColorChange() {
        CURRENT_COLOR = word.getColor();
        HOVERED_COLOR = word.getColor().brighter();
        
        this.setBorder(BorderFactory.createMatteBorder(BORDER_THICKNESS, BORDER_THICKNESS, BORDER_THICKNESS, BORDER_THICKNESS, CURRENT_COLOR.darker()));
        
        this.setBackground(CURRENT_COLOR);
        this.repaint();
    }
}
