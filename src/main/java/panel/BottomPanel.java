package panel;

import main.Sentence;
import main.Utils;
import window.MainWindow;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class BottomPanel extends JPanel {
    
    MainWindow parent;
    Dimension panelSize;
    
    JScrollPane scrollPane;
    JTextPane textPane;
    TextBox textBox;
    
    List<Sentence> sentences;
    
    public BottomPanel(Dimension size, MainWindow parent) {
        this.parent = parent;
        this.panelSize = size;
        this.sentences = parent.getSentences();
    
        this.setOpaque(true);
        this.setSize(size);
        this.setPreferredSize(size);
        this.setLayout(null);
        this.setVisible(true);
        
//        textPane = new JTextPane();
//        textPane.setSize(size.width/2, size.height);
//        textPane.setText("text text text abc xyz");
        
//        scrollPane = new JScrollPane(textPane);
        TextBox box = new TextBox(new Dimension(size.width/2, size.height+100), this);
        scrollPane = new JScrollPane(box);
        scrollPane.setSize(size.width/2+200, size.height);
        scrollPane.setBackground(Color.white);
        this.add(scrollPane);
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
