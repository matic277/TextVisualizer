package panel;

import main.Main;
import main.Pair;
import main.Sentence;
import main.Utils;

import javax.swing.*;
import javax.swing.border.StrokeBorder;
import java.awt.*;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class TopBox extends JComponent {
    
    TopPanel parent;
    
    Map<Pair<Integer, String>, List<Sentence>> chapters;
    
    public TopBox(TopPanel parent) {
        this.parent = parent;
        
        this.setLayout(new WrapLayout());
        
//        JLabel lbl = new JLabel(" TOP PANEL ");
//        lbl.setOpaque(true);
//        lbl.setBackground(Color.green);
//        lbl.setFont(Utils.getFont(14));
//        lbl.setPreferredSize(new Dimension(lbl.getPreferredSize().width, lbl.getPreferredSize().height+5));
//        this.add(lbl);
//        for (int i=0; i<100; i++) {
//            JLabel lbl2 = new JLabel(" " + i + " ");
//            lbl2.setOpaque(true);
//            lbl2.setBackground(Color.green);
//            lbl2.setFont(Utils.getFont(14));
////            lbl.setPreferredSize(new Dimension(75, 25));
//            this.add(lbl2);
//        }
    
    }
    
    public void init() {
//        var x = new Object() { int x = 0; }; chapters.forEach((k, v) -> { x.x += v.size(); }); System.out.println(x.x); // counts sentences
    
        AtomicInteger x = new AtomicInteger(0);
        
        // create panel for each chapter
        chapters.forEach((k, v) -> {
            JPanel mainPanel = new JPanel();
            mainPanel.setOpaque(true);
            mainPanel.setBorder(new StrokeBorder(new BasicStroke(2)));
            mainPanel.setLayout(new BorderLayout());
            mainPanel.setBackground(Color.white);
            
            JLabel title = new JLabel(k.getB());
            title.setBorder(new StrokeBorder(new BasicStroke(1)));
            title.setFont(Utils.getFont(14));
            title.setHorizontalAlignment(SwingConstants.CENTER);
            mainPanel.add(title, BorderLayout.NORTH);
            
            JPanel sentencesPanel = new JPanel();
            sentencesPanel.setOpaque(true);
            sentencesPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
            mainPanel.add(sentencesPanel, BorderLayout.CENTER);
            
            v.forEach(s -> {
                SentenceLabel lbl = new SentenceLabel(this.parent, s);
                lbl.setPreferredSize(Utils.SENTENCE_SIZE);
//                lbl.setBorder(new StrokeBorder(new BasicStroke(0.5f)));
                lbl.setOpaque(true);
//                lbl.setBackground(Utils.getRandomColor());
                lbl.init();
                sentencesPanel.add(lbl);
            });
            
            this.add(mainPanel);
            x.incrementAndGet();
        });
        
    
    }
    
    public void setParent(TopPanel topPanel) {
        this.parent = topPanel;
        this.chapters = parent.chapters;
    }
}
