package main.fileParsers;

import main.UserDictionary.UserDictionary;
import main.UserDictionary.WordGroup;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DictionaryReader {
    
    String file;
    
    private static final Pattern nameRgx = Pattern.compile("<name=\"(.*)\">");
    private static final Pattern groupRgx = Pattern.compile("<group name=\"(.*)\" color=\"(.*)\">");
    
    public List<String> fileLines;
    
    // Reading from file
    public DictionaryReader(String file) {
        this.file = file;
        fileLines = new LinkedList<>();
    }
    
    public void readLines() {
        try { fileLines = Files.readAllLines(Paths.get(file)); }
        catch (IOException e) { System.out.println("Cannot read file specified: \"" + file+ "\"."); e.printStackTrace(); }
    }
    
    public List<String> getLines() {
        return this.fileLines;
    }
    
    public static UserDictionary buildDictionary(List<String> lines) throws RuntimeException {
        final var dict = new UserDictionary();
        
        boolean firstLine = true;
        boolean secondLine = true;
    
        WordGroup currentGroup = null;
        
        for (String line : lines) {
            line = line.trim();
            
            // header (dictionary name)
            if (firstLine) {
                firstLine = false;
                System.out.println("Detected header: \"" + line + "\".");
                Matcher matcher = nameRgx.matcher(line);
                if (matcher.matches()) {
                    String dictName = matcher.group(1).trim();
                    if (dictName.isBlank() || dictName.isEmpty()) throw new RuntimeException("Dictionary name can't be empty.");
                    dict.setName(dictName);
                }
                continue;
            }
            
            if (secondLine) {
                secondLine = false;
                if (!line.startsWith("<")) {
                    throw new RuntimeException("Second line should be a group, but it's \"" + line + "\".");
                }
            }
            
            // group
            if (line.startsWith("<")) {
                Matcher matcher = groupRgx.matcher(line);
                if (matcher.matches()) {
                    System.out.println("Detected group: \"" + matcher.group(0) + "\".");
                    
                    String groupName = matcher.group(1).trim();
                    if (groupName.isBlank() || groupName.isEmpty()) throw new RuntimeException("Group name can't be empty.");
                    
                    String groupColor = matcher.group(2).trim();
                    if (groupColor.isBlank() || groupColor.isEmpty()) throw new RuntimeException("Group color can't be empty.");
                    
                    if (currentGroup == null) {
                        currentGroup = new WordGroup()
                                .setGroupName(groupName)
                                .setDefaultGroupColor(groupColor);
                    } else {
                        boolean error = dict.addGroup(currentGroup);
                        if (error) throw new RuntimeException("Couldn't add group with name \"" + currentGroup.name + "\". Probably a duplicate group name.");
                        
                        currentGroup = new WordGroup()
                                .setGroupName(groupName)
                                .setDefaultGroupColor(groupColor);
                    }
                } else {
                    throw new RuntimeException("Line started with '<', but group tag was not detected, line \"" + line + "\".");
                }
                
                continue;
            }
            
            // word
            if (currentGroup == null) throw new RuntimeException("(BUG) Parsing word \"" + line + "\",but under no group.");
            String[] tokens = line.split("<");
            // no overwrite color defined
            if (tokens.length == 1) {
                currentGroup.addWord(line.trim());
            }
            // overwrite color defined
            else {
                currentGroup.addWord(tokens[0].trim(), tokens[1].trim());
            }
        }
        
        // reading done, now add the last group
        if (currentGroup == null) System.out.println("Last group is unexpectedly NULL, or there was only one group defined.");
        else dict.addGroup(currentGroup);
        
        return dict;
    }
    
    //public static void main(String[] args) {
    //    String r = "<name=\"(.*)\">";
    //    String s = "<name=\"Test dictionary\">";
    //    Pattern rgx = Pattern.compile(r);
    //    Matcher mcr = rgx.matcher(s);
    //
    //    if (mcr.find( )) {
    //        System.out.println("Found value: " + mcr.group(0) );
    //        System.out.println("Found value: " + mcr.group(1) );
    //    } else {
    //        System.out.println("NO MATCH");
    //    }
    //}
}
