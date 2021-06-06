package panel.tabs;

import panel.tabs.TabsPanel;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class ColorTab extends JPanel {
    
    TabsPanel parent;
    
    public ColorTab(TabsPanel parent) {
        this.parent = parent;
        
        this.add(new JLabel("Unsupported"));
    
        Border b = BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0,1,1,0, new Color(0,0,0,0)),
                BorderFactory.createMatteBorder(0,1,1,1, Color.lightGray));
    }
}
