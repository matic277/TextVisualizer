package dictionary;

import main.UserDictionary.UserDictionary;
import main.UserDictionary.Word;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class UserDictionaryCollection {
    
    // Singleton
    private static final UserDictionaryCollection instance = new UserDictionaryCollection();
    
    public static UserDictionaryCollection get() { return instance; }
    
    // dictName -> dictionary
    Map<String, UserDictionary> dictMap;
    
    private UserDictionaryCollection() {
        dictMap = new HashMap<>();
    }
    
    public UserDictionary getDictionary(String name) { return this.dictMap.get(name); }
    
    public void addDictionary(String name, UserDictionary dict) {
        if (dictMap.containsKey(name)) throw new RuntimeException("User dictionary \"" + name + "\" already exists!");
        dictMap.put(name, dict);
    }
    
    public Set<String> getDictionaryNames() {
        return dictMap.keySet();
    }
    
    public boolean containsWord(Word w) {
        boolean contains = false;
        for (UserDictionary dict : dictMap.values()) {
            contains |= dict.containsWord(w);
        }
        return contains;
    }
    
}
