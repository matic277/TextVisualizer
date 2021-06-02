package panel;

import main.Pair;
import main.Sentence;
import main.Utils;
import main.VisualType;

import javax.swing.*;
import java.util.List;
import java.util.Map;

public class BottomPanel extends JSplitPane {
    
    MainPanel parent;
    
    RightPanel rightPanel;
    LeftPanel leftPanel;
    
    public BottomPanel(MainPanel parent) {
        super(HORIZONTAL_SPLIT, null, null);
        this.parent = parent;
    
        rightPanel = new RightPanel(this);
        leftPanel = new LeftPanel(this);
        
        this.setLeftComponent(leftPanel);
        this.setRightComponent(rightPanel);
        
        this.setDividerLocation(Utils.INITIAL_LEFT_MENU_WIDTH);
    }
    
    public void onSentenceClick(SentenceLabel clickedSentence) {
        leftPanel.onSentenceClick(clickedSentence);
        rightPanel.onSentenceClick(clickedSentence);
    }
    
    public void onSentenceHover(List<SentenceLabel> hoveredSentences) {
        leftPanel.onSentenceHover(hoveredSentences);
        rightPanel.onSentenceHover(hoveredSentences);
    }
    
    public void onVisualTypeChange(VisualType selectedItem) {
        leftPanel.onVisualTypeChange(selectedItem);
        rightPanel.onVisualTypeChange(selectedItem);
    }
    
    public void onNewTextImport(Map<Pair<Integer, String>, List<Sentence>> processedChapters) {
        rightPanel.onNewTextImport(processedChapters);
        leftPanel.onNewTextImport(processedChapters);
    }
}
