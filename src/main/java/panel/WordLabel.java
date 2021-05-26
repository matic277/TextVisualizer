package panel;

import main.Sentence;
import main.Utils;
import main.VisualType;
import word.AbsMeasurableWord;
import word.AbsWord;
import word.StopWord;

import javax.swing.*;
import javax.swing.border.StrokeBorder;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class WordLabel extends JLabel {
    
    Sentence parentSentence;
    AbsWord word;
    JComponent parent;
    JPanel containerParent;
    
    public Color CURRENT_COLOR = new Color(0, 0, 0);
    public Color HOVERED_COLOR = new Color(0, 0, 0);
    
    // sentiment
    public static final Color SENTIMENT_POSITIVE_COLOR = Utils.GREEN;
    public static final Color SENTIMENT_NEUTRAL_COLOR = Utils.GRAY2;
    public static final Color SENTIMENT_NEGATIVE_COLOR = Utils.RED;
    // imagery
    public static final Color IMAGERY_HIGH_COLOR = new Color(66,133,244);
    public static final Color IMAGERY_NEUTRAL_COLOR = new Color(137, 177, 238);
    public static final Color IMAGERY_LOW_COLOR = new Color(204, 227, 246);
    // activation
    public static final Color ACTIVATION_HIGH_COLOR = new Color(128, 66,244);
    public static final Color ACTIVATION_NEUTRAL_COLOR = new Color(172, 134, 243);
    public static final Color ACTIVATION_LOW_COLOR = new Color(212, 193, 246);
    
    // map: visualType  -> coloringFunction
    private static final Map<VisualType, Consumer<WordLabel>> colorMap = new HashMap<>();
    static {
        colorMap.put(VisualType.PLEASANTNESS, getSentimentColorer());
        colorMap.put(VisualType.IMAGERY, getImageryColorer());
        colorMap.put(VisualType.ACTIVATION, getActivationColorer());
    }
    
    public WordLabel (JComponent parent, JPanel containerParent, AbsWord word, VisualType visualType) {
        super(" " + word.getSourceText() + " ");
        this.parent = parent;
        this.containerParent = containerParent;
        this.word = word;
        
        this.setOpaque(true);
        this.setFont(Utils.getFont(14));
        
        colorMap.get(visualType).accept(this); // current coloring mode
        
        this.setBackground(CURRENT_COLOR);
    }
    
    private static Consumer<WordLabel> getSentimentColorer() {
        return (wrdLbl) -> {
            if (wrdLbl.word instanceof AbsMeasurableWord measWrd) {
                wrdLbl.CURRENT_COLOR = measWrd.getSentimentValue() < AbsMeasurableWord.NEUTRAL_THRESHOLD ?
                        SENTIMENT_NEGATIVE_COLOR : measWrd.getSentimentValue() > AbsMeasurableWord.POSITIVE_THRESHOLD ? SENTIMENT_POSITIVE_COLOR : SENTIMENT_NEUTRAL_COLOR;
                wrdLbl.HOVERED_COLOR = wrdLbl.CURRENT_COLOR.brighter();
                wrdLbl.setBackground(wrdLbl.CURRENT_COLOR);
                wrdLbl.setBorder(BorderFactory.createMatteBorder(2,2,2,2,
                        wrdLbl.getBackground().darker())
                );
            }
            else {
                // transparent with border
                wrdLbl.setBorder(new StrokeBorder(new BasicStroke(1)));
        
                if (wrdLbl.word instanceof StopWord) {
                    wrdLbl.setForeground(Utils.GRAY.darker().darker());
                }
    
                wrdLbl.CURRENT_COLOR = new Color(240, 240, 240);
                wrdLbl.HOVERED_COLOR = Color.white;
            }
        };
    }
    
    private static Consumer<WordLabel> getImageryColorer() {
        return (wrdLbl) -> {
            System.out.println("IMAGERY COLORER: " + wrdLbl.word);
            if (wrdLbl.word instanceof AbsMeasurableWord absWord) {
                wrdLbl.CURRENT_COLOR = absWord.isHighImagery() ?
                        IMAGERY_HIGH_COLOR : absWord.isMediumImagery() ?
                        IMAGERY_NEUTRAL_COLOR : IMAGERY_LOW_COLOR;
                wrdLbl.HOVERED_COLOR = wrdLbl.CURRENT_COLOR.brighter();
                wrdLbl.setBackground(wrdLbl.CURRENT_COLOR);
                wrdLbl.setBorder(BorderFactory.createMatteBorder(2,2,2,2,
                        wrdLbl.getBackground().darker())
                );
            }
            else {
                // transparent with border
                wrdLbl.setBorder(new StrokeBorder(new BasicStroke(1)));

                if (wrdLbl.word instanceof StopWord) {
                    wrdLbl.setForeground(Utils.GRAY.darker().darker());
                }
    
                wrdLbl.CURRENT_COLOR = new Color(240, 240, 240);
                wrdLbl.HOVERED_COLOR = Color.white;
            }
        };
    }
    
    private static Consumer<WordLabel> getActivationColorer() {
        return (wrdLbl) -> {
            if (wrdLbl.word instanceof AbsMeasurableWord absWord) {
                wrdLbl.CURRENT_COLOR = absWord.isHighActivation() ?
                        ACTIVATION_HIGH_COLOR : absWord.isMediumActivation() ?
                        ACTIVATION_NEUTRAL_COLOR : ACTIVATION_LOW_COLOR;
                wrdLbl.HOVERED_COLOR = wrdLbl.CURRENT_COLOR.brighter();
                wrdLbl.setBackground(wrdLbl.CURRENT_COLOR);
                wrdLbl.setBorder(BorderFactory.createMatteBorder(2,2,2,2,
                        wrdLbl.getBackground().darker())
                );
            }
            else {
                // transparent with border
                wrdLbl.setBorder(new StrokeBorder(new BasicStroke(1)));

                if (wrdLbl.word instanceof StopWord) {
                    wrdLbl.setForeground(Utils.GRAY.darker().darker());
                }
                
                wrdLbl.CURRENT_COLOR = new Color(240, 240, 240);
                wrdLbl.HOVERED_COLOR = Color.white;
            }
        };
    }
    
    public void onVisualTypeChange(VisualType type) {
        colorMap.get(type).accept(this);
        this.setBackground(CURRENT_COLOR);
    }
    
    public void setParentSentence(Sentence parent) {
        this.parentSentence = parent;
    }
}
