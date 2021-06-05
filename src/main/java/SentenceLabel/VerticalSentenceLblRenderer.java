package SentenceLabel;

import java.awt.*;

public class VerticalSentenceLblRenderer implements SentenceRenderer {
    
    @Override
    public void draw(Graphics2D gr, SentenceLabel lbl) {
        gr.setColor(lbl.CURRENT_COLOR_HIGH);
        gr.fillRect(0, 0, lbl.getWidth(), lbl.highHeight);
        
        gr.setColor(lbl.CURRENT_COLOR_MED);
        gr.fillRect(0, lbl.highHeight,lbl.getWidth(), lbl.medHeight);
        
        gr.setColor(lbl.CURRENT_COLOR_LOW);
        gr.fillRect(0, lbl.highHeight + lbl.medHeight,lbl.getWidth(), lbl.lowHeight);
        
        gr.setColor(lbl.CURRENT_UNRECOGNIZED_COLOR);
        gr.fillRect(0, lbl.lowHeight + lbl.medHeight + lbl.highHeight,lbl.getWidth(), lbl.unknHeight);
        
        lbl.searchWordBorderDrawer.accept(gr, lbl);
        lbl.selectionBorderDrawer.accept(gr, lbl);
    }
}
