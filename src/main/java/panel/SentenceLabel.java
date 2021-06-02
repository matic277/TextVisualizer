package panel;

import main.Sentence;
import main.Utils;
import main.VisualType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class SentenceLabel extends JLabel {
    
    
    Sentence sentence;
    ChaptersPanel parent;
    
    public boolean isHighlightedBySlider = false;
    
    
    private Color CURRENT_COLOR_HIGH = Utils.GREEN;
    private Color CURRENT_COLOR_MED = Utils.GRAY2;
    private Color CURRENT_COLOR_LOW = Utils.RED;
    private Color CURRENT_UNRECOGNIZED_COLOR = new Color(150, 150, 150);
    
    private Color NORMAL_COLOR_HIGH = Utils.GREEN;
    private Color NORMAL_COLOR_MED = Utils.GRAY2;
    private Color NORMAL_COLOR_LOW = Utils.RED;
    private static Color NORMAL_UNRECOGNIZED_COLOR = new Color(150, 150, 150);
    
    private Color HOVERED_COLOR_HIGH = NORMAL_COLOR_HIGH.brighter();
    private Color HOVERED_COLOR_MED = NORMAL_COLOR_MED.brighter();
    private Color HOVERED_COLOR_LOW = NORMAL_COLOR_LOW.brighter();
    private static Color HOVERED_UNRECOGNIZED_COLOR = NORMAL_UNRECOGNIZED_COLOR.brighter();
    
    int highHeight, medHeight, lowHeight, unknHeight;
    
    private boolean isSelected = false;
    private static final Color BORDER_COLOR = new Color(0, 0, 0, 100);
    private Consumer<Graphics2D> actualBorderDrawer;
    private Consumer<Graphics2D> nullBorderDrawer = (g) -> {};
    private Consumer<Graphics2D> borderDrawer = nullBorderDrawer;
    
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
    
    public SentenceLabel(ChaptersPanel parent, Sentence sentence, VisualType visualType) {
        super();
        this.sentence = sentence;
        this.parent = parent;
        
        this.setOpaque(true);
        this.setPreferredSize(Utils.SENTENCE_SIZE);
        
        colorMap.get(visualType).accept(this);
        addListener();
        
        actualBorderDrawer = (g) -> {
            g.setStroke(new BasicStroke(1));
            
            // draw it as highlighted
//            g.setColor(new Color(255, 255, 255, 100));
//            g.fillRect(0, 0, getWidth(), getHeight());
            
            // add border
            g.setColor(BORDER_COLOR);
            g.drawRect(0, 0, getWidth()-1, getHeight()-1);
        };
    }
    
    private static double customLog(double base, double logNumber) {
        return Math.log(logNumber) / Math.log(base);
    }
    
    public void init(VisualType visualType) {
        int wordSize = 3;
        double totalWords = sentence.getWords().size();
        double totalHeight = (int)customLog(1.035, totalWords * wordSize);
        
        this.setPreferredSize(new Dimension(this.getPreferredSize().width, (int)(totalHeight)));
        
        double highPerc;
        double medPerc;
        double lowPerc;
        double unknowPerc;
        
        if (visualType == VisualType.SENTIMENT) {
            highPerc = sentence.numOfPositiveWords / totalWords;
            medPerc = sentence.numOfNeutralWords / totalWords;
            lowPerc = sentence.numOfNegativeWords / totalWords;
        } else if (visualType == VisualType.ACTIVATION) {
            highPerc = sentence.numOfHighActivationWords / totalWords;
            medPerc = sentence.numOfMediumActivationWords / totalWords;
            lowPerc = sentence.numOfLowActivationWords / totalWords;
        } else {
            highPerc = sentence.numOfHighImageryWords / totalWords;
            medPerc = sentence.numOfMediumImageryWords / totalWords;
            lowPerc = sentence.numOfLowImageryWords / totalWords;
        }
        
//        unknowPerc = sentence.numOfUnrecognizedWords / totalWords;
        
        highHeight = (int)Math.ceil(totalHeight * highPerc);
        medHeight = (int)Math.floor(totalHeight * medPerc);
        lowHeight = (int)Math.floor(totalHeight * lowPerc);
        unknHeight = (int)(totalHeight - highHeight - medHeight - lowHeight);// whatever is left
        
    }
    
    public void onVisualTypeChange(VisualType visualType) {
        colorMap.get(visualType).accept(this);
        this.init(visualType);
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
        
        gr.setColor(CURRENT_COLOR_HIGH);
        gr.fillRect(0, 0, this.getWidth(), highHeight);
        
        gr.setColor(CURRENT_COLOR_MED);
        gr.fillRect(0, highHeight,this.getWidth(), medHeight);
        
        gr.setColor(CURRENT_COLOR_LOW);
        gr.fillRect(0, highHeight + medHeight,this.getWidth(), lowHeight);
        
        gr.setColor(CURRENT_UNRECOGNIZED_COLOR);
        gr.fillRect(0, lowHeight + medHeight + highHeight,this.getWidth(), unknHeight);
        
        borderDrawer.accept(gr);
    }
    
    public void highlight() {
//        isHighlightedBySlider = true;
//        if (isSelected) return;
        CURRENT_COLOR_HIGH = HOVERED_COLOR_HIGH;
        CURRENT_COLOR_MED = HOVERED_COLOR_MED;
        CURRENT_COLOR_LOW = HOVERED_COLOR_LOW;
        CURRENT_UNRECOGNIZED_COLOR = HOVERED_UNRECOGNIZED_COLOR;
        this.parent.repaint();
    }
    
    public void unhighlight() {
        isHighlightedBySlider = false;
        if (isSelected) return;
        CURRENT_COLOR_HIGH = NORMAL_COLOR_HIGH;
        CURRENT_COLOR_MED = NORMAL_COLOR_MED;
        CURRENT_COLOR_LOW = NORMAL_COLOR_LOW;
        CURRENT_UNRECOGNIZED_COLOR = NORMAL_UNRECOGNIZED_COLOR;
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
                    boolean removed = parent.parent.parent.getBottomPanel().rightPanel.removeSentence(clickedSentence);
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
