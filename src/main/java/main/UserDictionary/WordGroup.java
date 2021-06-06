package main.UserDictionary;

import main.Pair;
import main.Utils;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class WordGroup {
    
    Color DEFAULT_COLOR;
    Set<WordColorPair> words;
    
    public String name;
    
    public WordGroup() {
        words = new HashSet<>(100);
    }
    
    public boolean addWord(String word, String c) {
        Color clr;
        String parsed = c.substring(7, c.length()-2); // remove 'color="..."'
        try { clr = Utils.parseColor(parsed); }
        catch (Exception e) { throw new RuntimeException("Color value \"" + parsed+ "\" out of range for word \"" + word + "\" in group \"" + name + "\"."); }
        return this.words.add(new WordColorPair(word, clr));
    }
    
    public boolean addWord(String word) {
        return this.words.add(new WordColorPair(word, DEFAULT_COLOR));
    }
    
    public WordGroup setDefaultGroupColor (String c) {
        try { DEFAULT_COLOR = Utils.parseColor(c); }
        catch (Exception e) { throw new RuntimeException("Color value out of range for group \"" + name + "\"."); }
        return this;
    }
    
    public WordGroup setGroupName(String n) {
        this.name = n;
        return this;
    }
    
    // make the printing a little bit faster when dictionary is large
    private static final StringBuilder sb = new StringBuilder();
    @Override
    public String toString() {
        sb.setLength(0);
        words.iterator().forEachRemaining(w -> {
            sb.append("    -> ").append(w).append("\n");
        });
        return sb.toString();
    }
    
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
