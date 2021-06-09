package main.UserDictionary;

import main.Utils;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import word.AbsWord;

import java.awt.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Word {
    
    // SINGLETONS
    private final static Map<String, Word> singletonMap = new HashMap<>(30_000);
    
    public static Word of(String source, String clean, Color clr) {
        Word w =  singletonMap.computeIfAbsent(clean, k -> new Word(source, clean, clr));
        w.color = clr;
        return w;
    }
    
    public static Word of(String source, String clean) { return singletonMap.computeIfAbsent(clean, k -> new Word(source, clean)); }
    
    public String sourceText;
    public String processedText;
    public Color color;
    private static final Color UNRECOGNIZED_WORD_COLOR = Utils.GRAY2;
    
    Map<MapKey, String> statsMap;
    
    public enum MapKey {
        SOURCE(0, "Source"),
        PROCESSED(1, "Processed");
        //TAG(2, "Tag"),
        //TOKENS(3, "Tokens"),
        //PLEASANTNESS(4, "Pleasantness"),
        //ACTIVATION(5, "Activation"),
        //IMAGERY(6, "Imagery");
        ;
        public int order;
    
        public String printValue;
        MapKey(int order, String val) { this.order = order; this.printValue = val; }
    }
    
    public void updateKeyMap() {
        statsMap.put(MapKey.SOURCE, this.sourceText);
        statsMap.put(MapKey.PROCESSED, this.processedText);
    }
    
    private Word(String source, String clean, Color clr) {
        this.sourceText = source;
        this.processedText = clean;
        this.color = clr;
    
        this.statsMap = new HashMap<>(10);
        updateKeyMap();
    }
    
    private Word(String source, String clean) {
        this.sourceText = source;
        this.processedText = clean;
        this.color = UNRECOGNIZED_WORD_COLOR;
    
        this.statsMap = new HashMap<>(10);
        updateKeyMap();
    }
    
    public int getProcessedWordLength() { return this.processedText.length(); }
    
    public String getProcessedText() { return this.processedText; }
    
    public String getSourceText() { return this.sourceText; }
    
    public Map<MapKey, String> getStatsMap() { return this.statsMap; }
    
    
    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(processedText).toHashCode();
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null) { return false; }
        if (obj == this) { return true; }
        if (obj.getClass() != getClass()) {
            return false;
        }
        Word wrd = (Word) obj;
        return wrd.processedText.equals(this.processedText);
    }
    
    @Override
    public String toString() {
        return "[SRC=\""+sourceText+"\", PROC=\""+processedText+"\", CLR="+ Utils.colorToString(color)+"]";
    }
}
