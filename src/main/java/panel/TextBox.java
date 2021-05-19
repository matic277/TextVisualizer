package panel;

import main.Sentence;
import main.Utils;
import word.AbsMeasurableWord;
import word.AbsWord;

import javax.swing.*;
import javax.swing.border.StrokeBorder;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class TextBox extends JPanel {
    
    List<WordLabel> words = new LinkedList<>();
    LeftPanel parent;
    
    JPanel sentencesPanel;
    
    public TextBox() {
        this.setLayout(new WrapLayout());
        this.setBackground(Utils.GRAY);
        
        this.setLayout(new BorderLayout());
        
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BorderLayout());
        JLabel title = new JLabel(" Selected sentences ");
        title.setPreferredSize(new Dimension(300, 30));
        title.setOpaque(true);
        title.setBackground(Utils.GRAY);
        title.setFont(Utils.getFont(14));
        titlePanel.add(title, BorderLayout.CENTER);
        this.add(titlePanel, BorderLayout.NORTH);
        
        sentencesPanel = new JPanel();
        sentencesPanel.setBackground(Color.white);
        sentencesPanel.setLayout(new BoxLayout(sentencesPanel, BoxLayout.Y_AXIS));
        this.add(sentencesPanel, BorderLayout.CENTER);
        
        this.setMinimumSize(new Dimension(0, 0));
    }
    
    public void onSentenceClick(Sentence clickedSentence) {
        sentencesPanel.removeAll();
        
        JPanel sentencePanel = new JPanel();
        sentencePanel.setLayout(new WrapLayout());
        sentencePanel.setAlignmentX(LEFT_ALIGNMENT);
        sentencePanel.setBackground(Color.white);
        
        for (AbsWord word : clickedSentence.getWords()) {
            WordLabel lbl = new WordLabel(this.parent, word);
            sentencePanel.add(lbl);
        }
        
        sentencesPanel.add(sentencePanel);
        
        // need to call this otherwise this components doesn't get updated
        // immediately, but only after resize happens
        this.parent.updateUI();
    }
    
    public void onSentenceHover(List<SentenceLabel> hoveredSentences) {
        sentencesPanel.removeAll();
        
        for (SentenceLabel slbl : hoveredSentences) {
            JPanel mainPanel = new JPanel();
            mainPanel.setName("mainPanel");
            mainPanel.setLayout(new BorderLayout());
//            sentenceMainPanel.setAlignmentX(LEFT_ALIGNMENT);
            mainPanel.setOpaque(true);
//            mainPanel.setBackground(Color.red);
//            mainPanel.setBackground(Utils.getRandomColor());
            
            JLabel sentNumLbl = new JLabel(""+slbl.getSentence().sentenceNumber, SwingConstants.CENTER);
            sentNumLbl.setFont(Utils.getFont(12));
//            sentNumLbl.setHorizontalTextPosition(SwingConstants.RIGHT);
//            sentNumLbl.setVerticalTextPosition(SwingConstants.RIGHT);
            sentNumLbl.setBorder(new StrokeBorder(new BasicStroke(1)));
            sentNumLbl.setOpaque(true);
            sentNumLbl.setBackground(Utils.GRAY);
            sentNumLbl.setMinimumSize(new Dimension(30, 10));
            sentNumLbl.setPreferredSize(new Dimension(30, 10));
            mainPanel.add(sentNumLbl, BorderLayout.WEST);
            
            JPanel sentencePanel = new JPanel();
//            sentencePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
            sentencePanel.setLayout(new WrapLayout(WrapLayout.LEFT));
            sentencePanel.setBackground(Color.white);
            sentencePanel.setOpaque(false);
            sentencePanel.addComponentListener(new ComponentListener() {
                @Override public void componentResized(ComponentEvent e) {
//                    sentencePanel.revalidate();
                    sentencePanel.doLayout();
                    }
                @Override public void componentMoved(ComponentEvent e) { }
                @Override public void componentShown(ComponentEvent e) { }
                @Override public void componentHidden(ComponentEvent e) { }
            });
    
//            sentencePanel.setBackground(Utils.getRandomColor());
    
            for (AbsWord word : slbl.getSentence().getWords()) {
                WordLabel lbl = new WordLabel(this.parent, word);
                sentencePanel.add(lbl);
            }
            mainPanel.add(sentencePanel, BorderLayout.CENTER);
            
//            sentencesPanel.setAlignmentX(LEFT_ALIGNMENT);
//            mainPanel.updateUI();
//            mainPanel.setMaximumSize(new Dimension(1000, mainPanel.getPreferredSize().height));
//            mainPanel.setMaximumSize(mainPanel.getPreferredSize());
//            mainPanel.doLayout();
            
            sentencesPanel.add(mainPanel);
            sentencePanel.revalidate();
            sentencePanel.doLayout();
            sentencePanel.updateUI();
        }
        
        // need to call this otherwise this components doesn't get updated
        // immediately, but only after resize happens
        this.parent.updateUI();
    }
    
    
    public void setParent(LeftPanel leftPanel) {
        this.parent = leftPanel;
        System.out.println(this.getParent().getName());
    }
    

}