package panel;

import main.Sentence;
import main.Utils;
import window.MainWindow;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MainPanel extends JSplitPane {
    
    MainWindow parent;
    Dimension panelSize;
    
    // contains vertically split panels:
    TopPanel topPanel;
    BottomPanel bottomPanel;
    
    public MainPanel(Dimension size, MainWindow parent) {
        super(VERTICAL_SPLIT, null, null);
        this.parent = parent;
        this.panelSize = size;
        
        this.setOpaque(true);
//        this.setSize(size);
//        this.setPreferredSize(size);
        this.setVisible(true);
        
        
        topPanel = new TopPanel();
        bottomPanel = new BottomPanel(null, this);
    
        this.setTopComponent(topPanel);
        this.setBottomComponent(bottomPanel);
    
        this.setDividerLocation(400);

    }
    
//    @Override
//    public void paintComponent(Graphics g) {
//        Graphics2D gr = (Graphics2D) g;
//        gr.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//        gr.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
////
////        gr.setColor(Utils.bgColor);
////        gr.fillRect(0, 0, this.getWidth(), this.getHeight());
////
////        gr.setColor(Color.black);
////        gr.drawRect(5, 5,  this.getWidth()-10, this.getHeight() - 10);
//    }
    
    
    public List<Sentence> getSentences() {
        return this.parent.getSentences();
    }
}
