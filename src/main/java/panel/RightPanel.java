package panel;

import main.Pair;
import main.Sentence;
import main.Utils;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;

public class RightPanel extends JPanel {
    
    BottomPanel parent;
    Map<Pair<Integer, String>, List<Sentence>> chapters;
    
    public RightPanel(BottomPanel parent) {
        this.parent = parent;
        this.chapters = parent.parent.getChapters();
        this.setBackground(Color.CYAN);
        JLabel lbl = new JLabel(" RIGHT PANEL ");
        lbl.setOpaque(true);
        lbl.setBackground(Color.green);
        lbl.setFont(Utils.getFont(14));
        lbl.setPreferredSize(new Dimension(lbl.getPreferredSize().width, lbl.getPreferredSize().height+5));
//        this.setPreferredSize(new Dimension(300, 300));
//        this.setSize(new Dimension(300, 300));
//        this.setMaximumSize(new Dimension(300, 300));
//        this.setMinimumSize(new Dimension(300, 300));
        this.add(lbl);
    }
}
