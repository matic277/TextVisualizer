package main;

import word.AbsWord;

import java.util.ArrayList;
import java.util.List;

public class TextProcessor {
    
    String cleanSource;
    List<Sentence> sentences;
    
    public TextProcessor(String text) {
        this.cleanSource = text;
        sentences = new ArrayList<>(cleanSource.length() / 50);
    }
    
    public void processText() {
        String[] sentences = cleanSource.split("\\.");
        for (String sentence : sentences) {
            Sentence s = new Sentence(sentence);
            this.sentences.add(s);
        }
    }
}
