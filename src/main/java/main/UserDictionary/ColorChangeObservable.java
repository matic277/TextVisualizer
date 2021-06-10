package main.UserDictionary;

// Used for Word to signal it's user, SentenceLabelWord,
// that the words color has changed
public interface ColorChangeObservable {
    
    public void addObserver(ColorChangeObserver observer);
    public void notifyColorChange();
    
}
