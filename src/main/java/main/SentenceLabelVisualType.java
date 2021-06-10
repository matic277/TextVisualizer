package main;

import SentenceLabel.SentenceLabelBuilder;

public enum SentenceLabelVisualType {
    
    TRUE_POSITION(0, "True position"),
    DEFAULT(1, "Default")
    ;
    
    int id; String displayVal;
    
    SentenceLabelVisualType(int id_, String dv) { id=id_; displayVal=dv; }
    
    @Override
    public String toString() { return this.displayVal; }
}
