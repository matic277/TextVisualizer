package word;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Map;

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
    
    public AbsMeasurableWord(String source, String processed, String tag) {
        super(source, processed, tag);
        
        updateKeyMap();
    }
    
    public void updateKeyMap() {
        statsMap.put(MapKey.PLEASANTNESS, this.pleasantness+"");
        statsMap.put(MapKey.ACTIVATION, this.activation+"");
        statsMap.put(MapKey.IMAGERY, this.imagery+"");
        statsMap.put(MapKey.SOURCE, this.sourceText+"");
        statsMap.put(MapKey.PROCESSED, this.processedText+"");
        statsMap.put(MapKey.TOKENS, "-");
        statsMap.put(MapKey.TAG, this.getTag());
    }
    
    @Override public boolean hasSentimentValue() { return true; }
    @Override public boolean hasImageryValue() { return true; }
    @Override public boolean hasActivationValue() { return true; }
    
    // PLEASANTNESS
    public boolean isPositivePleasantness() { return pleasantness > POSITIVE_THRESHOLD; }
    public static boolean isPositivePleasantness(double pleasantness) { return pleasantness > POSITIVE_THRESHOLD; }
    
    public boolean isNeutralPleasantness() { return pleasantness >= NEUTRAL_THRESHOLD && pleasantness <= POSITIVE_THRESHOLD; }
    public static boolean isNeutralPleasantness(double pleasantness) { return pleasantness >= NEUTRAL_THRESHOLD && pleasantness <= POSITIVE_THRESHOLD; }
    
    public boolean isNegativePleasantness() { return pleasantness < NEUTRAL_THRESHOLD; }
    public static boolean isNegativePleasantness(double pleasantness) { return pleasantness < NEUTRAL_THRESHOLD; }
    
    // IMAGERY
    public boolean isHighImagery() { return imagery > POSITIVE_THRESHOLD; }
    public static boolean isHighImagery(double imagery) { return imagery > POSITIVE_THRESHOLD; }
    
    public boolean isNeutralImagery() { return imagery >= NEUTRAL_THRESHOLD && imagery <= POSITIVE_THRESHOLD; }
    public static boolean isNeutralImagery(double imagery) { return imagery >= NEUTRAL_THRESHOLD && imagery <= POSITIVE_THRESHOLD; }
    
    public boolean isLowImagery() { return imagery < NEUTRAL_THRESHOLD; }
    public static boolean isLowImagery(double imagery) { return imagery < NEUTRAL_THRESHOLD; }
    
    // ACTIVATION
    public boolean isHighActivation() { return activation > POSITIVE_THRESHOLD; }
    public static boolean isHighActivation(double activation) { return activation > POSITIVE_THRESHOLD; }
    
    public boolean isNeutralActivation() { return activation >= NEUTRAL_THRESHOLD && imagery <= POSITIVE_THRESHOLD; }
    public static boolean isNeutralActivation(double activation) { return activation >= NEUTRAL_THRESHOLD && activation <= POSITIVE_THRESHOLD; }
    
    public boolean isLowActivation() { return activation < NEUTRAL_THRESHOLD; }
    public static boolean isLowActivation(double activation) { return activation < NEUTRAL_THRESHOLD; }
    
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
    
    protected String getActivationTag() {
        if (isNeutralActivation()) return "(NEU)";
        if (isHighActivation()) return "(HIG)";
        return "(LOW)";
    }
    
    protected String getImageryTag() {
        if (isNeutralImagery()) return "(NEU)";
        if (isHighImagery()) return "(HIG)";
        return "(LOW)";
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
    public Map<MapKey, String> getStatsMap() {
        return this.statsMap;
    }
    
    public static final DecimalFormat format = new DecimalFormat("#.###"); {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator('.');
        format.setDecimalFormatSymbols(symbols);
    }
    
    @Override
    public String toString() {
        return "[" + getTag() + ", " + getSentimentTag() +
                ", src:'" + sourceText +
                "', prc:'" + processedText +
                "', P:" + format.format(pleasantness) +
                ", A:" + format.format(activation) +
                ", I:" + format.format(imagery) +"]";
    }
}
