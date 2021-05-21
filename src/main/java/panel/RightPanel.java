package panel;

import main.Pair;
import main.Sentence;
import main.Utils;
import word.AbsWord;

import javax.swing.*;
import javax.swing.border.StrokeBorder;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.List;
import java.util.Map;

public class RightPanel extends JScrollPane {
    
    BottomPanel parent;
    
    JPanel mainPanel;
        // NORTH
        JPanel mainSentencePanel;
                                  // NORTH is title
            JPanel sentencePanel; // CENTER
            JPanel buttonsPanel;  // SOUTH
                JButton clearBtn;
                JButton addAllBtn;
        
        // CENTER
        JPanel wordStatsPanel;
            JPanel statsPanel;
    
    Map<Pair<Integer, String>, List<Sentence>> chapters;
    
    public RightPanel(BottomPanel parent) {
        this.parent = parent;
        this.chapters = parent.parent.getChapters();
        
//        content.setParent(this);
    
        mainPanel = new JPanel();
        mainPanel.setBackground(Utils.GRAY3);
//        mainPanel.setLayout(new WrapLayout(0, 0, WrapLayout.LEFT));
        mainPanel.setLayout(new BorderLayout());
//        mainPanel.setLayout(new GridLayout(15, 2, 0, 0));
    
        
        // NORTH
        JLabel title = new JLabel(" Selected sentence");
        title.setPreferredSize(new Dimension(300, 27));
        title.setOpaque(true);
        title.setBackground(Utils.GRAY);
        title.setFont(Utils.getFont(14));
        title.setPreferredSize(new Dimension(title.getPreferredSize().width, title.getPreferredSize().height+5));
        
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BorderLayout());
        titlePanel.add(title, BorderLayout.CENTER);
        
        mainSentencePanel = new JPanel();
        mainSentencePanel.setLayout(new BorderLayout());
        mainSentencePanel.add(titlePanel, BorderLayout.NORTH);
        
        sentencePanel = new JPanel();
        sentencePanel.add(new JLabel("content"));
        sentencePanel.setLayout(new WrapLayout(WrapLayout.LEFT));
        sentencePanel.setBackground(Utils.GRAY3);
        mainSentencePanel.add(sentencePanel, BorderLayout.CENTER);
        
        buttonsPanel = new JPanel();
        buttonsPanel.setBackground(Utils.GRAY3);
        buttonsPanel.setLayout(new WrapLayout(WrapLayout.RIGHT, 10, 4));
//        buttonsPanel.setBorder(new StrokeBorder(new BasicStroke(1)));
        
        clearBtn = new JButton("Clear");
        addAllBtn = new JButton("Add all");
        buttonsPanel.add(clearBtn);
        buttonsPanel.add(addAllBtn);
        
        mainSentencePanel.add(buttonsPanel, BorderLayout.SOUTH);
        
        mainPanel.add(mainSentencePanel, BorderLayout.NORTH);
        
        
        
        // CENTER
        JPanel titlePanel2 = new JPanel();
        titlePanel2.setLayout(new BorderLayout());
        
        JLabel title2 = new JLabel(" Word and sentence statistics");
        title2.setPreferredSize(new Dimension(300, 27));
        title2.setOpaque(true);
        title2.setBackground(Utils.GRAY);
        title2.setFont(Utils.getFont(14));
        title2.setPreferredSize(new Dimension(title2.getPreferredSize().width, title2.getPreferredSize().height+5));
        titlePanel2.add(title2, BorderLayout.NORTH);
        
        wordStatsPanel = new JPanel();
        wordStatsPanel.setLayout(new BorderLayout());
        wordStatsPanel.add(titlePanel2, BorderLayout.NORTH);
        
        statsPanel = new JPanel();
        statsPanel.setBackground(Utils.GRAY3);
        statsPanel.add(new JLabel("CONTENT"));
        wordStatsPanel.add(statsPanel, BorderLayout.CENTER);
        
        mainPanel.add(wordStatsPanel, BorderLayout.CENTER);
        
        
        
        
        this.setViewportView(mainPanel);
    
        this.getVerticalScrollBar().setUnitIncrement(16);
        this.getHorizontalScrollBar().setUnitIncrement(16);
        this.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        this.setHorizontalScrollBar(null);
    
        this.addComponentListener(new ComponentListener() {
            RightPanel self = RightPanel.this;
            @Override public void componentResized(ComponentEvent e) {
                
                System.out.println(self.getSize());
                
                if (sentencePanel == null) return;
                
                System.out.println("Pref.height=> " + sentencePanel.getLayout().preferredLayoutSize(sentencePanel).height);
                
                sentencePanel.setPreferredSize(new Dimension(self.getSize().width-20, sentencePanel.getLayout().preferredLayoutSize(sentencePanel).height));
//                sentencePanel.setPreferredSize(new Dimension(self.getSize().width, sentencePanel.getLayout().preferredLayoutSize(sentencePanel).height));
                System.out.println("Pref.size=> " + sentencePanel.getSize());
                sentencePanel.setMaximumSize(sentencePanel.getPreferredSize());
                sentencePanel.setMinimumSize(sentencePanel.getPreferredSize());
                sentencePanel.revalidate();
                sentencePanel.doLayout();
                mainSentencePanel.revalidate();
                mainSentencePanel.doLayout();
            }
            @Override public void componentMoved(ComponentEvent e) { }
            @Override public void componentShown(ComponentEvent e) { }
            @Override public void componentHidden(ComponentEvent e) { }
        });
    }
    
    
    public void onSentenceHover(List<SentenceLabel> hoveredSentences) {
    
    }

    public void onSentenceClick(Sentence clickedSentence) {
        sentencePanel.removeAll();
//        if (sentencePanel != null) mainPanel.remove(sentencePanel);
        
        
        
        // SELECTED SENTENCE
//        this.sentencePanel = new JPanel();
//        sentencePanel.setLayout(new WrapLayout(WrapLayout.LEFT));
//        sentencePanel.setBackground(Utils.GRAY3);

        for (AbsWord word : clickedSentence.getWords()) {
            WordLabel lbl = new WordLabel(this, sentencePanel, word);
            sentencePanel.add(lbl);
            // remove selectable background (because its also implemented in WordLbl)
            // (because listener overlapping problem)
            sentencePanel.removeMouseListener(lbl.containerParentlistener);
        }
//        mainPanel.add(sentencePanel, BorderLayout.CENTER);
        sentencePanel.setPreferredSize(new Dimension(this.getSize().width-20, sentencePanel.getLayout().preferredLayoutSize(sentencePanel).height));
        System.out.println("Pref.size=> " + sentencePanel.getSize());
        sentencePanel.setMaximumSize(sentencePanel.getPreferredSize());
        sentencePanel.setMinimumSize(sentencePanel.getPreferredSize());
        sentencePanel.revalidate();
        sentencePanel.doLayout();
        
        
        
        // SENTENCE STATS
        if (this.statsPanel != null) wordStatsPanel.remove(statsPanel);
    
        statsPanel = new JPanel();
        statsPanel.setBackground(Utils.GRAY3);
        JLabel stats = new JLabel("<html>"+clickedSentence.toString()
                .replaceAll("<", "&lt;")
                .replaceAll(">", "&gt;")
                .replaceAll("\\n", "<br>") +
                "</html>");
        stats.setOpaque(false);
        System.out.println(stats.getText());
        statsPanel.add(stats);
        wordStatsPanel.add(statsPanel, BorderLayout.CENTER);
        
        
        // need to call this otherwise this components doesn't get updated
        // immediately, but only after resize happens
        this.parent.updateUI();
        
    }
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
