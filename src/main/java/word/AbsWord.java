package word;

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
    
    public abstract double getSentimentValue();
    
    public Map<AbsMeasurableWord.MapKey, String> getStatsMap() { return this.statsMap; }
    
    public abstract boolean checkIntegrity();
    
    public String getTag() {
        return "<" + tag + ">";
    }

    @Override
    public String toString() {
        return "[" + getTag() + ", src:'" + sourceText + "', prc:'" + processedText + "']";
    }
    public void setSourceText(String newStr) { this.sourceText = newStr; }
    public void setProcessedText(String newStr) { this.sourceText = newStr; }
    public String getSourceText() { return this.sourceText; }
    public String getProcessedText() { return this.processedText; }
}
