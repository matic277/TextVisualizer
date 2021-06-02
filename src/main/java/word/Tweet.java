package word;

import main.Sentence;
import main.Tokenizer;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.util.ArrayList;


public class Tweet {
    
    // source with original text
    String sourceText;
    
    // source where newline chars are removed
    String cleanSource;
    
    // sentences, words are classified
    ArrayList<Sentence> sentences;
    
    private String username;
    private String date;

//	private ArrayList<AbsWord> words;
    
    private int numOfNegativeWords = 0;
    private int numOfNeutralWords = 0;
    private int numOfPositiveWords = 0;
    
    private double sumOfNegativeWords = 0;
    private double sumOfNeutralWords = 0;
    private double sumOfPositiveWords = 0;
    
    private double sentimentValue;
    
    private int[] ngramFeatures;
    
    public LocalDate dateCollected;
    public int sentimentClass; // [-1, 0, 1]
    String lowercaseSourceText;
    
    public Tweet(String sourceText, String username) {
        this.sourceText = sourceText;
        this.username = (username == null)? "{UNKNOWN}" : username;
    }
    
    public Tweet(String sourceText, String username, String date) {
        this.date = date;
        this.sourceText = sourceText;
        this.username = (username == null)? "{UNKNOWN}" : username;
    }
    
    public Tweet(String sourceText, String username, LocalDate dateCollected) {
        this.dateCollected = dateCollected;
        this.sourceText = sourceText;
        this.username = (username == null)? "{UNKNOWN}" : username;
        this.lowercaseSourceText = sourceText.toLowerCase();
    }
    
    public boolean isPfizer() {
        return lowercaseSourceText.contains("pfizer") &&
            !lowercaseSourceText.contains("astrazeneca") &&
            !lowercaseSourceText.contains("moderna");
    }
    
    public boolean isModerna() {
        return lowercaseSourceText.contains("moderna") &&
                !lowercaseSourceText.contains("astrazeneca") &&
                !lowercaseSourceText.contains("pfizer");
    }
    
    public boolean isAstrazeneca() {
        return lowercaseSourceText.contains("astrazeneca") &&
                !lowercaseSourceText.contains("moderna") &&
                !lowercaseSourceText.contains("pfizer");
    }
    
    public boolean isMixed() {
        int c = 0;
        c += lowercaseSourceText.contains("pfizer") ? 1 : 0;
        c += lowercaseSourceText.contains("astrazeneca") ? 1 : 0;
        c += lowercaseSourceText.contains("moderna") ? 1 : 0;
        return c > 1;
    }
    
    public boolean isNone() {
        return !lowercaseSourceText.contains("moderna") &&
               !lowercaseSourceText.contains("astrazeneca") &&
               !lowercaseSourceText.contains("pfizer");
    }
    
    private void test() {
        boolean changedWordsSentiment = false;
        for (Sentence s : sentences)
        {
            ArrayList<AbsWord> words = s.getWords();
            // if a word has a negator in front of it,
            // flip its pleasantness value
            for (int i=0; i<words.size()-1; i++) {
                AbsWord word = words.get(i);
                
                if (word instanceof NegationWord) {
                    int ii = i + 1;
                    if (ii < words.size()) {
                        if (words.get(ii) instanceof AbsMeasurableWord) {
                            AbsMeasurableWord w = (AbsMeasurableWord) words.get(ii);
                            w.flipPleasantness();
                            changedWordsSentiment = true;
                            i++;
                            continue;
                        }
                    }
                    if (ii < words.size()) {
                        if (words.get(ii) instanceof AbsMeasurableWord) {
                            AbsMeasurableWord w = (AbsMeasurableWord) words.get(ii);
                            w.flipPleasantness();
                            changedWordsSentiment = true;
                            i += 2;
                            continue;
                        }
                    }
                }
            }
            
            // if a word is upper-case (or close to)
            // magnify its pleasantness value, except for emojis
            // (how close is defined in upper-case function)
            for (AbsWord w : words) {
                if (w instanceof AbsMeasurableWord && !(w instanceof Emoji) && isUpperCase(w.getSourceText())) {
                    AbsMeasurableWord mw = (AbsMeasurableWord) w;
                    mw.magnifyPleasantness();
                    changedWordsSentiment = true;
                }
            }
            
            if (changedWordsSentiment) {
                //changedWordsSentiment = false; (unnecessary?)
                s.calculateSentiment();
            }
            
            // if a sentence contains ! or ?
            // magnify its sentiment value
            if (s.getSentence().endsWith("?") || s.getSentence().endsWith("!"))
                s.magnifySentiment();
        }
    }
    
