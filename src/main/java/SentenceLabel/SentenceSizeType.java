package SentenceLabel;

import java.util.function.*;

public enum SentenceSizeType {
    LOGARITHMIC(0, "Logarithmic", (totalWords, wordSize) -> (int)customLog(1.035, totalWords * wordSize)),
    LINEAR(1, "Linear", (totalWords, wordSize) -> totalWords * wordSize)
    ;
    
    int id;
    String displayVal;
    
    IntBinaryOperator sizeDeterminator;
    
    SentenceSizeType(int id_, String dv, IntBinaryOperator sd) { id=id_; displayVal=dv; sizeDeterminator=sd; }
    
    private static double customLog(double base, double logNumber) {
        return Math.log(logNumber) / Math.log(base);
    }
    
    @Override
    public String toString() { return this.displayVal; }
}
