package word;

import java.util.Collections;
import java.util.Map;

public class NGramEntry extends AbsWord {
    
    int seqNumber;
    
    public NGramEntry(String source, int sequenceNumber) {
        super(source, null, "NGM");
        this.seqNumber = sequenceNumber;
        this.tag = "NGM";
        // TODO Auto-generated constructor stub
    }
    
    public int getSequenceNumber() {
        return seqNumber;
    }
    
    @Override
    public boolean checkIntegrity() {
        // TODO Auto-generated method stub
        return true;
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
    public String toString() {
        return "["+getTag()+" '"+sourceText+"' seqNum:'" + seqNumber + "']";
    }
}
