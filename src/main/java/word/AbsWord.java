package word;

public abstract class AbsWord {
    
    protected String sourceText;
    protected String processedText;
    
    protected String tag;
    
    public AbsWord(String source, String processed) {
        this.sourceText = source;
        this.processedText = processed;
    }
    
    public boolean hasSentimentValue() {
        return false;
    }
    
    public abstract double getSentimentValue();
    
    public abstract boolean checkIntegrity();
    
    public String getTag() {
        return "<" + tag + ">";
    }

    @Override
    public String toString() {
        return "[" + getTag() + ", src:'" + sourceText + "', prc:'" + processedText + "']";
    }
    public void setSourceText(String newStr) { this.sourceText = newStr; }
    public void setProcessedText(String newStr) { this.sourceText = newStr; }
    public String getSourceText() { return this.sourceText; }
    public String getProcessedText() { return this.processedText; }
}
