package panel;

import main.Sentence;
import main.Utils;
import word.AbsMeasurableWord;
import word.AbsWord;
import word.StopWord;

import javax.swing.*;
import javax.swing.border.StrokeBorder;
import java.awt.*;
import java.awt.event.MouseListener;

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
//                    word.getSentimentValue() < AbsMeasurableWord.NEUTRAL_THRESHOLD ?
//                            Color.red : word.getSentimentValue() > AbsMeasurableWord.POSITIVE_THRESHOLD ? Color.green : Color.darkGray)
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
        
//        this.containerParentlistener = new MouseListener() {
//            @Override public void mouseClicked(MouseEvent e) {
//                // Do nothing here!
//                // Listener for this must be defined in parentPanel, SEPARATELY!
//                // Otherwise this action will be fired for every word in sentence
//            }
//            @Override public void mouseEntered(MouseEvent e) {
//               containerParent.setBackground(Color.white);
//               containerParent.setBorder(BorderFactory.createMatteBorder(1,1,1,1,Utils.GRAY));
//               parent.repaint();
//            }
//            @Override public void mouseExited(MouseEvent e) {
//               containerParent.setBorder(BorderFactory.createMatteBorder(1,1,1,1,Utils.GRAY3));
//               containerParent.setBackground(Utils.GRAY3);
//               parent.repaint();
//            }
//            @Override public void mousePressed(MouseEvent e) { }
//            @Override public void mouseReleased(MouseEvent e) { }
//        };
        
//        this.wordListener = new MouseListener() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                // only one word is clicked
//                WordLabel.this.rightPanel.onWordsClick(Collections.singletonList(WordLabel.this.word));
//            }
//
//            @Override public void mouseEntered(MouseEvent e) {
//                WordLabel.this.setBackground(WordLabel.this.HOVERED_COLOR);
//                WordLabel.this.containerParent.setBackground(Color.white);
//                WordLabel.this.containerParent.setBorder(BorderFactory.createMatteBorder(1,1,1,1,Utils.GRAY));
//                WordLabel.this.parent.repaint();
//            }
//
//            @Override public void mouseExited(MouseEvent e) {
//                WordLabel.this.setBackground(WordLabel.this.NORMAL_COLOR);
//                WordLabel.this.containerParent.setBorder(BorderFactory.createMatteBorder(1,1,1,1,Utils.GRAY3));
//                WordLabel.this.containerParent.setBackground(Utils.GRAY3);
//                WordLabel.this.parent.repaint();
//            }
//            @Override public void mousePressed(MouseEvent e) { }
//            @Override public void mouseReleased(MouseEvent e) { }
//        };
        
//        this.addMouseListener(wordListener);
//        this.containerParent.addMouseListener(containerParentlistener);
    }
    
    public void setWordListener(MouseListener wordListener) {
        this.addMouseListener(wordListener);
    }
    
    public void setRightPanel(RightPanel rightPanel) {
        this.rightPanel = rightPanel;
    }
    
    public void setParentSentence(Sentence parent) {
        this.parentSentence = parent;
    }
}