    public void processTweet() {
        String processedText;
        Tokenizer t = new Tokenizer();
        
        processedText = t.findEmojis(sourceText);
        cleanSource = processedText = t.trimIntoSingleLine(processedText);
        
        // at this point, text is in single line
        // and emojis are represented as unicodes
        sentences = t.splitIntoSentences(processedText);

        
        test();
        doSomeStatistics();
        
        // calculate sentiment after fully processing
        // sentences (like executing function *test.txt*)
        calculateSentiment();
    }
    
    // this method and getSentiment() are badly "designed"
    // TODO fix
    private void calculateSentiment() {
        double sentiment = 0;
        for (Sentence s : sentences) {
            double smileyEmojiSentiment = s.getSentimentValueOfEmojisAndSmileys();
            if (smileyEmojiSentiment < -0.3 || smileyEmojiSentiment > 0.3) s.magnifySentiment();
            sentiment += s.getSentiment();
        }
        this.sentimentValue = sentiment;
    }
    
//	private void buildNGramFeatures() {
//		ArrayList<String> ngramWords = new ArrayList<String>(words.size());
//		words.forEach(w -> {
//			if (w instanceof Emoji) return;
//			if (w instanceof URL) return;
//			if (w instanceof Target) return;
//			if (w instanceof Hashtag) return;
//			ngramWords.add((w.getProcessedText() == null || w.getProcessedText().length() == 0)? 
//					w.getSourceText() : w.getProcessedText());
//		});
//		
//		NGramDictionary dictionary = (NGramDictionary) DictionaryCollection.getDictionaryCollection().getNGramDictionary();
//		int numberOfFeatures = dictionary.getHashmap().size();
//		ArrayList<Integer> featureSeq = new ArrayList<Integer>(numberOfFeatures);
//		
//		for (int i=1; i<4; i++) {
//			NGram ngram = new NGram(i, ngramWords);
//			ArrayList<Gram> list = ngram.getListOfNGrams();
//			list.forEach(g -> {
//				if (dictionary.contains(g.ngram)) {
//					NGramEntry entry = (NGramEntry) dictionary.getEntry(g.ngram);
//					featureSeq.add(entry.getSequenceNumber());
//				}
//			});
//		}
//		
//		int[] featureList = new int[numberOfFeatures];
//		for (int i=0; i<featureList.length; i++) {
//			if (featureSeq.contains(new Integer(i)))
//				featureList[i] = 1;
//			else
//				featureList[i] = 0;
//		}
//		this.ngramFeatures = featureList;
//	}
    
    private void doSomeStatistics() {
        for (Sentence s : sentences)
        {
            for (AbsWord word : s.getWords()) {
                // AffectionWord, Hastag, Smiley, Acronym, Phrase, (Emoji)
                if (word instanceof AbsMeasurableWord) {
                    AbsMeasurableWord mw = (AbsMeasurableWord) word;
                    double pleasantness = mw.getPleasantness();
                    
                    if (mw.isNegativePleasantness()) {
                        numOfNegativeWords++;
                        sumOfNegativeWords += pleasantness;
                    } else if (mw.isNeutralPleasantness()) {
                        numOfNeutralWords++;
                        sumOfNeutralWords += pleasantness;
                        
                    } else {
                        numOfPositiveWords++;
                        sumOfPositiveWords += pleasantness;
                    }
                }
                
                // URL, Target, StopWord, Other, NegationWord
                else {
                    numOfNeutralWords++;
                }
            }
        }
    }
    
