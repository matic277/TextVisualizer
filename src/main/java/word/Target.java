package word;

import java.util.Collections;
import java.util.Map;

public class Target extends AbsWord {

    public Target(String source) {
        super(source, null, "TRG");
        super.processedText = source.substring(1);
    }
    
    public static boolean isType(String s) {
        if (s.charAt(0) == '@') return true;
        return false;
    }
    
    @Override
    public double getImagery() {
        throw new RuntimeException("Unsupported getImageryfor class " + this.getClass().getSimpleName());
    }
    
    @Override
    public double getActivation() {
        throw new RuntimeException("Unsupported getActivation class " + this.getClass().getSimpleName());
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
