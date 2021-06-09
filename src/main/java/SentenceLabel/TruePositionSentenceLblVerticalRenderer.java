package SentenceLabel;

import main.VisualType;

import java.awt.*;

public class TruePositionSentenceLblVerticalRenderer implements SentenceRenderer {
    
    @Override
    public void draw(Graphics2D gr, SentenceLabel lbl) {
        // it's expected that words are ordered
        lbl.getSentence().getSentenceLabelWords().forEach(w -> {
            gr.setColor(w.getCurrentRenderColor());
            gr.fillRect(0, w.getPosition(), lbl.getWidth(), w.getSize());
        });
        
        lbl.searchWordBorderDrawer.accept(gr, lbl);
        lbl.selectionBorderDrawer.accept(gr, lbl);
    }
}
