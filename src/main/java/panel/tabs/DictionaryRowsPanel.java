package panel.tabs;

import com.formdev.flatlaf.ui.FlatRoundBorder;
import main.UserDictionary.UserDictionary;
import main.UserDictionary.Word;
import main.Utils;
import panel.ColorChooserPanel;
import panel.ColorRef;
import panel.VerticalFlowLayout;
import panel.WordLabel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

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
                JPanel wordPanel = getWordPanel(word, groupPanel);
                groupPanel.add(wordPanel);
            });
            
            // list the rest of the words
            wordGroup.getWords().forEach(word -> {
                JPanel wordPanel = getWordPanel(word, groupPanel);
                groupPanel.add(wordPanel);
            });
            
            this.add(groupPanel);
        });
    }
    
    private JPanel getWordPanel(Word w, JPanel parent) {
        Color panelInnerBg;
        var panel = new JPanel() {
            public Color bg = getBackground();
            final int arc = UIManager.getInt("Component.arc");
            // needed to get rid of corners of panel, when it has a round border, because of highlighting on mouse hover
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D gr = (Graphics2D) g;
                gr.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                gr.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                
                gr.setColor(getBackground());
                gr.fillRect(0, 0 , getWidth(), getHeight());
                
                gr.setPaint(bg);
                gr.fillRoundRect(1, 1, getWidth()-2, getHeight()-2, arc, arc);
            }
        };
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));
        panel.setOpaque(true);
        panel.setBorder(new FlatRoundBorder());
    
        final Color normalClr = panel.getBackground();
        final Color hoveredClr = panel.getBackground().brighter();
        
        JLabel wordLbl = new JLabel(w.getSourceText()); // TODO
        
        
        
        // SELECTED COLOR CIRCLE
        ColorRef clrRef = new ColorRef(panel, w.color);
        
        JPanel selectedColorPanel = new JPanel(){
            @Override public void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D gr = (Graphics2D) g;
                gr.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                gr.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                gr.setPaint(clrRef.circleColor);
                gr.fillOval(1, 1, getWidth()-2, getHeight()-2);
                // border
                gr.setColor(Color.lightGray);
                gr.drawOval(0, 0, getWidth()-1, getHeight()-1);
            }};
        selectedColorPanel.setPreferredSize(new Dimension(30, 30));
        clrRef.setPanelToRepaint(selectedColorPanel);
        
        
        
        ColorChooserPanel colorChooser = new ColorChooserPanel(w.color, new Dimension(200, 150));
        colorChooser.setColorChangeReference(clrRef);
        colorChooser.setBackground2(normalClr);
        colorChooser.setBorderArc(UIManager.getInt("Component.arc"));
        colorChooser.initColorPanel();
        
        panel.addMouseListener(new MouseListener() {
            @Override public void mouseClicked(MouseEvent e) {
                panel.add(colorChooser);
                // this ensured correct panel resizing
                panel.revalidate();
                parent.revalidate();
                panel.doLayout();
                parent.doLayout();
                panel.repaint();
                parent.repaint();
            }
            @Override public void mouseEntered(MouseEvent e) {
                //panel.setBackground(hoveredClr);
                panel.bg = hoveredClr;
                panel.repaint();
                selectedColorPanel.setBackground(hoveredClr);
                selectedColorPanel.repaint();
                colorChooser.setBackground(hoveredClr);
                colorChooser.repaint();
            }
            @Override public void mouseExited(MouseEvent e) {
                //panel.setBackground(normalClr);
                panel.bg = normalClr;
                panel.repaint();
                selectedColorPanel.setBackground(normalClr);
                selectedColorPanel.repaint();
                colorChooser.setBackground(normalClr);
                colorChooser.repaint();
            }
            @Override public void mousePressed(MouseEvent e) { }
            @Override public void mouseReleased(MouseEvent e) { }
        });
        
        panel.add(wordLbl);
        panel.add(selectedColorPanel);
        return panel;
    }
    
}
