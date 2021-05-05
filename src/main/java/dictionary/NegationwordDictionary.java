package dictionary;

import word.NegationWord;

import java.io.IOException;

public class NegationwordDictionary extends AbsDictionary {
    
    public NegationwordDictionary(String relativeFilePath) throws IOException {
        super();
        buildHashtable(relativeFilePath, "negation-words", 0, 30);
        checkIntegrity();
    }
    
    private void checkIntegrity() {
        // does not contain '
        String strangeChars = " �!\"#$%&/()=?*Њ�Ǝ����:;_~�^���`�����+-*\"<>-�����\\…��";
        super.checkIntegrity(strangeChars);
    }

    @Override
    public void processLine(String line) {
        // data structure: word
        // ' -> ’ (file reading and encoding problem)
        String processedLine = line.toLowerCase();
        processedLine = processedLine.replace("’", "'");

        hashTable.put(processedLine, new NegationWord(processedLine, null));
    }
}
