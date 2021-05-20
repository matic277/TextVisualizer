package main;

import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import word.AbsWord;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
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
    
        //Loading sentence detector model
        InputStream inputStream = null;
        SentenceModel model = null;
        try {
            inputStream = new FileInputStream("./da-sent.bin");
            model = new SentenceModel(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        SentenceModel finalModel = model;
        
        chapters.forEach((k, v) -> {
            String chapterText = v;
            
            //Instantiating the SentenceDetectorME class
            SentenceDetectorME detector = new SentenceDetectorME(finalModel);
            //Detecting the sentence
            String[] rawSentences = detector.sentDetect(chapterText);
            
            List<Sentence> sentences = new ArrayList<>(rawSentences.length + 1);
            int sentenceNumber = 0;
            for (String sentence : rawSentences) {
                if (sentence.isBlank() || sentence.isEmpty()) continue;
                if (sentence.length() < 5) {
                    System.out.print("Suspect sentence: '" + sentence + "'.");
                    System.out.println(" Did nothing with it.");
                }
                Sentence s = new Sentence(sentence, ++sentenceNumber);
                sentences.add(s);
            }
            processedChapters.put(k, sentences);
        });
    }
    
    public Map<Pair<Integer, String>, List<Sentence>> getProcessedChapters() { return this.processedChapters; }
}
