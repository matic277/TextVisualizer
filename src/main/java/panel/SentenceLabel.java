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
    
    private Color NORMAL_COLOR_HIGH = Utils.GREEN;
    private Color NORMAL_COLOR_MED = Utils.GRAY2;
    private Color NORMAL_COLOR_LOW = Utils.RED;
    
    private Color HOVERED_COLOR_HIGH = NORMAL_COLOR_HIGH.brighter();
    private Color HOVERED_COLOR_MED = NORMAL_COLOR_MED.brighter();
    private Color HOVERED_COLOR_LOW = NORMAL_COLOR_LOW.brighter();
    
    int posHeight, neuHeight, negHeight;
    
    private boolean isSelected = false;
    private static final Color BORDER_COLOR = new Color(0, 0, 0, 100);
    private Consumer<Graphics2D> actualBorderDrawer;
    private Consumer<Graphics2D> nullBorderDrawer = (g) -> {};
    private Consumer<Graphics2D> borderDrawer = nullBorderDrawer;
    
    // sentiment
    public static final Color SENTIMENT_POSITIVE_COLOR = Utils.GREEN;
    public static final Color SENTIMENT_NEUTRAL_COLOR = Utils.GRAY2;
    public static final Color SENTIMENT_NEGATIVE_COLOR = Utils.RED;
    public static Color SENTIMENT_POSITIVE_COLOR_HOVERED = SENTIMENT_POSITIVE_COLOR.brighter();
    public static Color SENTIMENT_NEUTRAL_COLOR_HOVERED = SENTIMENT_NEUTRAL_COLOR.brighter();
    public static Color SENTIMENT_NEGATIVE_COLOR_HOVERED = SENTIMENT_NEGATIVE_COLOR.brighter();
    // imagery
    public static final Color IMAGERY_HIGH_COLOR = new Color(66,133,244);
    public static final Color IMAGERY_NEUTRAL_COLOR = new Color(120, 170, 238);
    public static final Color IMAGERY_LOW_COLOR = new Color(196, 219, 246);
    public static Color IMAGERY_HIGH_COLOR_HOVERED = IMAGERY_HIGH_COLOR.brighter();
    public static Color IMAGERY_MED_COLOR_HOVERED = IMAGERY_NEUTRAL_COLOR.brighter();
    public static Color IMAGERY_LOW_COLOR_HOVERED = IMAGERY_LOW_COLOR.brighter();
    // activation
    public static final Color ACTIVATION_HIGH_COLOR = new Color(128, 66,244);
    public static final Color ACTIVATION_NEUTRAL_COLOR = new Color(166, 124,245);
    public static final Color ACTIVATION_LOW_COLOR = new Color(212, 193, 246);
    public static Color ACTIVATION_HIGH_COLOR_HOVERED = ACTIVATION_HIGH_COLOR.brighter();
    public static Color ACTIVATION_MED_COLOR_HOVERED = ACTIVATION_NEUTRAL_COLOR.brighter();
    public static Color ACTIVATION_LOW_COLOR_HOVERED = ACTIVATION_LOW_COLOR.brighter();
    
    // map: visualType  -> coloringFunction
    private static final Map<VisualType, Consumer<SentenceLabel>> colorMap = new HashMap<>();
    static {
        colorMap.put(VisualType.PLEASANTNESS, getSentimentColorer());
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
            g.setColor(HOVERED_COLOR_HIGH);
            g.fillRect(0, 0, this.getWidth(), negHeight);
            g.setColor(HOVERED_COLOR_MED);
            g.fillRect(0, negHeight,this.getWidth(), neuHeight);
            g.setColor(HOVERED_COLOR_LOW);
            g.fillRect(0, negHeight + neuHeight,this.getWidth(), posHeight);
            
            // add border
            g.setColor(BORDER_COLOR);
            g.drawRect(0, 0, getWidth()-1, getHeight()-1);
        };
    }
    
    private static double customLog(double base, double logNumber) {
        return Math.log(logNumber) / Math.log(base);
    }
    
    public void init() {
        double totalWords = sentence.getWords().size();
//        double totalHeight = Utils.SENTENCE_SIZE.getHeight();
        int wordSize = 3;
        double totalHeight = (int)customLog(1.035, totalWords * wordSize);
//        double totalHeight = (int)Math.log1p(totalWords * wordSize);
        
        this.setPreferredSize(new Dimension(this.getPreferredSize().width, (int)(totalHeight)));
        
        double posPerc = sentence.numOfPositiveWords / totalWords;
        double neuPerc = sentence.numOfNeutralWords  / totalWords;
        
        posHeight = (int)Math.ceil(totalHeight * posPerc);
        neuHeight = (int)Math.floor(totalHeight * neuPerc);
        negHeight = (int) totalHeight - posHeight - neuHeight; // whatever is left
        
    }
    
    public void onVisualTypeChange(VisualType visualType) {
        colorMap.get(visualType).accept(this);
        this.repaint();
    }
    
    private static Consumer<SentenceLabel> getSentimentColorer() {
        return (sentLbl) -> {
            sentLbl.CURRENT_COLOR_HIGH =  SENTIMENT_POSITIVE_COLOR;
            sentLbl.CURRENT_COLOR_MED =   SENTIMENT_NEUTRAL_COLOR;
            sentLbl.CURRENT_COLOR_LOW =   SENTIMENT_NEGATIVE_COLOR;
            sentLbl.NORMAL_COLOR_HIGH =   SENTIMENT_POSITIVE_COLOR;
            sentLbl.NORMAL_COLOR_MED =    SENTIMENT_NEUTRAL_COLOR;
            sentLbl.NORMAL_COLOR_LOW =    SENTIMENT_NEGATIVE_COLOR;
            sentLbl.HOVERED_COLOR_HIGH =  SENTIMENT_POSITIVE_COLOR_HOVERED;
            sentLbl.HOVERED_COLOR_MED =   SENTIMENT_NEUTRAL_COLOR_HOVERED;
            sentLbl.HOVERED_COLOR_LOW =   SENTIMENT_NEGATIVE_COLOR_HOVERED;
        };
    }
    
    private static Consumer<SentenceLabel> getActivationColorer() {
        return (sentLbl) -> {
            sentLbl.CURRENT_COLOR_HIGH =  ACTIVATION_HIGH_COLOR;
            sentLbl.CURRENT_COLOR_MED =   ACTIVATION_NEUTRAL_COLOR;
            sentLbl.CURRENT_COLOR_LOW =   ACTIVATION_LOW_COLOR;
            sentLbl.NORMAL_COLOR_HIGH =   ACTIVATION_HIGH_COLOR;
            sentLbl.NORMAL_COLOR_MED =    ACTIVATION_NEUTRAL_COLOR;
            sentLbl.NORMAL_COLOR_LOW =    ACTIVATION_LOW_COLOR;
            sentLbl.HOVERED_COLOR_HIGH =  ACTIVATION_HIGH_COLOR_HOVERED;
            sentLbl.HOVERED_COLOR_MED =   ACTIVATION_MED_COLOR_HOVERED;
            sentLbl.HOVERED_COLOR_LOW =   ACTIVATION_LOW_COLOR_HOVERED;
        };
    }
    
    private static Consumer<SentenceLabel> getImageryColorer() {
        return (sentLbl) -> {
            sentLbl.CURRENT_COLOR_HIGH =  IMAGERY_HIGH_COLOR;
            sentLbl.CURRENT_COLOR_MED =   IMAGERY_NEUTRAL_COLOR;
            sentLbl.CURRENT_COLOR_LOW =   IMAGERY_LOW_COLOR;
            sentLbl.NORMAL_COLOR_HIGH =   IMAGERY_HIGH_COLOR;
            sentLbl.NORMAL_COLOR_MED =    IMAGERY_NEUTRAL_COLOR;
            sentLbl.NORMAL_COLOR_LOW =    IMAGERY_LOW_COLOR;
            sentLbl.HOVERED_COLOR_HIGH =  IMAGERY_HIGH_COLOR_HOVERED;
            sentLbl.HOVERED_COLOR_MED =   IMAGERY_MED_COLOR_HOVERED;
            sentLbl.HOVERED_COLOR_LOW =   IMAGERY_LOW_COLOR_HOVERED;
        };
    }
    
    @Override
    public void paintComponent(Graphics g) {
        Graphics2D gr = (Graphics2D) g;
        
        gr.setColor(CURRENT_COLOR_LOW);
        gr.fillRect(0, 0, this.getWidth(), negHeight);
        
        gr.setColor(CURRENT_COLOR_MED);
        gr.fillRect(0, negHeight,this.getWidth(), neuHeight);
        
        gr.setColor(CURRENT_COLOR_HIGH);
        gr.fillRect(0, negHeight + neuHeight,this.getWidth(), posHeight);
        
        borderDrawer.accept(gr);
    }
    
    public void highlight() {
        CURRENT_COLOR_HIGH = HOVERED_COLOR_HIGH;
        CURRENT_COLOR_MED = HOVERED_COLOR_MED;
        CURRENT_COLOR_LOW = HOVERED_COLOR_LOW;
        this.parent.repaint();
    }
    
    public void unhighlight() {
        isHighlightedBySlider = false;
        CURRENT_COLOR_HIGH = NORMAL_COLOR_HIGH;
        CURRENT_COLOR_MED = NORMAL_COLOR_MED;
        CURRENT_COLOR_LOW = NORMAL_COLOR_LOW;
        this.parent.repaint();
    }
    
    public void onUnselect() {
        this.isSelected = false;
        this.borderDrawer = nullBorderDrawer;
        this.parent.repaint();
    }
    
    public void onSelect() {
        this.isSelected = true;
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
