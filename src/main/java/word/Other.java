package word;

public class Other extends AbsWord {
    
    public Other(String source, String processed) {
        super(source, processed);
        super.tag = "OTR";
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
