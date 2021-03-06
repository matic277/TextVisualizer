package SentenceLabel;

import main.*;
import main.UserDictionary.UserDictionary;
import panel.ChaptersPanel;
import panel.tabs.QueryTab;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class SentenceLabel extends JLabel {
    
    public Sentence sentence;
    public ChaptersPanel parent;

    private static final SentenceLabelBuilder truePositionVerticalBuilder = new TruePositionSentenceLblBuilderVertical();
    private static final SentenceLabelBuilder truePositionHorizontalBuilder = new TruePositionSentenceLblBuilderHorizontal();
    private static final SentenceLabelBuilder defaultVerticalBuilder = new VerticalSentenceLabelBuilder();
    private static final SentenceLabelBuilder defaultHorizontalBuilder = new HorizontalSentenceLabelBuilder();
    SentenceLabelBuilder labelBuilder;
    
    private static final SentenceRenderer verticalRenderer = new VerticalSentenceLblRenderer();
    private static final SentenceRenderer horizontalRenderer = new HorizontalSentenceLblRenderer();
    private static final SentenceRenderer truePositionVerticalRenderer = new TruePositionSentenceLblVerticalRenderer();
    private static final SentenceRenderer truePositionHorizontalRenderer = new TruePositionSentenceLblHorizontalRenderer();
    SentenceRenderer renderer; // default at start
    
    public boolean isHighlightedBySlider = false;
    
    public Color CURRENT_COLOR_HIGH = Utils.GREEN;
    public Color CURRENT_COLOR_MED = Utils.GRAY2;
    public Color CURRENT_COLOR_LOW = Utils.RED;
    public Color CURRENT_UNRECOGNIZED_COLOR = new Color(150, 150, 150);
    
    private Color NORMAL_COLOR_HIGH = Utils.GREEN;
    private Color NORMAL_COLOR_MED = Utils.GRAY2;
    private Color NORMAL_COLOR_LOW = Utils.RED;
    public  static Color NORMAL_UNRECOGNIZED_COLOR = new Color(150, 150, 150);
    
    private Color HOVERED_COLOR_HIGH = NORMAL_COLOR_HIGH.brighter();
    private Color HOVERED_COLOR_MED = NORMAL_COLOR_MED.brighter();
    private Color HOVERED_COLOR_LOW = NORMAL_COLOR_LOW.brighter();
    private static Color HOVERED_UNRECOGNIZED_COLOR = NORMAL_UNRECOGNIZED_COLOR.brighter();
    
    public int highWidth, medWidth, lowWidth, unknWidth;
    public int highHeight, medHeight, lowHeight, unknHeight;
    
    private boolean isSelected = false;
    private static final Color BORDER_COLOR = new Color(0, 0, 0, 100);
    
    // borders on found and selected
    private final BiConsumer<Graphics2D, SentenceLabel> nullBorderDrawer = (g, lbl) -> {};
    private final BiConsumer<Graphics2D, SentenceLabel> selectedSentenceBorderDrawer;
    private final BiConsumer<Graphics2D, SentenceLabel> searchWordFoundBorderDrawer;
    public BiConsumer<Graphics2D, SentenceLabel> selectionBorderDrawer = nullBorderDrawer;
    public BiConsumer<Graphics2D, SentenceLabel> searchWordBorderDrawer = nullBorderDrawer;
    
    public VisualType currentVisualType;
    public ChapterType currentChapterType;
    public SentenceLabelVisualType currentLabelVisualType;
    public SentenceSizeType currentSentenceSizeType;
    
    public static Dimension charSize = new Dimension(Utils.SENTENCE_SIZE.width, Utils.SENTENCE_SIZE.height);
    
    public SentenceLabel(ChaptersPanel parent, Sentence sentence, VisualType visualType, ChapterType chapterType, SentenceLabelVisualType labelVisualType, SentenceSizeType sentenceSizeType) {
        super();
        this.sentence = sentence;
        this.parent = parent;
        this.currentChapterType = chapterType;
        this.currentVisualType = visualType;
        this.currentLabelVisualType = labelVisualType;
        this.currentSentenceSizeType = sentenceSizeType;
        this.setOpaque(true);
        
        
        // set up renderers and label builders
        if (chapterType == ChapterType.HORIZONTAL) {
            if (labelVisualType == SentenceLabelVisualType.DEFAULT) {
                this.labelBuilder = defaultVerticalBuilder;
                this.renderer = verticalRenderer;
            }
            if (labelVisualType == SentenceLabelVisualType.TRUE_POSITION) {
                this.labelBuilder = truePositionVerticalBuilder;
                this.renderer = truePositionVerticalRenderer;
            }
            
        }
        else if (chapterType == ChapterType.VERTICAL) {
            if (labelVisualType == SentenceLabelVisualType.DEFAULT) {
                this.labelBuilder = defaultHorizontalBuilder;
                this.renderer = horizontalRenderer;
            }
            if (labelVisualType == SentenceLabelVisualType.TRUE_POSITION) {
                this.labelBuilder = truePositionHorizontalBuilder;
                this.renderer = truePositionHorizontalRenderer;
            }
        }
        
        addListener();
        
        // on selected border drawer
        this.selectedSentenceBorderDrawer = (g, slbl) -> {
            g.setStroke(new BasicStroke(1));
            
            // add border
            g.setColor(BORDER_COLOR);
            g.drawRect(0, 0, slbl.getWidth()-1, slbl.getHeight()-1);
        };
        
        // on word found by querytab border drawer
        this.searchWordFoundBorderDrawer = (g, slbl) -> {
            g.setColor(new Color(255, 255, 0, 100));
            g.fillRect(0, 0, slbl.getWidth(), slbl.getHeight());
        };
    }
    
    public void init() {
        labelBuilder.rebuild(this);
    }
    
    
    public void onSentenceLblVisualTypeChange(SentenceLabelVisualType sentenceVisualType) {
        currentLabelVisualType = sentenceVisualType;
        if (currentChapterType == ChapterType.VERTICAL) {
            if (sentenceVisualType == SentenceLabelVisualType.DEFAULT) {
                labelBuilder = defaultHorizontalBuilder;
                renderer = horizontalRenderer;
            }
            if (sentenceVisualType == SentenceLabelVisualType.TRUE_POSITION) {
                labelBuilder = truePositionHorizontalBuilder;
                renderer = truePositionHorizontalRenderer;
            }
        } else if (currentChapterType == ChapterType.HORIZONTAL) {
            if (sentenceVisualType == SentenceLabelVisualType.DEFAULT) {
                labelBuilder = defaultVerticalBuilder;
                renderer = verticalRenderer;
            }
            if (sentenceVisualType == SentenceLabelVisualType.TRUE_POSITION) {
                labelBuilder = truePositionVerticalBuilder;
                renderer = truePositionVerticalRenderer;
            }
        }
        this.init();
        this.repaint();
        this.parent.repaint();
    }
    
    
    public void onNewDictionaryImport(UserDictionary dict) {
        // switch colors
        this.sentence.getSentenceLabelWords().forEach(w -> {
            //Color newColor = dict.getColorForWord(w.getProcessedText()); // TODO source text or processed text or both?
            //newColor = newColor == null ? NORMAL_UNRECOGNIZED_COLOR : newColor;
            
            //System.out.println("new color " + w + " -> " + Utils.colorToString(newColor));
            //System.out.println("new color: " + newColor);
            //w.setNormalRenderColor(newColor);
            //w.setCurrentRenderColor(newColor);
            //w.setHoveredRenderColor(newColor.brighter());
            w.updateColors(w.getWord().getColor());
        });
    }
    
    public void onSizeChange() {
        labelBuilder.rebuild(this);
    }
    
    @Override
    public void paintComponent(Graphics g) {
        Graphics2D gr = (Graphics2D) g;
        
        renderer.draw(gr, this);
        //System.out.println("redrawn");
        // debug
        // gr.setFont(Utils.getFont(8));
        // gr.setColor(Color.black);
        // String s = getBounds().toString().toLowerCase().replace("java.awt.rectangle", "");
        // gr.drawString(s, 0, 10);
    }
    
    public void highlight() {
        //isHighlightedBySlider = true;
        //if (isSelected) return;
        CURRENT_COLOR_HIGH = HOVERED_COLOR_HIGH;
        CURRENT_COLOR_MED = HOVERED_COLOR_MED;
        CURRENT_COLOR_LOW = HOVERED_COLOR_LOW;
        CURRENT_UNRECOGNIZED_COLOR = HOVERED_UNRECOGNIZED_COLOR;
        
        // shitty
        if (currentLabelVisualType == SentenceLabelVisualType.TRUE_POSITION) {
            sentence.getSentenceLabelWords().forEach(w -> w.setCurrentRenderColor(w.getHoveredRenderColor()));
        }
        
        this.parent.repaint();
    }
    
    public void unhighlight() {
        isHighlightedBySlider = false;
        if (isSelected) return;
        
        CURRENT_COLOR_HIGH = NORMAL_COLOR_HIGH;
        CURRENT_COLOR_MED = NORMAL_COLOR_MED;
        CURRENT_COLOR_LOW = NORMAL_COLOR_LOW;
        CURRENT_UNRECOGNIZED_COLOR = NORMAL_UNRECOGNIZED_COLOR;
        
        // shitty
        if (currentLabelVisualType == SentenceLabelVisualType.TRUE_POSITION) {
            sentence.getSentenceLabelWords().forEach(w -> w.setCurrentRenderColor(w.getNormalRenderColor()));
        }
        
        this.parent.repaint();
    }
    
    public void onUnselect() {
        this.isSelected = false;
        if (isHighlightedBySlider) {
            this.selectionBorderDrawer = nullBorderDrawer;
            this.repaint();
            return;
        }
        CURRENT_COLOR_HIGH = NORMAL_COLOR_HIGH;
        CURRENT_COLOR_MED = NORMAL_COLOR_MED;
        CURRENT_COLOR_LOW = NORMAL_COLOR_LOW;
        CURRENT_UNRECOGNIZED_COLOR = NORMAL_UNRECOGNIZED_COLOR;
        this.selectionBorderDrawer = nullBorderDrawer;
        
        // shitty
        if (currentLabelVisualType == SentenceLabelVisualType.TRUE_POSITION) {
            sentence.getSentenceLabelWords().forEach(w -> w.setCurrentRenderColor(w.getNormalRenderColor()));
        }
        
        this.repaint();
       // this.parent.repaint();
    }
    
    public void onSelect() {
        this.isSelected = true;
        CURRENT_COLOR_HIGH = HOVERED_COLOR_HIGH;
        CURRENT_COLOR_MED = HOVERED_COLOR_MED;
        CURRENT_COLOR_LOW = HOVERED_COLOR_LOW;
    
        // shitty
        if (currentLabelVisualType == SentenceLabelVisualType.TRUE_POSITION) {
            sentence.getSentenceLabelWords().forEach(w -> w.setCurrentRenderColor(w.getHoveredRenderColor()));
        }
        
        CURRENT_UNRECOGNIZED_COLOR = HOVERED_UNRECOGNIZED_COLOR;
        this.selectionBorderDrawer = selectedSentenceBorderDrawer;
        this.repaint();
        //this.parent.repaint();
    }
    
    public void onWordSearch(String word, QueryTab caller) {
        long occurrences = this.sentence.countOccurrences(word);
        if (occurrences > 0) {
            this.searchWordBorderDrawer = searchWordFoundBorderDrawer;
            caller.incrementWordInSentenceFoundOccurrence();
            caller.incrementWordFoundOccurrence(occurrences);
            this.repaint();
        } else {
            this.searchWordBorderDrawer = nullBorderDrawer;
            this.repaint();
        }
    }
    
    private void addListener() {
        this.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                SentenceLabel clickedSentence = SentenceLabel.this;
                if (isSelected()) {
                    clickedSentence.onUnselect();
                    boolean removed = parent.parent.parent.getBottomPanel().getRightPanel().removeSentence(clickedSentence);
                    //System.out.println("size, removed => " + parent.parent.parent.getBottomPanel().rightPanel.allSelectedSentences.size() + ", " + removed);
                    assert removed;
                }
                else {
                    clickedSentence.onSelect();
                    SentenceLabel.this.parent.parent.parent.getBottomPanel().onSentenceClick(clickedSentence);
                }
            }
            @Override public void mouseEntered(MouseEvent e) {
                highlight();
            }
            @Override public void mouseExited(MouseEvent e) {
                if (!isHighlightedBySlider) unhighlight();
            }
            @Override public void mousePressed(MouseEvent e) { }
            @Override public void mouseReleased(MouseEvent e) { }
        });
    }
    
    public Sentence getSentence() {
        return this.sentence;
    }
    
    public boolean isSelected() { return this.isSelected; }
}
