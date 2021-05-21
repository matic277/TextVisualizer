package panel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class LabelListener implements MouseListener {
    
    TextBoxBroken textBoxBroken;
    
    public LabelListener(TextBoxBroken parent) {
        this.textBoxBroken = parent;
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println(
            ((JLabel) e.getSource()).getText()
        );
    }
    
    @Override
    public void mouseEntered(MouseEvent e) {
        ((JLabel) e.getSource()).setBackground(Color.blue);
    }
    
    @Override
    public void mouseExited(MouseEvent e) {
        ((JLabel) e.getSource()).setBackground(Color.green);
    }
    
    @Override public void mousePressed(MouseEvent e) { }
    @Override public void mouseReleased(MouseEvent e) { }
}
