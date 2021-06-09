package main.UserDictionary;

import main.Utils;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.awt.*;
import java.util.*;

public class WordGroup {
    
    final Color DEFAULT_COLOR;
    
    // map of words that have specific color defined, which overwrites DEFAULT_COLOR
    // as specified in user dictionary txt file
    Map<String, Color> specificColors;
    
    // set of words which have DEFAULT_COLOR
    Set<String> words;
    
    public String name;
    
    public WordGroup(String defaultClr) {
        try { DEFAULT_COLOR = Utils.parseColor(defaultClr); }
        catch (Exception e) { throw new RuntimeException("Color value out of range for group \"" + name + "\"."); }
        
        words = new HashSet<>(100);
        specificColors = new HashMap<>();
    }
    
    // TODO
    // these default_clr checks might be excessive, field is final!
    public boolean addWord(String word, String c) {
        if (DEFAULT_COLOR == null) throw new RuntimeException("Trying to add word " + word + ", witch color " + c + " to WordGroup, but default group color is unspecified!");
        Color clr;
        String parsed = c.substring(7, c.length()-2); // remove 'color="..."'
        try { clr = Utils.parseColor(parsed); }
        catch (Exception e) { throw new RuntimeException("Color value \"" + parsed+ "\" out of range for word \"" + word + "\" in group \"" + name + "\"."); }
        return this.specificColors.put(word, clr) != null;
    }
    
    public Color getColorForWord(String word) {
        if (!specificColors.containsKey(word) && !words.contains(word)) return null;
        return specificColors.getOrDefault(word, DEFAULT_COLOR);
    }
    
    // TODO
    // these default_clr checks might be excessive, field is final!
    public boolean addWord(String word) {
        if (DEFAULT_COLOR == null) throw new RuntimeException("Trying to add word " + word + ", to WordGroup, but default group color is unspecified!");
        return this.words.add(word);
    }
    
    //public WordGroup setDefaultGroupColor (String c) {
    //    try { DEFAULT_COLOR = Utils.parseColor(c); }
    //    catch (Exception e) { throw new RuntimeException("Color value out of range for group \"" + name + "\"."); }
    //    return this;
    //}
    public WordGroup setGroupName(String n) {
        this.name = n;
        return this;
    }
    
    public Set<String> getWords() { return this.words; }
    
    public Map<String, Color> getWordsWithSpecificColors() { return this.specificColors; }
    
    public Color getDefaultColor() { return this.DEFAULT_COLOR; }
    
    // make the printing a little bit faster when dictionary is large
    private static final StringBuilder sb = new StringBuilder();
    @Override
    public String toString() {
        sb.setLength(0);
        specificColors.forEach((w, c) -> {
            sb.append("    -> ").append(w).append(" -> ").append(Utils.colorToString(c)).append("\n");
        });
        words.iterator().forEachRemaining(w -> {
            sb.append("    -> ").append(w).append("\n");
        });
        return sb.toString();
    }
    
    @Deprecated
    private static class WordColorPair {
        public String word;
        public Color clr;
        WordColorPair(String w, Color c) { word=w; clr=c; }
        
        @Override public String toString() {
            return "[" + word + ", " + Utils.colorToString(clr) + "]";
        }
        @Override public boolean equals(Object o) {
            return o.getClass() == this.getClass() &&
                    ((WordColorPair) o).word.equals(this.word);
        }
        @Override public int hashCode() {
            return new HashCodeBuilder()
                    .append(word)
                    .toHashCode();
        }
    }
}
