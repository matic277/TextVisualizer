package panel.tabs;

import main.UserDictionary.UserDictionary;
import main.Utils;
import main.fileParsers.DictionaryReader;
import panel.WrapLayout;

import javax.swing.*;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.GroupLayout.SequentialGroup;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.List;
import java.util.stream.Collectors;

public class DictionaryTab extends JTabbedPane {
    
    public TabsPanel parent;
    
    //JPanel mainPanel;
    //    JPanel InputPanel; // NORTH
    //    JPanel dictionaryPanel; // CENTER
    //        JLabel dictionaryPanelTitle; // NORTH
    //        JScrollPane dictionaryPane;
    //            JTextArea dictionaryText; // CENTER
    
    public DictionaryImportAndEditSubtab dictSubtab;
    public UpdateColorsSubtab dictColorsSubtab;
    
    public DictionaryTab(TabsPanel parent) {
        this.parent = parent;
        
        
        this.setMinimumSize(new Dimension(0,0));
    
        dictSubtab = new DictionaryImportAndEditSubtab(this);
        dictColorsSubtab = new UpdateColorsSubtab(this);
        
        this.addTab("Import & Edit", dictSubtab);
        this.addTab("Change colors", dictColorsSubtab);
        
        //
        //this.setBorder(BorderFactory.createCompoundBorder(
        //        BorderFactory.createMatteBorder(0,1,1,0, new Color(0,0,0,0)),
        //        BorderFactory.createMatteBorder(0,1,1,1, Color.lightGray)));
    }
    
    
}
