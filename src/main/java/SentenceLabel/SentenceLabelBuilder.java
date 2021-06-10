package SentenceLabel;

import javax.swing.*;
import java.awt.*;

public interface SentenceLabelBuilder {
    
    void rebuild(SentenceLabel label);
    
    LayoutManager getParentLayout(JPanel parent);
}
