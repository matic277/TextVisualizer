package dictionary;

import word.AbsMeasurableWord;
import word.Smiley;

import java.io.IOException;

public class SmileyDictionary extends AbsDictionary {

    public SmileyDictionary(String relativeFilePath) throws IOException {
        super();
        buildHashtable(relativeFilePath, "smileys", 3, 150);
        checkIntegrity();
    }
    
    private void checkIntegrity() {
        String strangeChars = " ¨!\"#$%&?ÐŠÈÆŽŠðšæèž~¡¢°²`ÿ´½¨¸+¤ßè×÷â€¦™«";
        super.checkIntegrity(strangeChars);
    }
    
    @Override
    public void processLine(String line) {
        // data structure: smiley,pleasantness
        // â€‘ -> - (encoding problems)
        String[] tokens = line.split(",");
        tokens[0] = tokens[0].replace("â€‘", "-");

        
        hashTable.put(tokens[0], new Smiley(tokens[0], tokens[1]));
    }
    
    @Override
    public AbsMeasurableWord getEntry(String key) {
        return (AbsMeasurableWord) hashTable.get(key);
    }
}
