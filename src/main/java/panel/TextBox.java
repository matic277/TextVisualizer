package panel;

import main.Sentence;
import main.Utils;
import word.AbsMeasurableWord;
import word.AbsWord;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.List;

public class TextBox extends JComponent {
    
    Dimension size;
    List<JLabel> words = new LinkedList<>();
    BottomPanel parent;
    
    final Color UNMEASURABLE_WORD_COLOR = Color.gray;
    final Color POSITIVE_WORD_COLOR = Color.decode("#34A853");
    final Color NEUTRAL_WORD_COLOR = Color.decode("#E0E0E0");
    final Color NEGATIVE_WORD_COLOR = Color.decode("#ea4335");
    
    public TextBox(Dimension size, BottomPanel parent) {
        this.size = size;
        this.parent = parent;
        this.setPreferredSize(size);
        this.setLayout(new FlowLayout());
        this.setBackground(Color.white);
        initWords();
    }
    
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D gr = (Graphics2D) g;
        gr.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        gr.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        
        gr.setColor(Color.white);
        gr.fillRect(0, 0,  this.getWidth(), this.getHeight());
        
        super.paint(g);
        
//        gr.setColor(Color.RED);
//        gr.drawRect(5, 5,  this.getWidth()-10, this.getHeight() - 10);
    }
    
    public void initWords() {
        for (Sentence sentence : parent.sentences) {
            for (AbsWord word : sentence.getWords()) {
                JLabel lbl = new JLabel(" " + word.getSourceText()+" ");
                lbl.setOpaque(true);
                lbl.setBackground(
                        word.hasSentimentValue() ?
                                word.getSentimentValue() < AbsMeasurableWord.NEUTRAL_THRESHOLD ?
                                    NEGATIVE_WORD_COLOR : word.getSentimentValue() > AbsMeasurableWord.POSITIVE_THRESHOLD ?
                                    POSITIVE_WORD_COLOR : NEUTRAL_WORD_COLOR
                                : UNMEASURABLE_WORD_COLOR
                );
                lbl.setFont(Utils.getFont(14));
                lbl.setPreferredSize(new Dimension(lbl.getPreferredSize().width, lbl.getPreferredSize().height+5));
                this.words.add(lbl);
                this.add(lbl);
            }
        }
    }
    
    public void generateRandomWords() {
        for (int i=0; i<150; i++) {
            JLabel lbl = new JLabel(" " + Utils.randomString(3 + Utils.RAND.nextInt(6))+" ");
            lbl.setOpaque(true);
            lbl.setBackground(new Color(Utils.RAND.nextInt(255), Utils.RAND.nextInt(255), Utils.RAND.nextInt(255)));
            lbl.setFont(Utils.getFont(14));
            lbl.setPreferredSize(new Dimension(lbl.getPreferredSize().width, lbl.getPreferredSize().height+5));
            words.add(lbl);
            this.add(lbl);
        }
        System.out.println(words.get(0).getPreferredSize());
    }
}