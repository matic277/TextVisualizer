package panel;

import main.Sentence;
import main.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class SentenceLabel extends JLabel {
    
    Sentence sentence;
    
    int posHeight, neuHeight, negHeight;
    
    public SentenceLabel(Sentence sentence) {
        super();
        this.sentence = sentence;
        
        double totalWords = sentence.getWords().size();
        double totalHeight = Utils.SENTENCE_SIZE.getHeight();
        
        double posPerc = sentence.numOfPositiveWords / totalWords;
        double neuPerc = sentence.numOfNeutralWords  / totalWords;
        
        posHeight = (int)Math.ceil(totalHeight * posPerc);
        neuHeight = (int)Math.floor(totalHeight * neuPerc);
        negHeight = (int) totalHeight - posHeight - neuHeight; // whatever is left
    
        System.out.println("pos="+posHeight+", neu="+neuHeight+", neg="+negHeight);
        
        addListener();
    }
    
    @Override
    public void paintComponent(Graphics g) {
//        super.paintComponent(g);
        
        System.out.println(this.getBounds().getX());
        
        g.setColor(Utils.RED);
        g.fillRect(this.getX(), this.getY(),Utils.SENTENCE_SIZE.width, negHeight);

        g.setColor(Utils.GRAY);
        g.fillRect(this.getX(), this.getY() + negHeight,Utils.SENTENCE_SIZE.width, neuHeight);

        g.setColor(Utils.GREEN);
        g.fillRect(this.getX(), this.getY() + negHeight + neuHeight,Utils.SENTENCE_SIZE.width, posHeight);
    }
    
    private void addListener() {
        this.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println(SentenceLabel.this.sentence.getSentence());
            }
            @Override public void mousePressed(MouseEvent e) { }
            @Override public void mouseReleased(MouseEvent e) { }
            @Override public void mouseEntered(MouseEvent e) { }
            @Override public void mouseExited(MouseEvent e) { }
        });
    }
}
