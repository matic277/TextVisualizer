package word;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public abstract class AbsWord {
    
    protected String sourceText;
    protected String processedText;
    
    protected String tag;
    
    Map<MapKey, String> statsMap;
    
    public enum MapKey {
        SOURCE(0, "Source"),
        PROCESSED(1, "Processed"),
        TAG(2, "Tag"),
        TOKENS(3, "Tokens"),
        PLEASANTNESS(4, "Pleasantness"),
        ACTIVATION(5, "Activation"),
        IMAGERY(6, "Imagery");
    
        public int order;
        public String printValue;
        MapKey(int order, String val) { this.order = order; this.printValue = val; }
    }
    
    public AbsWord(String source, String processed, String tag) {
        this.sourceText = source;
        this.processedText = processed;
        this.tag = tag;
        
        this.statsMap = new HashMap<>(10);
        
        updateKeyMap();
    }
    
    public void updateKeyMap() {
        statsMap.put(MapKey.SOURCE, this.sourceText);
        statsMap.put(MapKey.PROCESSED, this.processedText);
        statsMap.put(MapKey.TAG, tag);
    }
    
    public boolean hasSentimentValue() {
        return false;
    }
    
    public boolean hasImageryValue() {
        return false;
    }
    
    public boolean hasActivationValue() {
        return false;
    }
    
    public abstract double getSentimentValue();
    
    public abstract double getActivation();
    
    public abstract double getImagery();
    
    public Map<AbsMeasurableWord.MapKey, String> getStatsMap() { return this.statsMap; }
    
    public abstract boolean checkIntegrity();
    
    public String getTag() {
        return "<" + tag + ">";
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
    
    public int getProcessedWordLength() { return this.processedText.length(); }
    
    @Override
    public String toString() {
        return "[" + getTag() + ", src:'" + sourceText + "', prc:'" + processedText + "']";
    }
    public void setSourceText(String newStr) { this.sourceText = newStr; }
    public void setProcessedText(String newStr) { this.sourceText = newStr; }
    public String getSourceText() { return this.sourceText; }
    public String getProcessedText() { return this.processedText; }
}
