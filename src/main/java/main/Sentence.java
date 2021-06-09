package main;


import main.UserDictionary.Word;
import word.AbsMeasurableWord;
import word.AbsWord;
import word.Emoji;
import word.Smiley;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;

public class Sentence {
    
    String sentence;
    ArrayList<Word> words;
    
    ArrayList<SentenceLabelWord> lblWords; // these should be aligned with ArrayList.words
    
    //private double sentiment;
    //private double imagery;
    //private double activation;
    
    public int sentenceNumber;
    
    public int numOfNegativeWords = 0,
            sumOfNegativeWords = 0,
            numOfNeutralWords = 0,
            sumOfNeutralWords = 0,
            numOfPositiveWords = 0,
            sumOfPositiveWords = 0;
    
    public int numOfHighImageryWords = 0,
            numOfMediumImageryWords = 0,
            numOfLowImageryWords = 0;
    
    public int numOfHighActivationWords = 0,
            numOfMediumActivationWords = 0,
            numOfLowActivationWords = 0;
    
    public int numOfUnrecognizedWords;
    
    public Sentence(String sentence, int sentenceNumber) {
        this.sentence = sentence;
        this.sentenceNumber = sentenceNumber;
        processSentence();
        
        for (Word w : words) w.updateKeyMap();
    }
    
    private void processSentence() {
        Tokenizer t = new Tokenizer();
        
        // create word tokens
        String[] tokens = sentence.split(" ");
        words = t.classifyAndGetWords(tokens);
    
        lblWords = new ArrayList<>(words.size());
        words.forEach(w -> {
            lblWords.add(new SentenceLabelWord(w));
        });
    }
    
    public ArrayList<Word> getWords() { return this.words; }
    
    public ArrayList<SentenceLabelWord> getSentenceLabelWords() { return this.lblWords; }
    
    /**
     * Returns the sum of length of all words.
     * Ignores spaces, only considers length of processed words, not source words!
     */
    public int getLengthOfSentence() {
        return this.words.stream()
                .map(Word::getProcessedWordLength)
                .reduce(0, Integer::sum);
    }
    
    /**
     * Only checks processedText, not source!
     * Check by using contains, not equals.
     */
    public long countOccurrences(String word) {
        return this.getWords().stream()
                .filter(w -> w.getProcessedText().contains(word))
                .count();
    }
    
    public String getSentenceString() { return sentence; }
    
    //public String toStringLast() {
    //    DecimalFormat format = new DecimalFormat("#.###");
    //    DecimalFormatSymbols symbols = new DecimalFormatSymbols();
    //    symbols.setDecimalSeparator('.');
    //    format.setDecimalFormatSymbols(symbols);
    //
    //    StringBuilder sb = new StringBuilder("\t\t|-> Sentence: ");
    //    sb.append("\"").append(sentence).append("\"");
    //    sb.append("\n");
    //
    //    for (AbsWord w : words) {
    //        sb.append("\t\t|-> ");
    //        sb.append(w.toString());
    //        sb.append("\n");
    //    }
    //    sb.append("\t\t|-> Sentence sentiment value: ").append(format.format(sentiment));
    //    return sb.toString();
    //}
    static final DecimalFormat format = new DecimalFormat("#.###");
    
    static final DecimalFormatSymbols symbols = new DecimalFormatSymbols();
    
    @Override
    public String toString() {
        return sentence;
        //symbols.setDecimalSeparator('.');
        //format.setDecimalFormatSymbols(symbols);
        //
        //StringBuilder sb = new StringBuilder("\t|\t|-> Sentence: ");
        //sb.append("\"").append(sentence).append("\"");
        //sb.append("\n");
        //
        //for (AbsWord w : words) {
        //    sb.append("\t|\t|-> ");
        //    sb.append(w.toString());
        //    sb.append("\n");
        //}
        //sb.append("\t|\t|-> Sentence sentiment value: ").append(format.format(-1));
        //
        //sb.append("\n\t|");
        //return sb.toString();
    }
    
    public String getSentence() {
        return this.sentence;
    }
}
