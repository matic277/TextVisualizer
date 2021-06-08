package SentenceLabel;

import SentenceLabel.SentenceLabel;
import main.VisualType;

import javax.swing.*;
import java.awt.*;

public class VerticalSentenceLabelBuilder implements SentenceLabelBuilder {
    
    public final LayoutManager parentLayout = new FlowLayout(FlowLayout.LEFT, 0, 0);
    
    @Override
    public void rebuild(SentenceLabel label) {
        int wordSize = 3;
        double totalWords = label.getSentence().getWords().size();
        //double totalHeight = (int)customLog(1.035, totalWords * wordSize);
        double totalHeight = label.currentSentenceSizeType.sizeDeterminator.applyAsInt(wordSize, (int)totalWords);
        
        double highPerc;
        double medPerc;
        double lowPerc;
        double unknowPerc;
        
        VisualType currentVisualType = label.currentVisualType;

        if (currentVisualType == VisualType.SENTIMENT) {
            highPerc = label.getSentence().numOfPositiveWords / totalWords;
            medPerc = label.getSentence().numOfNeutralWords / totalWords;
            lowPerc = label.getSentence().numOfNegativeWords / totalWords;
        } else if (currentVisualType == VisualType.ACTIVATION) {
            highPerc = label.getSentence().numOfHighActivationWords / totalWords;
            medPerc = label.getSentence().numOfMediumActivationWords / totalWords;
            lowPerc = label.getSentence().numOfLowActivationWords / totalWords;
        } else {
            highPerc = label.getSentence().numOfHighImageryWords / totalWords;
            medPerc = label.getSentence().numOfMediumImageryWords / totalWords;
            lowPerc = label.getSentence().numOfLowImageryWords / totalWords;
        }
        
//        unknowPerc = sentence.numOfUnrecognizedWords / totalWords;
        
        label.highHeight = (int)Math.ceil(totalHeight * highPerc);
        label.medHeight = (int)Math.floor(totalHeight * medPerc);
        label.lowHeight = (int)Math.floor(totalHeight * lowPerc);
        label.unknHeight = (int)(totalHeight - label.highHeight - label.medHeight - label.lowHeight);// whatever is left

        int lblHeight = label.highHeight + label.medHeight + label.lowHeight + label.unknHeight;
        int lblWidth  = 5;
        
        label.setPreferredSize(new Dimension(lblWidth, lblHeight));
    }
    
    @Override
    public LayoutManager getParentLayout(JPanel parent) {
        return parentLayout;
    }
}
