package word;

import dictionary.DictionaryCollection;

import java.util.Collections;
import java.util.Map;

public class StopWord extends AbsWord {
    
    public StopWord(String source, String processed) {
        super(source, processed, "STP");
    }
    
    public static boolean isType(String s) {
        return DictionaryCollection.getDictionaryCollection().getStopwordDictionary().contains(s);
    }
    
    @Override
    public Map<AbsMeasurableWord.MapKey, String> getStatsMap() {
        return Collections.emptyMap();
    }
    
    @Override
    public double getSentimentValue() {
        throw new RuntimeException("Unsupported getSentiment for class " + this.getClass().getSimpleName());
    }
    
    @Override
    public boolean checkIntegrity() {
        if (sourceText.length() < 2 || sourceText.length() > 10) return false;
        return true;
    }
}
