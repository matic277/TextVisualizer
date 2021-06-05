package panel;

import javax.swing.*;
import java.awt.*;

public class ColorTab extends JPanel {
    
    TabsPanel parent;
    
    public ColorTab(TabsPanel parent) {
        this.parent = parent;
        
        this.add(new JLabel("Unsupported"));
    }
}
