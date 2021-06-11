package panel.sentenceRows;

import SentenceLabel.SentenceLabel;
import main.Utils;
import panel.LeftPanel;
import panel.WrapLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class LeftSentenceRowPanel extends SentenceRowPanel {
    
    LeftPanel parent;
    JPanel parentContainer;
    
    SentenceLabel sentenceLbl;
    
    WrapLayout layout;
    
    public LeftSentenceRowPanel(LeftPanel parent, JPanel parentContainer, SentenceLabel sentenceLbl) {
        super(parent.getBottomPanel());
        this.parent = parent;
        this.parentContainer = parentContainer;
        this.sentenceLbl = sentenceLbl;
        
        this.layout = new WrapLayout(WrapLayout.LEFT);
        this.setLayout(layout);
        this.setName("SentencePanel");
        this.setBackground(Utils.GRAY3);
        this.setBorder(BorderFactory.createMatteBorder(1,1,1,1,Utils.GRAY3));
        
        init();
    }
    
    private void init() {
        this.addMouseListener(new MouseListener() {
            @Override public void mouseClicked(MouseEvent e) {
                if (!sentenceLbl.isSelected()) {
                    parent.getBottomPanel().onSentenceClick(sentenceLbl);
                    sentenceLbl.onSelect();
                }
            }
            @Override public void mouseEntered(MouseEvent e) {
                setBackground(Color.white);
                setBorder(BorderFactory.createMatteBorder(1,1,1,1,Utils.GRAY));
                repaint();
            }
            @Override public void mouseExited(MouseEvent e) {
                // instead of removing border, set it to the same color as background
                // because border slightly resizes(enlarges) component, when its non-null!
                setBorder(BorderFactory.createMatteBorder(1,1,1,1,Utils.GRAY3));
                setBackground(Utils.GRAY3);
                repaint();
            }
            @Override public void mousePressed(MouseEvent e) { this.mouseClicked(e); }
            @Override public void mouseReleased(MouseEvent e) { }
        });
        
        super.addWordLabels(sentenceLbl, this);
    }
}
