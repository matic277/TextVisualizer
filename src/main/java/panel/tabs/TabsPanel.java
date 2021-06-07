package panel.tabs;

import panel.ChaptersPanel;
import panel.TopPanel;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;

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
    
        this.addTab("Colors", colorTab);
        this.addTab("Query", queryTab);
        this.addTab("Dictionary", dictionaryTab);
    }
    
    //@Override
    //public void paintComponent(Graphics g) {
    //    super.paintComponent(g);
    //
    //    System.out.println(this.getHeight());
    //
    //    g.setColor(Color.PINK);
    //    g.fillRect(0, 0, getWidth(), getHeight()+20);
    //
    //    g.setColor(Color.lightGray);
    //    g.drawRect(3, 2, getWidth()-3, getHeight());
    //}
}
