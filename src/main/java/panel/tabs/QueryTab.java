package panel.tabs;

import panel.VerticalFlowLayout2;
import panel.tabs.TabsPanel;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.atomic.AtomicLong;

public class QueryTab extends JPanel {
    
    TabsPanel parent;
    
    // TOP
    JPanel searchPanel;
        JLabel title; // WEST
        JTextField searchBar; // CENTER
    // CENTER
    JPanel mainPanel;
        JLabel title2; // NORTH
        JPanel statsPanel; // CENTER
            JLabel wordOccurrence;
            JLabel sentenceOccurrence;
    
    public QueryTab(TabsPanel parent) {
        this.parent = parent;
        
        //this.setBackground(Color.pink);
        this.setLayout(new BorderLayout());
    
        initSearchpanel();
        initMainPanel();
    }
    
    private void initMainPanel() {
        mainPanel = new JPanel();
        //mainPanel.setBackground(Color.white);
        mainPanel.setLayout(new BorderLayout());
        
        title = new JLabel("     Statistics: ");
        title.setPreferredSize(new Dimension(70, 30));
        //title.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, (ColorUIResource)UIManager.getLookAndFeel().getDefaults().get("TabbedPane.darkShadow")));
        
        System.out.println(UIManager.getLookAndFeel().getDefaults().get("TabbedPane.shadow"));
        
        statsPanel = new JPanel();
        statsPanel.setLayout(new VerticalFlowLayout2(VerticalFlowLayout2.LEFT, VerticalFlowLayout2.TOP, 0, 10));
        
        wordOccurrence = new JLabel("     Number of occurences: ");
        sentenceOccurrence = new JLabel("     Number of sentence occurrence: ");
        statsPanel.add(wordOccurrence);
        statsPanel.add(sentenceOccurrence);
        
        
        mainPanel.add(title, BorderLayout.NORTH);
        mainPanel.add(statsPanel, BorderLayout.CENTER);
        this.add(mainPanel, BorderLayout.CENTER);
    }
    
    private void initSearchpanel() {
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new BorderLayout());
        
        title = new JLabel("     Search :");
        title.setPreferredSize(new Dimension(70, 30));
        
        searchBar = new JTextField();
        searchBar.addActionListener(a -> {
            // potential synchronization issues if searching in fast succession?....
            wordOccurrenceCounter.set(0);
            sentenceOccurrenceCounter.set(0);
            parent.chaptersPanel.onWordSearch(searchBar.getText().toLowerCase(), this);
            updateStatistics();
        });
        
        searchPanel.add(getDummySpacer(10,10), BorderLayout.NORTH);
        searchPanel.add(title, BorderLayout.WEST);
        searchPanel.add(searchBar, BorderLayout.CENTER);
        
        this.add(searchPanel, BorderLayout.NORTH);
    }
    
    private void updateStatistics() {
        wordOccurrence.setText("     Number of word occurrence: " + wordOccurrenceCounter.get());
        sentenceOccurrence.setText("     Number of sentence occurrence: " + sentenceOccurrenceCounter.get());
    }
    
    private final AtomicLong wordOccurrenceCounter = new AtomicLong(0);
    private final AtomicLong sentenceOccurrenceCounter = new AtomicLong(0);
    
    public synchronized void incrementWordFoundOccurrence(long byAmmount) {
        wordOccurrenceCounter.set(wordOccurrenceCounter.get() + byAmmount);
    }
    
    public void incrementWordInSentenceFoundOccurrence() {
        sentenceOccurrenceCounter.incrementAndGet();
    }
    
    private JLabel getDummySpacer(int width, int height) {
        JLabel lbl = new JLabel();
        lbl.setPreferredSize(new Dimension(width, height));
        lbl.setMaximumSize(new Dimension(width, height));
        lbl.setMinimumSize(new Dimension(width, height));
        lbl.setOpaque(false);
        return lbl;
    }
}
