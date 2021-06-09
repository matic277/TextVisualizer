package panel;

import SentenceLabel.SentenceLabel;
import main.Sentence;
import main.UserDictionary.Word;
import main.Utils;
import main.VisualType;
import word.AbsWord;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.*;
import java.util.List;
import java.util.function.Function;

public class SentenceRowPanel extends JPanel {
    
    VisualType currentVisualType = VisualType.SENTIMENT; // default
    
    JPanel mainSentencePanel;
    RightPanel rightPanel;
    SentenceLabel sentenceLabel;
    
    JPanel statsPanel;
        JLabel visualTypeLbl;
    JPanel sentencePanel;
        List<WordLabel> words;
    
    public static final DecimalFormat format = new DecimalFormat("#.##"); static {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator('.');
        format.setDecimalFormatSymbols(symbols);
    }
    
    public SentenceRowPanel(JPanel parent, SentenceLabel sentenceLabel, VisualType visualType) {
        this.mainSentencePanel = parent;
        this.sentenceLabel = sentenceLabel;
        this.currentVisualType = visualType;
        
        this.setLayout(new BorderLayout());
        
        initStatsPanel();
        initSentencePanel(currentVisualType);
    }
    
    private void initStatsPanel() {
        JPanel statsContainer = new JPanel();
        statsContainer.setOpaque(false);
        statsContainer.setLayout(new BorderLayout());
    
        JPanel infoContainer = new JPanel();
        infoContainer.setOpaque(false);
        infoContainer.setLayout(new WrapLayout(WrapLayout.LEFT));
        
        statsPanel = new JPanel();
        statsPanel.setLayout(new BorderLayout());
//        statsPanel.setLayout(new WrapLayout(WrapLayout.LEFT));
        statsPanel.setOpaque(false);
    
        Font hoveredFont = Utils.getBoldFont(14);
        Font normalFont = Utils.getFont(14);
        JLabel removeSentenceLbl = new JLabel(" x ");
        removeSentenceLbl.setFont(normalFont);
        removeSentenceLbl.setPreferredSize(new Dimension(removeSentenceLbl.getPreferredSize().width,10));
        removeSentenceLbl.setForeground(Color.gray);
        removeSentenceLbl.setOpaque(false);
        removeSentenceLbl.addMouseListener(new MouseListener() {
            final Color NORMAL_COLOR = Color.gray;
            final Color HOVERED_COLOR = Color.darkGray.darker();
            @Override public void mouseClicked(MouseEvent e) {
                rightPanel.onRemoveSentencePress(SentenceRowPanel.this);
            }
            @Override public void mouseEntered(MouseEvent e) {
                removeSentenceLbl.setForeground(HOVERED_COLOR);
                removeSentenceLbl.setFont(hoveredFont);
                setBackground(Color.white);
                setBorder(BorderFactory.createMatteBorder(1,1,1,1,Utils.GRAY));
                repaint();
                repaint();
            }
            @Override public void mouseExited(MouseEvent e) {
                removeSentenceLbl.setForeground(NORMAL_COLOR);
                removeSentenceLbl.setFont(normalFont);
                setBorder(BorderFactory.createMatteBorder(1,1,1,1,Utils.GRAY3));
                setBackground(Utils.GRAY3);
                repaint();
            }
            @Override public void mouseReleased(MouseEvent e) { }
            @Override public void mousePressed(MouseEvent e) { }
        });
        
        JLabel wordsNumLbl = getStatLabel("Number of words: " + sentenceLabel.getSentence().getWords().size() + "   | ");
        wordsNumLbl.setPreferredSize(new Dimension(wordsNumLbl.getPreferredSize().width, 11));
        infoContainer.add(wordsNumLbl);
        
        
        statsContainer.add(removeSentenceLbl, BorderLayout.EAST);
        statsContainer.add(infoContainer, BorderLayout.CENTER);
        
        this.add(statsContainer, BorderLayout.NORTH);
    }
    
    private void initSentencePanel(VisualType visualType) {
        sentencePanel = new JPanel();
        sentencePanel.setLayout(new WrapLayout(WrapLayout.LEFT));
        sentencePanel.setBackground(new Color(0, 0, 0, 0));
        
        words = new ArrayList<>(sentenceLabel.getSentence().getWords().size());
        
        // SELECTED SENTENCE
        for (Word word : sentenceLabel.getSentence().getWords()) {
            WordLabel wrdLbl = new WordLabel(this, null, word);
            wrdLbl.setParentSentence(sentenceLabel.getSentence());
            wrdLbl.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    // only one word is clicked
                    rightPanel.onWordsClick(Collections.singletonList(word));
                }
                @Override public void mouseEntered(MouseEvent e) {
                    wrdLbl.setBackground(wrdLbl.HOVERED_COLOR);
                    setBackground(Color.white);
                    setBorder(BorderFactory.createMatteBorder(1,1,1,1, Utils.GRAY));
                    repaint();
                }
                @Override public void mouseExited(MouseEvent e) {
                    wrdLbl.setBackground(wrdLbl.CURRENT_COLOR);
                    setBorder(BorderFactory.createMatteBorder(1,1,1,1,Utils.GRAY3));
                    setBackground(Utils.GRAY3);
                    repaint();
                }
                @Override public void mousePressed(MouseEvent e) { }
                @Override public void mouseReleased(MouseEvent e) { }
            });
            sentencePanel.add(wrdLbl);
            words.add(wrdLbl);
        }
        this.add(sentencePanel, BorderLayout.CENTER);
    }
    
    private JLabel getStatLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setVerticalTextPosition(SwingConstants.TOP);
        lbl.setFont(Utils.getFont(10));
        lbl.setForeground(Color.gray);
        lbl.setOpaque(false);
        return lbl;
    }
    
    public void setRightPanel(RightPanel rightPanel) {
        this.rightPanel = rightPanel;
    }
    
//    public void setSentencePanelParent(JPanel parent) {
//        this.sentencePanelParent = parent;
//    }
    
    public boolean representsSentenceLabel(SentenceLabel sentence) {
        return sentenceLabel == sentence;
    }
}
