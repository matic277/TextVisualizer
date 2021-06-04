package SentenceLabel;

import main.*;
import panel.ChaptersPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class SentenceLabel extends JLabel {
    
    public Sentence sentence;
    public ChaptersPanel parent;
    
    SentenceLabelBuilder labelBuilder;
    final SentenceLabelBuilder truePositionVerticalBuilder = new TruePositionSentenceLblBuilderVertical();
    final SentenceLabelBuilder truePositionHorizontalBuilder = new TruePositionSentenceLblBuilderHorizontal();
    final SentenceLabelBuilder defaultVerticalBuilder = new VerticalSentenceLabelBuilder();
    final SentenceLabelBuilder defaultHorizontalBuilder = new HorizontalSentenceLabelBuilder();
    
    final SentenceRenderer verticalRenderer = new VerticalSentenceLblRenderer();
    final SentenceRenderer horizontalRenderer = new HorizontalSentenceLblRenderer();
    final SentenceRenderer truePositionVerticalRenderer = new TruePositionSentenceLblVerticalRenderer();
    final SentenceRenderer truePositionHorizontalRenderer = new TruePositionSentenceLblHorizontalRenderer();
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
    private Consumer<Graphics2D> actualBorderDrawer;
    private Consumer<Graphics2D> nullBorderDrawer = (g) -> {};
    public Consumer<Graphics2D> borderDrawer = nullBorderDrawer;
    
    // sentiment
    public static final Color SENTIMENT_HIGH_COLOR = Utils.GREEN;
    public static final Color SENTIMENT_MED_COLOR = Utils.GRAY2;
    public static final Color SENTIMENT_LOW_COLOR = Utils.RED;
    public static final Color SENTIMENT_POSITIVE_COLOR_HOVERED = SENTIMENT_HIGH_COLOR.brighter();
    public static final Color SENTIMENT_NEUTRAL_COLOR_HOVERED = SENTIMENT_MED_COLOR.brighter();
    public static final Color SENTIMENT_NEGATIVE_COLOR_HOVERED = SENTIMENT_LOW_COLOR.brighter();
    // imagery
    public static final Color IMAGERY_HIGH_COLOR = new Color(66,133,244);
    public static final Color IMAGERY_MED_COLOR = new Color(137, 177, 238);
    public static final Color IMAGERY_LOW_COLOR = new Color(204, 227, 246);
    public static final Color IMAGERY_HIGH_COLOR_HOVERED = IMAGERY_HIGH_COLOR.brighter();
    public static final Color IMAGERY_MED_COLOR_HOVERED = IMAGERY_MED_COLOR.brighter();
    public static final Color IMAGERY_LOW_COLOR_HOVERED = IMAGERY_LOW_COLOR.brighter();
    // activation
    public static final Color ACTIVATION_HIGH_COLOR = new Color(128, 66,244);
    public static final Color ACTIVATION_MED_COLOR = new Color(172, 134, 243);
    public static final Color ACTIVATION_LOW_COLOR = new Color(212, 193, 246);
    public static final Color ACTIVATION_HIGH_COLOR_HOVERED = ACTIVATION_HIGH_COLOR.brighter();
    public static final Color ACTIVATION_MED_COLOR_HOVERED = ACTIVATION_MED_COLOR.brighter();
    public static final Color ACTIVATION_LOW_COLOR_HOVERED = ACTIVATION_LOW_COLOR.brighter();
    
    // map: visualType  -> coloringFunction
    private static final Map<VisualType, Consumer<SentenceLabel>> colorMap = new HashMap<>();
    static {
        colorMap.put(VisualType.SENTIMENT, getSentimentColorer());
        colorMap.put(VisualType.IMAGERY, getImageryColorer());
        colorMap.put(VisualType.ACTIVATION, getActivationColorer());
    }
    
    public VisualType currentVisualType;
    public ChapterType currentChapterType;
    public SentenceLabelVisualType currentLabelVisualType;
    
    public SentenceLabel(ChaptersPanel parent, Sentence sentence, VisualType visualType, ChapterType chapterType, SentenceLabelVisualType labelVisualType) {
        super();
        this.sentence = sentence;
        this.parent = parent;
        this.currentChapterType = chapterType;
        this.currentVisualType = visualType;
        this.currentLabelVisualType = labelVisualType;
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
        
        colorMap.get(visualType).accept(this);
        addListener();
        
        this.actualBorderDrawer = (g) -> {
            g.setStroke(new BasicStroke(1));
            
            // add border
            g.setColor(BORDER_COLOR);
            g.drawRect(0, 0, getWidth()-1, getHeight()-1);
        };
    }
    
    public void init() {
        labelBuilder.rebuild(this);
    }
    
    public void onVisualTypeChange(VisualType visualType) {
        currentVisualType = visualType;
        colorMap.get(visualType).accept(this);
        this.init();
        this.repaint();
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
    }
    
    private static Consumer<SentenceLabel> getSentimentColorer() {
        return (sentLbl) -> {
            sentLbl.CURRENT_COLOR_HIGH = SENTIMENT_HIGH_COLOR;
            sentLbl.CURRENT_COLOR_MED =  SENTIMENT_MED_COLOR;
            sentLbl.CURRENT_COLOR_LOW =  SENTIMENT_LOW_COLOR;
            sentLbl.NORMAL_COLOR_HIGH =  SENTIMENT_HIGH_COLOR;
            sentLbl.NORMAL_COLOR_MED =   SENTIMENT_MED_COLOR;
            sentLbl.NORMAL_COLOR_LOW =   SENTIMENT_LOW_COLOR;
            sentLbl.HOVERED_COLOR_HIGH = SENTIMENT_POSITIVE_COLOR_HOVERED;
            sentLbl.HOVERED_COLOR_MED =  SENTIMENT_NEUTRAL_COLOR_HOVERED;
            sentLbl.HOVERED_COLOR_LOW =  SENTIMENT_NEGATIVE_COLOR_HOVERED;
        };
    }
    
    private static Consumer<SentenceLabel> getActivationColorer() {
        return (sentLbl) -> {
            sentLbl.CURRENT_COLOR_HIGH = ACTIVATION_HIGH_COLOR;
            sentLbl.CURRENT_COLOR_MED =  ACTIVATION_MED_COLOR;
            sentLbl.CURRENT_COLOR_LOW =  ACTIVATION_LOW_COLOR;
            sentLbl.NORMAL_COLOR_HIGH =  ACTIVATION_HIGH_COLOR;
            sentLbl.NORMAL_COLOR_MED =   ACTIVATION_MED_COLOR;
            sentLbl.NORMAL_COLOR_LOW =   ACTIVATION_LOW_COLOR;
            sentLbl.HOVERED_COLOR_HIGH = ACTIVATION_HIGH_COLOR_HOVERED;
            sentLbl.HOVERED_COLOR_MED =  ACTIVATION_MED_COLOR_HOVERED;
            sentLbl.HOVERED_COLOR_LOW =  ACTIVATION_LOW_COLOR_HOVERED;
        };
    }
    
    private static Consumer<SentenceLabel> getImageryColorer() {
        return (sentLbl) -> {
            sentLbl.CURRENT_COLOR_HIGH = IMAGERY_HIGH_COLOR;
            sentLbl.CURRENT_COLOR_MED =  IMAGERY_MED_COLOR;
            sentLbl.CURRENT_COLOR_LOW =  IMAGERY_LOW_COLOR;
            sentLbl.NORMAL_COLOR_HIGH =  IMAGERY_HIGH_COLOR;
            sentLbl.NORMAL_COLOR_MED =   IMAGERY_MED_COLOR;
            sentLbl.NORMAL_COLOR_LOW =   IMAGERY_LOW_COLOR;
            sentLbl.HOVERED_COLOR_HIGH = IMAGERY_HIGH_COLOR_HOVERED;
            sentLbl.HOVERED_COLOR_MED =  IMAGERY_MED_COLOR_HOVERED;
            sentLbl.HOVERED_COLOR_LOW =  IMAGERY_LOW_COLOR_HOVERED;
        };
    }
    
    @Override
    public void paintComponent(Graphics g) {
        Graphics2D gr = (Graphics2D) g;
        
        renderer.draw(gr, this);
        
//        gr.setFont(Utils.getFont(8));
//        gr.setColor(Color.black);
//        String s = getBounds().toString().toLowerCase().replace("java.awt.rectangle", "");
//        gr.drawString(s, 0, 10);
    }
    
    public void highlight() {
//        isHighlightedBySlider = true;
//        if (isSelected) return;
        CURRENT_COLOR_HIGH = HOVERED_COLOR_HIGH;
        CURRENT_COLOR_MED = HOVERED_COLOR_MED;
        CURRENT_COLOR_LOW = HOVERED_COLOR_LOW;
        CURRENT_UNRECOGNIZED_COLOR = HOVERED_UNRECOGNIZED_COLOR;
        
        // shitty
        if (currentLabelVisualType == SentenceLabelVisualType.TRUE_POSITION) {
            sentence.getWords().forEach(w -> w.setCurrentRenderColor(w.getHoveredRenderColor()));
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
            sentence.getWords().forEach(w -> w.setCurrentRenderColor(w.getNormalRenderColor()));
        }
        
        this.parent.repaint();
    }
    
    public void onUnselect() {
        this.isSelected = false;
        CURRENT_COLOR_HIGH = NORMAL_COLOR_HIGH;
        CURRENT_COLOR_MED = NORMAL_COLOR_MED;
        CURRENT_COLOR_LOW = NORMAL_COLOR_LOW;
        CURRENT_UNRECOGNIZED_COLOR = NORMAL_UNRECOGNIZED_COLOR;
        this.borderDrawer = nullBorderDrawer;
        this.parent.repaint();
    }
    
    public void onSelect() {
        this.isSelected = true;
        CURRENT_COLOR_HIGH = HOVERED_COLOR_HIGH;
        CURRENT_COLOR_MED = HOVERED_COLOR_MED;
        CURRENT_COLOR_LOW = HOVERED_COLOR_LOW;
        
        CURRENT_UNRECOGNIZED_COLOR = HOVERED_UNRECOGNIZED_COLOR;
        this.borderDrawer = actualBorderDrawer;
        this.parent.repaint();
    }
    
    private void addListener() {
        this.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                SentenceLabel clickedSentence = SentenceLabel.this;
                if (isSelected()) {
                    clickedSentence.onUnselect();
                    boolean removed = parent.parent.parent.getBottomPanel().getRightPanel().removeSentence(clickedSentence);
//                    System.out.println("size, removed => " + parent.parent.parent.getBottomPanel().rightPanel.allSelectedSentences.size() + ", " + removed);
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
