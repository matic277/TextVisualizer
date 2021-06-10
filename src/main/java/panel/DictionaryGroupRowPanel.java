package panel;

import com.formdev.flatlaf.ui.FlatRoundBorder;
import main.UserDictionary.Word;
import main.UserDictionary.WordGroup;
import main.Utils;
import panel.tabs.DictionaryRowsPanel;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class DictionaryGroupRowPanel extends JPanel implements Consumer<Color> {
    
    public DictionaryRowsPanel parent;
    WordGroup group;
    
    JPanel groupTitlePanel;
    List<DictionaryWordRowPanel> wordPanels;
    
    final private static int arc = UIManager.getInt("Component.arc");
    boolean colorChooserVisible = false;
    
    public DictionaryGroupRowPanel(DictionaryRowsPanel parent, WordGroup group) {
        this.parent = parent;
        this.group = group;
        
        this.setLayout(new VerticalFlowLayout(VerticalFlowLayout.LEFT, VerticalFlowLayout.TOP));
        setBorder(group.getDefaultColor());
        
        this.wordPanels = new ArrayList<>(group.getWords().size());
        
        initTitle();
        initWords();
    }
    
    public void setBorder(Color newColor) {
        // border of this
        this.setBorder(new Utils.RoundBorder( newColor, null, new BasicStroke(3), arc));
    }
    
    private void initWords() {
        // list the rest of the words
        group.getWords().forEach(word -> {
            DictionaryWordRowPanel wordPanel = new DictionaryWordRowPanel(DictionaryGroupRowPanel.this, word);
            wordPanels.add(wordPanel);
            this.add(wordPanel);
        });
    }
    
    private void initTitle() {
        groupTitlePanel = getGroupTitlePanel();
        Border margin = new EmptyBorder(8,8,8,8);
        groupTitlePanel.setBorder(new CompoundBorder(new Utils.RoundBorder(group.getDefaultColor(), null, new BasicStroke(2), arc), margin));
        
        this.add(groupTitlePanel);
    }
    
    private JPanel getGroupTitlePanel() {
        JLabel groupNameLbl = new JLabel(" " + group.name);
        
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
        
        
        
        // SELECTED COLOR CIRCLE
        ColorRef clrRef = new ColorRef(panel, group.getDefaultColor());
        clrRef.setNewColorConsumer(this);
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
        
        
        
        ColorChooserPanel colorChooser = new ColorChooserPanel(group.getDefaultColor(), new Dimension(200, 150));
        colorChooser.setColorChangeReference(clrRef);
        colorChooser.setBackground2(normalClr);
        colorChooser.setBorderArc(UIManager.getInt("Component.arc"));
        colorChooser.initColorPanel();
        
        panel.addMouseListener(new MouseListener() {
            @Override public void mouseClicked(MouseEvent e) {
                if (colorChooserVisible) {
                    groupTitlePanel.remove(colorChooser);
                    colorChooserVisible = false;
                }
                else {
                    groupTitlePanel.add(colorChooser);
                    colorChooserVisible = true;
                }
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
        
        panel.add(groupNameLbl);
        panel.add(selectedColorPanel);
        return panel;
    }
    
    // On Apply button press
    @Override
    public void accept(Color color) {
        // Change the colors of all words in this group
        // but only the ones with default color!
        
        // border of this
        setBorder(color);
        
        // border of group title (name)
        Border margin = new EmptyBorder(8,8,8,8);
        groupTitlePanel.setBorder(new CompoundBorder(new Utils.RoundBorder(color, null, new BasicStroke(2), arc), margin));
        
        // also update the word rows color circles
        wordPanels.forEach(wrdRow -> wrdRow.onNewGroupDefaultColor(group.getDefaultColor(), color));
        
        group.setNewDefaultColor(color);
        
        // repaint chapters
        parent.tabParent.getDictionaryTab().getTabsPanel().getChaptersPanel().repaint();
    }
}
