package panel;

import com.formdev.flatlaf.ui.FlatRoundBorder;
import main.UserDictionary.Word;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.function.Consumer;

public class DictionaryWordRowPanel extends JPanel implements Consumer<Color> {
    
    DictionaryGroupRowPanel parent;
    Word word;
    
    public Color bg = getBackground();
    final private static int arc = UIManager.getInt("Component.arc");
    
    Color normalClr;
    Color hoveredClr;
    
    ColorRef clrRef;
    
    JPanel selectedColorPanel;
    
    boolean colorChooserVisible = false;
    
    public DictionaryWordRowPanel(DictionaryGroupRowPanel parent, Word word) {
        this.parent = parent;
        this.word = word;
        
        this.setLayout(new FlowLayout(FlowLayout.LEFT));
        this.setOpaque(true);
        this.setBorder(new FlatRoundBorder());
        
        normalClr = this.getBackground();
        hoveredClr = this.getBackground().brighter();
        
        clrRef = new ColorRef(this, word.getColor());
        
        init();
    }
    
    private void init() {
        JLabel wordLbl = new JLabel(" " + word.getProcessedText()); // TODO
        
        // SELECTED COLOR CIRCLE
        
        clrRef.setNewColorConsumer(this);
        selectedColorPanel = new JPanel(){
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
        
        
        // COLOR CHOOSER
        ColorChooserPanel colorChooser = new ColorChooserPanel(word.getColor(), new Dimension(200, 150));
        colorChooser.setColorChangeReference(clrRef);
        colorChooser.setBackground2(normalClr);
        colorChooser.setBorderArc(UIManager.getInt("Component.arc"));
        colorChooser.initColorPanel();
        
        
        // LISTENERS
        this.addMouseListener(new MouseListener() {
            @Override public void mouseClicked(MouseEvent e) {
                if (colorChooserVisible) {
                    System.out.println("click1");
                    remove(colorChooser);
                    colorChooserVisible = false;
                }
                else {
                    add(colorChooser);
                    colorChooserVisible = true;
                }
                // this ensured correct panel resizing
                revalidate();
                parent.revalidate();
                doLayout();
                parent.doLayout();
                repaint();
                parent.repaint();
            }
            @Override public void mouseEntered(MouseEvent e) {
                //panel.setBackground(hoveredClr);
                bg = hoveredClr;
                repaint();
                selectedColorPanel.setBackground(hoveredClr);
                selectedColorPanel.repaint();
                colorChooser.setBackground(hoveredClr);
                colorChooser.repaint();
            }
            @Override public void mouseExited(MouseEvent e) {
                //panel.setBackground(normalClr);
                bg = normalClr;
                repaint();
                selectedColorPanel.setBackground(normalClr);
                selectedColorPanel.repaint();
                colorChooser.setBackground(normalClr);
                colorChooser.repaint();
            }
            @Override public void mousePressed(MouseEvent e) { }
            @Override public void mouseReleased(MouseEvent e) { }
        });
        
        this.add(wordLbl);
        this.add(selectedColorPanel);
    }
    
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
    // On Apply button press
    @Override
    public void accept(Color color) {
        // only change the color of the word
        word.setNewColor(color);
        
        // update new color in colorRef and repaint
        clrRef.circleColor = color;
        selectedColorPanel.repaint();
        
        // repaint chapters
        parent.parent.tabParent.getDictionaryTab().getTabsPanel().getChaptersPanel().repaint();
    }
    // TODO these methods are too similar, one should simply call the other
    // => let this class not only have a Word, but WordGroup aswel, so it knows its default color
    // bottom method calls top method
    public void onNewGroupDefaultColor(Color groupDefaultClr, Color newClr) {
        // Comparing with getRgb() method
        if (word.getColor().getRGB() == groupDefaultClr.getRGB()) {
            word.setNewColor(newClr);
    
            // update new color in colorRef and repaint
            clrRef.circleColor = newClr;
            selectedColorPanel.repaint();
        }
    }
}
