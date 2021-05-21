package word;


import dictionary.DictionaryCollection;
import dictionary.EmojiDictionary;
import dictionary.IDictionary;

public class Emoji extends AbsMeasurableWord {
    
    // when tokenizing
    public Emoji(String source, String processed) {
        super(source, processed, "EMJ");
        
        EmojiDictionary emojiDictionary = (EmojiDictionary) DictionaryCollection.getDictionaryCollection().getEmojiDictionary();
        this.processedText = emojiDictionary.getEntry(source).getProcessedText().replace(" ", "-");
        this.pleasantness = emojiDictionary.getEntry(source).getPleasantness();
        
        // Update
        this.statsMap.put(MapKey.PLEASANTNESS, this.pleasantness+"");
        this.statsMap.put(MapKey.PROCESSED, this.processedText+"");
    }
    
    //when building hastable dictionary
    public Emoji(String codepoint, String description, String sentiment) {
        super(codepoint, description, "EMJ");
        super.tag = "EMJ";
        super.pleasantness = Double.parseDouble(sentiment);
    }
    
    public static boolean isType(String str) {
        IDictionary emojiDictionary = DictionaryCollection.getDictionaryCollection().getEmojiDictionary();
        return emojiDictionary.contains(str);
    }

    @Override
    public boolean checkIntegrity() {
        if (sourceText.length() == 7  || sourceText.length() == 6) {
            return true;
        }
        return false;
    }
    
    @Override
    public String toString() {
        String defaultToString = super.toString();
        String[] tokens = defaultToString.split("]");
        return tokens[0] + ", " + "]";
    }
}
