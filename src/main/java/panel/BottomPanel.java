package panel;

import main.Sentence;
import main.Utils;

import javax.swing.*;
import java.util.List;

public class BottomPanel extends JSplitPane {
    
    MainPanel parent;
    
    RightPanel rightPanel;
    LeftPanel leftPanel;
    
    
    public BottomPanel(MainPanel parent) {
        super(HORIZONTAL_SPLIT, null, null);
        this.parent = parent;
    
        rightPanel = new RightPanel(this);
        leftPanel = new LeftPanel(this);
        
        this.setLeftComponent(leftPanel);
        this.setRightComponent(rightPanel);
        
        this.setDividerLocation(Utils.INITIAL_LEFT_MENU_WIDTH);
        
        
        // hacky stuff
//        leftPanel.addComponentListener(new ComponentListener() {
//            @Override public void componentResized(ComponentEvent e) {
//                System.out.println(box.sentencesPanel.getHeight());
////                box.sentencesPanel.setPreferredSize(new Dimension(
////                        leftPanel.getSize().width ,
////                        box.sentencesPanel.getHeight()));
//                box.sentencesPanel.setPreferredSize(box.getSize());
////                box.setPreferredSize(box.getSize());
//                box.sentencesPanel.revalidate();
//                box.sentencesPanel.doLayout();
//                box.updateUI();
//                box.doLayout();
//                leftPanel.revalidate();
//                leftPanel.updateUI();
//                leftPanel.doLayout();
//            }
//            @Override public void componentMoved(ComponentEvent e) { }
//            @Override public void componentShown(ComponentEvent e) { }
//            @Override public void componentHidden(ComponentEvent e) { }
//        });
//        box.setPreferredSize(new Dimension(leftPanel.getSize().width, 1000));
//        box.updateUI();
//        box.doLayout();
    }
    
    public void onSentenceClick(Sentence clickedSentence) {
        leftPanel.onSentenceClick(clickedSentence);
        rightPanel.onSentenceClick(clickedSentence);
    }
    
    public void onSentenceHover(List<SentenceLabel> hoveredSentences) {
        leftPanel.onSentenceHover(hoveredSentences);
        rightPanel.onSentenceHover(hoveredSentences);
    }

//    @Override
//    public void paintComponent(Graphics g) {
//        Graphics2D gr = (Graphics2D) g;
//        gr.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//        gr.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
//
//        gr.setColor(Utils.bgColor);
//        gr.fillRect(0, 0, this.getWidth(), this.getHeight());
//
//        gr.setColor(Color.black);
//        gr.drawRect(5, 5,  this.getWidth()-10, this.getHeight() - 10);
//    }
}
