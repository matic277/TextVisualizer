package panel;

import main.Pair;
import main.Sentence;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SlidingWindow extends Rectangle {
    
    TopPanel parent;
    Map<Pair<Integer, String>, List<Sentence>> chapters;
    
    Color color = Color.black;
    Stroke stroke = new BasicStroke(4f);
    
    public SlidingWindow(TopPanel parent) {
        this.parent = parent;
        
        this.setBounds(30, 100, 100, 74);
    }
    
    public void paint(Graphics g) {
        Graphics2D gr = (Graphics2D) g;
        gr.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        gr.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        
        gr.setColor(color);
        gr.setStroke(stroke);
        gr.drawRoundRect(x, y, width, height, 7, 7);
        
        gr.drawString("[" + x + ", " + y + "]", x, y-4);
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
    
    public void setParent(TopPanel parent) {
        this.parent = parent;
        this.chapters = parent.chapters;
    }
    
    public void setColor(Color color) {
        this.color = color;
    }
}
