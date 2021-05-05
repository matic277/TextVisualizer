package dictionary;


import word.Acronym;

import java.io.IOException;

public class AcronymDictionary extends AbsDictionary {
    
    public AcronymDictionary(String relativeFilePath) throws IOException {
        super();
        buildHashtable(relativeFilePath, "acronyms", 0, 100);
        checkIntegrity();
    }
    
    private void checkIntegrity() {
        // does not contain '-/\& or space
        String strangeChars = "¨!\"#$%()=?*ÐŠÈÆŽŠðšæèž:;_~¡^¢°²`ÿ´½¨¸+*<>¤ßè×÷â€¦™«";
        super.checkIntegrity(strangeChars);
    }
    
    @Override
    public void processLine(String line) {
        // data: acronym,full text
        line = line.toLowerCase();
        String[] tokens = line.split(",");

        hashTable.put(tokens[0], new Acronym(tokens[0], tokens[1], null));
    }
}
