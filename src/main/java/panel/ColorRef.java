package panel;

import javax.swing.*;
import java.awt.*;

// This class serves as a communication point between
// a word row panel and colorchooserpanel
public class ColorRef {
    public Color circleColor;
    public JPanel panelToUpdate;
    public JPanel parent; // highlighted
    
    public ColorRef(JPanel parent, Color initialClr) {
        this.circleColor = initialClr;
        this.parent = parent;
    }
    
    public void setPanelToRepaint(JPanel selectedColorPanel) {
        this.panelToUpdate = selectedColorPanel;
    }
}
