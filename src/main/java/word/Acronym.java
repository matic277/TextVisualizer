package word;


import dictionary.DictionaryCollection;
import dictionary.IDictionary;

import java.text.DecimalFormat;
import java.util.Arrays;

public class Acronym extends AbsMeasurableWord {
    
                            // example acronym: "jk"
                            // "jk" (from super.sourceText)
    String fullText;        // "just kidding"
    String[] listOfWords;   // {"just", "kidding"}
    
    // use when building dictionary hashtable
    // String x is useless but something is needed to differentiate
    // between the two constructors - this is dumb, TODO: fix
    public Acronym(String acronymn, String fullText, String x) {
        super(acronymn, null, "ACR");
        
        this.fullText = fullText;
        
        this.statsMap.put(MapKey.TOKENS, Arrays.deepToString(listOfWords));
        
        // process the full-text pleasantness
        // with the use of Whissell dictionary
        calculatePleasantness();
        
        // TODO: should update imagery, activation aswel?
        // update
        this.statsMap.put(MapKey.PLEASANTNESS, this.pleasantness+"");
    }
    
    // use when tokenizing
    public Acronym(String source, String processed) {
        super(source, processed, "ACR");
        
        Acronym a = (Acronym) DictionaryCollection.getDictionaryCollection().getAcronymDictionary().getEntry(processed);
        this.fullText = a.getFullText();
        
        // process the full-text pleasantness
        // with the use of Whissell dictionary
        calculatePleasantness();
    
        // TODO: should update imagery, activation aswel?
        // update
        this.statsMap.put(MapKey.PLEASANTNESS, this.pleasantness+"");
    }
    
    public static boolean isType(String s) {
        return DictionaryCollection.getDictionaryCollection().getAcronymDictionary().contains(s);
    }
    
    private void calculatePleasantness() {
        fullText = fullText.toLowerCase();
        listOfWords = fullText.split(" ");
        
        IDictionary dictionary = DictionaryCollection.getDictionaryCollection().getWhissellDictionary();
        
        int positiveWordsNum = 0;
        int negativeWordsNum = 0;
        int neutralWordsNum = 0;
        
        double negativeWordsSum = 0;
        double positiveWordsSum = 0;
        double neutralWordsSum = 0;
        
        double negativeWordsAvg = 0;
        double positiveWordsAvg = 0;
        double neutralWordsAvg = 0;
        
        // TODO: CHANGED HERE
        for (String word : listOfWords) {
            if (dictionary.contains(word)) {
                AbsMeasurableWord absWord = (AbsMeasurableWord) dictionary.getEntry(word);
                double pleasantness = absWord.getPleasantness();
                
                if (pleasantness < NEUTRAL_THRESHOLD) {
                    negativeWordsNum++;
                    negativeWordsSum += pleasantness;
                } else if (pleasantness < POSITIVE_THRESHOLD) {
                    neutralWordsNum++;
                    neutralWordsSum += pleasantness;
                } else {
                    positiveWordsNum++;
                    positiveWordsSum += pleasantness;
                }
            } else {
                neutralWordsNum++;
            }
        }
        
        negativeWordsAvg = negativeWordsSum / negativeWordsNum;
        positiveWordsAvg = positiveWordsSum / positiveWordsNum;
        neutralWordsAvg =  neutralWordsSum  / neutralWordsNum;
        
        // some could be NaN (division by zero in the code above)
        negativeWordsAvg = (Double.isNaN(negativeWordsAvg))? 0 : negativeWordsAvg;
        positiveWordsAvg = (Double.isNaN(positiveWordsAvg))? 0 : positiveWordsAvg;
        neutralWordsAvg =  (Double.isNaN(neutralWordsAvg))? 0 : neutralWordsAvg;;
        
        double totalAvg = (negativeWordsAvg + positiveWordsAvg + neutralWordsAvg) / 3;
        pleasantness = totalAvg;
        
        // TODO:
        // total overhead when calculating average
        // pleasantness, but leaving it for now as
        // these numbers might play a role in the future
        
        // PROBLEM:
        // example: lol -> ['lol' -> 'laughing out loud', P:0,073]
        // lol only gets a pleasantness of 0,073 which is completely
        // neutral... probably not good?
        // Take the pleasantness of the most pleasant word???
    }
    
    public String getFullText() {
        return fullText;
    }
    
    public String[] getListOfWords() {
        return listOfWords;
    }
    
    @Override
    public double getPleasantness() {
        return pleasantness;
    }
    
    @Override
    public double getActivation() {
        return -2;
    }
    
    @Override
    public double getImagery() {
        return -2;
    }
    
    @Override
    public String toString() {
        DecimalFormat format = new DecimalFormat("#.###");
        return "[" + getTag() + ", " + getSentimentTag() + ", '" + sourceText + "' -> " + "'" + fullText + "'" + ", P:" + format.format(pleasantness) + "]";
    }
    
    @Override
    public boolean checkIntegrity() {
        if (sourceText.length() > 1 && checkValidValue(pleasantness)) {
            return true;
        }
        return false;
    }
}
