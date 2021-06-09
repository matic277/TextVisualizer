package main;

import SentenceLabel.SentenceLabelBuilder;

public enum SentenceLabelVisualType {
    
    DEFAULT(1, "Default"),
    TRUE_POSITION(0, "True position")
    ;
    
    int id; String displayVal;
    
    SentenceLabelVisualType(int id_, String dv) { id=id_; displayVal=dv; }
    
    @Override
    public String toString() { return this.displayVal; }
}
