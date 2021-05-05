package word;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public abstract class AbsMeasurableWord extends AbsWord {
    
    // magnitude, how much should
    // pleasantness get amplified by
    protected double magnitude = 1.3;
    
    public static final double POSITIVE_THRESHOLD = 0.3;
    public static final double NEUTRAL_THRESHOLD = -0.3;
    
    // for all: [-1, 1]
    protected double pleasantness = 0;  // (unpleasant) - (pleasant)
    protected double activation = 0;    // (passive) - (active)
    protected double imagery = 0;       // (difficult to form a mental picture of this word) - (easy to form a mental picture)
    
    public AbsMeasurableWord(String source, String processed) {
        super(source, processed);
    }
    
    @Override
    public boolean hasSentimentValue() {
        return true;
    }
    
    public boolean isPositivePleasantness() {
        return pleasantness > POSITIVE_THRESHOLD;
    }
    
    public boolean isNeutralPleasantness() {
        return pleasantness >= NEUTRAL_THRESHOLD && pleasantness <= POSITIVE_THRESHOLD;
    }
    
    public boolean isNegativePleasantness() {
        return pleasantness < NEUTRAL_THRESHOLD;
    }
    
    public void magnifyPleasantness() {
        pleasantness *= magnitude;
        if (pleasantness > 1) {
            pleasantness = 1;
        }
        else if (pleasantness < -1) {
            pleasantness = -1;
        }
    }
    
    protected String getSentimentTag() {
        if (isNeutralPleasantness()) return "(NEU)";
        if (isPositivePleasantness()) return "(POS)";
        return "(NEG)";
    }
    
    protected boolean checkValidValue(double value) {
        return value >= -1 && value <= 1;
    }
    
    public void setPleasantness(double pleasantness) {
        this.pleasantness = pleasantness;
        if (this.pleasantness < -1) this.pleasantness = -1;
        else if (this.pleasantness > 1) this.pleasantness = 1;
    }
    
    public void flipPleasantness() {
        pleasantness = -pleasantness;
    }
    
    public double getPleasantness() {
        return pleasantness;
    }
    
    public double getActivation() {
        return activation;
    }
    
    public double getImagery() {
        return imagery;
    }
    
    @Override
    public double getSentimentValue() {
        return this.pleasantness;
    }
    
    @Override
    public String toString() {
        DecimalFormat format = new DecimalFormat("#.###");
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator('.');
        format.setDecimalFormatSymbols(symbols);
        return "[" + getTag() + ", " + getSentimentTag() +
                ", src:'" + sourceText +
                "', prc:'" + processedText +
                "', P:" + format.format(pleasantness) +
                ", A:" + format.format(activation) +
                ", I:" + format.format(imagery) +"]";
    }
}
