package panel;

import SentenceLabel.SentenceLabel;
import main.Pair;
import main.Sentence;
import main.Utils;
import main.VisualType;
import panel.sentenceRows.LeftSentenceRowPanel;
import panel.sentenceRows.SentenceRowPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class LeftPanel extends JScrollPane {
    
    BottomPanel parent;
    
    JPanel mainPanel;
    JPanel titlePanel;
    
    List<LeftSentenceRowPanel> sentenceRows;
    
    Map<Pair<Integer, String>, List<Sentence>> chapters;
    
    public LeftPanel(BottomPanel parent) {
        this.parent = parent;
        this.chapters = parent.parent.getChapters();
        this.setOpaque(true);
        this.setBackground(Utils.GRAY3);
        
        this.sentenceRows = new ArrayList<>(50);
        
        this.getVerticalScrollBar().setUnitIncrement(16);
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
        titlePanel.setPreferredSize(new Dimension(this.getSize().width, titlePanel.getPreferredSize().height));
        
        mainPanel = new JPanel();
        mainPanel.setBackground(Utils.GRAY3);
        mainPanel.setLayout(new VerticalFlowLayout(VerticalFlowLayout.LEFT, VerticalFlowLayout.TOP, 0, 0));
        
        mainPanel.add(titlePanel);
        
        this.setViewportView(mainPanel);
        
        this.addComponentListener(new ComponentListener() {
            @Override public void componentResized(ComponentEvent e) {
                Arrays.stream(mainPanel.getComponents()).forEach(c -> {
                    if (!(c instanceof JPanel)) return;
                    // Crucial!
                    //                     parentWidth - scrollBarWidth(arrox)  ,    preferredHeight of layout
                    c.setPreferredSize(new Dimension(getSize().width-14, ((JPanel)c).getLayout().preferredLayoutSize((JPanel)c).height));
                    c.setMaximumSize(c.getPreferredSize());
                    c.setMinimumSize(c.getPreferredSize());
                    c.revalidate();
                    c.doLayout();
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
        titlePanel.setPreferredSize(new Dimension(this.getSize().width-14, titlePanel.getPreferredSize().height));
        mainPanel.add(titlePanel); // TODO: readding everytime, fix later
        
        for (SentenceLabel sentenceLbl : hoveredSentences) {
            LeftSentenceRowPanel sentenceRow = new LeftSentenceRowPanel(this, mainPanel, sentenceLbl);
            
            // adding row with label on left, screws up in combination with WrapLayout
            // elements start vibrating as they reach their locations (probably a screw-up by WrapLayout)
            mainPanel.add(sentenceRow);
            sentenceRows.add(sentenceRow);
            
            // for some reason, this must be called AFTER the component is added to mainPanel, otherwise height gets calculated wrongly
            sentenceRow.setPreferredSize(new Dimension(this.getSize().width-14, sentenceRow.getLayout().preferredLayoutSize(sentenceRow).height));
        }
        
        this.parent.updateUI();
    }
    
    public void onNewTextImport(Map<Pair<Integer, String>, List<Sentence>> processedChapters) {
        this.chapters = processedChapters;
        
        sentenceRows.forEach(row -> mainPanel.remove(row));
        sentenceRows.clear();
        
        mainPanel.revalidate();
        mainPanel.doLayout();
        mainPanel.repaint();
        
        this.updateUI();
    }
    
    public BottomPanel getBottomPanel() { return this.parent; }
}
