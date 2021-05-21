package word;


import dictionary.DictionaryCollection;
import dictionary.SmileyDictionary;

public class Smiley extends AbsMeasurableWord {
    
    // when creating dictionary hashtable
    public Smiley(String source, String plesantness) {
        super(source, null, "SML");
        this.pleasantness = Double.parseDouble(plesantness);
    }
    
    // when tokenizing
    public Smiley(String source) {
        super(source, null, "SML");
        
        SmileyDictionary dictionary = (SmileyDictionary) DictionaryCollection.getDictionaryCollection().getSmileyDictionary();
        this.pleasantness = dictionary.getEntry(source).getPleasantness();
        
        // update
        statsMap.put(MapKey.PLEASANTNESS, this.pleasantness+"");
    }
    
    public static boolean isType(String s) {
        return DictionaryCollection.getDictionaryCollection().getSmileyDictionary().contains(s);
    }

    @Override
    public boolean checkIntegrity() {
        if (sourceText.length() > 1 && pleasantness >= -1 && pleasantness <= 1) return true;
        return false;
    }
}
