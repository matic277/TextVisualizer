package main.UserDictionary;

import main.Utils;

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
    
    public void setName(String name) { this.name = name; }
    
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
