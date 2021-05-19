package main;

import word.AbsWord;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TextProcessor {
    
    Map<Pair<Integer, String>, String> chapters;
    Map<Pair<Integer, String>, List<Sentence>> processedChapters;
    
    public TextProcessor(Map<Pair<Integer, String>, String> chapters) {
        this.chapters = chapters;
        this.processedChapters = new HashMap<>(chapters.size());
    }
    
    public void processText() {
        chapters.forEach((k, v) -> {
            String[] rawSentences = v.split("\\.");
            List<Sentence> sentences = new ArrayList<>(rawSentences.length + 1);
            int sentenceNumber = 0;
            for (String sentence : rawSentences) {
                if (sentence.isBlank() || sentence.isEmpty()) continue;
                if (sentence.length() < 5) System.out.println("Suspect sentence: " + sentence);
                Sentence s = new Sentence(sentence + ".", ++sentenceNumber);
                sentences.add(s);
            }
            processedChapters.put(k, sentences);
        });
    }
    
    public Map<Pair<Integer, String>, List<Sentence>> getProcessedChapters() { return this.processedChapters; }
}
