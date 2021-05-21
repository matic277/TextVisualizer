package panel;

import main.Sentence;
import main.Utils;
import word.AbsMeasurableWord;
import word.AbsWord;
import word.StopWord;

import javax.swing.*;
import javax.swing.border.StrokeBorder;
import java.awt.*;

public class WordLabel extends JLabel {
    
    Sentence parentSentence;
    AbsWord word;
    JComponent parent;
    JPanel containerParent;
    
    RightPanel rightPanel;
    
    public final Color NORMAL_COLOR;
    public final Color HOVERED_COLOR;
    
    public WordLabel (JComponent parent, JPanel containerParent, AbsWord word) {
        super(" " + word.getSourceText() + " ");
        this.parent = parent;
        this.containerParent = containerParent;
        this.word = word;
        
        this.setOpaque(true);
        this.setFont(Utils.getFont(14));
        
        if (word.hasSentimentValue()) {
            NORMAL_COLOR = word.getSentimentValue() < AbsMeasurableWord.NEUTRAL_THRESHOLD ?
                    Utils.RED : word.getSentimentValue() > AbsMeasurableWord.POSITIVE_THRESHOLD ? Utils.GREEN : Utils.GRAY2;
            HOVERED_COLOR = NORMAL_COLOR.brighter();
            this.setBackground(NORMAL_COLOR);
            this.setBorder(BorderFactory.createMatteBorder(2,2,2,2,
                    this.getBackground().darker())
            );
        }
        else {
            // transparent with border
            this.setBorder(new StrokeBorder(new BasicStroke(1)));
            
            if (word instanceof StopWord) {
                this.setForeground(Utils.GRAY.darker().darker());
            }
            
            NORMAL_COLOR = new Color(240, 240, 240);
            HOVERED_COLOR = Color.white;
        }
        
        this.setBackground(NORMAL_COLOR);
    }
    
    public void setRightPanel(RightPanel rightPanel) { this.rightPanel = rightPanel; }
    
    public void setParentSentence(Sentence parent) {
        this.parentSentence = parent;
    }
}
