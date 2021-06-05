package panel.tabs;

import com.formdev.flatlaf.ui.FlatRoundBorder;
import main.Utils;
import panel.VerticalFlowLayout2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.concurrent.CompletableFuture;

public class DictionaryTab extends JPanel {
    
    TabsPanel parent;
    
    JPanel chaptersInputPanel;
    
    public DictionaryTab(TabsPanel parent) {
        this.parent = parent;
        
        this.setLayout(new BorderLayout());
        
        initChaptersInputPanel();
    }
    
    private void initChaptersInputPanel() {
        JLabel title = new JLabel("     Path to dictionary :  ");
        JLabel info = new JLabel();
        info.setHorizontalAlignment(SwingConstants.CENTER);
        info.setOpaque(false);
        info.setForeground(Utils.GREEN);
        info.setFont(Utils.getFont(12));
        //FlatRoundBorder border= new FlatRoundBorder();
        //new RoundB
        //border.
        JButton importBtn = new JButton("Import");
        JTextField dictionaryInput = new JTextField();
        // put the same listener to btn and field, so enter press and btn
        // press behaves the same
        importBtn.addActionListener(getImportListener(dictionaryInput, info));
        dictionaryInput.addActionListener(getImportListener(dictionaryInput, info));
        
        chaptersInputPanel = new JPanel();
        chaptersInputPanel.setLayout(new BorderLayout());
        chaptersInputPanel.add(title, BorderLayout.WEST);
        chaptersInputPanel.add(dictionaryInput, BorderLayout.CENTER);
        chaptersInputPanel.add(importBtn, BorderLayout.EAST);
        chaptersInputPanel.add(getDummySpacer(10, 10), BorderLayout.NORTH);
        
        JPanel infoContainer = new JPanel();
        infoContainer.setLayout(new FlowLayout(FlowLayout.CENTER));
        infoContainer.add(info);
        chaptersInputPanel.add(infoContainer, BorderLayout.SOUTH);
        
        this.add(chaptersInputPanel, BorderLayout.NORTH);
        
    }
    
    private ActionListener getImportListener(JTextField dictionaryInput, JLabel info) {
        return a -> {
            CompletableFuture.runAsync(() -> {
                System.out.println("Input: " + dictionaryInput.getText());
                info.setBorder(new Utils.RoundBorder(Utils.GREEN, null, new BasicStroke(2), 5));
                info.setText(" Input (not supported yet): " + dictionaryInput.getText() + " ");
                info.setVisible(true);
                
                Utils.sleep(1000);
    
                info.setVisible(false);
                info.setBorder(null);
                info.setText("");
            });
        };
    }
    
    private JLabel getDummySpacer(int width, int height) {
        JLabel lbl = new JLabel();
        lbl.setPreferredSize(new Dimension(width, height));
        lbl.setMaximumSize(new Dimension(width, height));
        lbl.setMinimumSize(new Dimension(width, height));
        lbl.setOpaque(false);
        return lbl;
    }
    
}
