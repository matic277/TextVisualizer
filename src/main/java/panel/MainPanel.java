package panel;

import main.Utils;
import window.MainWindow;

import javax.swing.*;
import java.awt.*;

public class MainPanel extends JPanel {
    
    MainWindow parent;
    Dimension panelSize;
    
    public MainPanel(Dimension size, MainWindow parent) {
        this.parent = parent;
        this.panelSize = size;
        
        this.setOpaque(true);
        this.setSize(size);
        this.setPreferredSize(size);
        this.setLayout(null);
        this.setVisible(true);
    }
    
    @Override
    public void paintComponent(Graphics g) {
        Graphics2D gr = (Graphics2D) g;
        gr.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        gr.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        
        gr.setColor(Utils.bgColor);
        gr.fillRect(0, 0, this.getWidth(), this.getHeight());
    
        gr.setColor(Color.black);
        gr.drawRect(5, 5,  this.getWidth()-10, this.getHeight() - 10);
    }
}
