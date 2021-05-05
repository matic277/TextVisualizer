package dictionary;

import word.AbsWord;

public interface IDictionary {
	
	boolean contains(String key);
	AbsWord getEntry(String key);

}
