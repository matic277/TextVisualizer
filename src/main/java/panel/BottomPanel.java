package panel;

import main.Sentence;
import main.Utils;
import window.MainWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.List;

public class BottomPanel extends JSplitPane {
    
    MainPanel parent;
    
    LeftPanel leftPanel;
    RightPanel rightPanel;
    
    
    public BottomPanel(MainPanel parent) {
        super(HORIZONTAL_SPLIT, null, null);
        this.parent = parent;
        
        leftPanel = new LeftPanel(this, new TextBox());
        rightPanel = new RightPanel(this);
        
        this.setLeftComponent(leftPanel);
        this.setRightComponent(rightPanel);
        
        this.setDividerLocation(400);
    }
    
    public void onSentenceClick(Sentence clickedSentence) {
        rightPanel.onSentenceClick(clickedSentence);
        leftPanel.onSentenceClick(clickedSentence);
    }
    
    public void onSentenceHover(List<SentenceLabel> hoveredSentences) {
        rightPanel.onSentenceHover(hoveredSentences);
        leftPanel.onSentenceHover(hoveredSentences);
    }

//    @Override
//    public void paintComponent(Graphics g) {
//        Graphics2D gr = (Graphics2D) g;
//        gr.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//        gr.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
//
//        gr.setColor(Utils.bgColor);
//        gr.fillRect(0, 0, this.getWidth(), this.getHeight());
//
//        gr.setColor(Color.black);
//        gr.drawRect(5, 5,  this.getWidth()-10, this.getHeight() - 10);
//    }
}
