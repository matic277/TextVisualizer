package main;

import panel.ChaptersPanel;
import panel.SentenceLabel;

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
            chapterPanel.setName("Main panel for chapter " + k.getB());
            
            JLabel title = new JLabel(k.getB());
            title.setBorder(new StrokeBorder(new BasicStroke(1)));
            title.setFont(Utils.getFont(14));
            title.setHorizontalAlignment(SwingConstants.CENTER);
            chapterPanel.add(title, BorderLayout.NORTH);
            
            JPanel sentencesPanel = new JPanel();
            sentencesPanel.setName("Sentence panel for chapter " + k.getB());
            sentencesPanel.setOpaque(true);
            sentencesPanel.setBackground(Utils.GRAY);
            sentencesPanel.setLayout(panel.currentChapterType.sentenceLblBuilder.getParentLayout(sentencesPanel));
            chapterPanel.add(sentencesPanel, BorderLayout.CENTER);
            
//            int[] maxSentLblWidth = new int[]{0};
//            int[] height = new int[]{0};
            v.forEach(s -> {
                SentenceLabel lbl = new SentenceLabel(panel, s, panel.currentVisualType, panel.currentChapterType.sentenceLblBuilder);
                lbl.init();
                sentencesPanel.add(lbl);
//                if (lbl.getSize().width > maxSentLblWidth[0]) maxSentLblWidth[0] = lbl.getPreferredSize().width;
//                height[0] += lbl.getPreferredSize().height;
            });
            
//            sentencesPanel.setPreferredSize(new Dimension(maxSentLblWidth[0], height[0]));
            sentencesPanel.doLayout();
            sentencesPanel.revalidate();
            
            chapterPanel.setMaximumSize(chapterPanel.getPreferredSize());
            chapterPanels.add(chapterPanel);
            
            panel.mainPanel.add(chapterPanel);
            panel.mainPanel.add(Box.createRigidArea(new Dimension(100, 10))); // dummy spacing component
        });
    }
}
