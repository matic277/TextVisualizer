package panel;

import main.Sentence;
import main.Utils;
import word.AbsWord;

import javax.swing.*;
import javax.swing.border.StrokeBorder;
import java.awt.*;
import java.util.LinkedList;
import java.util.List;

public class TextBoxBroken extends JPanel {
    
    List<WordLabel> words = new LinkedList<>();
    LeftPanelBroken parent;
    
    JPanel sentencesPanel;
    
    public TextBoxBroken() {
        this.setLayout(new WrapLayout());
        this.setBackground(Utils.GRAY3);
        
        this.setLayout(new BorderLayout());
        
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BorderLayout());
        JLabel title = new JLabel(" Selected sentences ");
        title.setPreferredSize(new Dimension(300, 30));
        title.setOpaque(true);
        title.setBackground(Utils.GRAY);
        title.setFont(Utils.getFont(14));
        titlePanel.add(title, BorderLayout.CENTER);
        this.add(titlePanel, BorderLayout.NORTH);
        
        sentencesPanel = new JPanel();
        sentencesPanel.setBackground(Utils.GRAY3);
        sentencesPanel.setLayout(new BoxLayout(sentencesPanel, BoxLayout.Y_AXIS));
        this.add(sentencesPanel, BorderLayout.CENTER);
        
        this.setMinimumSize(new Dimension(0, 0));
    }
    
    public void onSentenceClick(Sentence clickedSentence) {
        sentencesPanel.removeAll();
        sentencesPanel.setPreferredSize(parent.getSize());
        
        JPanel sentencePanel = new JPanel();
        sentencePanel.setLayout(new WrapLayout());
        sentencePanel.setAlignmentX(LEFT_ALIGNMENT);
        sentencePanel.setBackground(Utils.RED);
        
        for (AbsWord word : clickedSentence.getWords()) {
            WordLabel lbl = new WordLabel(this.parent, sentencePanel, word);
            sentencePanel.add(lbl);
        }
        
        sentencesPanel.add(sentencePanel);
        
        // need to call this otherwise this components doesn't get updated
        // immediately, but only after resize happens
        this.parent.updateUI();
    }
    
    public void onSentenceHover(List<SentenceLabel> hoveredSentences) {
        sentencesPanel.removeAll();
//        sentencesPanel.doLayout();
//        sentencesPanel.revalidate();
//        sentencesPanel.repaint();
//        sentencesPanel.setPreferredSize(new Dimension(500, 500));
        
        for (SentenceLabel slbl : hoveredSentences) {
            JPanel mainPanel = new JPanel();
            mainPanel.setName("mainPanel");
            mainPanel.setLayout(new BorderLayout());
//            sentenceMainPanel.setAlignmentX(LEFT_ALIGNMENT);
            mainPanel.setOpaque(true);
            mainPanel.setBackground(Utils.GRAY3);
            mainPanel.setBorder(BorderFactory.createMatteBorder(1,1,1,1,Utils.GRAY3));
//            mainPanel.addMouseListener(new MouseListener() {
//                @Override
//                public void mouseClicked(MouseEvent e) {
//                    // TODO
//                }
//
//                @Override public void mouseEntered(MouseEvent e) {
//                    mainPanel.setBackground(Color.white);
//                    mainPanel.setBorder(BorderFactory.createMatteBorder(1,1,1,1,Utils.GRAY));
//                }
//
//                @Override public void mouseExited(MouseEvent e) {
//                    mainPanel.setBackground(Utils.GRAY3);
//                    mainPanel.setBorder(BorderFactory.createMatteBorder(1,1,1,1,Utils.GRAY3));
//                }
//
//                @Override public void mousePressed(MouseEvent e) { }
//                @Override public void mouseReleased(MouseEvent e) { }
//            });
//            mainPanel.setBackground(Utils.getRandomColor());
            
            JLabel sentNumLbl = new JLabel(""+slbl.getSentence().sentenceNumber, SwingConstants.CENTER);
            sentNumLbl.setFont(Utils.getFont(12));
            sentNumLbl.setBorder(new StrokeBorder(new BasicStroke(1)));
            sentNumLbl.setOpaque(true);
            sentNumLbl.setBackground(Utils.GRAY2);
            sentNumLbl.setMinimumSize(new Dimension(30, 10));
            sentNumLbl.setPreferredSize(new Dimension(30, 10));
            mainPanel.add(sentNumLbl, BorderLayout.WEST);
            
            JPanel sentencePanel = new JPanel();
//            sentencePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
            sentencePanel.setLayout(new WrapLayout(WrapLayout.LEFT));
            sentencePanel.setBackground(Utils.GRAY3);
            sentencePanel.setOpaque(false);
//            mainPanel.addComponentListener(new ComponentListener() {
//                @Override public void componentResized(ComponentEvent e) {
////                    System.out.println("resized="+e.getComponent().getWidth());
//                    sentencePanel.revalidate();
//                    sentencePanel.doLayout();
//                    mainPanel.revalidate();
//                    mainPanel.doLayout();
//                    }
//                @Override public void componentMoved(ComponentEvent e) { }
//                @Override public void componentShown(ComponentEvent e) { }
//                @Override public void componentHidden(ComponentEvent e) { }
//            });
    
//            sentencePanel.setBackground(Utils.getRandomColor());
    
            for (AbsWord word : slbl.getSentence().getWords()) {
                WordLabel lbl = new WordLabel(this.parent, mainPanel, word);
                sentencePanel.add(lbl);
            }
            mainPanel.add(sentencePanel, BorderLayout.CENTER);
            
//            sentencesPanel.setAlignmentX(LEFT_ALIGNMENT);
//            mainPanel.updateUI();
//            mainPanel.setMaximumSize(new Dimension(1000, mainPanel.getPreferredSize().height));
//            mainPanel.setMaximumSize(mainPanel.getPreferredSize());
//            mainPanel.doLayout();
            
            sentencesPanel.add(mainPanel);
//            sentencePanel.revalidate();
//            sentencePanel.doLayout();
//            sentencePanel.updateUI();
        }
        
//        this.revalidate();
//        this.doLayout();
//        this.updateUI();
//
//        parent.revalidate();
//        parent.updateUI();
//        parent.doLayout();
        
        // need to call this otherwise this components doesn't get updated
        // immediately, but only after resize happens
        this.parent.updateUI();
    }
    
    
    public void setParent(LeftPanelBroken leftPanelBroken) {
        this.parent = leftPanelBroken;
        System.out.println(this.getParent().getName());
    }
    

}