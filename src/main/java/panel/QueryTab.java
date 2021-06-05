package panel;

import main.Utils;

import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import java.awt.*;

public class QueryTab extends JPanel {
    
    TabsPanel parent;
    
    // TOP
    JPanel searchPanel;
        JLabel title; // WEST
        JTextField searchBar; // CENTER
    // CENTER
    JPanel mainPanel;
        JLabel title2; // NORTH
        JPanel statsPanel; // CENTER
    
    public QueryTab(TabsPanel parent) {
        this.parent = parent;
        
        //this.setBackground(Color.pink);
        this.setLayout(new BorderLayout());
    
        initSearchpanel();
        initMainPanel();
    }
    
    private void initMainPanel() {
        mainPanel = new JPanel();
        //mainPanel.setBackground(Color.white);
        mainPanel.setLayout(new BorderLayout());
        
        title = new JLabel("     Statistics: ");
        title.setPreferredSize(new Dimension(70, 30));
        //title.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, (ColorUIResource)UIManager.getLookAndFeel().getDefaults().get("TabbedPane.darkShadow")));
    
        System.out.println(UIManager.getLookAndFeel().getDefaults().get("TabbedPane.shadow"));
    
        statsPanel = new JPanel();
        //statsPanel.setBackground((ColorUIResource)UIManager.getLookAndFeel().getDefaults().get("TabbedPane.darkShadow"));
        
        
        mainPanel.add(title, BorderLayout.NORTH);
        mainPanel.add(statsPanel, BorderLayout.CENTER);
        this.add(mainPanel, BorderLayout.CENTER);
    }
    
    private void initSearchpanel() {
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new BorderLayout());
        
        title = new JLabel("     Search :");
        title.setPreferredSize(new Dimension(70, 30));
        
        searchBar = new JTextField();
        searchBar.addActionListener(a -> {
            // TODO
            System.out.println("Search action");
        });
        
        searchPanel.add(getDummySpacer(10,10), BorderLayout.NORTH);
        searchPanel.add(title, BorderLayout.WEST);
        searchPanel.add(searchBar, BorderLayout.CENTER);
        
        this.add(searchPanel, BorderLayout.NORTH);
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
