package panel;

import main.Pair;
import main.Sentence;
import main.Utils;
import main.VisualType;

import javax.swing.*;
import java.awt.*;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class TopPanel extends JPanel {
    
    MainPanel parent;
    
    JPanel controlPanel;
        JPanel sentenceSliderPanel;
            JSlider sentenceWidthSlider;
        JPanel sliderSliderPanel;
            JSlider sliderWidthSlider;
        JPanel dropdownPanel;
            JComboBox<VisualType> dropdownType;
    
    ChaptersPanel chaptersPanel;
    
    Map<Pair<Integer, String>, List<Sentence>> chapters;
    
    public TopPanel(MainPanel parent) {
        this.parent = parent;
        this.chapters = parent.getChapters();
        
        controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        
        initSentenceWindthSlider();
        initSliderWidthSlider();
        initDropdownPanel();
        
        chaptersPanel = new ChaptersPanel(this);
        
        this.setLayout(new BorderLayout());
        this.add(controlPanel, BorderLayout.NORTH);
        this.add(chaptersPanel, BorderLayout.CENTER);
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
    }
    
    private void initSliderWidthSlider() {
        sliderSliderPanel = new JPanel();
        sliderSliderPanel.setLayout(new BorderLayout());
        
        JLabel title = new JLabel("Width of slider");
        title.setFont(Utils.getFont(12));
        
        int sliderMin = 80, sliderMax = 200;
        sliderWidthSlider = new JSlider(sliderMin, sliderMax, Utils.INITIAL_SLIDER_WIDTH);
        Hashtable<Integer, JLabel> sliderMap = new Hashtable<>();
        Font lblFont = new Font("Calibri", Font.BOLD, 12);
        JLabel minLbl = new JLabel(sliderMin+""); minLbl.setFont(lblFont);
        JLabel maxLbl = new JLabel(sliderMax+""); maxLbl.setFont(lblFont);
        sliderMap.put(sliderMin, minLbl);
        sliderMap.put(sliderMax, maxLbl);
        sliderWidthSlider.setLabelTable(sliderMap);
        sliderWidthSlider.setMajorTickSpacing(40);
        sliderWidthSlider.setPaintTicks(true);
        sliderWidthSlider.setPaintLabels(true);
        sliderWidthSlider.setPreferredSize(new Dimension(100, 40));
        sliderWidthSlider.setFont(Utils.getFont(12));
        sliderWidthSlider.setEnabled(true);
        sliderWidthSlider.addChangeListener(c -> onSliderWidthChange());
    
        sliderSliderPanel.add(title, BorderLayout.NORTH);
        sliderSliderPanel.add(sliderWidthSlider, BorderLayout.CENTER);
        controlPanel.add(sliderSliderPanel);
    }
    
    private void initSentenceWindthSlider() {
        sentenceSliderPanel = new JPanel();
        sentenceSliderPanel.setLayout(new BorderLayout());
        
        JLabel title = new JLabel("Width of sentences");
        title.setFont(Utils.getFont(12));
        
        int sliderMin = 1, sliderMax = 16;
        sentenceWidthSlider = new JSlider(sliderMin, sliderMax, Utils.SENTENCE_SIZE.width);
        Hashtable<Integer, JLabel> sliderMap = new Hashtable<>();
        Font lblFont = new Font("Calibri", Font.BOLD, 12);
        JLabel minLbl = new JLabel(sliderMin+""); minLbl.setFont(lblFont);
        JLabel maxLbl = new JLabel(sliderMax+""); maxLbl.setFont(lblFont);
        sliderMap.put(sliderMin, minLbl);
        sliderMap.put(sliderMax, maxLbl);
        sentenceWidthSlider.setLabelTable(sliderMap);
        sentenceWidthSlider.setMajorTickSpacing(5);
        sentenceWidthSlider.setPaintTicks(true);
        sentenceWidthSlider.setPaintLabels(true);
        sentenceWidthSlider.setPreferredSize(new Dimension(100, 40));
        sentenceWidthSlider.setFont(Utils.getFont(12));
        sentenceWidthSlider.setEnabled(true);
        sentenceWidthSlider.addChangeListener(c -> onSentenceWidthChange());
    
        sentenceSliderPanel.add(title, BorderLayout.NORTH);
        sentenceSliderPanel.add(sentenceWidthSlider, BorderLayout.CENTER);
        controlPanel.add(sentenceSliderPanel);
    }
    
    private void onSliderWidthChange() {
        chaptersPanel.onSliderWidthChange(sliderWidthSlider.getValue());
    }
    
    private void onSentenceWidthChange() {
        int newWidth = sentenceWidthSlider.getValue();
        chaptersPanel.onSentenceWidthChange(newWidth);
    }
    
    public void onVisualTypeChange(VisualType selectedItem) {
        chaptersPanel.onVisualTypeChange(selectedItem);
    }
}
