package panel;

import SentenceLabel.SentenceLabel;
import main.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.*;
import java.util.List;

public class ChaptersPanel extends JScrollPane {
    
    public TopPanel parent;
    
    public JPanel mainPanel;
    public List<JPanel> chapterPanels = new ArrayList<>(20);
    
    SlidingWindow slider;
    SlidingWindowListener listener;
    
    public Map<Pair<Integer, String>, List<Sentence>> chapters;
    
    public VisualType currentVisualType;
    public ChapterType currentChapterType;
    public SentenceLabelVisualType currentSentenceLblVisualType;
    
    ChapterBuilder chapterBuilder = new HorizontalChapterBuilder();
    
    public ChaptersPanel(TopPanel parent) {
        this.parent = parent;
        this.chapters = parent.chapters;
        
        // defaults
        currentVisualType = VisualType.SENTIMENT;
        currentChapterType = ChapterType.HORIZONTAL;
        currentSentenceLblVisualType = SentenceLabelVisualType.DEFAULT;
        
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
        
        this.getVerticalScrollBar().setUnitIncrement(16);
        this.getHorizontalScrollBar().setUnitIncrement(16);
    }
    
    public void init() {
        chapterPanels.clear();
        this.mainPanel.removeAll();
        
        this.mainPanel.add(Box.createRigidArea(new Dimension(100, 10))); // dummy spacing component
        
        chapterBuilder.rebuild(this);
        
        mainPanel.revalidate();
        mainPanel.doLayout();
        mainPanel.repaint();
        this.updateUI();
    }
    
    public void onVisualTypeChange(VisualType visualType) {
        currentVisualType = visualType;
        chapterPanels.forEach(chapterPanel -> {
            JPanel sentencesPanel = (JPanel) chapterPanel.getComponents()[1];
            for (Component sentenceCmp : sentencesPanel.getComponents()) {
                if (sentenceCmp instanceof SentenceLabel sentLbl) {
                    sentLbl.onVisualTypeChange(visualType);
                }
            }
            sentencesPanel.revalidate();
        });
    }
    
    public void onSentenceSizeChange(int newSize) {
        chapterPanels.forEach(chapterPanel -> {
            JPanel sentencesPanel = (JPanel) chapterPanel.getComponents()[1];
            for (Component sentenceCmp : sentencesPanel.getComponents()) {
                if (sentenceCmp instanceof SentenceLabel sentLbl) {
                    if (currentChapterType == ChapterType.HORIZONTAL) {
                        sentLbl.setPreferredSize(new Dimension(newSize, sentLbl.getHeight()));
                        sentLbl.setMinimumSize(new Dimension(newSize, sentLbl.getHeight()));
//                        sentLbl.init();
                    } else if (currentChapterType == ChapterType.VERTICAL) {
                        sentLbl.setPreferredSize(new Dimension(sentLbl.getWidth(), newSize));
                        sentLbl.setMinimumSize(new Dimension(sentLbl.getWidth(), newSize));
//                        sentLbl.init();
                    }
                }
            }
            sentencesPanel.revalidate();
        });
    }
    
    public void onSliderSizeChange(int newSize) {
        if (currentChapterType == ChapterType.HORIZONTAL) slider.setSize(newSize, slider.height);
        if (currentChapterType == ChapterType.VERTICAL) slider.setSize(slider.width, newSize);
        this.repaint();
    }
    
    public void onNewTextImport(Map<Pair<Integer, String>, List<Sentence>> processedChapters) {
        chapters = processedChapters;
        slider.setLocation(10, 10);
        
        mainPanel.removeAll();
        chapterPanels.clear();
        
        init();
        
        System.out.println(" -> Chapters inited.");
    }
    
    public void onChapterTypeChange(ChapterType chapterType) {
        currentChapterType = chapterType;
        
        if (chapterType == ChapterType.HORIZONTAL) {
            this.chapterBuilder = new HorizontalChapterBuilder();
        } else if (chapterType == ChapterType.VERTICAL) {
            this.chapterBuilder = new VerticalChapterBuilder();
        }
        
        init();
        slider.onNewChapterTypeChange(chapterType);
    }
    
    public void onSentenceLabelVisualTypeChange(SentenceLabelVisualType visualType) {
        currentSentenceLblVisualType = visualType;
        
        chapterPanels.forEach(chapterPanel -> {
            JPanel sentencesPanel = (JPanel) chapterPanel.getComponents()[1];
            for (Component sentenceCmp : sentencesPanel.getComponents()) {
                if (sentenceCmp instanceof SentenceLabel sentLbl) {
                    sentLbl.onSentenceLblVisualTypeChange(visualType);
                }
            }
            sentencesPanel.revalidate();
        });
        
        mainPanel.revalidate();
        mainPanel.doLayout();
        mainPanel.repaint();
        this.updateUI();
    }
    
    public void onWordSearch(String word, QueryTab caller) {
        chapterPanels.parallelStream()
                .map(panel -> (JPanel) panel.getComponents()[1])
                .map(Container::getComponents)
                .flatMap(Arrays::stream)
                .map(sentenceCmp -> {
                    if(sentenceCmp instanceof SentenceLabel sentLbl) return sentLbl;
                    return null;
                })
                .filter(Objects::nonNull)
                .forEach(sentLbl -> sentLbl.onWordSearch(word, caller));
        
        this.repaint();
        
        //chapterPanels.forEach(chapterPanel -> {
        //    JPanel sentencesPanel = (JPanel) chapterPanel.getComponents()[1];
        //    for (Component sentenceCmp : sentencesPanel.getComponents()) {
        //        if (sentenceCmp instanceof SentenceLabel sentLbl) {
        //            sentLbl.onSentenceLblVisualTypeChange(visualType);
        //        }
        //    }
        //    sentencesPanel.revalidate();
        //});
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
                
                // snap it to snapped panel
                // and resize to fit size
                if (currentChapterType == ChapterType.HORIZONTAL) {
                    if (snappedPannel != null) {
                        slider.setLocation(mouse.x - dx, snappedPannel.getLocation().y - 10);
                        slider.setSize(slider.width, snappedPannel.getHeight() + 15);
                        List<SentenceLabel> sentences = slider.getHoveredSentences(snappedPannel);
                        parent.parent.parent.getBottomPanel().onSentenceHover(sentences);
                    } else {
                        slider.setLocation(mouse.x - dx, mouse.y - dy);
                    }
                }
                else if (currentChapterType == ChapterType.VERTICAL) {
                    if (snappedPannel != null) {
                        slider.setLocation(snappedPannel.getLocation().x-8, mouse.y - dy);
                        slider.setSize(snappedPannel.getWidth()+10, slider.height);
                        List<SentenceLabel> sentences = slider.getHoveredSentences(snappedPannel);
                        parent.parent.parent.getBottomPanel().onSentenceHover(sentences);
                    } else {
                        slider.setLocation(mouse.x - dx, mouse.y - dy);
                    }
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
