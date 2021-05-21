package panel;

import main.Sentence;
import main.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

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
    
    public SentenceLabel(ChaptersPanel parent, Sentence sentence) {
        super();
        this.sentence = sentence;
        this.parent = parent;
    
        this.setOpaque(true);
        this.setPreferredSize(Utils.SENTENCE_SIZE);
        
//        this.setBackground(new Color(150, 0, 0, 0));
        
        addListener();
    }
    
    public void init() {
        double totalWords = sentence.getWords().size();
        double totalHeight = Utils.SENTENCE_SIZE.getHeight();
        
        double posPerc = sentence.numOfPositiveWords / totalWords;
        double neuPerc = sentence.numOfNeutralWords  / totalWords;
    
        posHeight = (int)Math.ceil(totalHeight * posPerc);
        neuHeight = (int)Math.floor(totalHeight * neuPerc);
        negHeight = (int) totalHeight - posHeight - neuHeight; // whatever is left
    
//        System.out.println("pos="+posHeight+", neu="+neuHeight+", neg="+negHeight);
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        g.setColor(COLOR_NEGATIVE);
        g.fillRect(0, 0, this.getWidth(), negHeight);
        
        g.setColor(COLOR_NEUTRAL);
        g.fillRect(0, negHeight,this.getWidth(), neuHeight);
        
        g.setColor(COLOR_POSITVE);
        g.fillRect(0, negHeight + neuHeight,this.getWidth(), posHeight);
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
    
    private void addListener() {
        this.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Sentence clickedSentence = SentenceLabel.this.sentence;
                System.out.println(clickedSentence.getSentenceString());
                SentenceLabel.this.parent.parent.parent.getBottomPanel().onSentenceClick(clickedSentence);
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
}
