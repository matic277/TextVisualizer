package main;


import word.AbsMeasurableWord;
import word.AbsWord;
import word.Emoji;
import word.Smiley;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;

public class Sentence {
    
    String sentence;
    ArrayList<AbsWord> words;
    
    private double sentiment;
    
    private final double magnifyValue = 2;
    
    public Sentence(String sentence) {
        this.sentence = sentence;
        processSentence();
        calculateSentiment();
    }
    
    private void processSentence() {
        Tokenizer t = new Tokenizer();
        
        // create word tokens
        String[] tokens = sentence.split(" ");
        words = t.classifyAndGetWords(tokens);
    
        System.out.println("processed, words="+words.size());
    }
    
    public ArrayList<AbsWord> getWords() {
        return words;
    }
    
    // only call this if you changed sentiment
    // of words after creating this class
    public void calculateSentiment() {
        double sentiment = 0;
        for (int i=0; i<words.size(); i++) {
            AbsWord w = words.get(i);
            if (w instanceof AbsMeasurableWord) {
                AbsMeasurableWord mw = (AbsMeasurableWord) w;
                sentiment += mw.getPleasantness();
            }
        }
        this.sentiment = sentiment;

//        if (sentence.equals("Not bad.")) {
//            double sent = 0;
//            for (int i=0; i<words.size(); i++) {
//                AbsWord w = words.get(i);
//                if (w instanceof AbsMeasurableWord) {
//                    AbsMeasurableWord mw = (AbsMeasurableWord) w;
//                    sent += mw.getPleasantness();
//                    System.out.println("adding : " + mw.getSourceText() + "val: " + mw.getPleasantness());
//                }
//            }
//            System.out.println("end sent: " + sent);
//        }
    }
    
    private ArrayList<AbsMeasurableWord> getListOfEmojisAndSmileys() {
        ArrayList<AbsMeasurableWord> list = new ArrayList<AbsMeasurableWord>(5);
        words.forEach(w -> {
            if (w instanceof Smiley || w instanceof Emoji)
                list.add((AbsMeasurableWord) w);
        });
        return list;
//        words.stream().filter(w -> {
//            if (w instanceof Smiley || w instanceof Emoji) return true;
//        }).collect(Collectors.toList());
    }
    
    public double getSentimentValueOfEmojisAndSmileys() {
        double sentiment = 0;
        for (AbsMeasurableWord mw : getListOfEmojisAndSmileys()) sentiment += mw.getPleasantness();
        return sentiment;
//        return getListOfEmojisAndSmileys()
//                .stream()
//                .mapToDouble(AbsMeasurableWord::getPleasantness)
//                .sum();
    }
    
    public void magnifySentiment() {
        this.sentiment *= magnifyValue;
    }
    
    public double getSentiment() {
        return sentiment;
    }
    
    public String getSentence() {
        return sentence;
    }
    
    public String toStringLast() {
        DecimalFormat format = new DecimalFormat("#.###");
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator('.');
        format.setDecimalFormatSymbols(symbols);
        
        StringBuilder sb = new StringBuilder("\t\t|-> Sentence: ");
        sb.append("\"").append(sentence).append("\"");
        sb.append("\n");
        
        for (AbsWord w : words) {
            sb.append("\t\t|-> ");
            sb.append(w.toString());
            sb.append("\n");
        }
        sb.append("\t\t|-> Sentence sentiment value: ").append(format.format(sentiment));
        return sb.toString();
    }
    
    @Override
    public String toString() {
        DecimalFormat format = new DecimalFormat("#.###");
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator('.');
        format.setDecimalFormatSymbols(symbols);
        
        StringBuilder sb = new StringBuilder("\t|\t|-> Sentence: ");
        sb.append("\"").append(sentence).append("\"");
        sb.append("\n");
        
        for (AbsWord w : words) {
            sb.append("\t|\t|-> ");
            sb.append(w.toString());
            sb.append("\n");
        }
        sb.append("\t|\t|-> Sentence sentiment value: ").append(format.format(sentiment));
        
        sb.append("\n\t|");
        return sb.toString();
    }
}
