package SentenceLabel;

import java.awt.*;

public class HorizontalSentenceLblRenderer implements SentenceRenderer {
    @Override
    public void draw(Graphics2D gr, SentenceLabel lbl) {
        gr.setColor(lbl.CURRENT_COLOR_HIGH);
        gr.fillRect(0, 0, lbl.highWidth, lbl.getHeight());
        
        gr.setColor(lbl.CURRENT_COLOR_MED);
        gr.fillRect(lbl.highWidth, 0, lbl.medWidth, lbl.getHeight());
        
        gr.setColor(lbl.CURRENT_COLOR_LOW);
        gr.fillRect(lbl.highWidth + lbl.medWidth, 0, lbl.lowWidth, lbl.getHeight());
        
        gr.setColor(lbl.CURRENT_UNRECOGNIZED_COLOR);
        gr.fillRect(lbl.highWidth + lbl.medWidth + lbl.lowWidth, 0, lbl.unknWidth, lbl.getHeight());
        
        lbl.searchWordBorderDrawer.accept(gr, lbl);
        lbl.selectionBorderDrawer.accept(gr, lbl);
    }
}
