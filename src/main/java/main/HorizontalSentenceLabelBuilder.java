package main;

import panel.SentenceLabel;
import panel.VerticalFlowLayout;
import panel.VerticalFlowLayout2;
import panel.WrapLayout;

import javax.swing.*;
import java.awt.*;

public class HorizontalSentenceLabelBuilder implements SentenceLabelBuilder {
    
    @Override
    public void rebuild(SentenceLabel label) {
        int wordSize = 3;
        double totalWords = label.getSentence().getWords().size();
        double totalWidth = (int)customLog(1.035, totalWords * wordSize);
    
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
    
    private static double customLog(double base, double logNumber) {
        return Math.log(logNumber) / Math.log(base);
    }
    
    @Override
    public void draw(Graphics2D gr, SentenceLabel label) {
        gr.setColor(label.CURRENT_COLOR_HIGH);
        gr.fillRect(0, 0, label.highWidth, label.getHeight());
        
        gr.setColor(label.CURRENT_COLOR_MED);
        gr.fillRect(label.highWidth, 0, label.medWidth, label.getHeight());
        
        gr.setColor(label.CURRENT_COLOR_LOW);
        gr.fillRect(label.highWidth + label.medWidth, 0, label.lowWidth, label.getHeight());
        
        gr.setColor(label.CURRENT_UNRECOGNIZED_COLOR);
        gr.fillRect(label.highWidth + label.medWidth + label.lowWidth, 0, label.unknWidth, label.getHeight());
    
        label.borderDrawer.accept(gr);
    }
    
    @Override
    public LayoutManager getParentLayout(JPanel parent) {
//        return new BoxLayout(parent, BoxLayout.Y_AXIS);
//        return new WrapLayout(WrapLayout.CENTER, 0, 0);
        return new VerticalFlowLayout2(VerticalFlowLayout2.CENTER, VerticalFlowLayout2.TOP, 0, 0);
    }
}
