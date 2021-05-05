package word;

public class NGramEntry extends AbsWord {
    
    int seqNumber;

    public NGramEntry(String source, int sequenceNumber) {
        super(source, null);
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
    public String toString() {
        return "["+getTag()+" '"+sourceText+"' seqNum:'" + seqNumber + "']";
    }
}
