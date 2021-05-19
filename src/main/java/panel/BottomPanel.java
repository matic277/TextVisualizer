package panel;

import main.Sentence;
import main.Utils;
import window.MainWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.Arrays;
import java.util.List;

public class BottomPanel extends JSplitPane {
    
    MainPanel parent;
    
    LeftPanel leftPanel;
    RightPanel rightPanel;
    
    TextBox box;
    
    
    public BottomPanel(MainPanel parent) {
        super(HORIZONTAL_SPLIT, null, null);
        this.parent = parent;
        
        box = new TextBox();
        
        leftPanel = new LeftPanel(this, box);
        rightPanel = new RightPanel(this);
        
        this.setLeftComponent(leftPanel);
        this.setRightComponent(rightPanel);
        
        this.setDividerLocation(Utils.INITIAL_LEFT_MENU_WIDTH);
        
        
        // hacky stuff
        leftPanel.addComponentListener(new ComponentListener() {
            @Override public void componentResized(ComponentEvent e) {
                box.sentencesPanel.setPreferredSize(new Dimension(
                        leftPanel.getSize().width,
                        box.sentencesPanel.getHeight()));
                box.sentencesPanel.revalidate();
                box.sentencesPanel.doLayout();
                box.updateUI();
                box.doLayout();
            }
            @Override public void componentMoved(ComponentEvent e) { }
            @Override public void componentShown(ComponentEvent e) { }
            @Override public void componentHidden(ComponentEvent e) { }
        });
        box.sentencesPanel.setPreferredSize(new Dimension(leftPanel.getSize().width, 1000));
        box.updateUI();
        box.doLayout();
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
