package panel;

import main.Pair;
import main.Sentence;
import main.Utils;
import org.w3c.dom.Text;

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

public class TopBox extends JPanel {
    
    TopPanel parent;
    SlidingWindow slider;
    SlidingWindowListener listener;
    
    JPanel chaptersPanel;
    
    Map<Pair<Integer, String>, List<Sentence>> chapters;
    
    public TopBox(TopPanel parent) {
        this.parent = parent;
        
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        slider = new SlidingWindow(parent);
        
        this.setBackground(Utils.GRAY3);
    }
    
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        
       slider.paint(g);
    }
    
    public void init() {
        this.add(Box.createRigidArea(new Dimension(100, 10))); // dummy spacing component
        
        // create panel for each chapter
        chapters.forEach((k, v) -> {
            JPanel mainPanel = new JPanel() {
                @Override
                public void paint(Graphics g) {
                    super.paint(g);
                    
                    g.setColor(Color.black);
                    g.drawString("[" + getLocation().x + ", " + getLocation().y + "]", 5, 15);
                }
            };
            mainPanel.setOpaque(true);
            mainPanel.setBorder(new StrokeBorder(new BasicStroke(2)));
            mainPanel.setLayout(new BorderLayout());
            mainPanel.setBackground(Color.white);
            mainPanel.setName("Main panel for chapter " + k.getB());
            
            JLabel title = new JLabel(k.getB());
            title.setBorder(new StrokeBorder(new BasicStroke(1)));
            title.setFont(Utils.getFont(14));
            title.setHorizontalAlignment(SwingConstants.CENTER);
            mainPanel.add(title, BorderLayout.NORTH);
            
            JPanel sentencesPanel = new JPanel();
            sentencesPanel.setName("Sentence panel for chapter " + k.getB());
            sentencesPanel.setOpaque(true);
            sentencesPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
            mainPanel.add(sentencesPanel, BorderLayout.CENTER);
            
            v.forEach(s -> {
                SentenceLabel lbl = new SentenceLabel(this.parent, s);
                lbl.setPreferredSize(Utils.SENTENCE_SIZE);
                lbl.setOpaque(true);
                lbl.init();
                sentencesPanel.add(lbl);
            });
            
            mainPanel.setMaximumSize(mainPanel.getPreferredSize());
            this.add(mainPanel);
            this.add(Box.createRigidArea(new Dimension(100, 10))); // dummy spacing component
        });
        
        listener = new SlidingWindowListener();
        this.setFocusable(true);
        this.addMouseListener(listener);
        this.addMouseMotionListener(listener);
    }
    
    public void setParent(TopPanel topPanel) {
        this.slider.setParent(topPanel);
        this.parent = topPanel;
        this.chapters = parent.chapters;
    }
    
    public BottomPanel getBottomPanel() {
        return this.parent.parent.bottomPanel;
    }
    
    class SlidingWindowListener implements MouseMotionListener, MouseListener {
    
        boolean isSelected = false;
        JPanel snappedPannel;
        Integer dx, dy;
        
        Point mouse = new Point(0, 0);
        TopBox parent = TopBox.this;
        
        List<JPanel> chapterPanels = new ArrayList<>(20); {
            chapterPanels.addAll(Arrays.stream(parent.getComponents())
                    .filter(c -> (c instanceof JPanel))
                    .map(c -> (JPanel) c)
                    .collect(Collectors.toList()));
        }
        
        @Override
        public void mouseClicked(MouseEvent e) {
            mouse.setLocation(e.getPoint());
            System.out.println("click");
    
            System.out.println(chapterPanels.size());
        }
        
        @Override
        public void mousePressed(MouseEvent e) {
            mouse.setLocation(e.getPoint());

            isSelected = slider.contains(mouse.x, mouse.y);
            
            if (isSelected) {
                dx = mouse.x - slider.x;
                dy = mouse.y - slider.y;
            }
        }
        
        @Override
        public void mouseReleased(MouseEvent e) {
            mouse.setLocation(e.getPoint());
            isSelected = false;
            dx = null; dy = null;
        }
        
        @Override
        public void mouseDragged(MouseEvent e) {
            mouse.setLocation(e.getPoint());
            if (isSelected) {
//                slider.setColor(Color.black);
                
                snappedPannel = null;
                Rectangle chapterBounds = new Rectangle();
                for (JPanel p : this.chapterPanels) {
                    chapterBounds.setBounds(p.getLocation().x, p.getLocation().y, p.getWidth(), p.getHeight());
                    if (chapterBounds.contains(mouse.getLocation())) {
                        snappedPannel = p;
                        break;
                    }
                }
                
                // is snapped to some chapter panel
                if (snappedPannel != null) {
                    slider.setLocation(mouse.x - dx, snappedPannel.getLocation().y-10);
                    
                    List<SentenceLabel> sentences = slider.getHoveredSentences(snappedPannel);
                    parent.getBottomPanel().onSentenceHover(sentences);
                    
                } else {
                    slider.setLocation(mouse.x - dx, mouse.y - dy);
                }
                
                // needs to be called, otherwise sliders
                // position doesn't get updated
                parent.updateUI();
            }
        }
        
        @Override public void mouseMoved(MouseEvent e) { mouse.setLocation(e.getPoint());}
        
        @Override public void mouseEntered(MouseEvent e) { }
        @Override public void mouseExited(MouseEvent e) { }
    
    }
}
