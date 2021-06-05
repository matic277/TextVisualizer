package panel.tabs;

import panel.tabs.TabsPanel;

import javax.swing.*;

public class ColorTab extends JPanel {
    
    TabsPanel parent;
    
    public ColorTab(TabsPanel parent) {
        this.parent = parent;
        
        this.add(new JLabel("Unsupported"));
    }
}
