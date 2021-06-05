package panel.tabs;

import panel.ChaptersPanel;
import panel.TopPanel;

import javax.swing.*;

public class TabsPanel extends JTabbedPane {
    
    TopPanel parent;
    ChaptersPanel chaptersPanel;
    
    QueryTab queryTab;
    ColorTab colorTab;
    DictionaryTab dictionaryTab;
    
    public TabsPanel(TopPanel parent) {
        this.parent = parent;
        this.chaptersPanel = parent.getChaptersPanel();
        
        queryTab = new QueryTab(this);
        colorTab = new ColorTab(this);
        dictionaryTab = new DictionaryTab(this);
        
        this.addTab("Query", queryTab);
        this.addTab("Colors", colorTab);
        this.addTab("Dictionary", dictionaryTab);
    }
}
