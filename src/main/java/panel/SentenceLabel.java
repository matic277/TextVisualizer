package panel;

import main.Sentence;
import main.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.function.Consumer;

public class SentenceLabel extends JLabel {
    
    Sentence sentence;
    ChaptersPanel parent;
    
    public boolean isHighlightedBySlider = false;
    
    private Color COLOR_POSITVE = Utils.GREEN;
    private Color COLOR_NEUTRAL = Utils.GRAY2;
    private Color COLOR_NEGATIVE = Utils.RED;
    
    private static Color NORMAL_COLOR_POSTIVE = Utils.GREEN;
    private static Color HOVERED_COLOR_POSITIVE = Utils.GREEN.brighter();
    
    private static Color NORMAL_COLOR_NEUTRAL = Utils.GRAY2;
    private static Color HOVERED_COLOR_NEUTRAL = Utils.GRAY2.brighter();
    
    private static Color NORMAL_COLOR_NEGATIVE = Utils.RED;
    private static Color HOVERED_COLOR_NEGATIVE = Utils.RED.brighter();
    
    int posHeight, neuHeight, negHeight;
    
    private static int wordSize = 3;
    
    private boolean isSelected = false;
    private static final Color BORDER_COLOR = new Color(0, 0, 0, 100);
    private Consumer<Graphics2D> actualBorderDrawer;
    private Consumer<Graphics2D> nullBorderDrawer = (g) -> {};
    private Consumer<Graphics2D> borderDrawer = nullBorderDrawer;
    
    public SentenceLabel(ChaptersPanel parent, Sentence sentence) {
        super();
        this.sentence = sentence;
        this.parent = parent;
    
        this.setOpaque(true);
        this.setPreferredSize(Utils.SENTENCE_SIZE);
        
        addListener();
    
        actualBorderDrawer = (g) -> {
            g.setStroke(new BasicStroke(1));
            
            // draw it as highlighted
            g.setColor(HOVERED_COLOR_NEGATIVE);
            g.fillRect(0, 0, this.getWidth(), negHeight);
            g.setColor(HOVERED_COLOR_NEUTRAL);
            g.fillRect(0, negHeight,this.getWidth(), neuHeight);
            g.setColor(HOVERED_COLOR_POSITIVE);
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
        double totalHeight = (int)customLog(1.05, totalWords * wordSize);
//        double totalHeight = (int)Math.log1p(totalWords * wordSize);
        
        this.setPreferredSize(new Dimension(this.getPreferredSize().width, (int)(totalHeight)));
    
        double posPerc = sentence.numOfPositiveWords / totalWords;
        double neuPerc = sentence.numOfNeutralWords  / totalWords;
    
        posHeight = (int)Math.ceil(totalHeight * posPerc);
        neuHeight = (int)Math.floor(totalHeight * neuPerc);
        negHeight = (int) totalHeight - posHeight - neuHeight; // whatever is left
        
    }
    
    @Override
    public void paintComponent(Graphics g) {
        Graphics2D gr = (Graphics2D) g;

        gr.setColor(COLOR_NEGATIVE);
        gr.fillRect(0, 0, this.getWidth(), negHeight);
        
        gr.setColor(COLOR_NEUTRAL);
        gr.fillRect(0, negHeight,this.getWidth(), neuHeight);
        
        gr.setColor(COLOR_POSITVE);
        gr.fillRect(0, negHeight + neuHeight,this.getWidth(), posHeight);
        
        borderDrawer.accept(gr);
    }
    
    public void highlight() {
        COLOR_POSITVE  = HOVERED_COLOR_POSITIVE;
        COLOR_NEUTRAL  = HOVERED_COLOR_NEUTRAL;
        COLOR_NEGATIVE = HOVERED_COLOR_NEGATIVE;
        this.parent.repaint();
    }
    
    public void unhighlight() {
        isHighlightedBySlider = false;
        COLOR_POSITVE  = NORMAL_COLOR_POSTIVE;
        COLOR_NEUTRAL  = NORMAL_COLOR_NEUTRAL;
        COLOR_NEGATIVE = NORMAL_COLOR_NEGATIVE;
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
