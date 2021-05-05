package dictionary;

import word.StopWord;

import java.io.IOException;

public class StopwordDictionary extends AbsDictionary {
    
    public StopwordDictionary(String relativeFilePath) throws IOException {
        super();
        buildHashtable(relativeFilePath, "stop-words", 0, 120);
        checkIntegrity();
    }
    
    private void checkIntegrity() {
        // does not contain '
        String strangeChars = " ¨!\"#$%&/()=?*ÐŠÈÆŽŠðšæèž:;_~¡^¢°²`ÿ´½¨¸+-*\"<>-¤ßè×÷\\â€¦™«";
        super.checkIntegrity(strangeChars);
    }
    
    @Override
    public void processLine(String line) {
        hashTable.put(line, new StopWord(line, null));
    }

}
