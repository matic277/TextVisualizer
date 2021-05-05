package main;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

public class FileReader {
    
    String file;
    List<String> lines;
    
    StringBuilder textBuilder;
    
    public FileReader(String file) {
        this.file = file;
        this.lines = new LinkedList<>();
        this.textBuilder = new StringBuilder();
    }
    
    public void readFile() {
        try { Files.readAllLines(Paths.get(file)).forEach(this::processLine); }
        catch (IOException e) { e.printStackTrace(); }
    }
    
    private void processLine(String s) {
        lines.add(s);
        textBuilder.append(s);
    }
    
    public String getText() {
        return textBuilder.toString();
    }
}
