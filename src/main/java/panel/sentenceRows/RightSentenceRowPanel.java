package panel.sentenceRows;

import SentenceLabel.SentenceLabel;
import main.UserDictionary.Word;
import main.Utils;
import panel.RightPanel;
import panel.WordLabel;
import panel.WrapLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.*;
import java.util.List;

public class RightSentenceRowPanel extends SentenceRowPanel {
    
    RightPanel parent;
    JPanel parentContainer;
    public SentenceLabel sentenceLbl;
    
    JPanel statsPanel;
        JLabel visualTypeLbl;
    JPanel sentencePanel;
        List<WordLabel> words;
    
    public static final DecimalFormat format = new DecimalFormat("#.##"); static {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator('.');
        format.setDecimalFormatSymbols(symbols);
    }
    
    public RightSentenceRowPanel(RightPanel parent, JPanel parentContainer, SentenceLabel sentenceLbl) {
        super(parent.getBottomPanel());
        this.parent = parent;
        this.parentContainer = parentContainer;
        this.sentenceLbl = sentenceLbl;
        
        this.setLayout(new BorderLayout());
        this.setBackground(Utils.GRAY3);
        this.setBorder(BorderFactory.createMatteBorder(1,1,1,1,Utils.GRAY3));
        
        initStatsPanel();
        initSentencePanel();
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
                parent.onRemoveSentencePress(RightSentenceRowPanel.this);
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
        
        JLabel wordsNumLbl = getStatLabel("Number of words: " + sentenceLbl.getSentence().getWords().size() + "   | ");
        wordsNumLbl.setPreferredSize(new Dimension(wordsNumLbl.getPreferredSize().width, 11));
        infoContainer.add(wordsNumLbl);
        
        
        statsContainer.add(removeSentenceLbl, BorderLayout.EAST);
        statsContainer.add(infoContainer, BorderLayout.CENTER);
        
        this.add(statsContainer, BorderLayout.NORTH);
    }
    
    private void initSentencePanel() {
        sentencePanel = new JPanel();
        sentencePanel.setLayout(new WrapLayout(WrapLayout.LEFT));
        sentencePanel.setBackground(new Color(0, 0, 0, 0));
        
        words = new ArrayList<>(sentenceLbl.getSentence().getWords().size());
        
        // TODO calling super method here wont work due to highlighting problem
        // annoying (the "abstract" method is only called from 1 place - loses its abstraction meaning)
        // but whatever
        
        for (Word word : sentenceLbl.getSentence().getWords()) {
            WordLabel wrdLbl = new WordLabel(this, word);
            wrdLbl.setParentSentence(sentenceLbl.getSentence());
            wrdLbl.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    // only one word is clicked
                    parent.onWordsClick(Collections.singletonList(word));
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
        
        
        this.addMouseListener(new MouseListener() {
            @Override public void mouseClicked(MouseEvent e) {
                // all words are clicked
                parent.onWordsClick(sentenceLbl.getSentence().getWords());
            }
            @Override public void mouseEntered(MouseEvent e) {
               setBackground(Color.white);
               setBorder(BorderFactory.createMatteBorder(1,1,1,1,Utils.GRAY));
               repaint();
            }
            @Override public void mouseExited(MouseEvent e) {
                setBorder(BorderFactory.createMatteBorder(1,1,1,1,Utils.GRAY3));
                setBackground(Utils.GRAY3);
                repaint();
            }
            @Override public void mousePressed(MouseEvent e) { }
            @Override public void mouseReleased(MouseEvent e) { }
        });
    }
    
    private JLabel getStatLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setVerticalTextPosition(SwingConstants.TOP);
        lbl.setFont(Utils.getFont(10));
        lbl.setForeground(Color.gray);
        lbl.setOpaque(false);
        return lbl;
    }
    
    public boolean representsSentenceLabel(SentenceLabel sentence) { return sentenceLbl == sentence; }
}
