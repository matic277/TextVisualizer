package SentenceLabel;

import SentenceLabel.SentenceLabel;
import main.VisualType;
import panel.VerticalFlowLayout2;

import javax.swing.*;
import java.awt.*;

public class HorizontalSentenceLabelBuilder implements SentenceLabelBuilder {
    
    final LayoutManager layout = new VerticalFlowLayout2(VerticalFlowLayout2.CENTER, VerticalFlowLayout2.TOP, 0, 0);
    
    @Override
    public void rebuild(SentenceLabel label) {
        int wordSize = 3;
        double totalWords = label.getSentence().getWords().size();
        double totalWidth = (int) customLog(1.035, totalWords * wordSize);
    
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
        
        label.highWidth = (int)Math.ceil(totalWidth * highPerc);
        label.medWidth = (int)Math.floor(totalWidth * medPerc);
        label.lowWidth = (int)Math.floor(totalWidth * lowPerc);
        label.unknWidth = (int)(totalWidth - label.highWidth - label.medWidth - label.lowWidth);// whatever is left
        
        int lblWidth = label.highWidth + label.medWidth + label.lowWidth + label.unknWidth;
        int lblHeight = 5;
        
        label.setMinimumSize(new Dimension(lblWidth, lblHeight));
        label.setPreferredSize(new Dimension(lblWidth, lblHeight));
    }
    
    @Override
    public LayoutManager getParentLayout(JPanel parent) {
        return layout;
    }
}
