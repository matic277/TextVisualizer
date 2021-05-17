package panel;

import main.Utils;

import javax.swing.*;
import java.awt.*;

public class RightPanel extends JPanel {
    
    public RightPanel() {
        this.setOpaque(true);
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
