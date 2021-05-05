package dictionary;


import word.AbsWord;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.stream.Stream;

public abstract class AbsDictionary implements IDictionary {
    
    HashMap<String, AbsWord> hashTable;
    
    public AbsDictionary() { }
    
    protected void buildHashtable(String relativeFilePath, String dictionaryName, int skipToLine, int hashtableSize) throws IOException {
        if (DictionaryCollection.printingMode) System.out.println("Creating dictionary of " + dictionaryName + "...");
        if (DictionaryCollection.printingMode) System.out.println("\t|-> Creating hashtable...");
        
        // hashtable:
        // key		-> string,
        // value	-> node(word and statistics)
        hashTable = new HashMap<String, AbsWord>(hashtableSize);
        //Charset.defaultCharset();
        Stream<String> linesToRead;
        try (Stream<String> lines = Files.lines(Paths.get(relativeFilePath), Charset.forName("ISO_8859_1"))) {
            linesToRead = lines.skip(skipToLine);
            linesToRead.forEachOrdered(
                line -> processLine(line)
            );
        }
        if (DictionaryCollection.printingMode) System.out.println("\t|-> Done.");
        if (DictionaryCollection.printingMode) System.out.println("\t|-> Number of entries: " + hashTable.size());
    }
    
    public abstract void processLine(String line);

    protected void checkIntegrity(String strangeChars) {
        if (DictionaryCollection.printingMode) System.out.println("\t|-> Checking integrity...");
        hashTable.forEach((word, node) -> {
            if (!node.checkIntegrity()) {
                if (DictionaryCollection.printingMode) System.out.println("\t|\t|-> Maybe a bad node: " + node.toString());
            }
            String s = node.getSourceText();
            char[] chars = s.toCharArray();
            
            loop:
            for (int i=0; i<strangeChars.length(); i++) {
                for (int j=0; j<chars.length; j++) {
                    if (chars[j] == strangeChars.charAt(i)) {
                        if (DictionaryCollection.printingMode) System.out.println("\t|\t|-> Maybe a bad node (suspect char: '"+ chars[j]+"'): "+ node.toString());
                        break loop;
                    }
                }
            }
        });
        if (DictionaryCollection.printingMode) System.out.println("\t\\-> Done.");
        if (DictionaryCollection.printingMode) System.out.println();
    }

    @Override
    public boolean contains(String key) {
        return hashTable.containsKey(key);
    }

    @Override
    public AbsWord getEntry(String key) {
        return hashTable.get(key);
    }
    
    public HashMap<String, AbsWord> getHashmap() {
        return hashTable;
    }

}
