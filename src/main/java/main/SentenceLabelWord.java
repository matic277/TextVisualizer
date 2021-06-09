package main;

import SentenceLabel.SentenceLabel;
import main.UserDictionary.Word;

import java.awt.*;

public class SentenceLabelWord {
    
    Word word;
    
    public SentenceLabelWord(Word word) {
        this.word = word;
        this.currentColor = word.color;
        this.normalColor = word.color;
        this.hoveredColor = word.color.brighter();
    }
    
    // position in SentenceLabel
    // and size of word  (in vertical   should be used as height)
    //                   (in horizontal should be used as width )
    private int position;
    private int size;
    public void setPosition(int p) { this.position = p; }
    public int getPosition() { return this.position; }
    public void setSize(int s) { this.size = s; }
    public int getSize() { return this.size; }
    
    // color used when rendering in SentenceLabel
    Color currentColor;
    Color normalColor;
    Color hoveredColor;
    public void setNormalRenderColor(Color c) { this.normalColor = c; }
    public Color getNormalRenderColor() { return this.normalColor; }
    public void setCurrentRenderColor(Color c) { this.currentColor = c; }
    public Color getCurrentRenderColor() { return this.currentColor; }
    public void setHoveredRenderColor(Color c) { this.hoveredColor = c; }
    public Color getHoveredRenderColor() { return this.hoveredColor; }
    
    public void updateColors(Color newColor) {
        currentColor = newColor;
        normalColor = newColor;
        hoveredColor = newColor.brighter();
    }
    
    public String getProcessedText() { return this.word.getProcessedText(); }
    
    public int getProcessedWordLength() { return this.word.getProcessedWordLength(); }
    
    public Word getWord() { return this.word; }
}
