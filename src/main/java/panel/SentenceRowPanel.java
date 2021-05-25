package panel;

import javax.swing.*;

public class SentenceRowPanel extends JPanel {
    
    JPanel parent;
    SentenceLabel sentenceLabel;
    
    public SentenceRowPanel(JPanel parent, SentenceLabel sentenceLabel) {
        this.parent = parent;
        this.sentenceLabel = sentenceLabel;
    }
    
    public boolean representsSentenceLabel(SentenceLabel sentence) {
        return sentenceLabel == sentence;
    }
}
