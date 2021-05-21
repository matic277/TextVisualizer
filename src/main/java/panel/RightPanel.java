package panel;

import main.Pair;
import main.Sentence;
import main.Utils;
import word.AbsMeasurableWord;
import word.AbsWord;

import javax.swing.*;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class RightPanel extends JScrollPane {
    
    BottomPanel parent;
    
    Sentence selectedSentence; // contents of sentencePanel
    
    JPanel mainPanel;
        // NORTH
        JPanel mainSentencePanel;
                                  // NORTH is titlePanel
            JPanel sentencePanel; // CENTER
            JPanel buttonsPanel;  // SOUTH
                JButton clearBtn;
                JButton addAllBtn;
        
        // CENTER
        JPanel wordStatsPanel;
                                  // NORTH is titlePanel
            JPanel statsPanel;    // CENTER
                                  // SOUTH is empty
    
    Map<Pair<Integer, String>, List<Sentence>> chapters;
    
    public RightPanel(BottomPanel parent) {
        this.parent = parent;
        this.chapters = parent.parent.getChapters();
        
//        content.setParent(this);
    
        mainPanel = new JPanel();
        mainPanel.setBackground(Utils.GRAY3);
//        mainPanel.setLayout(new WrapLayout(0, 0, WrapLayout.LEFT));
        mainPanel.setLayout(new BorderLayout());
//        mainPanel.setLayout(new GridLayout(15, 2, 0, 0));
    
        
        // NORTH
        JLabel title = new JLabel(" Selected sentence");
        title.setPreferredSize(new Dimension(300, 27));
        title.setOpaque(true);
        title.setBackground(Utils.GRAY);
        title.setFont(Utils.getFont(14));
        title.setPreferredSize(new Dimension(title.getPreferredSize().width, title.getPreferredSize().height+5));
        
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BorderLayout());
        titlePanel.add(title, BorderLayout.CENTER);
        
        mainSentencePanel = new JPanel();
        mainSentencePanel.setLayout(new BorderLayout());
        mainSentencePanel.add(titlePanel, BorderLayout.NORTH);
        
        sentencePanel = new JPanel();
        sentencePanel.add(new JLabel("content"));
        sentencePanel.setLayout(new WrapLayout(WrapLayout.LEFT));
        sentencePanel.setBackground(Utils.GRAY3);
        sentencePanel.addMouseListener(new MouseListener() {
            @Override public void mouseClicked(MouseEvent e) {
                // all words are clicked
                onWordsClick(selectedSentence.getWords());
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
            @Override public void mousePressed(MouseEvent e) { }
            @Override public void mouseReleased(MouseEvent e) { }
        });
        mainSentencePanel.add(sentencePanel, BorderLayout.CENTER);
        
        buttonsPanel = new JPanel();
        buttonsPanel.setBackground(Utils.GRAY3);
        buttonsPanel.setLayout(new WrapLayout(WrapLayout.RIGHT, 10, 4));
//        buttonsPanel.setBorder(new StrokeBorder(new BasicStroke(1)));
        
        clearBtn = new JButton("Clear");
        addAllBtn = new JButton("Add all");
        buttonsPanel.add(clearBtn);
        buttonsPanel.add(addAllBtn);
        
        mainSentencePanel.add(buttonsPanel, BorderLayout.SOUTH);
        
        mainPanel.add(mainSentencePanel, BorderLayout.NORTH);
        
        
        
        // CENTER
        JPanel titlePanel2 = new JPanel();
        titlePanel2.setLayout(new BorderLayout());
        
        JLabel title2 = new JLabel(" Word statistics");
        title2.setPreferredSize(new Dimension(300, 27));
        title2.setOpaque(true);
        title2.setBackground(Utils.GRAY);
        title2.setFont(Utils.getFont(14));
        title2.setPreferredSize(new Dimension(title2.getPreferredSize().width, title2.getPreferredSize().height+5));
        titlePanel2.add(title2, BorderLayout.NORTH);
        
        wordStatsPanel = new JPanel();
        wordStatsPanel.setLayout(new BorderLayout());
        wordStatsPanel.add(titlePanel2, BorderLayout.NORTH);
        
        statsPanel = new JPanel();
        statsPanel.setBackground(Utils.GRAY3);
        statsPanel.add(new JLabel("CONTENT"));
        wordStatsPanel.add(statsPanel, BorderLayout.CENTER);
        
        mainPanel.add(wordStatsPanel, BorderLayout.CENTER);
        
        
        
        
        this.setViewportView(mainPanel);
    
        this.getVerticalScrollBar().setUnitIncrement(16);
        this.getHorizontalScrollBar().setUnitIncrement(16);
        this.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        this.setHorizontalScrollBar(null);
    
        this.addComponentListener(new ComponentListener() {
            RightPanel self = RightPanel.this;
            @Override public void componentResized(ComponentEvent e) {
                
                System.out.println(self.getSize());
                
                if (sentencePanel == null) return;
                
                System.out.println("Pref.height=> " + sentencePanel.getLayout().preferredLayoutSize(sentencePanel).height);
                
                sentencePanel.setPreferredSize(new Dimension(self.getSize().width-20, sentencePanel.getLayout().preferredLayoutSize(sentencePanel).height));
//                sentencePanel.setPreferredSize(new Dimension(self.getSize().width, sentencePanel.getLayout().preferredLayoutSize(sentencePanel).height));
                System.out.println("Pref.size=> " + sentencePanel.getSize());
                sentencePanel.setMaximumSize(sentencePanel.getPreferredSize());
                sentencePanel.setMinimumSize(sentencePanel.getPreferredSize());
                sentencePanel.revalidate();
                sentencePanel.doLayout();
                mainSentencePanel.revalidate();
                mainSentencePanel.doLayout();
            }
            @Override public void componentMoved(ComponentEvent e) { }
            @Override public void componentShown(ComponentEvent e) { }
            @Override public void componentHidden(ComponentEvent e) { }
        });
    }
    
    
    public void onSentenceHover(List<SentenceLabel> hoveredSentences) {
    
    }

    public void onSentenceClick(Sentence clickedSentence) {
        sentencePanel.removeAll();
        this.selectedSentence = clickedSentence;
        
        // SELECTED SENTENCE
        for (AbsWord word : clickedSentence.getWords()) {
            WordLabel lbl = new WordLabel(this, sentencePanel, word);
            lbl.setRightPanel(this);
            lbl.setParentSentence(clickedSentence);
            lbl.setWordListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    // only one word is clicked
                    parent.rightPanel.onWordsClick(Collections.singletonList(word));
                }
                @Override public void mouseEntered(MouseEvent e) {
                    lbl.setBackground(lbl.HOVERED_COLOR);
                    sentencePanel.setBackground(Color.white);
                    sentencePanel.setBorder(BorderFactory.createMatteBorder(1,1,1,1,Utils.GRAY));
                    sentencePanel.repaint();
                }
                @Override public void mouseExited(MouseEvent e) {
                    lbl.setBackground(lbl.NORMAL_COLOR);
                    sentencePanel.setBorder(BorderFactory.createMatteBorder(1,1,1,1,Utils.GRAY3));
                    sentencePanel.setBackground(Utils.GRAY3);
                    sentencePanel.repaint();
                }
                @Override public void mousePressed(MouseEvent e) { }
                @Override public void mouseReleased(MouseEvent e) { }
            });
            sentencePanel.add(lbl);
        }
        
        // some resizing
        sentencePanel.setPreferredSize(new Dimension(this.getSize().width-20, sentencePanel.getLayout().preferredLayoutSize(sentencePanel).height));
