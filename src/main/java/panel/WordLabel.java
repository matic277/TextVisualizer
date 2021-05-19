package panel;

import main.Utils;
import word.AbsMeasurableWord;
import word.AbsWord;

import javax.swing.*;
import javax.swing.border.StrokeBorder;
import java.awt.*;

public class WordLabel extends JLabel {
    
    AbsWord word;
    TextBox parent;
    
    public WordLabel (TextBox parent, AbsWord word) {
        super(" " + word.getSourceText() + " ");
        this.parent = parent;
        this.word = word;
        
        this.setOpaque(true);
        this.setFont(Utils.getFont(14));
        
        if (word.hasSentimentValue()) {
            this.setBackground(
                    word.getSentimentValue() < AbsMeasurableWord.NEUTRAL_THRESHOLD ?
                            Utils.RED : word.getSentimentValue() > AbsMeasurableWord.POSITIVE_THRESHOLD ? Utils.GREEN : Utils.GRAY2);
        }
        else {
            // transparent with border
            this.setBackground(new Color(0, 0, 0, 0));
            this.setBorder(new StrokeBorder(new BasicStroke(1)));
        }
    }
}
