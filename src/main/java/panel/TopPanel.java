package panel;

import SentenceLabel.SentenceSizeType;
import main.*;
import panel.tabs.TabsPanel;

import javax.swing.*;
import java.awt.*;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class TopPanel extends JPanel {
    
    public MainPanel parent;
    
    // BorderLayout.CENTER
    TabsPanel tabsPanel; // left
    ChaptersPanel chaptersPanel; // right
    JSplitPane splitPane;
    
    // BorderLayout.TOP
    JPanel controlPanel;
        JPanel importFilePanel;
            JTextField importField;
            JButton importBtn;
        JPanel sentenceWidthSliderPanel;
            JSlider sentenceWidthSlider;
        JPanel sentenceHeightSliderPanel;
            JSlider sentenceHeightSlider;
        JPanel sliderSliderPanel;
            JSlider sliderSizeSlider;
        JPanel chapterTypePanel;
            JComboBox<ChapterType> chapterTypeDropdown;
        JPanel sentenceLabelVisualType;
            JComboBox<SentenceLabelVisualType> sentenceLabelVisualTypeDropdown;
        JPanel sentenceSizeType;
            JComboBox<SentenceSizeType> sentenceSizeTypeDropdown;
    
    
    
    Map<Pair<Integer, String>, List<Sentence>> chapters;
    
    public TopPanel(MainPanel parent) {
        this.parent = parent;
        this.chapters = parent.getChapters();
        
        controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        
        initImportField();
        initSentenceWidthSlider();
        initSentenceHeightSlider();
        initSliderSizeSlider();
        initChapterTypePanel();
        initSentenceLabelVisualTypePanel();
        initSentenceSizeTypePanel();
        
        chaptersPanel = new ChaptersPanel(this);
        tabsPanel = new TabsPanel(this);
        
        splitPane = new JSplitPane();
        splitPane.setLeftComponent(tabsPanel);
        splitPane.setRightComponent(chaptersPanel);
        splitPane.setDividerLocation(330);
        
        this.setLayout(new BorderLayout());
        this.add(controlPanel, BorderLayout.NORTH);
        //this.add(chaptersPanel, BorderLayout.CENTER);
        this.add(splitPane, BorderLayout.CENTER);
    }
    
    private void initSentenceHeightSlider() {
        sentenceHeightSliderPanel = new JPanel();
        sentenceHeightSliderPanel.setLayout(new BorderLayout());
    
        JLabel title = new JLabel("Size of sentences");
        title.setFont(Utils.getFont(12));
        //Font defaultFont = UIManager.getDefaults().getFont("Label.font");
        //Font boldFont = new Font(defaultFont.getName(), Font.BOLD,defaultFont.getSize());
        //title.setFont(defaultFont);
    
        int sliderMin = Utils.SENTENCE_HEIGHT_RANGE.getMinimum(), sliderMax = Utils.SENTENCE_HEIGHT_RANGE.getMaximum();
        sentenceHeightSlider = new JSlider(sliderMin, sliderMax, Utils.SENTENCE_SIZE.height);
        Hashtable<Integer, JLabel> sliderMap = new Hashtable<>();
        //Font lblFont = new Font("Calibri", Font.BOLD, 12);
        JLabel minLbl = new JLabel(sliderMin+""); //minLbl.setFont(lblFont);
        JLabel maxLbl = new JLabel(sliderMax+""); //maxLbl.setFont(lblFont);
        sliderMap.put(sliderMin, minLbl);
        sliderMap.put(sliderMax, maxLbl);
        sentenceHeightSlider.setLabelTable(sliderMap);
        sentenceHeightSlider.setMajorTickSpacing(5);
        sentenceHeightSlider.setPaintTicks(true);
        sentenceHeightSlider.setPaintLabels(true);
        sentenceHeightSlider.setPreferredSize(new Dimension(100, 40));
        sentenceHeightSlider.setFont(Utils.getFont(12));
        sentenceHeightSlider.setEnabled(true);
        sentenceHeightSlider.addChangeListener(c -> onSentenceSizeChange(sentenceWidthSlider.getValue(), sentenceHeightSlider.getValue()));
        
        sentenceHeightSliderPanel.add(title, BorderLayout.NORTH);
        sentenceHeightSliderPanel.add(sentenceHeightSlider, BorderLayout.CENTER);
        controlPanel.add(sentenceHeightSliderPanel);
        
        //JPanel spacer = new JPanel();
        //spacer.setPreferredSize(new Dimension(30, 30));
        //spacer.setOpaque(false);
        //controlPanel.add(spacer);
    }
    
    private void initSentenceWidthSlider() {
        //JPanel sentenceWidthSliderPanel;
        //JSlider sentenceWidthSlider;
        sentenceWidthSliderPanel = new JPanel();
        sentenceWidthSliderPanel.setLayout(new BorderLayout());
    
        JLabel title = new JLabel("Size of sentences");
        title.setFont(Utils.getFont(12));
        //Font defaultFont = UIManager.getDefaults().getFont("Label.font");
        //Font boldFont = new Font(defaultFont.getName(), Font.BOLD,defaultFont.getSize());
        //title.setFont(defaultFont);
        
        int sliderMin = Utils.SENTENCE_WIDTH_RANGE.getMinimum(), sliderMax = Utils.SENTENCE_WIDTH_RANGE.getMaximum();
        sentenceWidthSlider = new JSlider(sliderMin, sliderMax, Utils.SENTENCE_SIZE.width);
        Hashtable<Integer, JLabel> sliderMap = new Hashtable<>();
        //Font lblFont = new Font("Calibri", Font.BOLD, 12);
        JLabel minLbl = new JLabel(sliderMin+""); //minLbl.setFont(lblFont);
        JLabel maxLbl = new JLabel(sliderMax+""); //maxLbl.setFont(lblFont);
        sliderMap.put(sliderMin, minLbl);
        sliderMap.put(sliderMax, maxLbl);
        sentenceWidthSlider.setLabelTable(sliderMap);
        sentenceWidthSlider.setMajorTickSpacing(5);
        sentenceWidthSlider.setPaintTicks(true);
        sentenceWidthSlider.setPaintLabels(true);
        sentenceWidthSlider.setPreferredSize(new Dimension(100, 40));
        sentenceWidthSlider.setFont(Utils.getFont(12));
        sentenceWidthSlider.setEnabled(true);
        sentenceWidthSlider.addChangeListener(c -> onSentenceSizeChange(sentenceWidthSlider.getValue(), sentenceHeightSlider.getValue()));
        
        sentenceWidthSliderPanel.add(title, BorderLayout.NORTH);
        sentenceWidthSliderPanel.add(sentenceWidthSlider, BorderLayout.CENTER);
        controlPanel.add(sentenceWidthSliderPanel);
    }
    
    private void initSentenceSizeTypePanel() {
        JLabel title = new JLabel("Sentence label visual type:");
        title.setFont(Utils.getFont(12));
        
        sentenceSizeType = new JPanel();
        sentenceSizeType.setLayout(new BorderLayout());
        
        sentenceSizeTypeDropdown = new JComboBox<>(SentenceSizeType.values());
        sentenceSizeTypeDropdown.addActionListener(a -> chaptersPanel.onSentenceSizeTypeChange((SentenceSizeType) sentenceSizeTypeDropdown.getSelectedItem()));
        
        sentenceSizeType.add(title, BorderLayout.NORTH);
        sentenceSizeType.add(sentenceSizeTypeDropdown, BorderLayout.CENTER);
        
        controlPanel.add(sentenceSizeType);
    }
    
    private void initSentenceLabelVisualTypePanel() {
        JLabel title = new JLabel("Sentence label visual type:");
        title.setFont(Utils.getFont(12));
    
        sentenceLabelVisualType = new JPanel();
        sentenceLabelVisualType.setLayout(new BorderLayout());
    
        sentenceLabelVisualTypeDropdown = new JComboBox<>(SentenceLabelVisualType.values());
        sentenceLabelVisualTypeDropdown.addActionListener(a -> chaptersPanel.onSentenceLabelVisualTypeChange((SentenceLabelVisualType) sentenceLabelVisualTypeDropdown.getSelectedItem()));
    
        sentenceLabelVisualType.add(title, BorderLayout.NORTH);
        sentenceLabelVisualType.add(sentenceLabelVisualTypeDropdown, BorderLayout.CENTER);
    
        controlPanel.add(sentenceLabelVisualType);
    }
    
    private void initChapterTypePanel() {
        JLabel title = new JLabel("Chapter orientation:");
        title.setFont(Utils.getFont(12));
        
        chapterTypePanel = new JPanel();
        chapterTypePanel.setLayout(new BorderLayout());
        
        chapterTypeDropdown = new JComboBox<>(ChapterType.values());
        chapterTypeDropdown.addActionListener(a -> chaptersPanel.onChapterTypeChange((ChapterType) chapterTypeDropdown.getSelectedItem()));
        
        chapterTypePanel.add(title, BorderLayout.NORTH);
        chapterTypePanel.add(chapterTypeDropdown, BorderLayout.CENTER);
        
        controlPanel.add(chapterTypePanel);
    }
    
    private void initImportField() {
        JLabel title = new JLabel("Import text from file: ");
        title.setFont(Utils.getFont(12));
        
        importField = new JTextField("./texts/SherlockHolmes-AStudyInScarlet.txt");
    
        importFilePanel = new JPanel();
        importFilePanel.setLayout(new BorderLayout());
        
        importBtn = new JButton("Import");
        importBtn.addActionListener(a -> {
            parent.getMainWindow().onNewTextImport(importField);
        });
        
        importFilePanel.add(title, BorderLayout.NORTH);
        importFilePanel.add(importField, BorderLayout.CENTER);
        importFilePanel.add(importBtn, BorderLayout.SOUTH);
        
        controlPanel.add(importFilePanel);
        
        JPanel spacer = new JPanel();
        spacer.setPreferredSize(new Dimension(30, 30));
        spacer.setOpaque(false);
        controlPanel.add(spacer);
    }
    
    private void initSliderSizeSlider() {
        sliderSliderPanel = new JPanel();
        sliderSliderPanel.setLayout(new BorderLayout());
        
        JLabel title = new JLabel("Size of slider");
        title.setFont(Utils.getFont(12));
        
        int sliderMin = 80, sliderMax = 200;
        sliderSizeSlider = new JSlider(sliderMin, sliderMax, Utils.INITIAL_SLIDER_WIDTH);
        Hashtable<Integer, JLabel> sliderMap = new Hashtable<>();
        //Font lblFont = new Font("Calibri", Font.BOLD, 12);
        JLabel minLbl = new JLabel(sliderMin+""); //minLbl.setFont(lblFont);
        JLabel maxLbl = new JLabel(sliderMax+""); //maxLbl.setFont(lblFont);
        sliderMap.put(sliderMin, minLbl);
        sliderMap.put(sliderMax, maxLbl);
        sliderSizeSlider.setLabelTable(sliderMap);
        sliderSizeSlider.setMajorTickSpacing(40);
        sliderSizeSlider.setPaintTicks(true);
        sliderSizeSlider.setPaintLabels(true);
        sliderSizeSlider.setPreferredSize(new Dimension(100, 40));
        sliderSizeSlider.setFont(Utils.getFont(12));
        sliderSizeSlider.setEnabled(true);
        sliderSizeSlider.addChangeListener(c -> onSliderSizeChange());
    
        sliderSliderPanel.add(title, BorderLayout.NORTH);
        sliderSliderPanel.add(sliderSizeSlider, BorderLayout.CENTER);
        controlPanel.add(sliderSliderPanel);
    
        JPanel spacer = new JPanel();
        spacer.setPreferredSize(new Dimension(30, 30));
        spacer.setOpaque(false);
        controlPanel.add(spacer);
    }
    
    private void onSliderSizeChange() {
        chaptersPanel.onSliderSizeChange(sliderSizeSlider.getValue());
    }
    
    private void onSentenceSizeChange(int newWidth, int newHeight) {
        chaptersPanel.onSentenceSizeChange(newWidth, newHeight);
    }
    
    
    public void onNewTextImport(Map<Pair<Integer, String>, List<Sentence>> processedChapters) {
        this.chapters = processedChapters;
        chaptersPanel.onNewTextImport(processedChapters);
    }
    
    public ChaptersPanel getChaptersPanel() { return this.chaptersPanel; }
}
