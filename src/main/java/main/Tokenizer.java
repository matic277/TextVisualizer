package main;


import dictionary.DictionaryCollection;
import dictionary.WhissellDictionary;
import word.*;

import java.util.ArrayList;
import java.util.stream.IntStream;


public class Tokenizer {
    
    // Class is a collection of functions
    private String suspects = "-'!? ¨“\"#$%&/()=*ÐŠÈÆŽŠðšæèž:;”“,_~¡^¢°²`ÿ´½¨¸.*\"<>¤ßè×÷\\â€¦™«";
    private String[] suspects2 = {
            "\"", "-", "'", "!", "\\?", " ", "¨", "#", "$", "\\.", "“", "”",
            "%", "&", "/", "\\(", "\\)", "=", "Ð", "Š", "È", "Æ", "Ž",
            "Š", "ð", "š", "æ", "è", "ž", ":", ";", "\\,", "_", "~", "¡", "^",
            "¢", "°", "²", "`", "ÿ", "´", "½", "¨", "¸", "\\*", "<", ">",
            "¤", "ß", "è", "×", "÷", "\\\\", "â", "€", "¦", "™", "\\+", "«"
    };
    private String[] sentSepRegEx = new String[] {"\\.", "\\!", "\\?"};
    private String[] sentSep = new String[] {".", "!", "?"};

    public Tokenizer() { }
    
    public String trimIntoSingleLine(String str) {
        // clean strings of new line chars and stuff
        str = str.replaceAll("(\\n)+", " ");
        str = str.replaceAll("(\\.)+", ".");
        str = str.replaceAll("(\\!)+", "!");
        str = str.replaceAll("(\\?)+", "?");
        str = str.replaceAll("(\\n)+", " ");
        str = str.trim().replaceAll("( )+", " ");
        return str;
    }
    
    public ArrayList<Sentence> splitIntoSentences(String singleLineTrimmedText) {
        // split by sentences
        ArrayList<StringBuilder> sentences = new ArrayList<StringBuilder>(5);
        String[] words = singleLineTrimmedText.split(" ");
        
        StringBuilder sentence = new StringBuilder();
        for (String w : words) {
            if (containsSentenceSeparator(w)) {
                String[] tokens1 = (isSentenceSeparator(w))?
                        new String[] {w, ""} : getWordsSplitBySeparators(w);
                        
//				System.out.print("got: " + tokens1.length + " - tested for: '" + w + "'\n -> results :");
//				for (String t : tokens1) System.out.print("'"+t + "', ");
//				System.out.println("\n");
                
                sentences.add(sentence.append(tokens1[0]));
                sentence = new StringBuilder();
                sentence.append(tokens1[1] + " ");
            } else {
                sentence.append(w + " ");
            }
        }
        
        // add last sentence, which might not have a sentence separator
        // in the end, but it also might be empty
        if (!sentence.toString().matches("( )*")) sentences.add(sentence);
        
        // remove sentences that contain only spaces and sentence separators
        sentences.removeIf(sb -> {
            String str = sb.toString().trim();
            for (String sep : sentSep) {
                if (str.equals(sep)) return true;
            }
            return false;
        });
        
        // fix extra leading and trailing spaces
        sentences.forEach(s -> {
            String str = s.toString().trim();
            s.setLength(0);
            s.append(str);
        });
        
//		System.out.println("number of sentences: " + sentences.size());
//		sentences.forEach(sb -> System.out.println("-> '" + sb.toString() + "'"));
        
        ArrayList<Sentence> sentences2 = new ArrayList<Sentence>(sentences.size());
        sentences.forEach(sb -> sentences2.add(new Sentence(sb.toString(), -1)));
        return sentences2;
    }
    
    // case: ... word!This is ....
    // return: {word!, This}
    private String[] getWordsSplitBySeparators(String str) {
        if (str.contentEquals("!?")) return new String[] {"!?", ""};
        
        String[] t;
        
        if (str.contains("!?")) {
            t = str.split("\\!\\?");
            if (str.endsWith("!?")) return new String[] {t[0]+"!?", ""};
            else return new String[] {t[0]+"!?", t[1]};
        }
        
        for (int i=0; i<sentSep.length; i++) {
            if (str.contains(sentSep[i])) {
                t = str.split(sentSepRegEx[i]);
                if (str.endsWith(sentSep[i])) return new String[] {t[0]+sentSep[i], ""};
                if (str.startsWith(sentSep[i])) return new String[] {sentSep[i], t[1]};
                return new String[] {t[0]+sentSep[i], t[1]};
            }
        }
        return new String[] {"", ""};
    }
    
    private boolean isSentenceSeparator(String str) {
        for (String s : sentSep)
            if (str.equals(s)) return true;
        return false;
    }
    
    private boolean containsSentenceSeparator(String str) {
        for (String s : sentSep)
            if (!URL.isType(str) && !Smiley.isType(str) && str.contains(s)) return true;
        return false;
    }
    
