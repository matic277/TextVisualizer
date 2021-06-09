package main;

import panel.ChaptersPanel;
import SentenceLabel.SentenceLabel;

import javax.swing.*;
import javax.swing.border.StrokeBorder;
import java.awt.*;
import java.util.List;

public class VerticalChapterBuilder implements ChapterBuilder {

    @Override
    public void rebuild(ChaptersPanel panel) {
        List<JPanel> chapterPanels = panel.chapterPanels;
        
        // create panel for each chapter
        panel.chapters.forEach((k, v) -> {
            JPanel chapterPanel = new JPanel();
            chapterPanel.setOpaque(true);
            chapterPanel.setBorder(new StrokeBorder(new BasicStroke(2)));
            chapterPanel.setLayout(new BorderLayout());
            chapterPanel.setBackground(Color.white);
            //chapterPanel.setName("Main panel for chapter " + k.getB());
            
            JLabel title = new JLabel(k.getB());
            title.setBorder(new StrokeBorder(new BasicStroke(1)));
            title.setFont(Utils.getFont(14));
            title.setHorizontalAlignment(SwingConstants.CENTER);
            chapterPanel.add(title, BorderLayout.NORTH);
            
            JPanel sentencesPanel = new JPanel();
            //sentencesPanel.setName("Sentence panel for chapter " + k.getB());
            sentencesPanel.setOpaque(true);
            sentencesPanel.setBackground(Utils.GRAY);
            sentencesPanel.setLayout(panel.currentChapterType.sentenceLblBuilder.getParentLayout(sentencesPanel));
            chapterPanel.add(sentencesPanel, BorderLayout.CENTER);
            
            v.forEach(s -> {
                SentenceLabel lbl = new SentenceLabel(panel, s, panel.currentVisualType, panel.currentChapterType, panel.currentSentenceLblVisualType, panel.currentSentenceSizeType);
                lbl.init();
                sentencesPanel.add(lbl);
            });
            
            sentencesPanel.doLayout();
            sentencesPanel.revalidate();
            
            chapterPanel.setMaximumSize(chapterPanel.getPreferredSize());
            chapterPanels.add(chapterPanel);
            
            panel.mainPanel.add(chapterPanel);
            //panel.mainPanel.add(Box.createRigidArea(new Dimension(100, 10))); // dummy spacing component
        });
    }
}
