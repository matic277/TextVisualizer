package main;

import SentenceLabel.HorizontalSentenceLabelBuilder;
import SentenceLabel.SentenceLabelBuilder;
import SentenceLabel.VerticalSentenceLabelBuilder;

public enum ChapterType {
    HORIZONTAL(0, "Horizontal", new VerticalSentenceLabelBuilder()),
    VERTICAL(0, "Vertical", new HorizontalSentenceLabelBuilder())
    ;
    
    int id;
    String displayVal;
    SentenceLabelBuilder sentenceLblBuilder;
    
    ChapterType(int id_, String dVal, SentenceLabelBuilder slb) { id=id_; displayVal=dVal; sentenceLblBuilder=slb; }
}
