package panel;

import main.Pair;
import main.Sentence;
import main.Utils;
import word.AbsWord;

import javax.swing.*;
import javax.swing.border.StrokeBorder;
import java.awt.*;
import java.util.List;
import java.util.Map;

public class LeftPanel extends JPanel {
    
    BottomPanel parent;
    
//    JPanel titlePanel;
    JPanel sentencesPanel;
    
    Map<Pair<Integer, String>, List<Sentence>> chapters;
    
    public LeftPanel(BottomPanel parent) {
        super();
        this.parent = parent;
        this.chapters = parent.parent.getChapters();
        
        this.setLayout(new BorderLayout());
        
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BorderLayout());
//        titlePanel.setBackground(Color.yellow);
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
            WordLabel lbl = new WordLabel(this, word);
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
            mainPanel.setLayout(new BorderLayout());
//            sentenceMainPanel.setAlignmentX(LEFT_ALIGNMENT);
            mainPanel.setBackground(Color.white);
            mainPanel.setBackground(Utils.getRandomColor());
            
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
            sentencePanel.setLayout(new WrapLayout());
            sentencePanel.setAlignmentX(LEFT_ALIGNMENT);
            sentencePanel.setBackground(Color.white);
//            sentencePanel.setBackground(Utils.getRandomColor());
            
            for (AbsWord word : slbl.getSentence().getWords()) {
                WordLabel lbl = new WordLabel(this, word);
                sentencePanel.add(lbl);
            }
            mainPanel.add(sentencePanel, BorderLayout.CENTER);
            
//            sentencesPanel.setAlignmentX(LEFT_ALIGNMENT);
            
            sentencesPanel.add(mainPanel);
        }
        
        // need to call this otherwise this components doesn't get updated
        // immediately, but only after resize happens
        this.parent.updateUI();
    }
}
