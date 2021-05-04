package window;

import panel.BottomPanel;
import panel.MainPanel;

import javax.swing.*;
import java.awt.*;

public class MainWindow {
    
    protected JFrame frame;
    protected JPanel mainPanel;
    protected JPanel bottomPanel;
    
    protected Dimension windowSize;
    
    final Color bgColor = Color.white;
    
    public MainWindow(Dimension windowSize) {
        this.windowSize = windowSize;
        
        frame = new JFrame();
        frame.setSize(windowSize);
        frame.setPreferredSize(windowSize);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
        
        try { UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel"); }
        catch (Exception e) {e.printStackTrace();}
        
        Dimension mainSize = new Dimension(windowSize.width, windowSize.height * 2/3);
        Dimension bottomSize = new Dimension(windowSize.width, windowSize.height - mainSize.height);
        mainPanel = new MainPanel(mainSize, this);
        bottomPanel = new BottomPanel(bottomSize, this);
        
        frame.add(mainPanel, BorderLayout.CENTER);
        frame.add(bottomPanel, BorderLayout.SOUTH);
        frame.pack();
    }
}
