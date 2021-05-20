package panel;

import main.Utils;
import word.AbsMeasurableWord;
import word.AbsWord;

import javax.swing.*;
import javax.swing.border.StrokeBorder;
import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class WordLabel extends JLabel {
    
    AbsWord word;
    LeftPanel parent;
    JPanel containerParent;
    
    private final Color NORMAL_COLOR;
    private Color HOVERED_COLOR;
    
    public WordLabel (LeftPanel parent, JPanel containerParent, AbsWord word) {
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
//                    word.getSentimentValue() < AbsMeasurableWord.NEUTRAL_THRESHOLD ?
//                            Color.red : word.getSentimentValue() > AbsMeasurableWord.POSITIVE_THRESHOLD ? Color.green : Color.darkGray)
            );
        }
        else {
            // transparent with border
            this.setBorder(new StrokeBorder(new BasicStroke(1)));
            
            NORMAL_COLOR = new Color(240, 240, 240);
            HOVERED_COLOR = Color.white;
        }
        
        this.setBackground(NORMAL_COLOR);
        
        this.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // TODO
            }
            
            @Override public void mouseEntered(MouseEvent e) {
                WordLabel.this.setBackground(WordLabel.this.HOVERED_COLOR);
                WordLabel.this.containerParent.setBackground(Color.white);
                WordLabel.this.containerParent.setBorder(BorderFactory.createMatteBorder(1,1,1,1,Utils.GRAY));
                WordLabel.this.parent.repaint();
            }
            
            @Override public void mouseExited(MouseEvent e) {
                WordLabel.this.setBackground(WordLabel.this.NORMAL_COLOR);
                WordLabel.this.parent.repaint();
            }
            
            @Override public void mousePressed(MouseEvent e) { }
            @Override public void mouseReleased(MouseEvent e) { }
        });
    }
}
