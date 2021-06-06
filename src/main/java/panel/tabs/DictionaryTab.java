package panel.tabs;

import com.formdev.flatlaf.ui.FlatRoundBorder;
import main.UserDictionary.UserDictionary;
import main.Utils;
import main.fileParsers.DictionaryReader;
import panel.VerticalFlowLayout2;

import javax.swing.*;
import javax.swing.plaf.basic.BasicBorders;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public class DictionaryTab extends JScrollPane {
    
    TabsPanel parent;
    
    JPanel mainPanel;
        JPanel chaptersInputPanel; // NORTH
    
    public DictionaryTab(TabsPanel parent) {
        this.parent = parent;
        
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        
        initChaptersInputPanel();
    
        this.setViewportView(mainPanel);
    }
    
    private void initChaptersInputPanel() {
        JLabel title = new JLabel("Path to dictionary :");
        JPanel titleContainer = new JPanel();
        titleContainer.setLayout(new BorderLayout());
        titleContainer.add(getDummySpacer(10,10), BorderLayout.WEST);
        titleContainer.add(title, BorderLayout.CENTER);
        
        JLabel info = new JLabel();
        info.setHorizontalAlignment(SwingConstants.CENTER);
        info.setOpaque(false);
        info.setForeground(Utils.GREEN);
        info.setFont(Utils.getFont(12));
        
        JTextField dictionaryInput = new JTextField("./dictionary/testdict.txt");
        dictionaryInput.addActionListener(getImportListener(dictionaryInput, info));
        // put the same listener to btn and field, so enter press and btn
        // press behaves the same
        JButton importBtn = new JButton("Import");
        importBtn.addActionListener(getImportListener(dictionaryInput, info));
        
        JPanel btnContainer = new JPanel();
        btnContainer.setLayout(new BorderLayout());
        btnContainer.add(importBtn, BorderLayout.CENTER);
        btnContainer.add(getDummySpacer(10, 10), BorderLayout.EAST);
        
        chaptersInputPanel = new JPanel();
        chaptersInputPanel.setLayout(new BorderLayout(10, 0));
        chaptersInputPanel.add(titleContainer, BorderLayout.WEST);
        chaptersInputPanel.add(dictionaryInput, BorderLayout.CENTER);
        chaptersInputPanel.add(btnContainer, BorderLayout.EAST);
        chaptersInputPanel.add(getDummySpacer(10, 10), BorderLayout.NORTH);
        
        JPanel infoContainer = new JPanel();
        infoContainer.setLayout(new FlowLayout(FlowLayout.CENTER));
        infoContainer.add(info);
        chaptersInputPanel.add(infoContainer, BorderLayout.SOUTH);
        
        mainPanel.add(chaptersInputPanel, BorderLayout.NORTH);
    }
    
    private ActionListener getImportListener(JTextField dictionaryInput, JLabel info) {
        return a -> {
            CompletableFuture.runAsync(() -> {
                System.out.println("Input: " + dictionaryInput.getText());
                info.setBorder(new Utils.RoundBorder(Utils.GREEN, null, new BasicStroke(2), 5));
                info.setText(" Input (not supported yet): " + dictionaryInput.getText() + " ");
                info.setVisible(true);
    
                UserDictionary userDict;
                try {
                    userDict = new DictionaryReader(dictionaryInput.getText()).buildDictionary();
                    System.out.println(userDict);
                } catch (Exception e) {
                    e.printStackTrace();
                }
    
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
