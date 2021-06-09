package panel;

import SentenceLabel.SentenceLabel;
import main.ChapterType;
import main.Utils;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class SlidingWindow extends Rectangle {
    
    ChaptersPanel parent;
    
    final Color COLOR = Color.black;
    final Stroke STROKE = new BasicStroke(3f);
    
    ChapterType currentChapterType = ChapterType.HORIZONTAL;
    Renderer renderer;
    
    public SlidingWindow(ChaptersPanel parent) {
        this.parent = parent;
        
        this.renderer = VERTICAL_RENDERER;
        this.setBounds(1, 1, Utils.INITIAL_SLIDER_WIDTH, 90);
    }
    
    private final Renderer HORIZONTAL_RENDERER = (gr, slider) -> {
        Renderer.drawBaseRectangle(gr, slider);
        Renderer.enableAntiAliasing(gr);
        // left
        gr.fillRoundRect(x-2, y+1, 11, height-3, 5, 5);
        // right
        gr.fillRoundRect(x+width, y+1,  5, height-3, 5, 5);
    };
    private final Renderer VERTICAL_RENDERER = (gr, slider) -> {
        Renderer.drawBaseRectangle(gr, slider);
        Renderer.enableAntiAliasing(gr);
        // top
        gr.fillRoundRect(x, y, width+1, 11, 5, 5);
        // bottom
        gr.fillRoundRect(x, y+height-5,  width+1, 5, 5, 5);
    };
    private interface Renderer {
        void draw(Graphics2D g, SlidingWindow slider);
        static void drawBaseRectangle(Graphics2D gr, SlidingWindow slider) {
            gr.setColor(slider.COLOR);
            // lines, draw before antialiasing!
            gr.setStroke(slider.STROKE);
            gr.drawRect(slider.x+1, slider.y+2, slider.width-2, slider.height-6);
        }
        static void enableAntiAliasing(Graphics2D gr) {
            gr.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            gr.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        }
    }
    
    public void paint(Graphics g) {
        Graphics2D gr = (Graphics2D) g;
        gr.setColor(COLOR);
        
        renderer.draw(gr, this);
    }
    
    public void onNewChapterTypeChange(ChapterType chapterType) {
        currentChapterType = chapterType;
        
        if (chapterType == ChapterType.HORIZONTAL) renderer = VERTICAL_RENDERER;
        else if (chapterType == ChapterType.VERTICAL) renderer = HORIZONTAL_RENDERER;
        
        this.setLocation(1, 1);
        this.parent.repaint();
    }
    
    // Also highlights/unhighlights hovered sentences
    public List<SentenceLabel> getHoveredSentences(JPanel mainSentencePanel) {
        JPanel sentencesPanel = (JPanel) mainSentencePanel.getComponents()[1];
        List<SentenceLabel> hovered = new ArrayList<>(20);
        Component[] sentenceCmps = sentencesPanel.getComponents();
        
        if (currentChapterType == ChapterType.HORIZONTAL) {
            for (int i = 0; i < sentenceCmps.length; i++) {
                SentenceLabel slbl = (SentenceLabel) sentenceCmps[i];
                // true X position of sentenceLabel => mainSentencePanel.x + sentenceLabel.x
                if (this.getBounds().contains(sentenceCmps[i].getLocation().x + mainSentencePanel.getX(), mainSentencePanel.getY())) {
                    slbl.isHighlightedBySlider = true;
                    slbl.highlight();
                    hovered.add(slbl);
                } else {
                    slbl.unhighlight();
                }
            }
        }
        else if (currentChapterType == ChapterType.VERTICAL) {
            for (int i = 0; i < sentenceCmps.length; i++) {
                SentenceLabel slbl = (SentenceLabel) sentenceCmps[i];
                // +25 offset probably because of mainSentencePanel header width (title of chapterPanel)
                if (this.getBounds().contains(mainSentencePanel.getX(), sentenceCmps[i].getLocation().y + mainSentencePanel.getY()+25)) {
                    slbl.isHighlightedBySlider = true;
                    slbl.highlight();
                    hovered.add(slbl);
                } else {
                    slbl.unhighlight();
                }
            }
        }
        
        return hovered;
    }
}
