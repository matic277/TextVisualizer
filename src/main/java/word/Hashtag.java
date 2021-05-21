package word;

public class Hashtag extends AbsMeasurableWord {
    
    String[] words;

    public Hashtag(String source, String processed) {
        super(source, null, "HTG");
        sourceText = source;

        // removes # from hashtag:
        // #something -> something
        processed = source.substring(1);
        super.processedText = processed;
        
        // splits by upper case
        words = processedText.split("(?=[A-Z])");
        
        // should split by upper-case chars: #HelloWorld -> [hello, word]
        // then find the most polarizing word and return its value??
        // (return the value of the most positive word or the most negative word)
        // TODO: implement something
        this.pleasantness = 0;
    }
    
    public static boolean isType(String s) {
        if (s.charAt(0) == '#') return true;
        return false;
    }

    @Override
    public boolean checkIntegrity() {
        return true;
    }
    
    public String[] getListOfWords() {
        return words;
    }
    
    @Override
    public String toString() {
        String defaultToString = super.toString();
        String[] tokens = defaultToString.split("]");
        return tokens[0] + ", words:" + wordsToString() + "]";
    }
    
    private String wordsToString() {
        String wordsStr = "('";
        for (int i=0; i<words.length-1; i++) {
            wordsStr += words[i] + "', '";
        }
        wordsStr += words[words.length-1] + "')";
        return wordsStr;
    }
}
