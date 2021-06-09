package panel.tabs;

import dictionary.UserDictionaryCollection;
import main.UserDictionary.UserDictionary;
import main.Utils;
import main.fileParsers.DictionaryReader;
import panel.WrapLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class DictionaryImportAndEditSubtab extends JPanel {
    
    DictionaryTab parent;
    
    //JPanel mainPanel;
    //    JPanel InputPanel; // NORTH
    //    JPanel dictionaryPanel; // CENTER
    //        JLabel dictionaryPanelTitle; // NORTH
    //        JScrollPane dictionaryPane;
    //            JTextArea dictionaryText; // CENTER
    
    JTextArea textArea;
    
    public DictionaryImportAndEditSubtab(DictionaryTab parent) {
        this.parent = parent;
        
        initPanel();
        
        this.setMinimumSize(new Dimension(0,0));
        
        this.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0,1,1,0, new Color(0,0,0,0)),
                BorderFactory.createMatteBorder(0,1,1,1, Color.lightGray)));
    }
    
    // Layout done as documented in:
    // https://docs.oracle.com/javase/tutorial/uiswing/examples/components/index.html#TextSamplerDemo
    // TextAreaDemo
    private void initPanel() {
        // TOP ROW
        JLabel title = new JLabel("Path to dictionary :");
        JPanel titleContainer = new JPanel();
        titleContainer.setLayout(new BorderLayout());
        titleContainer.add(getDummySpacer(10,10), BorderLayout.WEST);
        titleContainer.add(title, BorderLayout.CENTER);
        
        JLabel updateInfo = new JLabel();
        JLabel importInfo = new JLabel();
        importInfo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));
        importInfo.setHorizontalAlignment(SwingConstants.CENTER);
        importInfo.setOpaque(false);
        importInfo.setFont(Utils.getFont(12));
        
        JTextField dictionaryInput = new JTextField("./dictionary/testdict.txt");
        dictionaryInput.addActionListener(getImportListener(dictionaryInput, importInfo));
        // put the same listener to btn and field, so enter press and btn
        // press behaves the same
        JButton importBtn = new JButton("Import");
        importBtn.addActionListener(a -> {
            updateInfo.setVisible(false);
            getImportListener(dictionaryInput, importInfo).actionPerformed(null);
        });
        
        JPanel btnContainer = new JPanel();
        btnContainer.setLayout(new BorderLayout());
        btnContainer.add(importBtn, BorderLayout.CENTER);
        btnContainer.add(getDummySpacer(10, 10), BorderLayout.EAST);
        
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BorderLayout(10, 0));
        inputPanel.add(titleContainer, BorderLayout.WEST);
        inputPanel.add(dictionaryInput, BorderLayout.CENTER);
        inputPanel.add(btnContainer, BorderLayout.EAST);
        inputPanel.add(getDummySpacer(10, 10), BorderLayout.NORTH);
        
        JPanel infoContainer = new JPanel();
        infoContainer.setLayout(new FlowLayout(FlowLayout.CENTER));
        infoContainer.add(importInfo);
        inputPanel.add(infoContainer, BorderLayout.SOUTH);
        inputPanel.setMaximumSize(new Dimension(1, 25)); // prevents vertical stretching
        
        // TEXT AREA
        textArea = new JTextArea();
        textArea.setFont(Utils.getFont(12));
        //textArea.setColumns(20);
        //textArea.setLineWrap(true);
        //textArea.setWrapStyleWord(true);
        //textArea.setRows(5);
        
        JButton updateBtn = new JButton("Apply update");
        updateBtn.addActionListener(a -> {
            importInfo.setVisible(false);
            applyUpdate(updateInfo);
        });
        
        updateInfo.setMaximumSize(new Dimension(Integer.MAX_VALUE, updateBtn.getPreferredSize().height));
        
        JPanel buttonContainer = new JPanel();
        buttonContainer.setLayout(new WrapLayout(WrapLayout.RIGHT, 0, 0));
        buttonContainer.setMaximumSize(new Dimension(1, 20)); // prevents vertical stretching
        buttonContainer.add(updateInfo);
        buttonContainer.add(getDummySpacer(10, 10));
        buttonContainer.add(updateBtn);
        
        
        GroupLayout layout = new GroupLayout(this);
        JScrollPane textScrollPane = new JScrollPane(textArea);
        
        //Create a sequential and a parallel groups
        GroupLayout.ParallelGroup h2 = layout.createParallelGroup(GroupLayout.Alignment.TRAILING);
        h2.addComponent(textScrollPane, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 212, Short.MAX_VALUE);
        h2.addComponent(inputPanel, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 212, Short.MAX_VALUE);
        h2.addComponent(buttonContainer, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 212, Short.MAX_VALUE);
        
        
        GroupLayout.SequentialGroup h1 = layout.createSequentialGroup();
        h1.addContainerGap();
        h1.addGroup(h2);
        h1.addContainerGap();
        
        //Create a parallel group for the horizontal axis
        GroupLayout.ParallelGroup hGroup = layout.createParallelGroup(GroupLayout.Alignment.LEADING);
        hGroup.addGroup(GroupLayout.Alignment.TRAILING,h1);
        layout.setHorizontalGroup(hGroup);
        
        GroupLayout.SequentialGroup v1 = layout.createSequentialGroup();
        v1.addContainerGap();
        v1.addComponent(inputPanel);
        v1.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED);
        v1.addComponent(textScrollPane, GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE);
        v1.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED);
        v1.addComponent(buttonContainer);
        v1.addContainerGap();
        
        GroupLayout.ParallelGroup vGroup = layout.createParallelGroup(GroupLayout.Alignment.LEADING);
        vGroup.addGroup(v1);
        
        //Create the vertical group
        layout.setVerticalGroup(vGroup);
        this.setLayout(layout);
    }
    
    // Reading from JTextField
    private void applyUpdate(JLabel info) {
        CompletableFuture.runAsync(() ->{
            info.setBorder(new Utils.RoundBorder(Utils.GREEN, null, new BasicStroke(2), 10));
            info.setForeground(Utils.GREEN);
            info.setText(" Reading dictionary... ");
            info.setVisible(true);
            info.repaint();
            
            java.util.List<String> lines = Arrays.stream(textArea.getText().split("\n")).collect(Collectors.toList());
            onNewDictionaryImport(info, lines);
        });
    }
    
    private void onNewDictionaryImport(JLabel info, List<String> dictionaryLines) {
        try {
            UserDictionary dict = DictionaryReader.buildDictionary(dictionaryLines);
            UserDictionaryCollection.get().addDictionary(dict.getName(), dict);
            
            System.out.println(dict);
            
            parent.parent.dictionaryTab.dictColorsSubtab.onNewDictionaryAdded();
            
            parent.parent.chaptersPanel.onNewDictionaryImport(dict);
            //parent.parent.onNewDictionaryImport(dict);
        } catch (RuntimeException e) {
            info.setBorder(new Utils.RoundBorder(Utils.RED, null, new BasicStroke(2), 10));
            info.setForeground(Utils.RED);
            info.setText(e.getLocalizedMessage());
            info.repaint();
            return;
        }
        
        info.setText(" Done! ");
        info.repaint();
        
        Utils.sleep(2000);
        
        info.setVisible(false);
        info.setBorder(null);
        info.setText("");
    }
    
    // Reading from file
    private ActionListener getImportListener(JTextField dictionaryInput, JLabel info) {
        return a -> {
            CompletableFuture.runAsync(() -> {
                System.out.println("Input: " + dictionaryInput.getText());
                info.setBorder(new Utils.RoundBorder(Utils.GREEN, null, new BasicStroke(2), 10));
                info.setForeground(Utils.GREEN);
                info.setText(" Reading dictionary... ");
                info.setVisible(true);
                
                DictionaryReader reader = new DictionaryReader(dictionaryInput.getText());
                reader.readLines();
                
                textArea.setText("");
                reader.fileLines.forEach(line -> {
                    textArea.append(line);
                    textArea.append("\n");
                });
                
                onNewDictionaryImport(info, reader.getLines());
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
