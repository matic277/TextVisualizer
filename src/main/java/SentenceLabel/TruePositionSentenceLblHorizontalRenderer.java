package SentenceLabel;

import panel.VerticalFlowLayout2;
import word.AbsWord;

import javax.swing.*;
import java.awt.*;

public class TruePositionSentenceLblHorizontalRenderer implements SentenceRenderer {
    
    @Override
    public void draw(Graphics2D gr, SentenceLabel lbl) {
        // it's expected that words are ordered
        lbl.getSentence().getWords().forEach(w -> {
            gr.setColor(w.getCurrentRenderColor());
            gr.fillRect(w.getPosition(), 0, w.getSize(), lbl.getHeight());
        });
    
        lbl.borderDrawer.accept(gr);
    }
}
