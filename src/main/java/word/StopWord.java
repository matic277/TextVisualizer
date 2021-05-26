package word;

import dictionary.DictionaryCollection;

import java.util.Collections;
import java.util.Map;

public class StopWord extends AbsWord {
    
    public StopWord(String source, String processed) {
        super(source, processed, "STP");
        
        updateKeyMap();
    }
    
    public static boolean isType(String s) {
        return DictionaryCollection.getDictionaryCollection().getStopwordDictionary().contains(s);
    }
    
    @Override
    public double getSentimentValue() {
        throw new RuntimeException("Unsupported getSentiment for class " + this.getClass().getSimpleName());
    }
    
    @Override
    public double getImagery() {
        throw new RuntimeException("Unsupported getImageryfor class " + this.getClass().getSimpleName());
    }
    
    @Override
    public double getActivation() {
        throw new RuntimeException("Unsupported getActivation class " + this.getClass().getSimpleName());
    }
    
    @Override
    public boolean checkIntegrity() {
        if (sourceText.length() < 2 || sourceText.length() > 10) return false;
        return true;
    }
}
