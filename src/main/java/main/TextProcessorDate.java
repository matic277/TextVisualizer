package main;

import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TextProcessorDate {
    Map<Pair<Integer, String>, String> chapters;
    Map<Pair<Integer, String>, List<Sentence>> processedChapters;
    
    public TextProcessorDate(Map<Pair<Integer, String>, String> chapters) {
        this.chapters = chapters;
        this.processedChapters = new HashMap<>(chapters.size());
    }
    
    public void processText() {
        chapters.forEach((k, v) -> {
            String chapterText = v;
            String[] rawSentences = v.split(FileReaderDate.TWEET_SEPARATOR);

            List<Sentence> sentences = new ArrayList<>(rawSentences.length + 1);
            int sentenceNumber = 0;
            for (String sentence : rawSentences) {
                if (sentence.isBlank() || sentence.isEmpty()) continue;
                Sentence s = new Sentence(sentence, ++sentenceNumber);
                sentences.add(s);
            }
            processedChapters.put(k, sentences);
            
//            List<Sentence> sentences = new ArrayList<>(1);
//            sentences.add(new Sentence(chapterText, 0));
//            processedChapters.put(k, sentences);
        });
    }
    
    public Map<Pair<Integer, String>, List<Sentence>> getProcessedChapters() { return this.processedChapters; }
}
