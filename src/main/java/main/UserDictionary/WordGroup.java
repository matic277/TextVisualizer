package main.UserDictionary;

import dictionary.UserDictionaryCollection;
import main.Utils;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.awt.*;
import java.util.*;

public class WordGroup {
    
    Color DEFAULT_COLOR;
    
    //// map of words that have specific color defined, which overwrites DEFAULT_COLOR
    //// as specified in user dictionary txt file
    //Map<Word, Color> specificColors;
    
    // set of words which have DEFAULT_COLOR
    Set<Word> words;
    
    public String name;
    
    public WordGroup(String defaultClr) {
        try { DEFAULT_COLOR = Utils.parseColor(defaultClr); }
        catch (Exception e) { throw new RuntimeException("Color value out of range for group \"" + name + "\"."); }
        
        words = new HashSet<>(100);
        //specificColors = new HashMap<>();
    }
    
    public boolean addWord(String word, String c) {
        if (DEFAULT_COLOR == null) throw new RuntimeException("Trying to add word " + word + ", witch color " + c + " to WordGroup, but default group color is unspecified!");
        
        Color clr;
        String parsed = c.substring(7, c.length()-2); // remove 'color="..."'
        clr = Utils.parseColor(parsed);
        
        Word wrd = Word.of(word, word, clr);
        if (words.contains(wrd)) throw new RuntimeException("Trying to add word " + word + ", to group \"" + name + "\", but it already exists.");
        
        if (UserDictionaryCollection.get().containsWord(wrd))
            throw new RuntimeException("Word \""+wrd+"\" already exists in some dictionary. Dictionaries should have distinct entries.");
        
        return this.words.add(wrd);
    }
    
    //public Color getColorForWord(String word) {
        //words.
        //if (!specificColors.containsKey(word) && !words.contains(word)) return null;
        //return specificColors.getOrDefault(word, DEFAULT_COLOR);
    //}
    
    public boolean addWord(String word) {
        if (DEFAULT_COLOR == null) throw new RuntimeException("Trying to add word " + word + ", to WordGroup, but default group color is unspecified!");
        
        Word wrd = Word.of(word, word, DEFAULT_COLOR);
        if (words.contains(wrd)) throw new RuntimeException("Trying to add word " + word + ", to group \"" + name + "\", but it already exists.");
        
        return this.words.add(wrd);
    }
    
    public boolean containsWord(Word w) {
        return words.contains(w);
    }
    
    public WordGroup setGroupName(String n) {
        this.name = n.trim().toLowerCase();
        return this;
    }
    
    public Set<Word> getWords() { return this.words; }
    
    //public Map<Word, Color> getWordsWithSpecificColors() { return this.specificColors; }
    
    public Color getDefaultColor() { return this.DEFAULT_COLOR; }
    
    // make the printing a little bit faster when dictionary is large
    private static final StringBuilder sb = new StringBuilder();
    @Override
    public String toString() {
        sb.setLength(0);
        //specificColors.forEach((w, c) -> {
        //    sb.append("    -> ").append(w).append(" -> ").append(Utils.colorToString(c)).append("\n");
        //});
        words.iterator().forEachRemaining(w -> {
            sb.append("    -> ").append(w).append("\n");
        });
        return sb.toString();
    }
    
    public void setNewDefaultColor(Color color) {
        this.DEFAULT_COLOR = color;
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
