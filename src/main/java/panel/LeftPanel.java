package panel;

import SentenceLabel.SentenceLabel;
import main.Pair;
import main.Sentence;
import main.Utils;
import main.VisualType;

import javax.swing.*;
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
    
    VisualType currentVisualType;
    
    JPanel mainPanel;
    JPanel titlePanel;
    
    Map<Pair<Integer, String>, List<Sentence>> chapters;
    
    public LeftPanel(BottomPanel parent) {
        this.parent = parent;
        this.chapters = parent.parent.getChapters();
        this.setOpaque(true);
        this.setBackground(Utils.GRAY3);
        
        currentVisualType = VisualType.SENTIMENT;
        
        this.getVerticalScrollBar().setUnitIncrement(16);
//        this.getHorizontalScrollBar().setUnitIncrement(16);
        this.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        this.setHorizontalScrollBar(null);
        
        titlePanel = new JPanel();
        titlePanel.setLayout(new BorderLayout());
        JLabel title = new JLabel(" Chapter sentences preview ");
        title.setPreferredSize(new Dimension(300, 27));
        title.setOpaque(true);
        title.setBackground(Utils.TITLE_BACKGROUND);
        title.setFont(Utils.getFont(14));
        title.setPreferredSize(new Dimension(title.getPreferredSize().width, title.getPreferredSize().height+5));
        titlePanel.add(title, BorderLayout.CENTER);
        
        mainPanel = new JPanel();
        mainPanel.setBackground(Utils.GRAY3);
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
                    if (!(c instanceof JPanel)) return;
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
    
    public void onSentenceClick(SentenceLabel clickedSentence) {
        // Do nothing ?
    }
    
    // Hover = sliding window! (not on mouse hover)
    public void onSentenceHover(List<SentenceLabel> hoveredSentences) {
        mainPanel.removeAll();
        mainPanel.add(titlePanel); // TODO: readding everytime, fix later
        
        for (SentenceLabel hoveredSentence : hoveredSentences) {
//            JPanel rowPanel = new JPanel();
//            rowPanel.setLayout(new BorderLayout());
            
            
            // Not used
//            JLabel sentNumLbl = new JLabel(""+hoveredSentence.getSentence().sentenceNumber, SwingConstants.CENTER);
//            sentNumLbl.setFont(Utils.getFont(12));
//            sentNumLbl.setBorder(new StrokeBorder(new BasicStroke(1)));
//            sentNumLbl.setOpaque(true);
//            sentNumLbl.setBackground(Utils.GRAY2);
//            sentNumLbl.setPreferredSize(new Dimension(30, 30));
            // Not used
            
            
            JPanel sentencePanel = new JPanel();
            sentencePanel.setName("SentencePanel");
            sentencePanel.setBackground(Utils.GRAY3);
            sentencePanel.setBorder(BorderFactory.createMatteBorder(1,1,1,1,Utils.GRAY3));
            sentencePanel.setLayout(new WrapLayout(WrapLayout.LEFT));
            sentencePanel.addMouseListener(new MouseListener() {
                @Override public void mouseClicked(MouseEvent e) {
                    // all words are clicked
//                    LeftPanel.this.parent.rightPanel.onWordsClick(hoveredSentence.sentence.getWords());
                    
                    if (!hoveredSentence.isSelected()) {
                        parent.parent.getBottomPanel().onSentenceClick(hoveredSentence);
                        hoveredSentence.onSelect();
                    }
                }
                @Override public void mouseEntered(MouseEvent e) {
                    sentencePanel.setBackground(Color.white);
                    sentencePanel.setBorder(BorderFactory.createMatteBorder(1,1,1,1,Utils.GRAY));
                    sentencePanel.repaint();
                }
                @Override public void mouseExited(MouseEvent e) {
                    sentencePanel.setBorder(BorderFactory.createMatteBorder(1,1,1,1,Utils.GRAY3));
                    sentencePanel.setBackground(Utils.GRAY3);
                    sentencePanel.repaint();
                }
                @Override public void mousePressed(MouseEvent e) { this.mouseClicked(e); }
                @Override public void mouseReleased(MouseEvent e) { }
            });
            
            hoveredSentence.getSentence().getWords().forEach(w -> {
                WordLabel lbl = new WordLabel(this, sentencePanel, w);
//                lbl.setName("WORDLBL=> " + w);
                lbl.setParentSentence(hoveredSentence.sentence);
                lbl.addMouseListener(new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        // only one word is clicked
                        if (!hoveredSentence.isSelected()) {
                            parent.parent.getBottomPanel().onSentenceClick(hoveredSentence);
                            hoveredSentence.onSelect();
                        }
                    }
                    @Override public void mouseEntered(MouseEvent e) {
                        lbl.setBackground(lbl.HOVERED_COLOR);
                        sentencePanel.setBackground(Color.white);
                        sentencePanel.setBorder(BorderFactory.createMatteBorder(1,1,1,1,Utils.GRAY));
                        sentencePanel.repaint();
                    }
                    @Override public void mouseExited(MouseEvent e) {
                        lbl.setBackground(lbl.CURRENT_COLOR);
                        sentencePanel.setBorder(BorderFactory.createMatteBorder(1,1,1,1,Utils.GRAY3));
                        sentencePanel.setBackground(Utils.GRAY3);
                        sentencePanel.repaint();
                    }
                    @Override public void mousePressed(MouseEvent e) { this.mouseClicked(e); }
                    @Override public void mouseReleased(MouseEvent e) { }
                });
                sentencePanel.add(lbl);
            });
            
            // adding row with label on left, screws up in combination with WrapLayout
            // elements start vibrating as they reach their locations (probably a screw-up by WrapLayout)
            mainPanel.add(sentencePanel);
        }
        
        // Re-do the layouts:
        // Only needed if bottom panel is stretched far up then
        // when slider gets moved, the sentencePanels get stretched
        // to fill vertical space. This prevents that.
        Arrays.stream(this.mainPanel.getComponents()).forEach(c -> {
            if (!(c instanceof JPanel)) return;
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
    
    public void onNewTextImport(Map<Pair<Integer, String>, List<Sentence>> processedChapters) {
        this.chapters = processedChapters;
        
        mainPanel.removeAll();
        mainPanel.revalidate();
        mainPanel.doLayout();
        mainPanel.repaint();
        
        this.updateUI();
    }
}
