package panel;

import main.Utils;

import javax.swing.*;
import java.awt.*;

public class LeftPanel extends JScrollPane {
    
    BottomPanel parent;
    TextBox textBox;
    
    public LeftPanel(BottomPanel parent, TextBox content) {
        super(content);
        this.parent = parent;
        this.textBox = content;
        this.textBox.setParent(this);
        
        Dimension panelSize = new Dimension(400, parent.getHeight());
//        this.setSize(panelSize);
//        this.setPreferredSize(panelSize);
        
    }
}
