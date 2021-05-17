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
    Dimension panelSize;
    
    LeftPanel leftPanel;
    RightPanel rightPanel;
    
    List<Sentence> sentences;
    
    public BottomPanel(Dimension size, MainPanel parent) {
        super(HORIZONTAL_SPLIT, null, null);
        this.parent = parent;
        this.panelSize = size;
//        this.sentences = parent.getSentences();
        
//        this.setOpaque(true);
//        this.setSize(size);
//        this.setPreferredSize(size);
//        this.setLayout(null);
//        this.setVisible(true);
        
        leftPanel = new LeftPanel(this, new TextBox());
        rightPanel = new RightPanel();
        
        this.setLeftComponent(leftPanel);
        this.setRightComponent(rightPanel);
    
        this.setDividerLocation(400);
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
