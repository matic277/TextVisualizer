package word;

public class Phrase extends AbsMeasurableWord {
    
    public Phrase(String source) {
        super(source, null);
    }

    @Override
    public String getTag() {
        return "<PHR>";
    }

    @Override
    public boolean checkIntegrity() {
        if (sourceText.length() > 5 && sourceText.contains(" ") && pleasantness >= -1 && pleasantness <= 1)
            return true;
        return false;
    }
    
//	@Override
//	public String toString() {
//		String defaultStr = super.toString();
//		String[] t = defaultStr.split("]");
//		return t[0] + ", " 
//	}
}
