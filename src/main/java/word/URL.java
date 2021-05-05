package word;

public class URL extends AbsWord {
    
    String sourceText;
    
    public URL(String source) {
        super(source, null);
        super.tag = "URL";
    }
    
    public static boolean isType(String s) {
        // https://rubular.com/r/eGPe4bGlwMd98E
        String regExPattern = "(https?:\\/\\/(?:www\\.|(?!www))[a-zA-Z0-9][a-zA-Z0-9-]+[a-zA-Z0-9]\\."
                + "[^\\s]{2,}|www\\.[a-zA-Z0-9][a-zA-Z0-9-]+[a-zA-Z0-9]\\.[^\\s]{2,}|https?:\\/\\/(?:"
                + "www\\.|(?!www))[a-zA-Z0-9]+\\.[^\\s]{2,}|www\\.[a-zA-Z0-9]+\\.[^\\s]{2,})";
        return s.matches(regExPattern);
    }
    
    @Override
    public double getSentimentValue() {
        throw new RuntimeException("Unsupported getSentiment for class " + this.getClass().getSimpleName());
    }
    
    @Override
    public boolean checkIntegrity() {
        return true;
    }
}
