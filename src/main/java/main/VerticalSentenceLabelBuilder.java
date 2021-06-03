package main;

import panel.SentenceLabel;

import javax.swing.*;
import java.awt.*;

public class VerticalSentenceLabelBuilder implements SentenceLabelBuilder {
    
    public final LayoutManager parentLayout = new FlowLayout(FlowLayout.LEFT, 0, 0);
    
    @Override
    public void rebuild(SentenceLabel label) {
        int wordSize = 3;
        double totalWords = label.getSentence().getWords().size();
        double totalHeight = (int)customLog(1.035, totalWords * wordSize);
        
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
    
    private static double customLog(double base, double logNumber) {
        return Math.log(logNumber) / Math.log(base);
    }
    
    @Override
    public LayoutManager getParentLayout(JPanel parent) {
        return parentLayout;
    }
    
    @Override
    public void draw(Graphics2D gr, SentenceLabel label) {
        gr.setColor(label.CURRENT_COLOR_HIGH);
        gr.fillRect(0, 0, label.getWidth(), label.highHeight);
    
        gr.setColor(label.CURRENT_COLOR_MED);
        gr.fillRect(0, label.highHeight,label.getWidth(), label.medHeight);
    
        gr.setColor(label.CURRENT_COLOR_LOW);
        gr.fillRect(0, label.highHeight + label.medHeight,label.getWidth(), label.lowHeight);
    
        gr.setColor(label.CURRENT_UNRECOGNIZED_COLOR);
        gr.fillRect(0, label.lowHeight + label.medHeight + label.highHeight,label.getWidth(), label.unknHeight);
        
        label.borderDrawer.accept(gr);
    }
}
