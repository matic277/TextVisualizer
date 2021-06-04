package SentenceLabel;

import SentenceLabel.SentenceLabel;
import main.Utils;
import main.VisualType;
import word.AbsMeasurableWord;
import word.AbsWord;
import word.StopWord;

import javax.swing.*;
import javax.swing.border.StrokeBorder;
import java.awt.*;

public interface SentenceLabelBuilder {
    void rebuild(SentenceLabel label);
    LayoutManager getParentLayout(JPanel parent);
    
    default double customLog(double base, double logNumber) {
        return Math.log(logNumber) / Math.log(base);
    }
    
    default Color determineWordColor(AbsWord word, VisualType visType) {
        // this is kind of ugly here
        if (visType == VisualType.SENTIMENT) {
            // Copy pasted from WordLabel.getSentimentColorer
            if (word instanceof AbsMeasurableWord measWrd) {
                return measWrd.getSentimentValue() < AbsMeasurableWord.NEUTRAL_THRESHOLD ?
                        SentenceLabel.SENTIMENT_LOW_COLOR : measWrd.getSentimentValue() > AbsMeasurableWord.POSITIVE_THRESHOLD ? SentenceLabel.SENTIMENT_HIGH_COLOR : SentenceLabel.SENTIMENT_MED_COLOR;
            }
            else {
                return SentenceLabel.NORMAL_UNRECOGNIZED_COLOR;
            }
        } else if (visType == VisualType.ACTIVATION) {
            System.out.println("Unsupported");
        } else if (visType == VisualType.IMAGERY) {
            System.out.println("Unsupported");
        } else {
            throw new RuntimeException("Unexpected visual type: " + visType + " when rendering.");
        }
        return null;
    }
}
