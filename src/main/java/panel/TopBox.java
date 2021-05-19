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
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class TopBox extends JPanel {
    
    TopPanel parent;
    SlidingWindow slider;
    SlidingWindowListener listener;
    
    JPanel chaptersPanel;
    
    Map<Pair<Integer, String>, List<Sentence>> chapters;
    
    public TopBox(TopPanel parent) {
        this.parent = parent;
        
        chaptersPanel = new JPanel();
        chaptersPanel.setLayout(new WrapLayout());
        chaptersPanel.setOpaque(true);
//        chaptersPanel.setBackground(new Color(0, 0, 255, 100));
        this.add(chaptersPanel);
        
        slider = new SlidingWindow(parent);
    }
    
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        
       slider.paint(g);
    }
    
    public void init() {
        // create panel for each chapter
        chapters.forEach((k, v) -> {
            JPanel mainPanel = new JPanel() {
                @Override
                public void paint(Graphics g) {
                    super.paint(g);
                    
                    g.setColor(Color.black);
                    g.drawString("[" + getBounds().x + ", " + getBounds().y + "]", 5, 10);
                }
            };
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
                lbl.setOpaque(true);
                lbl.init();
                sentencesPanel.add(lbl);
            });
            
            chaptersPanel.add(mainPanel);
        });
        
        listener = new SlidingWindowListener();
        this.addMouseListener(listener);
        this.addMouseMotionListener(listener);
    }
    
    public void setParent(TopPanel topPanel) {
        this.parent = topPanel;
        this.chapters = parent.chapters;
    }
    
    class SlidingWindowListener implements MouseMotionListener, MouseListener {
    
        boolean isSelected = false;
        
        Point mouse = new Point(0, 0);
        TopBox parent = TopBox.this;
        
        List<JPanel> chapterPanels = new ArrayList<>(20); {
            chapterPanels.addAll(Arrays.stream(chaptersPanel.getComponents())
                    .map(c -> (JPanel)c)
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
        }
        
        @Override
        public void mouseReleased(MouseEvent e) {
            mouse.setLocation(e.getPoint());
            isSelected = false;
        }
        
        @Override
        public void mouseDragged(MouseEvent e) {
            mouse.setLocation(e.getPoint());
            if (isSelected) {
                
                slider.setColor(Color.black);
                for (JPanel p : this.chapterPanels) {
                    if (p.getBounds().contains(mouse.getLocation())) {
                        slider.setColor(Color.red);
                        JLabel chapterTxt = (JLabel) p.getComponents()[0];
                        System.out.println("Chapter that contains slider: " + chapterTxt.getText() + " => " + p.getBounds());
                        break;
                    }
                }
                
                slider.setLocation(mouse.x, mouse.y);
                parent.updateUI();
            }
        }
        
        @Override public void mouseMoved(MouseEvent e) { mouse.setLocation(e.getPoint());}
        
        @Override public void mouseEntered(MouseEvent e) { }
        @Override public void mouseExited(MouseEvent e) { }
    }
}
