package SentenceLabel;

import main.VisualType;
import word.AbsWord;

import javax.swing.*;
import java.awt.*;

public class TruePositionSentenceLblBuilderVertical implements SentenceLabelBuilder {
    
    public final LayoutManager parentLayout = new FlowLayout(FlowLayout.LEFT, 0, 0);
    
    @Override
    public void rebuild(SentenceLabel label) {
        // calculate true position of words in sentence
        int charSize = 3;
        double totalWords = label.getSentence().getWords().size();
        int totalHeight = label.currentSentenceSizeType.sizeDeterminator.applyAsInt((int)totalWords, charSize);
        
        double sentenceLength = label.getSentence().getLengthOfSentence();
        
        // assign height and position to each word
        // based on how long it is and its successors
        
        int wordHeight = 0;
        for (AbsWord word : label.getSentence().getWords()) {
            // how long is this word percentage wise,
            // based on sentence length?
            double wrdLenPerc = (double) word.getProcessedWordLength() / sentenceLength;
            
            // based off percentage, calculate height (size)
            int height = (int) Math.round(totalHeight * wrdLenPerc);
            word.setSize(height);
            
            // set it's color used by renderer
            Color clr = determineWordColor(word, label.currentVisualType);
            word.setNormalRenderColor(clr);
            word.setCurrentRenderColor(clr);
            word.setHoveredRenderColor(clr.brighter());
            
            // set position
            word.setPosition(wordHeight);
            wordHeight += height;
        }
        
        int lblWidth  = 5;
        label.setPreferredSize(new Dimension(lblWidth, totalHeight));
    }
    
    @Override
    public LayoutManager getParentLayout(JPanel parent) {
        return parentLayout;
    }
}
