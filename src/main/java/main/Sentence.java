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
    private double imagery;
    private double activation;
    
    public int sentenceNumber;
    
    private final double magnifyValue = 2;
    public int numOfNegativeWords,
            sumOfNegativeWords,
            numOfNeutralWords,
            sumOfNeutralWords,
            numOfPositiveWords,
            sumOfPositiveWords;
    
    public int numOfHighImageryWords,
            numOfMediumImageryWords,
            numOfLowImageryWords;
    
    public int numOfHighActivationWords,
            numOfMediumActivationWords,
            numOfLowActivationWords;
    
    public int numOfUnrecognizedWords;
    
    public Sentence(String sentence, int sentenceNumber) {
        this.sentence = sentence;
        this.sentenceNumber = sentenceNumber;
        processSentence();
        calculateSentiment();
        
        for (AbsWord w : words) w.updateKeyMap();
    }
    
    private void processSentence() {
        Tokenizer t = new Tokenizer();
        
        // create word tokens
        String[] tokens = sentence.split(" ");
        words = t.classifyAndGetWords(tokens);
        
        doSomeStatistics();
    }
    
    private void doSomeStatistics() {
        this.imagery = 0;
        this.activation = 0;
        for (AbsWord word : words) {
            // AffectionWord, Hastag, Smiley, Acronym, Phrase, (Emoji)
            if (word instanceof AbsMeasurableWord mw) {
                double pleasantness = mw.getPleasantness();
                
                imagery += mw.getImagery();
                activation += mw.getActivation();
    
                if (mw.isNegativePleasantness()) {
                    numOfNegativeWords++;
                    sumOfNegativeWords += pleasantness;
                } else if (mw.isNeutralPleasantness()) {
                    numOfNeutralWords++;
                    sumOfNeutralWords += pleasantness;
                } else {
                    numOfPositiveWords++;
                    sumOfPositiveWords += pleasantness;
                }
    
                if (mw.isHighImagery()) {
                    numOfHighImageryWords++;
                } else if (mw.isMediumImagery()) {
                    numOfMediumImageryWords++;
                } else {
                    numOfLowImageryWords++;
                }
    
                if (mw.isHighActivation()) {
                    numOfHighActivationWords++;
                } else if (mw.isMediumActivation()) {
                    numOfMediumActivationWords++;
                } else {
                    numOfLowActivationWords++;
                }
            }
            // URL, Target, StopWord, Other, NegationWord
            else {
//                numOfNeutralWords++;
                numOfUnrecognizedWords++;
            }
        }
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
            if (w instanceof AbsMeasurableWord mw) {
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
    
    /**
     * Returns the sum of length of all words.
     * Ignores spaces, only considers length of processed words, not source words!
     */
    public double getLengthOfSentence() {
        return this.words.stream()
                .map(AbsWord::getProcessedWordLength)
                .reduce(0, Integer::sum)
                .doubleValue();
    }
    
    /**
     * Only check processedText, not source!
     * Check by using contains, not equals.
     */
    public boolean containsWord(String word) {
        return this.getWords().stream().anyMatch(w -> w.getProcessedText().contains(word));
    }
    
    public void magnifySentiment() { this.sentiment *= magnifyValue; }
    public double getSentiment() { return sentiment; }
    public double getImagery() { return imagery; }
    
    public double getActivation() { return activation; }
    
    public String getSentenceString() { return sentence; }
    
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
    static final DecimalFormat format = new DecimalFormat("#.###");
    
    static final DecimalFormatSymbols symbols = new DecimalFormatSymbols();
    
    @Override
    public String toString() {
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
    
    public String getSentence() {
        return this.sentence;
    }
}
