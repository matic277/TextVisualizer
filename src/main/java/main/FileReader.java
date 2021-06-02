package main;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class FileReader {
    
    String file;
    List<String> lines;
    // {chapterNumber, chapterText} -> text lines
    Map<Pair<Integer, String>, String> chapters;
    
    StringBuilder textBuilder;
    
    public FileReader(String file) {
        this.file = file;
        this.lines = new LinkedList<>();
        this.textBuilder = new StringBuilder();
        this.chapters = new HashMap<>();
    }
    
    public void readFile() throws IOException {
       Files.readAllLines(Paths.get(file)).forEach(this::processLine);
        
//        chapters.forEach((k, v) -> {
//            System.out.println("Key:{"+k+"} -> {"+v+"}");
//        });
    }
    
    private final String oddChars = "-.,;";
    
    Pair<Integer, String> lastChapter = null;
    int chapterCounter = -1;
    
    private void processLine(String s) {
        if (s.isEmpty() || s.isBlank()) return;
        if (s.length() < 4) {
            System.out.println("Suspect line length: " + s);
        }
        
        // contains chapter and is relatively short
        if (s.toLowerCase().contains("chapter") && s.length() < 14) {
            String cleared = s.toLowerCase().replace("chapter", "").trim();
            
            for (char oddChar : oddChars.toCharArray()) {
                cleared = cleared.replace(oddChar + "", "");
            }
            
            // is it a regular number
            Integer chapterNum = null;
            try { chapterNum = Integer.parseInt(cleared); }
            catch (Exception e) { }
            
            // try roman number
            if (chapterNum == null) {
                chapterNum = romanToDecimal(cleared);
            }
            
            Pair<Integer, String> key = new Pair<>(chapterNum, s);
            chapterCounter++;
            
            if (lastChapter == null) {
                lastChapter = key;
                return;
            } else {
                if (lastChapter.a < chapterCounter) {
                    lastChapter.a = chapterCounter;
                }
                chapters.put(lastChapter,  textBuilder.toString());
                textBuilder.setLength(0); // reset builder
                lastChapter = new Pair<>(chapterNum, s);
                return;
            }
        }
        
        lines.add(s);
        textBuilder.append(s).append(" ");
    }
    
    // https://stackoverflow.com/questions/9073150/converting-roman-numerals-to-decimal
    private int processDecimal(int decimal, int lastNumber, int lastDecimal) { return lastNumber > decimal ? lastDecimal - decimal : lastDecimal + decimal; }
    private int romanToDecimal(java.lang.String romanNumber) {
        int decimal = 0; int lastNumber = 0; String romanNumeral = romanNumber.toUpperCase();
        for (int x = romanNumeral.length() - 1; x >= 0 ; x--) { char convertToDecimal = romanNumeral.charAt(x); switch (convertToDecimal) {
                case 'M': decimal = processDecimal(1000, lastNumber, decimal); lastNumber = 1000; break;
                case 'D': decimal = processDecimal(500, lastNumber, decimal); lastNumber = 500; break;
                case 'C': decimal = processDecimal(100, lastNumber, decimal); lastNumber = 100; break;
                case 'L': decimal = processDecimal(50, lastNumber, decimal); lastNumber = 50; break;
                case 'X': decimal = processDecimal(10, lastNumber, decimal); lastNumber = 10; break;
                case 'V': decimal = processDecimal(5, lastNumber, decimal); lastNumber = 5; break;
                case 'I': decimal = processDecimal(1, lastNumber, decimal); lastNumber = 1; break;
        } } return decimal;
    }
    
    public String getText() {
        return textBuilder.toString();
    }
    
    public Map<Pair<Integer, String>, String> getChaptersMap() {
        return this.chapters;
    }
}