//        System.out.println("Pref.size=> " + sentencePanel.getSize());
        sentencePanel.setMaximumSize(sentencePanel.getPreferredSize());
        sentencePanel.setMinimumSize(sentencePanel.getPreferredSize());
        sentencePanel.revalidate();
        sentencePanel.doLayout();
        
        // need to call this otherwise this components doesn't get updated
        // immediately, but only after resize happens
        this.parent.updateUI();
    }
    
    // map: MapKey -> columnIndex (based off MapKey.order ordering)
    private final Map<AbsMeasurableWord.MapKey, Integer> columnIndexMap = new HashMap<>(10);
    private final String[] tableHeader = new String[AbsMeasurableWord.MapKey.values().length]; {
        int i = 0;
        for (AbsMeasurableWord.MapKey key : Arrays.stream(AbsMeasurableWord.MapKey.values())
                                             .sorted(Comparator.comparingInt(e -> e.order)).collect(Collectors.toList())) {
            tableHeader[i++] = key.printValue;
            columnIndexMap.put(key, key.order);
        }
    }
    
    // 1 can be clicked or an entire sentence
    public void onWordsClick(List<AbsWord> words) {
        System.out.println();
        System.out.println("selected words=> " + words.size() + " : "+ Arrays.toString(words.stream().map(AbsWord::getSourceText).toArray()));
        
        String[][] tableValues = new String[words.size()][tableHeader.length];
        
        System.out.println("StatsMap for " + words.get(0).getSourceText() + " ("+words.get(0).getClass().getSimpleName()+"):");
        words.get(0).getStatsMap().forEach((k, v) -> {
            System.out.println("  "+k+" -> "+v);
        });
    
//        System.out.println("table header: " + Arrays.deepToString(tableHeader));
        
        int i = 0;
        for (AbsWord word : words) {
            for (Map.Entry<AbsMeasurableWord.MapKey, String> entry : word.getStatsMap().entrySet()) {
                int columnIndex = columnIndexMap.get(entry.getKey());
                tableValues[i][columnIndex] = entry.getValue();
            }
            i++;
        }
        JTable stats = new JTable(tableValues, tableHeader);
        statsPanel.removeAll();
        statsPanel.add(new JScrollPane(stats));
        
        statsPanel.revalidate();
        statsPanel.updateUI();
        statsPanel.repaint();
    }
    
}
