package panel.tabs;

import com.formdev.flatlaf.ui.FlatRoundBorder;
import main.UserDictionary.UserDictionary;
import main.UserDictionary.Word;
import main.UserDictionary.WordGroup;
import main.Utils;
import panel.*;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

public class DictionaryRowsPanel extends JPanel {
    
    JPanel parent;
    public UpdateColorsSubtab tabParent;
    UserDictionary dictionary;
    
    List<DictionaryGroupRowPanel> groupPanels;
    
    public DictionaryRowsPanel(JPanel parent, UserDictionary dictionary, UpdateColorsSubtab tabParent) {
        this.parent = parent;
        this.dictionary = dictionary;
        this.tabParent = tabParent;
        
        this.setLayout(new VerticalFlowLayout(VerticalFlowLayout.LEFT, VerticalFlowLayout.TOP));
        
        groupPanels = new ArrayList<>(dictionary.groupMap.size());
        
        initGroups();
    }
    
    private void initGroups() {
        dictionary.groupMap.forEach((groupName, wordGroup) -> {
            DictionaryGroupRowPanel groupPanel = new DictionaryGroupRowPanel(this, wordGroup);
            groupPanels.add(groupPanel);
            this.add(groupPanel);
        });
    }
    
}
