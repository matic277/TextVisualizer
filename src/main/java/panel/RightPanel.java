package panel;

import main.Pair;
import main.Sentence;
import main.Utils;
import org.apache.commons.lang3.math.NumberUtils;
import word.AbsMeasurableWord;
import word.AbsWord;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
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
    
    List<Sentence> allSelectedSentences = new ArrayList<>(10);
    
    JPanel mainPanel;
        // NORTH
        JPanel mainSentencePanel;
                                   // NORTH is titlePanel
            JPanel sentencesPanel; // CENTER - contains sentencePanel for every sentence
            JPanel buttonsPanel;   // SOUTH
                JButton clearSentencesBtn;
                JButton clearTableBtn;
                JButton addAllBtn;
        
        // CENTER
        JPanel wordStatsPanel;
                                  // NORTH is titlePanel
            JPanel statsPanel;    // CENTER
                JTable table;
                                  // SOUTH is empty
    
    // TABLE vars
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
    private final DefaultTableModel tableModel = new DefaultTableModel(tableHeader, 0);
    
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
        
        sentencesPanel = new JPanel();
        sentencesPanel.setLayout(new BoxLayout(sentencesPanel, BoxLayout.Y_AXIS));
        sentencesPanel.setBackground(Utils.GRAY3);
        mainSentencePanel.add(sentencesPanel, BorderLayout.CENTER);
        
        buttonsPanel = new JPanel();
        buttonsPanel.setBackground(Utils.GRAY3);
        buttonsPanel.setLayout(new WrapLayout(WrapLayout.RIGHT, 10, 4));
