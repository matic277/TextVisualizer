package panel;

import main.Pair;
import main.Sentence;
import main.Utils;
import main.VisualType;
import window.MainWindow;

import javax.swing.*;
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
        this.setVisible(true);
        
        topPanel = new TopPanel(this);
        bottomPanel = new BottomPanel(this);
    
        this.setTopComponent(topPanel);
        this.setBottomComponent(bottomPanel);
    
        this.setDividerLocation(Utils.INITIAL_WINDOW_HEIGHT - Utils.INITIAL_BOTTOM_MENU_HEIGHT);
        this.setResizeWeight(1);
    }
    
    public BottomPanel getBottomPanel() { return this.bottomPanel; }
    
    public Map<Pair<Integer, String>, List<Sentence>> getChapters() {
        return this.parent.getChapters();
    }
    
    public void onVisualTypeChange(VisualType selectedItem) {
        topPanel.onVisualTypeChange(selectedItem);
        bottomPanel.onVisualTypeChange(selectedItem);
    }
}
