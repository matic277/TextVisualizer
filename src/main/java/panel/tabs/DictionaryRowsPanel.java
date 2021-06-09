package panel.tabs;

import com.formdev.flatlaf.ui.FlatRoundBorder;
import main.UserDictionary.UserDictionary;
import main.UserDictionary.Word;
import main.Utils;
import panel.VerticalFlowLayout;
import panel.WordLabel;

import javax.swing.*;
import java.awt.*;

public class DictionaryRowsPanel extends JPanel {
    
    JPanel parent;
    UserDictionary dictionary;
    
    public DictionaryRowsPanel(JPanel parent, UserDictionary dictionary) {
        this.parent = parent;
        this.dictionary = dictionary;
        
        this.setLayout(new VerticalFlowLayout(VerticalFlowLayout.LEFT, VerticalFlowLayout.TOP));
        
        initGroups();
    }
    
    private void initGroups() {
        final int arc = UIManager.getInt("Component.arc");
        dictionary.groupMap.forEach((groupName, wordGroup) -> {
            JPanel groupPanel = new JPanel();
            //{
            //    final int arc = UIManager.getInt("Component.arc");
            //    final Color bg = wordGroup.getDefaultColor();
            //    @Override public void paintComponent(Graphics g) {
            //        Graphics2D gr = (Graphics2D) g;
            //        gr.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            //        gr.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            //        gr.setColor(bg);
            //        gr.fillRoundRect(0, 0, getWidth(), getHeight(), arc, arc);
            //    }
            //};
            groupPanel.setLayout(new VerticalFlowLayout(VerticalFlowLayout.LEFT, VerticalFlowLayout.TOP));
            //groupPanel.setBorder(new FlatRoundBorder());
            groupPanel.setBorder(new Utils.RoundBorder( wordGroup.getDefaultColor(), null, new BasicStroke(2), arc));
    
            
            JLabel groupNameLbl = new JLabel(groupName);
            groupNameLbl.setBorder(new FlatRoundBorder());
            
            groupPanel.add(groupNameLbl);
            
            // first list words with custom colors
            wordGroup.getWordsWithSpecificColors().forEach((word, color) -> {
                JPanel wordPanel = getWordPanel(word);
                groupPanel.add(wordPanel);
            });
            
            // list the rest of the words
            wordGroup.getWords().forEach(word -> {
                JPanel wordPanel = getWordPanel(word);
                groupPanel.add(wordPanel);
            });
            
            this.add(groupPanel);
        });
    }
    
    private JPanel getWordPanel(Word w) {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));
        
        JLabel wordLbl = new JLabel(w.getSourceText()); // TODO
        
        // TODO add color circle
        JLabel colorInfo = new JLabel(Utils.colorToString(w.color));
        colorInfo.setOpaque(true);
        colorInfo.setBackground(w.color);
    
        panel.add(wordLbl);
        panel.add(colorInfo);
        
        return panel;
    }
    
}
