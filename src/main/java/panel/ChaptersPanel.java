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
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        
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
            JPanel chapterPanel = new JPanel() {
//                @Override
//                public void paint(Graphics g) {
//                    super.paint(g);
//                    g.setColor(Color.black);
//                    g.drawString("[" + getLocation().x + ", " + getLocation().y + "]", 5, 15);
//                }
            };
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
                lbl.setPreferredSize(Utils.SENTENCE_SIZE);
                lbl.setOpaque(true);
                lbl.init();
                sentencesPanel.add(lbl);
            });
            
            chapterPanel.setMaximumSize(chapterPanel.getPreferredSize());
            mainPanel.add(chapterPanel);
            mainPanel.add(Box.createRigidArea(new Dimension(100, 10))); // dummy spacing component
        });
    }
    
    class SlidingWindowListener implements MouseMotionListener, MouseListener {
        
        boolean isSelected = false;
        JPanel snappedPannel;
        Integer dx, dy;
        
        Point mouse = new Point(0, 0);
        ChaptersPanel parent = ChaptersPanel.this;
        
        List<JPanel> chapterPanels = new ArrayList<>(20); {
            chapterPanels.addAll(Arrays.stream(parent.mainPanel.getComponents())
                    .filter(c -> (c instanceof JPanel))
                    .map(c -> (JPanel) c)
                    .collect(Collectors.toList()));
        }
        
        @Override public void mouseClicked(MouseEvent e) {
            mouse.setLocation(e.getPoint());
            System.out.println("click");
            System.out.println(chapterPanels.size());
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
                
                for (JPanel p : this.chapterPanels) {
                    if (p.getBounds().contains(mouse.getLocation())) {
                        snappedPannel = p;
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