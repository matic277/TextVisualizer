package main.fileParsers;

import main.Pair;
import word.Tweet;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.*;

public class FileReaderDate {
    
    public static final String TWEET_SEPARATOR = "<!#_#>";
    
    String file;
    List<String> lines;
    // {chapterNumber, chapterText} -> text lines
    Map<Pair<Integer, String>, String> chapters;
    
    StringBuilder textBuilder;
    
    static List<Tweet> tweets = new ArrayList<>(250_000);
    static Map<LocalDate, List<Tweet>> dateMap = new HashMap<>();
    
    public FileReaderDate(String file) {
        this.file = file;
        this.lines = new LinkedList<>();
        this.textBuilder = new StringBuilder();
        this.chapters = new HashMap<>();
    }
    
    public void readFile() throws IOException {
        Files.lines(Paths.get("./texts/COMBINED_2way.txt"), Charset.forName("ISO_8859_1")).forEach(this::processLine);
        
        // create map based on date
        tweets.forEach(tweet -> {
            dateMap.computeIfAbsent(tweet.dateCollected, dateKey -> new ArrayList<>(4_000));
            dateMap.get(tweet.dateCollected).add(tweet);
        });
        
        var dateCounter = new Object() { int c = 0; };
        // print map and sentiments ordered by date
        dateMap.keySet().stream().sorted((d1, d2) -> d1.isBefore(d2) ? 1 : -1).forEachOrdered(date -> {
            StringBuilder lines = new StringBuilder();
            for (Tweet tweet : dateMap.get(date)) {
                lines.append(tweet.getSourceText()).append(" " + TWEET_SEPARATOR + " ");
            }
            if (dateCounter.c > 5) return;
            chapters.put(new Pair<>(dateCounter.c++, date.toString()), lines.toString());
        });
        
        System.out.println("datemap size=" + dateMap.size());
        
        chapters.forEach((k, v) -> {
            System.out.println("Key:{"+k+"} -> {"+v.split(TWEET_SEPARATOR).length+"}");
        });
    }
    
    private void processLine(String line) {
        line = line.trim();
        if (line.isEmpty() || line.isBlank()) return;
        
        String[] tokens = line.split(",");
        String classStr = tokens[0];
        String dateStr = tokens[1];
        String text = tokens[2];
        
        for (int i=3; i<tokens.length; i++) {
            text += "," + tokens[i];
        }
        
        String[] date = dateStr.split("-");
        Tweet t = new Tweet(text, null, LocalDate.of(Integer.parseInt(date[2]), Integer.parseInt(date[1]), Integer.parseInt(date[0])));
        t.sentimentClass = Integer.parseInt(classStr);
        tweets.add(t);
    }
    
    public String getText() {
        return textBuilder.toString();
    }
    
    public Map<Pair<Integer, String>, String> getChaptersMap() {
        return this.chapters;
    }
}
