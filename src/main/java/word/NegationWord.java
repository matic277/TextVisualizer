package word;

import dictionary.DictionaryCollection;

public class NegationWord extends AbsWord {

    public NegationWord(String source, String processed) {
        super(source, processed);
        super.tag = "NEG";
    }
    
    public static boolean isType(String s) {
        return DictionaryCollection.getDictionaryCollection().getNegationwordDictionary().contains(s);
    }
    
    @Override
    public boolean checkIntegrity() {
        if (sourceText.length() < 2 || sourceText.length() > 10) return false;
        return true;
    }
}
