package panel;

import main.Pair;
import main.Sentence;
import main.Utils;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SlidingWindow extends Rectangle {
    
    ChaptersPanel parent;
    Map<Pair<Integer, String>, List<Sentence>> chapters;
    
    Color color = Color.black;
    Stroke stroke = new BasicStroke(3f);
    
    public SlidingWindow(ChaptersPanel parent) {
        this.parent = parent;
        
        this.setBounds(30, 100, Utils.INITIAL_SLIDER_WIDTH, 90);
    }
    
    public void paint(Graphics g) {
        Graphics2D gr = (Graphics2D) g;
        gr.setColor(color);
        
        // vertical lines, draw before antialiasing hints!
        gr.setStroke(stroke);
        gr.drawRect(x+1, y+2, width-2, height-6);
        
        gr.setColor(Color.black);
        gr.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        gr.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        
        // top
        gr.fillRoundRect(x, y, width+1, 11, 5, 5);
        // bottom
        gr.fillRoundRect(x, y+height-5,  width+1, 5, 5, 5);
    }
    
    public List<SentenceLabel> getHoveredSentences(JPanel mainSentencePanel) {
        JPanel sentencesPanel = (JPanel) mainSentencePanel.getComponents()[1];
        List<SentenceLabel> hovered = new ArrayList<>(20);
        Component[] sentenceCmps = sentencesPanel.getComponents();
        
        for (int i=0; i<sentenceCmps.length; i++) {
            // true X position of sentenceLabel => mainSentencePanel.x + sentenceLabel.x
            if (this.getBounds().contains(sentenceCmps[i].getLocation().x + mainSentencePanel.getX(), mainSentencePanel.getY())) {
                SentenceLabel slbl = (SentenceLabel) sentenceCmps[i];
                hovered.add(slbl);
            }
        }
        
        return hovered;
    }
    
    public void setColor(Color color) {
        this.color = color;
    }
}
