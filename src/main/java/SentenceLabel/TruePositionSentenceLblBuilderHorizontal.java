package SentenceLabel;

import panel.VerticalFlowLayout2;
import word.AbsWord;

import javax.swing.*;
import java.awt.*;

public class TruePositionSentenceLblBuilderHorizontal implements SentenceLabelBuilder {
    
    final LayoutManager layout = new VerticalFlowLayout2(VerticalFlowLayout2.CENTER, VerticalFlowLayout2.TOP, 0, 0);
    
    @Override
    public void rebuild(SentenceLabel label) {
        // calculate true position of words in sentence
        int wordSize = 3;
        double totalWords = label.getSentence().getWords().size();
        int totalWidth = (int)customLog(1.035, totalWords * wordSize);
        
        double sentenceLength = label.getSentence().getLengthOfSentence();
        
        // assign height and position to each word
        // based on how long it is and its successors
        int wordWidth = 0;
        for (AbsWord word : label.getSentence().getWords()) {
            // how long is this word percentage wise,
            // based on sentence length?
            double wrdLenPerc = (double) word.getProcessedWordLength() / sentenceLength;
            
            // based off percentage, calculate height (size)
            int width = (int) (totalWidth * wrdLenPerc);
            word.setSize(width);
        
            // set position
            word.setPosition(wordWidth);
            wordWidth += width;
        }
        
        int lblHeight  = 5;
        label.setPreferredSize(new Dimension(totalWidth, lblHeight));
    }
    
    @Override
    public LayoutManager getParentLayout(JPanel parent) {
        return layout;
    }
}
