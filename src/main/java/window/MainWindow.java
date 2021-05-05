package window;

import main.Sentence;
import panel.BottomPanel;
import panel.MainPanel;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MainWindow {
    
    protected JFrame frame;
    protected JPanel mainPanel;
    protected JPanel bottomPanel;
    
    protected Dimension windowSize;
    
    final Color bgColor = Color.white;
    
    List<Sentence> sentences;
    
    public MainWindow(Dimension windowSize, List<Sentence> sentences) {
        this.windowSize = windowSize;
        this.sentences = sentences;
        
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
    
    public List<Sentence> getSentences() {
        return this.sentences;
    }
}