    private boolean isUpperCase(String word) {
        int uppercaseLettersNum = 0;
        for (int i=0; i<word.length(); i++) {
            if (Character.isUpperCase(word.charAt(i))) {
                uppercaseLettersNum++;
            }
        }
        double ratio = (double) uppercaseLettersNum / sourceText.length();
        return (ratio >= 0.4);
    }

    public static double positiveThreshold = 0.1;
    public static double negativeThreshold = -0.55;
    public static double threshold = 0.05;
    
    public int getSentimentThreeWay() {
        if (sentimentValue >= positiveThreshold) return 1;
        else if (sentimentValue <= negativeThreshold) return -1;
        else return 0;
    }
    
    public int getSentimentTwoWay() {
        return (sentimentValue > threshold)? 1 : -1;
    }
    
    public String getSourceText() {
        return sourceText;
    }
    
    public String getCleanSource() {
        return cleanSource;
    }
    
    public double getSentimentValue() {
        return this.sentimentValue;
    }
    
    public ArrayList<AbsWord> getWords() {
        ArrayList<AbsWord> words = new ArrayList<AbsWord>(sentences.size() * 5);
        for (Sentence s : sentences) words.addAll(s.getWords());
        return words;
    }
    
    public String getDate() {
        return this.date;
    }
    
    @Override
    public String toString() {
        DecimalFormat format = new DecimalFormat("#.###");
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator('.');
        format.setDecimalFormatSymbols(symbols);
        
        String s = "";
        s += "---------------------------\n";
        s += "Poster: " + username + "\n";
        s += "Source tweet:\n" + sourceText + "\n\n";
        s += "Clean tweet:\n" + cleanSource + "\n\n";
        s += "Tweet Sentences:\n";
        
        for (int i=0; i<sentences.size(); i++) {
            s += "\t|-> Sentence(" + (i+1) + ")\n";
            s += (i == sentences.size()-1)? sentences.get(i).toStringLast() + "\n" : sentences.get(i).toString() + "\n";
        }
        
        s += "\n";
        
        s += "Some statistics:\n";
        s += "\t|-> Num of neg words: " + numOfNegativeWords + "\n";
        s += "\t|-> Num of neu words: " + numOfNeutralWords + "\n";
        s += "\t|-> Num of pos words: " + numOfPositiveWords + "\n";
        s += "\t|-> Sum of neg words: " + format.format(sumOfNegativeWords) + "\n";
        s += "\t|-> Sum of neu words: " + format.format(sumOfNeutralWords) + "\n";
        s += "\t|-> Sum of pos words: " + format.format(sumOfPositiveWords) + "\n";
//		s += "\t|-> NGram features:   " + getNGramFeatures() + "\n";
        s += "\t|-> Sentiment value : " + format.format(sentimentValue) + "\n";
        s += "\t|-> 2-way class:      " + getSentimentTwoWay() + "\n";
        s += "\t\\-> 3-way class:      " + getSentimentThreeWay();
        
        s += "\n---------------------------\n";
        
        return s;
    }
    
//	public String getNGramFeatures() {
//		String s = "";
//		for (int i=0; i<ngramFeatures.length-1; i++) {
//			s += ngramFeatures[i] + ",";
//		}
//		s+= ngramFeatures[ngramFeatures.length - 1];
//		return s;
//	}
    
    public String getStatistics() {
        DecimalFormat format = new DecimalFormat("#.####");
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator('.');
        format.setDecimalFormatSymbols(symbols);
        String s = "";
        s = numOfPositiveWords + "," + numOfNeutralWords + "," + numOfNegativeWords + ",";
        s += format.format(sumOfPositiveWords) + "," + format.format(sumOfNeutralWords) + "," + format.format(sumOfNegativeWords);
        return s;
    }
}
