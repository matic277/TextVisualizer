package panel.tabs;

import com.formdev.flatlaf.ui.FlatRoundBorder;
import dictionary.UserDictionaryCollection;
import main.UserDictionary.UserDictionary;
import panel.VerticalFlowLayout;

import javax.swing.*;
import java.awt.*;

public class UpdateColorsSubtab extends JScrollPane {
    
    DictionaryTab parent;
    
    UserDictionaryCollection dictCollection = UserDictionaryCollection.get();
    
    JPanel mainPanel;
        JPanel dictDropdownPanel; // TOP
            JLabel dropdownInfo;
            JComboBox<String> dictDropdown;
        DictionaryRowsPanel rowsPanel;
    
    public UpdateColorsSubtab(DictionaryTab parent) {
        this.parent = parent;
        
        mainPanel = new JPanel();
        mainPanel.setLayout(new VerticalFlowLayout(VerticalFlowLayout.LEFT, VerticalFlowLayout.TOP));
        
        initDictDropdownPanel();
        //initDictRowsPanel();
        
        this.setViewportView(mainPanel);
        this.getVerticalScrollBar().setUnitIncrement(16);
    }
    
    private void initDictRowsPanel(UserDictionary selectedDict) {
        if (rowsPanel != null) mainPanel.remove(rowsPanel);
        
        rowsPanel = new DictionaryRowsPanel(mainPanel, selectedDict);
        mainPanel.add(rowsPanel);
        mainPanel.repaint();
    }
    
    private void initDictDropdownPanel() {
        dropdownInfo = new JLabel("  Select dictionary: ");
        
        dictDropdown = new JComboBox<>();
        dictDropdown.addActionListener(a -> onNewDictionarySelected((String)dictDropdown.getSelectedItem()));
        
        dictDropdownPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        dictDropdownPanel.add(dropdownInfo);
        dictDropdownPanel.add(dictDropdown);
        
        dictDropdownPanel.setBorder(new FlatRoundBorder());
        
        mainPanel.add(dictDropdownPanel);
    }
    
    private void onNewDictionarySelected(String dictName) {
        initDictRowsPanel(dictCollection.getDictionary(dictName));
    }
    
    public void onNewDictionaryAdded() {
        String[] dictNames = dictCollection.getDictionaryNames().toArray(String[]::new);
        dictDropdown.setModel(new DefaultComboBoxModel<>(dictNames));
    }
}