//        buttonsPanel.setBorder(new StrokeBorder(new BasicStroke(1)));
        
        clearSentencesBtn = new JButton("Clear sentences");
        clearSentencesBtn.addActionListener(a -> onClearSentencesBtnPress());
        addAllBtn = new JButton("Add all to table");
        addAllBtn.addActionListener(a -> onAddAllBtnPress());
        clearTableBtn = new JButton("Clear table");
        clearTableBtn.addActionListener(a -> onClearTableBtnPress());
        buttonsPanel.add(clearSentencesBtn);
        buttonsPanel.add(addAllBtn);
        buttonsPanel.add(clearTableBtn);
        
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
        statsPanel.setLayout(new BorderLayout());
        statsPanel.add(new JLabel("CONTENT"));
        wordStatsPanel.add(statsPanel, BorderLayout.CENTER);
        
        mainPanel.add(wordStatsPanel, BorderLayout.CENTER);
        
        
        // create JTable
        table = new JTable(tableModel);
        table.setFont(Utils.getFont(12));
        table.getTableHeader().setFont(Utils.getBoldFont(13));
    
        // custom column renderers
        TableColumn col = table.getColumnModel().getColumn(columnIndexMap.get(AbsWord.MapKey.PLEASANTNESS));
        col.setCellRenderer(new CustomRenderer());
        
        statsPanel.add(new JScrollPane(table), BorderLayout.CENTER);
        
        
        this.setViewportView(mainPanel);
    
        this.getVerticalScrollBar().setUnitIncrement(16);
        this.getHorizontalScrollBar().setUnitIncrement(16);
        this.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        this.setHorizontalScrollBar(null);
    
        this.addComponentListener(new ComponentListener() {
            RightPanel self = RightPanel.this;
            @Override public void componentResized(ComponentEvent e) {
                
                System.out.println(self.getSize());
                
                if (sentencesPanel == null) return;
                
                System.out.println("Pref.height=> " + sentencesPanel.getLayout().preferredLayoutSize(sentencesPanel).height);
                
                sentencesPanel.setPreferredSize(new Dimension(self.getSize().width-20, sentencesPanel.getLayout().preferredLayoutSize(sentencesPanel).height));
//                sentencePanel.setPreferredSize(new Dimension(self.getSize().width, sentencePanel.getLayout().preferredLayoutSize(sentencePanel).height));
                System.out.println("Pref.size=> " + sentencesPanel.getSize());
                sentencesPanel.setMaximumSize(sentencesPanel.getPreferredSize());
                sentencesPanel.setMinimumSize(sentencesPanel.getPreferredSize());
                sentencesPanel.revalidate();
                sentencesPanel.doLayout();
                mainSentencePanel.revalidate();
                mainSentencePanel.doLayout();
            }
            @Override public void componentMoved(ComponentEvent e) { }
            @Override public void componentShown(ComponentEvent e) { }
            @Override public void componentHidden(ComponentEvent e) { }
        });
    }
    
    private void onClearTableBtnPress() {
        System.out.println("Clear table pressed");
        tableModel.setRowCount(0);
        table.revalidate();
    }
    
    private void onAddAllBtnPress() {
        System.out.println("Add all pressed");
        allSelectedSentences.forEach(s -> appendWordsToTable(s.getWords()));
    }
    
    private void onClearSentencesBtnPress() {
        System.out.println("Clear sentences pressed");
        allSelectedSentences.clear();
        sentencesPanel.setPreferredSize(new Dimension(sentencesPanel.getSize().width, 0));
        sentencesPanel.removeAll();
        sentencesPanel.revalidate();
        sentencesPanel.doLayout();
        sentencesPanel.repaint();
    }
    
    
    public void onSentenceHover(List<SentenceLabel> hoveredSentences) {
    
    }

    public void onSentenceClick(Sentence clickedSentence) {
//        sentencesPanel.removeAll();
//        this.selectedSentence = clickedSentence;
        allSelectedSentences.add(clickedSentence);
        
        JPanel sentencePanel = new JPanel();
        sentencePanel.setLayout(new WrapLayout(WrapLayout.LEFT));
        sentencePanel.setBackground(Utils.GRAY3);
        sentencePanel.setBorder(BorderFactory.createMatteBorder(1,1,1,1,Utils.GRAY3));
        sentencePanel.addMouseListener(new MouseListener() {
            @Override public void mouseClicked(MouseEvent e) {
                // all words are clicked
                onWordsClick(clickedSentence.getWords());
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
        
        // SELECTED SENTENCE
        for (AbsWord word : clickedSentence.getWords()) {
            WordLabel lbl = new WordLabel(this, sentencesPanel, word);
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
                    sentencesPanel.setBackground(Color.white);
                    sentencesPanel.setBorder(BorderFactory.createMatteBorder(1,1,1,1,Utils.GRAY));
                    sentencesPanel.repaint();
                }
                @Override public void mouseExited(MouseEvent e) {
                    lbl.setBackground(lbl.NORMAL_COLOR);
                    sentencesPanel.setBorder(BorderFactory.createMatteBorder(1,1,1,1,Utils.GRAY3));
                    sentencesPanel.setBackground(Utils.GRAY3);
                    sentencesPanel.repaint();
                }
                @Override public void mousePressed(MouseEvent e) { }
                @Override public void mouseReleased(MouseEvent e) { }
            });
            sentencePanel.add(lbl);
        }
        sentencesPanel.add(sentencePanel);
        
        // some resizing
        // -20 for scrollbar, +1 so border is visible (or else it just barely gets cut-off on last sentence for some reason)
        sentencesPanel.setPreferredSize(new Dimension(this.getSize().width-20, sentencesPanel.getLayout().preferredLayoutSize(sentencesPanel).height+1));
//        System.out.println("Pref.size=> " + sentencePanel.getSize());
        sentencesPanel.setMaximumSize(sentencesPanel.getPreferredSize());
        sentencesPanel.setMinimumSize(sentencesPanel.getPreferredSize());
        sentencesPanel.revalidate();
        sentencesPanel.doLayout();
        
        // need to call this otherwise this components doesn't get updated
        // immediately, but only after resize happens
        this.parent.updateUI();
    }
    
    // 1 can be clicked or an entire sentence
    public void onWordsClick(List<AbsWord> words) {
//        System.out.println();
//        System.out.println("selected words=> " + words.size() + " : "+ Arrays.toString(words.stream().map(AbsWord::getSourceText).toArray()));
//
//
//        System.out.println("StatsMap for " + words.get(0).getSourceText() + " ("+words.get(0).getClass().getSimpleName()+"):");
//        words.get(0).getStatsMap().forEach((k, v) -> {
//            System.out.println("  "+k+" -> "+v);
//        });
//        System.out.println("table header: " + Arrays.deepToString(tableHeader));
        
        
        // Append to table
//        int i = 0;
//        String[][] tableValues = new String[words.size()][tableHeader.length];
//        for (AbsWord word : words) {
//            for (Map.Entry<AbsMeasurableWord.MapKey, String> entry : word.getStatsMap().entrySet()) {
//                int columnIndex = columnIndexMap.get(entry.getKey());
////                System.out.println(entry.getValue() + " -> "+NumberUtils.isNumber(entry.getValue()));
//                tableValues[i][columnIndex] =  NumberUtils.isNumber(entry.getValue()) ?
//                        AbsMeasurableWord.format.format(Double.parseDouble(entry.getValue())) : entry.getValue();
//            }
//            tableModel.addRow(tableValues[i]);
//            i++;
//        }
        appendWordsToTable(words);
        
        statsPanel.revalidate();
        statsPanel.updateUI();
        statsPanel.repaint();
    }
    
    private void appendWordsToTable(List<AbsWord> words) {
        int i = 0;
        String[][] tableValues = new String[words.size()][tableHeader.length];
        for (AbsWord word : words) {
            for (Map.Entry<AbsMeasurableWord.MapKey, String> entry : word.getStatsMap().entrySet()) {
                int columnIndex = columnIndexMap.get(entry.getKey());
                tableValues[i][columnIndex] =  NumberUtils.isNumber(entry.getValue()) ?
                        AbsMeasurableWord.format.format(Double.parseDouble(entry.getValue())) : entry.getValue();
            }
            tableModel.addRow(tableValues[i]);
            i++;
        }
    }
    
    class CustomRenderer extends DefaultTableCellRenderer
    {
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
        {
            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
//            setForeground(Color.blue);
            
//            System.out.println(c.getClass().getSimpleName());
//            System.out.println(value + ", " + value.getClass().getSimpleName());
            
            if (value != null && value instanceof String s) {
                String num = s;
                // What a fucking mess
                // MeasAbsWord.DecimalFormat formats negative words with strange "-"
                // sign, that can't be reverse parsed from string (encoding problem) ???
                double pleasantness = -99;
                if (!NumberUtils.isNumber(s.charAt(0)+"")) {
                    num = "-" + s.substring(1);
                }
                pleasantness = Double.parseDouble(num);
                
                setForeground(AbsMeasurableWord.isPositivePleasantness(pleasantness) ?
                        Utils.GREEN : AbsMeasurableWord.isNeutralPleasantness(pleasantness) ?
                            Color.DARK_GRAY : Utils.RED);
            }
            
            return c;
        }
    }
}
