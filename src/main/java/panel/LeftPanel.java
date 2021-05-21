package panel;

import main.Pair;
import main.Sentence;
import main.Utils;

import javax.swing.*;
import javax.swing.border.StrokeBorder;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class LeftPanel extends JScrollPane {
    
    BottomPanel parent;
    
    JPanel mainPanel;
    JPanel sentencetoStr;
    
    Map<Pair<Integer, String>, List<Sentence>> chapters;
    
    public LeftPanel(BottomPanel parent) {
        this.parent = parent;
        this.chapters = parent.parent.getChapters();
        this.setBackground(Color.CYAN);
    
        this.getVerticalScrollBar().setUnitIncrement(16);
//        this.getHorizontalScrollBar().setUnitIncrement(16);
        this.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        this.setHorizontalScrollBar(null);
    
//        this.setLayout(new BorderLayout());
//        this.setBackground(Utils.GRAY3);
//
//        JPanel titlePanel = new JPanel();
//        titlePanel.setLayout(new BorderLayout());
//        JLabel title = new JLabel(" Word and sentence statistics");
//        title.setPreferredSize(new Dimension(300, 27));
//        title.setOpaque(true);
//        title.setBackground(Utils.GRAY);
//        title.setFont(Utils.getFont(14));
//        title.setPreferredSize(new Dimension(title.getPreferredSize().width, title.getPreferredSize().height+5));
//        titlePanel.add(title, BorderLayout.CENTER);
        
        mainPanel = new JPanel();
        mainPanel.setBackground(Utils.GRAY2);
//        mainPanel.setLayout(new WrapLayout(0, 0, WrapLayout.LEFT));
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
//        mainPanel.setLayout(new GridLayout(15, 2, 0, 0));
        
        
        this.setViewportView(mainPanel);
        
        
        this.addComponentListener(new ComponentListener() {
            LeftPanel self = LeftPanel.this;
            @Override public void componentResized(ComponentEvent e) {

//                var x = new Object() {int x = 0;};
//                System.out.println(self.mainPanel.getComponents().length);
                Arrays.stream(self.mainPanel.getComponents()).forEach(c -> {
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
                    c.setPreferredSize(new Dimension(self.getSize().width-25, ((JPanel)c).getLayout().preferredLayoutSize((JPanel)c).height));
                    c.setMaximumSize(c.getPreferredSize());
                    c.setMinimumSize(c.getPreferredSize());
                    c.revalidate();
                    c.doLayout();
                    
//                    x.x++;
                });
            }
            @Override public void componentMoved(ComponentEvent e) { }
            @Override public void componentShown(ComponentEvent e) { }
            @Override public void componentHidden(ComponentEvent e) { }
        });
    }
    
    public void onSentenceClick(Sentence clickedSentence) {
        // TODO
//        sentencetoStr.removeAll();
//        JLabel lbl = new JLabel("<html>"+clickedSentence.toString()
//                .replaceAll("<", "&lt;")
//                .replaceAll(">", "&gt;")
//                .replaceAll("\\n", "<br>") +
//                "</html>");
//        sentencetoStr.add(lbl);
//
//        // needs to be called
//        parent.updateUI();
    }
    
    public void onSentenceHover(List<SentenceLabel> hoveredSentences) {
        mainPanel.removeAll();
        
        for (SentenceLabel hoveredSentence : hoveredSentences) {
//            JPanel rowPanel = new JPanel();
//            rowPanel.setLayout(new BorderLayout());
            
            
            // Not used
            JLabel sentNumLbl = new JLabel(""+hoveredSentence.getSentence().sentenceNumber, SwingConstants.CENTER);
            sentNumLbl.setFont(Utils.getFont(12));
            sentNumLbl.setBorder(new StrokeBorder(new BasicStroke(1)));
            sentNumLbl.setOpaque(true);
            sentNumLbl.setBackground(Utils.GRAY2);
            sentNumLbl.setPreferredSize(new Dimension(30, 30));
            // Not used
            
            
            JPanel sentencePanel = new JPanel();
            sentencePanel.setName("SentencePanel");
            sentencePanel.setBackground(Utils.GRAY3);
            sentencePanel.setBorder(BorderFactory.createMatteBorder(1,1,1,1,Utils.GRAY3));
            sentencePanel.setLayout(new WrapLayout(WrapLayout.LEFT));
//            sentencePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
            sentencePanel.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    // TODO
                }
                @Override public void mouseEntered(MouseEvent e) {
                    sentencePanel.setBackground(Color.white);
                    sentencePanel.setBorder(BorderFactory.createMatteBorder(1,1,1,1,Utils.GRAY));
                }
                @Override public void mouseExited(MouseEvent e) {
                    sentencePanel.setBackground(Utils.GRAY3);
                    sentencePanel.setBorder(BorderFactory.createMatteBorder(1,1,1,1,Utils.GRAY3));
                }
                @Override public void mousePressed(MouseEvent e) { }
                @Override public void mouseReleased(MouseEvent e) { }
            });
            
//            sentencePanel.add(sentNumLbl);
            
            hoveredSentence.getSentence().getWords().forEach(w -> {
                WordLabel lbl = new WordLabel(this, sentencePanel, w);
                sentencePanel.add(lbl);
            });
            
            // adding row with label on left, screws up in combination with WrapLayout
            // elements start vibrating as they reach their locations (probably a screw-up by WrapLayout)
            mainPanel.add(sentencePanel);
//            mainPanel.add(sentNumLbl);
        }
        
        Arrays.stream(this.mainPanel.getComponents()).forEach(c -> {
            // Crucial!
            //                     parentWidth - scrollBarWidth(arrox)  ,    preferredHeight of layout
            c.setPreferredSize(new Dimension(this.getSize().width-25, ((JPanel)c).getLayout().preferredLayoutSize((JPanel)c).height));
            c.setMaximumSize(c.getPreferredSize());
            c.setMinimumSize(c.getPreferredSize());
            c.revalidate();
            c.doLayout();
        });
        
        this.parent.updateUI();
    }
}
