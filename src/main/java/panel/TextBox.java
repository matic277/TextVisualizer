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
    
    List<WordLabel> words = new LinkedList<>();
    LeftPanel parent;
    
    JPanel mainPanel;
    JPanel sentencesPanel;
    
    public TextBox() {
        this.setLayout(new WrapLayout());
        this.setBackground(Color.pink);
        
        this.setLayout(new BorderLayout());
        
        
        mainPanel = new JPanel();
        mainPanel.setOpaque(true);
        mainPanel.setBackground(Color.blue);
        mainPanel.setLayout(new BorderLayout());
        
        sentencesPanel = new JPanel();
        sentencesPanel.setBackground(Color.red);
        sentencesPanel.setLayout(new BoxLayout(sentencesPanel, BoxLayout.Y_AXIS));
        mainPanel.add(sentencesPanel, BorderLayout.CENTER);
        
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BorderLayout());
//        titlePanel.setBackground(Color.yellow);
        JLabel title = new JLabel(" Selected sentences ");
        title.setPreferredSize(new Dimension(300, 30));
        title.setOpaque(true);
        title.setBackground(Color.pink);
        title.setFont(Utils.getFont(14));
        titlePanel.add(title, BorderLayout.CENTER);
        mainPanel.add(titlePanel, BorderLayout.NORTH);
        
        this.add(mainPanel, BorderLayout.CENTER);
    }
    
//    public void paint(Graphics g) {
//        super.paint(g);
//        Graphics2D gr = (Graphics2D) g;
//        gr.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//        gr.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
//
//        gr.setColor(Color.white);
//        gr.fillRect(0, 0,  this.getWidth(), this.getHeight());
//
//        super.paint(g);
//
////        gr.setColor(Color.RED);
////        gr.drawRect(5, 5,  this.getWidth()-10, this.getHeight() - 10);
//    }
    
    public void setParent(LeftPanel leftPanel) {
        this.parent = leftPanel;
        this.setSize(new Dimension(300, 100));
//        this.setPreferredSize(new Dimension(300, 100));
//        this.setMaximumSize(new Dimension(300, 100));
        this.updateUI();
    }
    
    public void onSentenceClick(Sentence clickedSentence) {
    
        JPanel sentencePanel = new JPanel();
        sentencePanel.setLayout(new WrapLayout());
        sentencePanel.setAlignmentX(LEFT_ALIGNMENT);
//        for (int i=0; i<clickedSentence.getWords().size()-1; i++) {
//            WordLabel lbl = new WordLabel(this, clickedSentence.getWords().get());
//            words.add(lbl);
//        }
        
        for (AbsWord word : clickedSentence.getWords()) {
            WordLabel lbl = new WordLabel(this, word);
            words.add(lbl);
            sentencePanel.add(lbl);
        }
    
        sentencesPanel.removeAll();
        sentencesPanel.add(sentencePanel);
        
        // need to call this otherwise this components doesn't get updated
        // immediately, but only after resize happens
        this.parent.updateUI();
    }
}