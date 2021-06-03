package main;

import panel.SentenceLabel;

import javax.swing.*;
import java.awt.*;

public interface SentenceLabelBuilder {
    public void rebuild(SentenceLabel label);
    public LayoutManager getParentLayout(JPanel parent);
    
    public void draw(Graphics2D g, SentenceLabel label);
}
