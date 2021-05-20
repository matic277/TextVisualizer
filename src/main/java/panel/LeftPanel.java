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

public class LeftPanel extends JScrollPane {
    
    BottomPanel parent;
    
    TextBox textBox;
    
    Map<Pair<Integer, String>, List<Sentence>> chapters;
    
    public LeftPanel(BottomPanel parent, TextBox content) {
        super(content);
        this.textBox = content;
        this.parent = parent;
        this.chapters = parent.parent.getChapters();
        
        content.setParent(this);
        
        this.getVerticalScrollBar().setUnitIncrement(16);
//        this.getHorizontalScrollBar().setUnitIncrement(16);
        this.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        this.setHorizontalScrollBar(null);
    }
    
    public void onSentenceClick(Sentence clickedSentence) {
        this.textBox.onSentenceClick(clickedSentence);
    }
    
    public void onSentenceHover(List<SentenceLabel> hoveredSentences) {
        this.textBox.onSentenceHover(hoveredSentences);
    }

//    public void onSentenceClick(Sentence clickedSentence) {
//        sentencesPanel.removeAll();
//
//        JPanel sentencePanel = new JPanel();
//        sentencePanel.setLayout(new WrapLayout());
//        sentencePanel.setAlignmentX(LEFT_ALIGNMENT);
//        sentencePanel.setBackground(Color.white);
//
//        for (AbsWord word : clickedSentence.getWords()) {
//            WordLabel lbl = new WordLabel(this, word);
//            sentencePanel.add(lbl);
//        }
//
//        sentencesPanel.add(sentencePanel);
//
//        // need to call this otherwise this components doesn't get updated
//        // immediately, but only after resize happens
//        this.parent.updateUI();
//    }
//
//    public void onSentenceHover(List<SentenceLabel> hoveredSentences) {
//        sentencesPanel.removeAll();
//
//        for (SentenceLabel slbl : hoveredSentences) {
//            JPanel mainPanel = new JPanel();
//            mainPanel.setLayout(new BorderLayout());
////            sentenceMainPanel.setAlignmentX(LEFT_ALIGNMENT);
//            mainPanel.setBackground(Color.white);
//            mainPanel.setBackground(Utils.getRandomColor());
//
//            JLabel sentNumLbl = new JLabel(""+slbl.getSentence().sentenceNumber, SwingConstants.CENTER);
//            sentNumLbl.setFont(Utils.getFont(12));
////            sentNumLbl.setHorizontalTextPosition(SwingConstants.RIGHT);
////            sentNumLbl.setVerticalTextPosition(SwingConstants.RIGHT);
//            sentNumLbl.setBorder(new StrokeBorder(new BasicStroke(1)));
//            sentNumLbl.setOpaque(true);
//            sentNumLbl.setBackground(Utils.GRAY);
//            sentNumLbl.setMinimumSize(new Dimension(30, 10));
//            sentNumLbl.setPreferredSize(new Dimension(30, 10));
//            mainPanel.add(sentNumLbl, BorderLayout.WEST);
//
//            JPanel sentencePanel = new JPanel();
//            sentencePanel.setLayout(new WrapLayout());
//            sentencePanel.setAlignmentX(LEFT_ALIGNMENT);
//            sentencePanel.setBackground(Color.white);
////            sentencePanel.setBackground(Utils.getRandomColor());
//
//            for (AbsWord word : slbl.getSentence().getWords()) {
//                WordLabel lbl = new WordLabel(this, word);
//                sentencePanel.add(lbl);
//            }
//            mainPanel.add(sentencePanel, BorderLayout.CENTER);
//
////            sentencesPanel.setAlignmentX(LEFT_ALIGNMENT);
//
//            sentencesPanel.add(mainPanel);
//        }
//
//        // need to call this otherwise this components doesn't get updated
//        // immediately, but only after resize happens
//        this.parent.updateUI();
//    }
}
