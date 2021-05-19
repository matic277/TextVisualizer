package panel;

import main.Sentence;
import main.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class SentenceLabel extends JLabel {
    
    Sentence sentence;
    TopPanel parent;
    
    int posHeight, neuHeight, negHeight;
    
    public SentenceLabel(TopPanel parent, Sentence sentence) {
        super();
        this.sentence = sentence;
        this.parent = parent;
        
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
        
        g.setColor(Utils.RED);
        g.fillRect(0, 0, this.getWidth(), negHeight);
        
        g.setColor(Utils.GRAY);
        g.fillRect(0, negHeight,this.getWidth(), neuHeight);
        
        g.setColor(Utils.GREEN);
        g.fillRect(0, negHeight + neuHeight,this.getWidth(), posHeight);
    }
    
    private void addListener() {
        this.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Sentence clickedSentence = SentenceLabel.this.sentence;
                System.out.println(clickedSentence.getSentenceString());
                SentenceLabel.this.parent.parent.bottomPanel.onSentenceClick(clickedSentence);
            }
            @Override public void mousePressed(MouseEvent e) { }
            @Override public void mouseReleased(MouseEvent e) { }
            @Override public void mouseEntered(MouseEvent e) { }
            @Override public void mouseExited(MouseEvent e) { }
        });
    }
    
    public Sentence getSentence() {
        return this.sentence;
    }
}
