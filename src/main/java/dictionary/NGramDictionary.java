package dictionary;

import word.AbsWord;
import word.NGramEntry;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.stream.Stream;

public class NGramDictionary extends AbsDictionary {

//	String positiveUnigramsPath = "datasets/ngrams/OrderedUniquePositive_unigrams.txt";
//	String neutralUnigramsPath = "datasets/ngrams/OrderedUniqueNeutral_unigrams.txt";
//	String negativeUnigramsPath = "datasets/ngrams/OrderedUniqueNegative_unigrams.txt";
//	
//	String  positiveBigramsPath = "datasets/ngrams/OrderedUniquePositive_bigrams.txt";
//	String  neutralBigramsPath = "datasets/ngrams/OrderedUniqueNeutral_bigrams.txt";
//	String  negativeBigramsPath = "datasets/ngrams/OrderedUniqueNegative_bigrams.txt";
//	
//	String  positiveTrigramsPath = "datasets/ngrams/OrderedUniquePositive_trigrams.txt";
//	String  neutralTrigramsPath = "datasets/ngrams/OrderedUniqueNeutral_trigrams.txt";
//	String  negativeTrigramsPath = "datasets/ngrams/OrderedUniqueNegative_trigrams.txt";
    
    String[] filePaths = {
        "datasets/ngrams/OrderedUniquePositive_unigrams.txt",
        "datasets/ngrams/OrderedUniqueNeutral_unigrams.txt",
        "datasets/ngrams/OrderedUniqueNegative_unigrams.txt",
        
        "datasets/ngrams/OrderedUniquePositive_bigrams.txt",
        "datasets/ngrams/OrderedUniqueNeutral_bigrams.txt",
        "datasets/ngrams/OrderedUniqueNegative_bigrams.txt",
        
        "datasets/ngrams/OrderedUniquePositive_trigrams.txt",
        "datasets/ngrams/OrderedUniqueNeutral_trigrams.txt",
        "datasets/ngrams/OrderedUniqueNegative_trigrams.txt"
    };
    
    final int linesToRead = 10;
    final int hashtableSize = linesToRead * filePaths.length;
    
    public NGramDictionary() throws IOException {
        super();
        
        buildHashtable();
    }
    
    static int seqNum = 0;
    static int linesRead = 0;
    
    protected void buildHashtable() throws IOException {
        if (DictionaryCollection.printingMode) System.out.println("Creating dictionary of n-grams...");
        if (DictionaryCollection.printingMode) System.out.println("\t|-> Creating hashtables...");
        
        // hashtable:
        // key		-> string (ngram),
        // value	-> ngram (*seqNum* -> feature sequence number)
        hashTable = new HashMap<String, AbsWord>(hashtableSize);
        
        
        for (int i=0; i<filePaths.length; i++) {
            if (DictionaryCollection.printingMode) System.out.print("\t|\t|-> Creating " + filePaths[i] + "...");
            linesRead = 0;
            try (Stream<String> lines = Files.lines(Paths.get(filePaths[i]), Charset.defaultCharset())) {
                lines.forEachOrdered(
                    line -> {
                        if (linesRead > (linesToRead-1)) return;
//                        System.out.println(" -> " + seqNum + ": " + line);
                        hashTable.put(line, new NGramEntry(line, seqNum));
                        seqNum++;
                        linesRead++;
                    }
                );
            }
            if (DictionaryCollection.printingMode) System.out.println(" done.");
        }
        
        if (DictionaryCollection.printingMode) System.out.println("\t|-> Done.");
        if (DictionaryCollection.printingMode) System.out.println("\t\\-> Number of entries: " + hashTable.size());
        
        for (AbsWord g : hashTable.values()) {
            System.out.println(g.toString());
        }
    }

    @Override
    public void processLine(String line) { }
}
