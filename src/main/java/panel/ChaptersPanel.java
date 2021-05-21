package panel;

import main.Pair;
import main.Sentence;
import main.Utils;

import javax.swing.*;
import javax.swing.border.StrokeBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ChaptersPanel extends JScrollPane {
    
    TopPanel parent;
    
    JPanel mainPanel;
    List<JPanel> chapterPanels = new ArrayList<>(20);
    
    SlidingWindow slider;
    SlidingWindowListener listener;
    
    Map<Pair<Integer, String>, List<Sentence>> chapters;
    
    public ChaptersPanel(TopPanel parent) {
        this.parent = parent;
        this.chapters = parent.chapters;
        
        slider = new SlidingWindow(this);
        
        mainPanel = new JPanel() {
            @Override public void paint(Graphics g) {
                super.paint(g);
                slider.paint(g);
            }
        };
        mainPanel.setBackground(Utils.GRAY3);
        mainPanel.setLayout(new WrapLayout(WrapLayout.CENTER));
        
        init(); // should be called before instantiating slider listener!
        
        listener = new SlidingWindowListener();
        mainPanel.setFocusable(true);
        mainPanel.addMouseListener(listener);
        mainPanel.addMouseMotionListener(listener);
        
        this.setViewportView(mainPanel);
    }
    
    public void init() {
        this.mainPanel.add(Box.createRigidArea(new Dimension(100, 10))); // dummy spacing component
        
        // create panel for each chapter
        chapters.forEach((k, v) -> {
            JPanel chapterPanel = new JPanel();
            chapterPanel.setOpaque(true);
            chapterPanel.setBorder(new StrokeBorder(new BasicStroke(2)));
            chapterPanel.setLayout(new BorderLayout());
            chapterPanel.setBackground(Color.white);
            chapterPanel.setName("Main panel for chapter " + k.getB());
            
            JLabel title = new JLabel(k.getB());
            title.setBorder(new StrokeBorder(new BasicStroke(1)));
            title.setFont(Utils.getFont(14));
            title.setHorizontalAlignment(SwingConstants.CENTER);
            chapterPanel.add(title, BorderLayout.NORTH);
            
            JPanel sentencesPanel = new JPanel();
            sentencesPanel.setName("Sentence panel for chapter " + k.getB());
            sentencesPanel.setOpaque(true);
            sentencesPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
            chapterPanel.add(sentencesPanel, BorderLayout.CENTER);
            
            v.forEach(s -> {
                SentenceLabel lbl = new SentenceLabel(this, s);
                lbl.init();
                sentencesPanel.add(lbl);
            });
            
            chapterPanel.setMaximumSize(chapterPanel.getPreferredSize());
            chapterPanels.add(chapterPanel);
            mainPanel.add(chapterPanel);
            mainPanel.add(Box.createRigidArea(new Dimension(100, 10))); // dummy spacing component
        });
    }
    
    public void onSentenceWidthChange(int newWidth) {
        chapterPanels.forEach(chapterPanel -> {
            JPanel sentencesPanel = (JPanel) chapterPanel.getComponents()[1];
            for (Component sentenceCmp : sentencesPanel.getComponents()) {
                JLabel sentence = (JLabel) sentenceCmp;
                sentence.setPreferredSize(new Dimension(newWidth, sentence.getHeight()));
                sentence.setMinimumSize(new Dimension(newWidth, sentence.getHeight()));
            }
            sentencesPanel.revalidate();
        });
    }
    
    public void onSliderWidthChange(int newWidth) {
        slider.setSize(newWidth, slider.height);
        this.repaint();
    }
    
    class SlidingWindowListener implements MouseMotionListener, MouseListener {
        
        boolean isSelected = false;
        JPanel snappedPannel;
        JPanel lastSnappedPannel;
        Integer dx, dy;
        
        Point mouse = new Point(0, 0);
        ChaptersPanel parent = ChaptersPanel.this;
        
        @Override public void mouseClicked(MouseEvent e) {
            mouse.setLocation(e.getPoint());
        }
        @Override public void mousePressed(MouseEvent e) {
            mouse.setLocation(e.getPoint());
            isSelected = slider.contains(mouse.x, mouse.y);
            
            if (isSelected) {
                dx = mouse.x - slider.x;
                dy = mouse.y - slider.y;
            }
        }
        @Override public void mouseReleased(MouseEvent e) {
            mouse.setLocation(e.getPoint());
            isSelected = false;
            dx = null; dy = null;
        }
        @Override public void mouseDragged(MouseEvent e) {
            mouse.setLocation(e.getPoint());
            
            if (isSelected) {
                snappedPannel = null;
                
                // only need for un-highlighting
                if (lastSnappedPannel != null) {
                    JPanel sentencesPanel = (JPanel) lastSnappedPannel.getComponents()[1];
                    Component[] sentenceCmps = sentencesPanel.getComponents();
    
                    for (int i=0; i<sentenceCmps.length; i++) {
                        SentenceLabel slbl = (SentenceLabel) sentenceCmps[i];
                        slbl.unhighlight();
                    }
                }
                
                for (JPanel p : parent.chapterPanels) {
                    if (p.getBounds().contains(mouse.getLocation())) {
                        snappedPannel = p;
                        lastSnappedPannel = p;
                        break;
                    }
                }
                
                // is snapped to some chapter panel
                if (snappedPannel != null) {
                    slider.setLocation(mouse.x - dx, snappedPannel.getLocation().y-10);
                    List<SentenceLabel> sentences = slider.getHoveredSentences(snappedPannel);
                    parent.parent.parent.getBottomPanel().onSentenceHover(sentences);
                } else {
                    slider.setLocation(mouse.x - dx, mouse.y - dy);
                }
                
                // needs to be called, otherwise sliders
                // position doesn't get updated
                mainPanel.repaint();
            }
        }
        @Override public void mouseMoved(MouseEvent e) { mouse.setLocation(e.getPoint());}
        @Override public void mouseEntered(MouseEvent e) { }
        @Override public void mouseExited(MouseEvent e) { }
    }
}
