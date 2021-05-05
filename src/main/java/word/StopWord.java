package word;

import dictionary.DictionaryCollection;

public class StopWord extends AbsWord {
    
    public StopWord(String source, String processed) {
        super(source, processed);
        super.tag = "STP";
    }
    
    public static boolean isType(String s) {
        return DictionaryCollection.getDictionaryCollection().getStopwordDictionary().contains(s);
    }

    @Override
    public boolean checkIntegrity() {
        if (sourceText.length() < 2 || sourceText.length() > 10) return false;
        return true;
    }
}
