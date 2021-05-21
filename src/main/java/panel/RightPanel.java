package panel;

import main.Pair;
import main.Sentence;
import main.Utils;
import word.AbsWord;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class RightPanel extends JScrollPane {
    
    BottomPanel parent;
    
    JPanel mainPanel;
    JPanel sentencePanel;
    JPanel wordStatsPanel;
    JPanel wordStatsContentPanel;
    
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
        
        
        
        // SOUTH
        JLabel title2 = new JLabel(" Word and sentence statistics");
        title2.setPreferredSize(new Dimension(300, 27));
        title2.setOpaque(true);
        title2.setBackground(Utils.GRAY);
        title2.setFont(Utils.getFont(14));
        title2.setPreferredSize(new Dimension(title2.getPreferredSize().width, title2.getPreferredSize().height+5));
    
        JPanel titlePanel2 = new JPanel();
        titlePanel2.setLayout(new BorderLayout());
        titlePanel2.add(title2, BorderLayout.CENTER);
        
        wordStatsPanel = new JPanel();
        wordStatsPanel.setLayout(new BorderLayout());
        wordStatsPanel.add(titlePanel2, BorderLayout.NORTH);
        
        wordStatsContentPanel = new JPanel();
        wordStatsContentPanel.setBackground(Utils.GRAY3);
        wordStatsContentPanel.add(new JLabel("CONTENT"));
        wordStatsPanel.add(wordStatsContentPanel, BorderLayout.CENTER);
        
        mainPanel.add(titlePanel, BorderLayout.NORTH);
        mainPanel.add(wordStatsPanel, BorderLayout.SOUTH);
        
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
                
                sentencePanel.setPreferredSize(new Dimension(self.getSize().width, sentencePanel.getLayout().preferredLayoutSize(sentencePanel).height));
                System.out.println("Pref.size=> " + sentencePanel.getSize());
                sentencePanel.setMaximumSize(sentencePanel.getPreferredSize());
                sentencePanel.setMinimumSize(sentencePanel.getPreferredSize());
                sentencePanel.revalidate();
                sentencePanel.doLayout();
                
//                var x = new Object() {int x = 0;};
//                System.out.println(self.mainPanel.getComponents().length);
//                Arrays.stream(self.mainPanel.getComponents()).forEach(c -> {
//                    if (x.x <= 0)
//                    System.out.println("width=>" + c.getWidth()+" : " +((JPanel)c).getLayout().preferredLayoutSize((JPanel)c).width);

//                    Component c = ((JPanel)c1).getComponents()[1];

//                    System.out.println(c.getName());
//                    System.out.println(c.getPreferredSize().height);
//                    System.out.println(((JPanel)c).getLayout().preferredLayoutSize((JPanel)c).height + " : " + ((JPanel)c).getLayout().minimumLayoutSize((JPanel)c).height);

//                    ((JPanel)c).getLayout().preferredLayoutSize((JPanel)c).height;
//                    ((JPanel)c).getLayout().minimumLayoutSize((JPanel)c).height;
                
                    // Crucial!
                    //                     parentWidth - scrollBarWidth(arrox)  ,    preferredHeight of layout
//                    c.setPreferredSize(new Dimension(self.getSize().width-25, ((JPanel)c).getLayout().preferredLayoutSize((JPanel)c).height));
//                    c.setMaximumSize(c.getPreferredSize());
//                    c.setMinimumSize(c.getPreferredSize());
//                    c.revalidate();
//                    c.doLayout();

//                    x.x++;
//                });
            }
            @Override public void componentMoved(ComponentEvent e) { }
            @Override public void componentShown(ComponentEvent e) { }
            @Override public void componentHidden(ComponentEvent e) { }
        });
    }
    
    
    public void onSentenceHover(List<SentenceLabel> hoveredSentences) {
    
    }

    public void onSentenceClick(Sentence clickedSentence) {
//        sentencesPanel.removeAll();
        if (sentencePanel != null) mainPanel.remove(sentencePanel);
        
        // SELECTED SENTENCE
        this.sentencePanel = new JPanel();
        sentencePanel.setLayout(new WrapLayout(WrapLayout.LEFT));
        sentencePanel.setBackground(Utils.RED);

        for (AbsWord word : clickedSentence.getWords()) {
            WordLabel lbl = new WordLabel(this, sentencePanel, word);
            sentencePanel.add(lbl);
            // remove selectable background (because its also implemented in WordLbl)
            // (because listener overlapping problem)
            sentencePanel.removeMouseListener(lbl.containerParentlistener);
        }
        mainPanel.add(sentencePanel, BorderLayout.CENTER);
        
        // SENTENCE STATS
        if (this.wordStatsContentPanel != null) wordStatsPanel.remove(wordStatsContentPanel);
    
        wordStatsContentPanel = new JPanel();
        wordStatsContentPanel.setBackground(Utils.GRAY3);
        JLabel stats = new JLabel("<html>"+clickedSentence.toString()
                .replaceAll("<", "&lt;")
                .replaceAll(">", "&gt;")
                .replaceAll("\\n", "<br>") +
                "</html>");
        stats.setOpaque(false);
        System.out.println(stats.getText());
        wordStatsContentPanel.add(stats);
        wordStatsPanel.add(wordStatsContentPanel, BorderLayout.CENTER);
        
        
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
