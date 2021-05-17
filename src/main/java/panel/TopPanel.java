package panel;

import main.Utils;

import javax.swing.*;
import java.awt.*;

public class TopPanel extends JPanel {
    
    public TopPanel() {
        this.setOpaque(true);
        this.setBackground(Color.red);
        JLabel lbl = new JLabel(" TOP PANEL ");
        lbl.setOpaque(true);
        lbl.setBackground(Color.green);
        lbl.setFont(Utils.getFont(14));
        lbl.setPreferredSize(new Dimension(lbl.getPreferredSize().width, lbl.getPreferredSize().height+5));
        this.add(lbl);
    }
}
