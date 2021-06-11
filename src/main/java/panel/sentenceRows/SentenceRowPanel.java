package panel.sentenceRows;

import SentenceLabel.SentenceLabel;
import main.Sentence;
import main.Utils;
import panel.BottomPanel;
import panel.WordLabel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class SentenceRowPanel extends JPanel {
    
    BottomPanel bottomPanel;
    
    public SentenceRowPanel(BottomPanel bottomPanel) {
        this.bottomPanel = bottomPanel;
    }
    
    public void addWordLabels(SentenceLabel sentenceLbl, JPanel container) {
        sentenceLbl.getSentence().getWords().forEach(w -> {
            WordLabel lbl = new WordLabel(this, w);
            lbl.setParentSentence(sentenceLbl.getSentence());
            lbl.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    // only one word is clicked
                    if (!sentenceLbl.isSelected()) {
                        bottomPanel.onSentenceClick(sentenceLbl);
                        sentenceLbl.onSelect();
                    }
                }
                @Override public void mouseEntered(MouseEvent e) {
                    lbl.setBackground(lbl.HOVERED_COLOR);
                    container.setBackground(Color.white);
                    container.setBorder(BorderFactory.createMatteBorder(1,1,1,1, Utils.GRAY));
                    container.repaint();
                }
                @Override public void mouseExited(MouseEvent e) {
                    lbl.setBackground(lbl.CURRENT_COLOR);
                    container.setBorder(BorderFactory.createMatteBorder(1,1,1,1,Utils.GRAY3));
                    container.setBackground(Utils.GRAY3);
                    container.repaint();
                }
                @Override public void mousePressed(MouseEvent e) { this.mouseClicked(e); }
                @Override public void mouseReleased(MouseEvent e) { }
            });
            container.add(lbl);
        });
    }
}
