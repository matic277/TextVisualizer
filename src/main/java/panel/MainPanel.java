package panel;

import main.Pair;
import main.Sentence;
import main.Utils;
import window.MainWindow;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;

public class MainPanel extends JSplitPane {
    
    MainWindow parent;
    
    // contains vertically split panels:
    TopPanel topPanel;
    BottomPanel bottomPanel;
    
    public MainPanel(MainWindow parent) {
        super(VERTICAL_SPLIT, null, null);
        this.parent = parent;
        
        this.setOpaque(true);
//        this.setSize(size);
//        this.setPreferredSize(size);
        this.setVisible(true);
        
        TopBox topBox = new TopBox(null);
        topPanel = new TopPanel(this, topBox);
        bottomPanel = new BottomPanel(this);
    
        this.setTopComponent(topPanel);
        this.setBottomComponent(bottomPanel);
    
        this.setDividerLocation(Utils.INITIAL_WINDOW_HEIGHT - Utils.INITIAL_BOTTOM_MENU_HEIGHT);
        this.setResizeWeight(1);
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
    
    
    public Map<Pair<Integer, String>, List<Sentence>> getChapters() {
        return this.parent.getChapters();
    }
}
