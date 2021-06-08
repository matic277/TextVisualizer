package panel;

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
        JPanel sentenceSliderPanel;
            JSlider sentenceSizeSlider;
        JPanel sliderSliderPanel;
            JSlider sliderSizeSlider;
        JPanel dropdownPanel;
            JComboBox<VisualType> dropdownType;
        JPanel chapterTypePanel;
            JComboBox<ChapterType> chapterTypeDropdown;
        JPanel sentenceLabelVisualType;
            JComboBox<SentenceLabelVisualType> sentenceLabelVisualTypeDropdown;
    
    
    
    Map<Pair<Integer, String>, List<Sentence>> chapters;
    
    public TopPanel(MainPanel parent) {
        this.parent = parent;
        this.chapters = parent.getChapters();
        
        controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        
        initImportField();
        initSentenceSizeSlider();
        initSliderSizeSlider();
        initDropdownPanel();
        initChapterTypePanel();
        initSentenceLabelVisualTypePanel();
        
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
        importField.addActionListener(a -> parent.onVisualTypeChange((VisualType) dropdownType.getSelectedItem()));
    
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
    
    private void initDropdownPanel() {
        JLabel title = new JLabel("Select visual mapping: ");
        title.setFont(Utils.getFont(12));
        
        dropdownType = new JComboBox<>(VisualType.values());
        dropdownType.addActionListener(a -> parent.onVisualTypeChange((VisualType) dropdownType.getSelectedItem()));
        
        dropdownPanel = new JPanel();
        dropdownPanel.setLayout(new BorderLayout());
        
        dropdownPanel.add(title, BorderLayout.NORTH);
        dropdownPanel.add(dropdownType, BorderLayout.CENTER);
        
        controlPanel.add(dropdownPanel);
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
    
    private void initSentenceSizeSlider() {
        sentenceSliderPanel = new JPanel();
        sentenceSliderPanel.setLayout(new BorderLayout());
        
        JLabel title = new JLabel("Size of sentences");
        title.setFont(Utils.getFont(12));
        //Font defaultFont = UIManager.getDefaults().getFont("Label.font");
        //Font boldFont = new Font(defaultFont.getName(), Font.BOLD,defaultFont.getSize());
        //title.setFont(defaultFont);
        
        int sliderMin = 1, sliderMax = 16;
        sentenceSizeSlider = new JSlider(sliderMin, sliderMax, Utils.SENTENCE_SIZE.width);
        Hashtable<Integer, JLabel> sliderMap = new Hashtable<>();
        //Font lblFont = new Font("Calibri", Font.BOLD, 12);
        JLabel minLbl = new JLabel(sliderMin+""); //minLbl.setFont(lblFont);
        JLabel maxLbl = new JLabel(sliderMax+""); //maxLbl.setFont(lblFont);
        sliderMap.put(sliderMin, minLbl);
        sliderMap.put(sliderMax, maxLbl);
        sentenceSizeSlider.setLabelTable(sliderMap);
        sentenceSizeSlider.setMajorTickSpacing(5);
        sentenceSizeSlider.setPaintTicks(true);
        sentenceSizeSlider.setPaintLabels(true);
        sentenceSizeSlider.setPreferredSize(new Dimension(100, 40));
        sentenceSizeSlider.setFont(Utils.getFont(12));
        sentenceSizeSlider.setEnabled(true);
        sentenceSizeSlider.addChangeListener(c -> onSentenceSizeChange());
        
        sentenceSliderPanel.add(title, BorderLayout.NORTH);
        sentenceSliderPanel.add(sentenceSizeSlider, BorderLayout.CENTER);
        controlPanel.add(sentenceSliderPanel);
    
        JPanel spacer = new JPanel();
        spacer.setPreferredSize(new Dimension(30, 30));
        spacer.setOpaque(false);
        controlPanel.add(spacer);
    }
    
    private void onSliderSizeChange() {
        chaptersPanel.onSliderSizeChange(sliderSizeSlider.getValue());
    }
    
    private void onSentenceSizeChange() {
        int newSize = sentenceSizeSlider.getValue();
        chaptersPanel.onSentenceSizeChange(newSize);
    }
    
    public void onVisualTypeChange(VisualType selectedItem) {
        chaptersPanel.onVisualTypeChange(selectedItem);
    }
    
    public void onNewTextImport(Map<Pair<Integer, String>, List<Sentence>> processedChapters) {
        this.chapters = processedChapters;
        chaptersPanel.onNewTextImport(processedChapters);
    }
    
    public ChaptersPanel getChaptersPanel() { return this.chaptersPanel; }
}
