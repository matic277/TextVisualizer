package SentenceLabel;

import java.awt.*;

public class HorizontalSentenceLblRenderer implements SentenceRenderer {
    @Override
    public void draw(Graphics2D gr, SentenceLabel label) {
        gr.setColor(label.CURRENT_COLOR_HIGH);
        gr.fillRect(0, 0, label.highWidth, label.getHeight());
        
        gr.setColor(label.CURRENT_COLOR_MED);
        gr.fillRect(label.highWidth, 0, label.medWidth, label.getHeight());
        
        gr.setColor(label.CURRENT_COLOR_LOW);
        gr.fillRect(label.highWidth + label.medWidth, 0, label.lowWidth, label.getHeight());
        
        gr.setColor(label.CURRENT_UNRECOGNIZED_COLOR);
        gr.fillRect(label.highWidth + label.medWidth + label.lowWidth, 0, label.unknWidth, label.getHeight());
        
        label.borderDrawer.accept(gr);
    }
}
