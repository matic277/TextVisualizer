package panel;

import main.Pair;
import main.Sentence;
import main.Utils;
import main.VisualType;
import org.apache.commons.lang3.math.NumberUtils;
import word.AbsMeasurableWord;
import word.AbsWord;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;
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
    
    VisualType currentVisualType;
    
    List<SentenceLabel> allSelectedSentences = new ArrayList<>(10);
    
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
    private final DefaultTableModel tableModel = new DefaultTableModel(tableHeader, 0) {
        @Override public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    
    Map<Pair<Integer, String>, List<Sentence>> chapters;
    
    public RightPanel(BottomPanel parent) {
        this.parent = parent;
        this.chapters = parent.parent.getChapters();
        
//        content.setParent(this);
        currentVisualType = VisualType.PLEASANTNESS;
    
        mainPanel = new JPanel();
        mainPanel.setBackground(Utils.GRAY3);
        mainPanel.setLayout(new BorderLayout());
    
        
        // NORTH
        JLabel title = new JLabel(" Selected sentence");
        title.setPreferredSize(new Dimension(300, 27));
        title.setOpaque(true);
        title.setBackground(Utils.TITLE_BACKGROUND);
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
        
        clearSentencesBtn = new JButton("Clear sentences");
        clearSentencesBtn.setFont(Utils.getButtonFont(14));
        clearSentencesBtn.addActionListener(a -> onClearSentencesBtnPress());
        addAllBtn = new JButton("Add all to table");
        addAllBtn.setFont(Utils.getButtonFont(14));
        addAllBtn.addActionListener(a -> onAddAllBtnPress());
        clearTableBtn = new JButton("Clear table");
        clearTableBtn.addActionListener(a -> onClearTableBtnPress());
        clearTableBtn.setFont(Utils.getButtonFont(14));
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
        title2.setBackground(Utils.TITLE_BACKGROUND);
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
        table.setFont(Utils.getFont(13));
        table.getTableHeader().setFont(Utils.getTableHeaderFont(14));
        table.setAutoCreateRowSorter(true);
        
        // custom column renderers
        TableColumn col = table.getColumnModel().getColumn(columnIndexMap.get(AbsWord.MapKey.PLEASANTNESS));
        col.setCellRenderer(new CustomRenderer());
        
        // sorter
        TableRowSorter<DefaultTableModel> tabSorter = new TableRowSorter<>(tableModel);
        tabSorter.setComparator(columnIndexMap.get(AbsWord.MapKey.PLEASANTNESS), Comparator.comparingDouble(x -> parseStringToDouble(x.toString())));
        tabSorter.setComparator(columnIndexMap.get(AbsWord.MapKey.ACTIVATION), Comparator.comparingDouble(x -> parseStringToDouble(x.toString())));
        tabSorter.setComparator(columnIndexMap.get(AbsWord.MapKey.IMAGERY), Comparator.comparingDouble(x -> parseStringToDouble(x.toString())));
        table.setRowSorter(tabSorter);
        
        statsPanel.add(new JScrollPane(table), BorderLayout.CENTER);
        
        
        this.setViewportView(mainPanel);
    
        this.getVerticalScrollBar().setUnitIncrement(16);
        this.getHorizontalScrollBar().setUnitIncrement(16);
        this.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        this.setHorizontalScrollBar(null);
    
        this.addComponentListener(new ComponentListener() {
            RightPanel self = RightPanel.this;
            @Override public void componentResized(ComponentEvent e) {
                if (sentencesPanel == null) return;
                
                sentencesPanel.setPreferredSize(new Dimension(self.getSize().width-20, sentencesPanel.getLayout().preferredLayoutSize(sentencesPanel).height));
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
        allSelectedSentences.forEach(s -> appendWordsToTable(s.getSentence().getWords()));
    }
    
    public void onRemoveSentencePress(SentenceRowPanel sentencePanel) {
        boolean removed = allSelectedSentences.removeIf(s -> {
            if (s == sentencePanel.sentenceLabel) {
                s.onUnselect();
                return true;
            }
            return false;
        });
        if (!removed) throw new RuntimeException("Not removed! Couldn't find sentence:\n" + sentencePanel.sentenceLabel.sentence);
        
        // returns void, don't know how else to check
        int beforeRemoving = sentencesPanel.getComponents().length;
        sentencesPanel.remove(sentencePanel);
        int afterRemoving = sentencesPanel.getComponents().length;
        // should be one less exactly
        if ((beforeRemoving  - afterRemoving) != 1) throw new RuntimeException("Not removed! Couldn't find " + SentenceRowPanel.class.getSimpleName() + ".");
        
        sentencesPanel.setPreferredSize(new Dimension(sentencesPanel.getSize().width, sentencesPanel.getLayout().preferredLayoutSize(sentencesPanel).height));
        sentencesPanel.revalidate();
        sentencesPanel.doLayout();
        sentencesPanel.repaint();
    }
    
    private void onClearSentencesBtnPress() {
        System.out.println("Clear sentences pressed");
        allSelectedSentences.forEach(SentenceLabel::onUnselect);
        allSelectedSentences.clear();
        sentencesPanel.setPreferredSize(new Dimension(sentencesPanel.getSize().width, 0));
        sentencesPanel.removeAll();
        sentencesPanel.revalidate();
        sentencesPanel.doLayout();
        sentencesPanel.repaint();
    }
    
    public boolean removeSentence(SentenceLabel sentence) {
        Component sentenceRowToRemove = Arrays.stream(sentencesPanel.getComponents())
                .map(jpnl -> (SentenceRowPanel) jpnl)
                .filter(srpnl -> srpnl.representsSentenceLabel(sentence))
                .findFirst()
                .orElseThrow(() -> { throw new RuntimeException("Cannot find sentence label in sentencesPanel, " + sentence); });
        
        boolean removedFromList = allSelectedSentences.remove(sentence);
        sentencesPanel.remove(sentenceRowToRemove); // does not return boolean to indicate if anything was removed...
        
        // re-calc layouts and repaint
        sentencesPanel.setPreferredSize(new Dimension(this.getSize().width-20, sentencesPanel.getLayout().preferredLayoutSize(sentencesPanel).height));
        sentencesPanel.setMaximumSize(sentencesPanel.getPreferredSize());
        sentencesPanel.setMinimumSize(sentencesPanel.getPreferredSize());
        sentencesPanel.revalidate();
        sentencesPanel.doLayout();
        sentencesPanel.repaint();
        
        return removedFromList;
    }
    
    public void onSentenceClick(SentenceLabel clickedSentence) {
        allSelectedSentences.add(clickedSentence);
    
        System.out.println("sentence added");
        
        // TODO
        // move this stuff to sentenceRowPnl class
        SentenceRowPanel mainSentencePanel = new SentenceRowPanel(sentencesPanel, clickedSentence, currentVisualType);
        mainSentencePanel.setRightPanel(this);
//        mainSentencePanel.setLayout(new BorderLayout());
        mainSentencePanel.setBackground(Utils.GRAY3);
        mainSentencePanel.setBorder(BorderFactory.createMatteBorder(1,1,1,1,Utils.GRAY3));
        mainSentencePanel.addMouseListener(new MouseListener() {
            @Override public void mouseClicked(MouseEvent e) {
                // all words are clicked
                onWordsClick(clickedSentence.getSentence().getWords());
            }
            @Override public void mouseEntered(MouseEvent e) {
                mainSentencePanel.setBackground(Color.white);
                mainSentencePanel.setBorder(BorderFactory.createMatteBorder(1,1,1,1,Utils.GRAY));
                mainSentencePanel.repaint();
            }
            @Override public void mouseExited(MouseEvent e) {
                mainSentencePanel.setBorder(BorderFactory.createMatteBorder(1,1,1,1,Utils.GRAY3));
                mainSentencePanel.setBackground(Utils.GRAY3);
                mainSentencePanel.repaint();
            }
            @Override public void mousePressed(MouseEvent e) { }
            @Override public void mouseReleased(MouseEvent e) { }
        });
        
        sentencesPanel.add(mainSentencePanel);
        
        // some resizing
        // -20 for scrollbar, +1 so border is visible (or else it just barely gets cut-off on last sentence for some reason)
        sentencesPanel.setPreferredSize(new Dimension(this.getSize().width-20, sentencesPanel.getLayout().preferredLayoutSize(sentencesPanel).height+1));
        sentencesPanel.setMaximumSize(sentencesPanel.getPreferredSize());
        sentencesPanel.setMinimumSize(sentencesPanel.getPreferredSize());
        sentencesPanel.revalidate();
        sentencesPanel.doLayout();
        
        // need to call this otherwise this components doesn't get updated
        // immediately, but only after resize happens
        this.parent.updateUI();
    }
    
    public void onWordsClick(List<AbsWord> words) {
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
    
    public void onVisualTypeChange(VisualType visualType) {
        currentVisualType = visualType;
        
        // TODO
        // This is kind of disgusting (casting)
        // Could do: custom classes that extend JPanels
        // and keep track of its components, which could be
        // retrieved by a simple class method.
        // That way, there is no need for JPanel.getComponents()
        // and casting.
        // Same applies to other Panels.onVisualTypeChange, where i
        // do the same shit as here.
        Arrays.stream(sentencesPanel.getComponents()).forEach(sc -> {
            if (sc instanceof SentenceRowPanel srp) {
                srp.onVisualTypeChange(visualType);
//                Arrays.stream(srp.getComponents()).forEach(wc -> {
//                    if (wc instanceof WordLabel wrdlbl) {
//                        wrdlbl.onVisualTypeChange(visualType);
//                    }
//                });
            }
        });
    }
    
    static class CustomRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            if (value instanceof String s) {
                double pleasantness = parseStringToDouble(s);
                setForeground(AbsMeasurableWord.isPositivePleasantness(pleasantness) ?
                        Utils.GREEN : AbsMeasurableWord.isNeutralPleasantness(pleasantness) ?
                            Color.DARK_GRAY : Utils.RED);
            }
            return c;
        }
    }
    
    // What a fucking mess
    // MeasAbsWord.DecimalFormat formats negative words with strange "-"
    // sign, that can't be reverse parsed from string (encoding problem) ???
    public static double parseStringToDouble(String s) {
        if (!NumberUtils.isNumber(s.charAt(0)+"")) {
            s = "-" + s.substring(1);
        }
        return Double.parseDouble(s);
    }
    
    public void onSentenceHover(List<SentenceLabel> hoveredSentences) { }
}
