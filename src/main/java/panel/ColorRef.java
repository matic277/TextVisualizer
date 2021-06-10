package panel;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

// This class serves as a communication point between
// a word row panel and colorchooserpanel
public class ColorRef {
    
    public Color circleColor;
    public JPanel panelToUpdate;
    public JPanel parent; // highlighted
    
    // either a specific word (DictionaryWordRowPanel)
    // or an entire group (DictionaryGroupRowPanel)
    Consumer<Color> newAppliedColorConsumer;
    
    public ColorRef(JPanel parent, Color initialClr) {
        this.circleColor = initialClr;
        this.parent = parent;
    }
    
    public void setPanelToRepaint(JPanel selectedColorPanel) { this.panelToUpdate = selectedColorPanel; }
    
    public void setNewColorConsumer(Consumer<Color> consumer) { this.newAppliedColorConsumer = consumer; }
    
    public void onApplyButtonPress(Color newClr) {
        this.newAppliedColorConsumer.accept(newClr);
    }
}
