package word;

import java.util.Collections;
import java.util.Map;

public class Other extends AbsWord {
    
    public Other(String source, String processed) {
        super(source, processed, "OTR");
    }
    
    @Override
    public double getSentimentValue() {
        throw new RuntimeException("Unsupported getSentiment for class " + this.getClass().getSimpleName());
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
    public boolean checkIntegrity() {
        return true;
    }
}
