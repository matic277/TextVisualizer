package panel;

import main.Pair;
import main.Sentence;
import main.Utils;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;

public class RightPanel extends JPanel {
    
    BottomPanel parent;
    
    JPanel mainPanel;
    JPanel sentencetoStr;
    
    Map<Pair<Integer, String>, List<Sentence>> chapters;
    
    public RightPanel(BottomPanel parent) {
        this.parent = parent;
        this.chapters = parent.parent.getChapters();
        this.setBackground(Color.CYAN);
    
        this.setLayout(new BorderLayout());
    
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BorderLayout());
        JLabel title = new JLabel(" Word and sentence statistics");
        title.setPreferredSize(new Dimension(300, 27));
        title.setOpaque(true);
        title.setBackground(Utils.GRAY);
        title.setFont(Utils.getFont(14));
        title.setPreferredSize(new Dimension(title.getPreferredSize().width, title.getPreferredSize().height+5));
        titlePanel.add(title, BorderLayout.CENTER);
        
        mainPanel = new JPanel();
        mainPanel.setBackground(Color.blue);
        mainPanel.setLayout(new BorderLayout());
        
        sentencetoStr = new JPanel();
        sentencetoStr.setBackground(Color.white);
    
        mainPanel.add(titlePanel, BorderLayout.NORTH);
        mainPanel.add(sentencetoStr, BorderLayout.CENTER);
        mainPanel.setMinimumSize(new Dimension(50,50));
        this.add(mainPanel, BorderLayout.CENTER);
    }
    
    public void onSentenceClick(Sentence clickedSentence) {
        sentencetoStr.removeAll();
        JLabel lbl = new JLabel("<html>"+clickedSentence.toString()
                .replaceAll("<", "&lt;")
                .replaceAll(">", "&gt;")
                .replaceAll("\\n", "<br>") +
                "</html>");
        sentencetoStr.add(lbl);
        
        // needs to be called
        parent.updateUI();
    }
    
    public void onSentenceHover(List<SentenceLabel> hoveredSentences) {
        // TODO
    }
}
