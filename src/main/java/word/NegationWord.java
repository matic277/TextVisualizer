package word;

import dictionary.DictionaryCollection;

import java.util.Collections;
import java.util.Map;

public class NegationWord extends AbsWord {

    public NegationWord(String source, String processed) {
        super(source, processed, "NEG");
        super.tag = "NEG";
    }
    
    public static boolean isType(String s) {
        return DictionaryCollection.getDictionaryCollection().getNegationwordDictionary().contains(s);
    }
    
    @Override
    public double getSentimentValue() {
        throw new RuntimeException("Unsupported getSentiment for class " + this.getClass().getSimpleName());
    }
    
    @Override
    public Map<AbsMeasurableWord.MapKey, String> getStatsMap() {
        return Collections.emptyMap();
    }
    
    @Override
    public boolean checkIntegrity() {
        if (sourceText.length() < 2 || sourceText.length() > 10) return false;
        return true;
    }
}
