package SentenceLabel;

import java.awt.*;

public class VerticalSentenceLblRenderer implements SentenceRenderer {
    
    @Override
    public void draw(Graphics2D gr, SentenceLabel label) {
        gr.setColor(label.CURRENT_COLOR_HIGH);
        gr.fillRect(0, 0, label.getWidth(), label.highHeight);
        
        gr.setColor(label.CURRENT_COLOR_MED);
        gr.fillRect(0, label.highHeight,label.getWidth(), label.medHeight);
        
        gr.setColor(label.CURRENT_COLOR_LOW);
        gr.fillRect(0, label.highHeight + label.medHeight,label.getWidth(), label.lowHeight);
        
        gr.setColor(label.CURRENT_UNRECOGNIZED_COLOR);
        gr.fillRect(0, label.lowHeight + label.medHeight + label.highHeight,label.getWidth(), label.unknHeight);
        
        label.borderDrawer.accept(gr);
    }
}
