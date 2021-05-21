package word;


import dictionary.DictionaryCollection;
import dictionary.WhissellDictionary;

public class AffectionWord extends AbsMeasurableWord {
    
    // use this constructor when reading from file and building hashtable
    public AffectionWord(String sourceText, String pleasantness, String activation, String imagery) {
        super(sourceText, null, "AFT");
        super.tag = "AFT";
        
        // -2 for normalizing from [1, 3] to [-1, 1]
        this.pleasantness = Double.parseDouble(pleasantness) - 2;
        this.activation = Double.parseDouble(activation) - 2;
        this.imagery = Double.parseDouble(imagery) - 2;
    
        statsMap.put(MapKey.PLEASANTNESS, this.pleasantness+"");
        statsMap.put(MapKey.ACTIVATION, this.activation+"");
        statsMap.put(MapKey.IMAGERY, this.imagery+"");
    }
    
    // use this constructor when tokenizing
    public AffectionWord(String sourceText, String processedText) {
        super(sourceText, processedText, "AFT");
        super.tag = "AFT";
        
        WhissellDictionary dictionary = (WhissellDictionary) DictionaryCollection.getDictionaryCollection().getWhissellDictionary();
        AbsMeasurableWord word = dictionary.getEntry(processedText);
        
        this.pleasantness = word.getPleasantness();
        this.activation = word.getActivation();
        this.imagery = word.getImagery();
    }

    public static boolean isType(String s) {
        return DictionaryCollection.getDictionaryCollection().getWhissellDictionary().contains(s);
    }
    
    @Override
    public boolean checkIntegrity() {
        if (sourceText.length() > 1 && checkValidValue(pleasantness) && checkValidValue(activation) && checkValidValue(imagery)) {
            return true;
        }
        return false;
    }
}
