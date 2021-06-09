package main.UserDictionary;

import main.Utils;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserDictionary {
    
    List<WordGroup> groups;
    
    // groupName -> group
    public Map<String, WordGroup> groupMap;
    
    String name;
    
    public UserDictionary() {
        groupMap = new HashMap<>(10);
    }
    
    public boolean addGroup(WordGroup group) {
        if (groupMap.containsKey(group.name)) return false;
        return groupMap.put(group.name, group) != null;
    }
    
    /**
     * Returns null if word isn't in any group.
     * (Unspecified in user dictionary)
     */
    public Color getColorForWord(String word) {
        for (String key : groupMap.keySet()) {
            WordGroup wrdGrp = groupMap.get(key);
            Color potentialColor = wrdGrp.getColorForWord(word);
            if (potentialColor != null) return potentialColor;
        }
        return null;
    }
    
    public void setName(String name) { this.name = name; }
    public String getName() { return this.name; }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder()
                .append("Dictionary: ").append(name);
        groupMap.forEach((k, v) -> {
            sb.append("\n  -> Group{name=").append(k)
                    .append(", size=").append(v.words.size())
                    .append(", defaultColor=").append(Utils.colorToString(v.DEFAULT_COLOR)).append("}\n")
                    .append(v);
        });
        return sb.toString();
    }
}
