package main;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.List;

public class TextBox extends JComponent {
    
    Dimension size;
    List<JLabel> words = new LinkedList<>();
    
    public TextBox(Dimension size) {
        this.size = size;
        this.setPreferredSize(size);
        this.setLayout(new FlowLayout());
        
        for (int i=0; i<150; i++) {
            JLabel lbl = new JLabel(" " + Utils.randomString(3 + Utils.RAND.nextInt(6))+" ");
            lbl.setOpaque(true);
            lbl.setBackground(new Color(Utils.RAND.nextInt(255), Utils.RAND.nextInt(255), Utils.RAND.nextInt(255)));
            lbl.setFont(Utils.getFont(14));
            lbl.setPreferredSize(new Dimension(lbl.getPreferredSize().width, lbl.getPreferredSize().height+5));
            words.add(lbl);
            this.add(lbl);
        }
        System.out.println(words.get(0).getPreferredSize());
    }
    
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D gr = (Graphics2D) g;
        gr.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        gr.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        
        gr.setColor(Color.RED);
        gr.drawRect(5, 5,  this.getWidth()-10, this.getHeight() - 10);
    }
}

class Word {
    String word;
    Color bgColor;
    
    
    public void draw(Graphics2D g) {
    
    }
}
