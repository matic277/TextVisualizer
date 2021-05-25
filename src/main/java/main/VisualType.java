package main;

public enum VisualType {
    
    PLEASANTNESS(0, "Pleasantness"),
    IMAGERY(1, "Imagery"),
    ACTIVATION(2, "Activation")
    ;
    
    int id;
    String displayValue;
    
    VisualType(int id_, String dispVal) { id=id_; displayValue=dispVal; }
    
    
    @Override
    public String toString() { return displayValue; }
}
