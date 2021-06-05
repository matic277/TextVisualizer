package panel;

import javax.swing.*;

public class TabsPanel extends JTabbedPane {
    
    TopPanel parent;
    ChaptersPanel chaptersPanel;
    
    QueryTab queryTab;
    ColorTab colorTab;
    
    public TabsPanel(TopPanel parent) {
        this.parent = parent;
        this.chaptersPanel = parent.chaptersPanel;
        
        queryTab = new QueryTab(this);
        colorTab = new ColorTab(this);
        
        this.addTab("Query", queryTab);
        this.addTab("Colors", colorTab);
        //this.setBackgroundAt(0, Tools.GRAY3);
    }
}