    public String findEmojis(String singleLineTrimmedText) {
        IntStream s = singleLineTrimmedText.codePoints();
        int[] arr = s.toArray();
        
        String str = "";
//        int pointsI = 0;
        for (int i=0; i<arr.length; i++) {
            if(arr[i] >= 256) {
                str += " 0x" + Integer.toHexString(arr[i]).toLowerCase() + " ";
                // points.get(pointsI)
//        		pointsI++;
            } else {
                str += (char)arr[i];
            }
        }

        // change these to normal characters:
        // U+201C - LEFT DOUBLE QUOTATION MARK
        // U+201D - RIGHT DOUBLE QUOTATION MARK
        // U+2018 - LEFT SINGLE QUOTATION MARK
        // U+2019 - RIGHT SINGLE QUOTATION MARK
        // (0x instead od U+)
        str = str.replace("0x201C", "\"");
        str = str.replace("0x201D", "\"");
        str = str.replace("0x2018", "'");
        str = str.replace("0x2019", "'");
        return str;
    }
    
    // don't remove -
    private String cleanToken1(String token) {
        String chars = suspects.replace("-", "");
        for (int i=0; i<chars.length(); i++) {
            token = token.replace("" + chars.charAt(i), "");
        }
        return token;
    }
    
    private String cleanToken2(String token) {
//        String x = token;
//        boolean b = false;
//        if (x.contains("married")) {
//            b = true;
//            System.out.println("Before='" + x + "'.");
//        }
        for (int i=0; i<suspects2.length; i++) {
            token = token.replaceAll(suspects2[i], "");
//            if (token.isEmpty())
//                System.out.println("empty");
        }
//        if (b) System.out.println("After='"+token+"'.");
//        System.out.println("I don't like these \"double\" quotes".replace("\"", " "));
        return token;
    }

    // input should be a tokenized (by spaces)
    // string that is in a single line
    public ArrayList<AbsWord> classifyAndGetWords(String[] tokens) {
        ArrayList<AbsWord> words = new ArrayList<AbsWord>(tokens.length);
        for (String token : tokens)
        {
            if (token.length() == 0) continue;
            if (token.length() == 0) continue;
            
            String rawToken = token;
    
//            if (rawToken.toLowerCase().contains("nothing")) {
//                System.out.println("");
//            }
            
            /*
            TYPE CHECKING:
            Remove all suspect characters from *rawToken*, but don't
            remove "-".
            Use *checkToken1* and *checkToken2* for checking types!
            Use *rawToken* when checking for emojis!
            */
        
            String checkToken1 = cleanToken1(rawToken).toLowerCase();	// without -
            String checkToken2 = cleanToken2(rawToken).toLowerCase(); 	// with -
            
            // SMILEY
            if (Smiley.isType(rawToken)) {
                words.add(new Smiley(rawToken));
            }
            
            // URL
            else if (URL.isType(rawToken)) {
                words.add(new URL(rawToken));
            }
            
            // HASHTAG
            else if (Hashtag.isType(rawToken)) {
                words.add(new Hashtag(rawToken, null));
            }
            
            // TARGET
            else if (Target.isType(rawToken)) {
                words.add(new Target(rawToken));
            }
            
            // EMOJI
            else if (Emoji.isType(rawToken)) {
                words.add(new Emoji(rawToken, null));
            }
            
            
            
            // from here on out, the order of type checking is important
            // always check first for negation words or stop-words, since
            // Whissell dictionary contains some of them
            
            // TODO skip?
            // NEGATION WORD
//            else if (NegationWord.isType(checkToken1)) {
//                words.add(new NegationWord(rawToken, checkToken1));
//            }
//            else if (NegationWord.isType(checkToken2)) {
//                words.add(new NegationWord(rawToken, checkToken2));
//            }
            
            // ACRONYM
            else if (Acronym.isType(checkToken1)) {
                words.add(new Acronym(rawToken, checkToken1));

            }
            else if (Acronym.isType(checkToken2)) {
                words.add(new Acronym(rawToken, checkToken2));
            }
            
            // STOP WORD
            else if (StopWord.isType(checkToken1)) {
                words.add(new StopWord(rawToken, checkToken1));

            }
            else if (StopWord.isType(checkToken2)) {
                words.add(new StopWord(rawToken, checkToken2));
            }
    
//            if (rawToken.toLowerCase().contains("married")) {
//                boolean x = AffectionWord.isType(checkToken1);
//                System.out.println(checkToken1 + " -> "+x);
//
//                AbsWord w = DictionaryCollection.getDictionaryCollection().getWhissellDictionary().getEntry(checkToken1);
//                System.out.println(w);
//
//                DictionaryCollection.getDictionaryCollection().getWhissellDictionary().getHashmap().forEach((k, v) -> {
//                    System.out.println("'"+k+"' -> " + v);
//                });
//
//                System.out.println();
//            }
            
            // AFFECTION WORD
            else if (AffectionWord.isType(checkToken1)) {
                words.add(new AffectionWord(rawToken, checkToken1));

            }
            else if (AffectionWord.isType(checkToken2)) {
                words.add(new AffectionWord(rawToken, checkToken2));
            }
            
            // unknown/other word
            else words.add(new Other(rawToken, checkToken2));
        }
        return words;
    }
}
