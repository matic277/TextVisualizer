package panel.tabs;

import com.formdev.flatlaf.ui.FlatRoundBorder;
import com.formdev.flatlaf.ui.FlatUIUtils;
import panel.VerticalFlowLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class ColorTab extends JPanel {
    
    TabsPanel parent;
    
    public ColorTab(TabsPanel parent) {
        this.parent = parent;
        
        //this.add(new JLabel("Unsupported"));
        this.setLayout(new VerticalFlowLayout());
        
        initColorPanel();
        
        this.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0,1,1,0, new Color(0,0,0,0)),
                BorderFactory.createMatteBorder(0,1,1,1, Color.lightGray)));
    }
    
    private void initColorPanel() {
        final Dimension containerSize = new Dimension(320, 260);
        JPanel mainColorContainer = new JPanel();
        mainColorContainer.setBorder(new FlatRoundBorder());
        mainColorContainer.setPreferredSize(containerSize);
        //mainColorContainer.setMaximumSize(containerSize);
        //mainColorContainer.setMinimumSize(containerSize);
        mainColorContainer.setLayout(new FlowLayout(FlowLayout.CENTER));
        
        Color gradientColor = Color.RED;
        Point mouseGradient = new Point(100, 100);
        final var selectedColor = new Object() { Color clr = Color.yellow; };
        Robot rbt = null;
        try { rbt = new Robot(); }
        catch (Exception e) { e.printStackTrace(); }
    
        final FlatRoundBorder brd = new FlatRoundBorder() {
            protected final int arc = 10;
            protected int getArc(Component c) {
                if (this.isCellEditor(c)) {
                    return 0;
                } else {
                    Boolean roundRect = FlatUIUtils.isRoundRect(c);
                    return roundRect != null ? (roundRect ? 32767 : 0) : this.arc;
                }
            }
        };
        
        final Dimension rgbSize = new Dimension(36, 25);
        JLabel txtR = new JLabel("R");
        JTextField clrR = new JTextField("0");
        clrR.setPreferredSize(rgbSize);
        clrR.setBorder(brd);
        JLabel txtG = new JLabel("G");
        JTextField clrG = new JTextField("0");
        clrG.setPreferredSize(rgbSize);
        clrG.setBorder(brd);
        JLabel txtB = new JLabel("B");
        JTextField clrB = new JTextField("0");
        clrB.setPreferredSize(rgbSize);
        clrB.setBorder(brd);
        
        JPanel containerR = new JPanel();
        containerR.setLayout(new FlowLayout(FlowLayout.LEFT, 2, 0));
        containerR.add(txtR);
        containerR.add(clrR);
        
        JPanel containerG = new JPanel();
        containerG.setLayout(new FlowLayout(FlowLayout.LEFT, 2, 0));
        containerG.add(txtG);
        containerG.add(clrG);
        
        JPanel containerB = new JPanel();
        containerB.setLayout(new FlowLayout(FlowLayout.LEFT, 2, 0));
        containerB.add(txtB);
        containerB.add(clrB);
        
        
        JPanel rgbContainer = new JPanel();
        rgbContainer.setLayout(new VerticalFlowLayout(VerticalFlowLayout.CENTER, VerticalFlowLayout.BOTTOM, 0, 10));
        rgbContainer.setBorder(new FlatRoundBorder());
        rgbContainer.add(containerR);
        rgbContainer.add(containerG);
        rgbContainer.add(containerB);
        
        JPanel selectedColorPanel = new JPanel(){
            @Override public void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D gr = (Graphics2D) g;
                gr.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                gr.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                gr.setPaint(selectedColor.clr);
                gr.fillOval(1, 1, getWidth()-2, getHeight()-2);
                // border
                gr.setColor(Color.lightGray);
                gr.drawOval(0, 0, getWidth()-1, getHeight()-1);
            }};
        selectedColorPanel.setPreferredSize(new Dimension(75, 75));
        
        JPanel selectedClrAndRGBcontainer = new JPanel(new VerticalFlowLayout());
        selectedClrAndRGBcontainer.add(rgbContainer);
        selectedClrAndRGBcontainer.add(selectedColorPanel);
        
        
        
        // MAIN GRADIENT PANEL
        Dimension gradPanelSize = new Dimension(200, 200);
        GradientPaint primary = new GradientPaint(
                0f, 0f, Color.WHITE, gradPanelSize.width, 0f, gradientColor);
        final GradientPaint shade = new GradientPaint(
                0f, 0f, new Color(0, 0, 0, 0),
                0f, gradPanelSize.height, new Color(0, 0, 0, 255));
        // thanks to https://stackoverflow.com/questions/13771575/java-3-color-gradient
        JPanel gradientPanel = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                // anti-aliasing
                Graphics2D gr = (Graphics2D) g;
                gr.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                gr.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                
                gr.setPaint(primary);
                gr.fillRect(0, 0, getWidth(), getHeight());
                gr.setPaint(shade);
                gr.fillRect(0, 0, getWidth(), getHeight());
                
                // border
                gr.setColor(Color.lightGray);
                gr.drawRect(0, 0, getWidth()-1, getHeight()-1);
                
                // draw indicator
                gr.setStroke(new BasicStroke(3));
                gr.setColor(Color.YELLOW);
                gr.drawOval(mouseGradient.x-5, mouseGradient.y-5, 15, 15);
            }
        };
        Robot finalRbt = rbt;
        gradientPanel.addMouseMotionListener(new MouseMotionListener() {
            @Override public void mouseMoved(MouseEvent e) { }
            @Override public void mouseDragged(MouseEvent e) {
                if (!gradientPanel.contains(e.getPoint())) return;
                mouseGradient.setLocation(e.getPoint().x-2, e.getPoint().y-2);
                
                Color selectedClr = finalRbt.getPixelColor(e.getXOnScreen(), e.getYOnScreen()); // how else? creating buffered images from Graphics then taking RGB? meh...
                selectedColor.clr = selectedClr;
                
                clrR.setText(selectedClr.getRed()+"");
                clrG.setText(selectedClr.getGreen()+"");
                clrB.setText(selectedClr.getBlue()+"");
                
                selectedColorPanel.repaint();
                gradientPanel.repaint();
            }
        });
        gradientPanel.setPreferredSize(gradPanelSize);
        
        
        
        // RAINBOW PANEL
        Point mouseRainbow = new Point(0, 0);
        JPanel rainbowPanel = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D gr = (Graphics2D) g;
                gr.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                gr.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                
                GradientPaint primary = new GradientPaint(
                        0f, 0f, Color.WHITE, 200f, 0f, gradientColor);
                GradientPaint shade = new GradientPaint(
                        0f, 0f, new Color(0, 0, 0, 0),
                        0f, 200f, new Color(0, 0, 0, 255));
                
                // rainbow fill (TODO)
                gr.setPaint(primary);
                gr.fillRoundRect(0, 0, getWidth()-1, getHeight(), 20, 20);
                gr.setPaint(shade);
                gr.fillRoundRect(0, 0, getWidth()-1, getHeight(), 20, 20);
                
                // border
                gr.setColor(Color.lightGray);
                gr.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 20, 20);
                
                // draw slider
                gr.setColor(Color.darkGray);
                gr.fillRoundRect(mouseRainbow.x, 0, 8, getHeight(), 10, 10);
            }
        };
        rainbowPanel.addMouseMotionListener(new MouseMotionListener() {
            @Override public void mouseMoved(MouseEvent e) { }
            @Override public void mouseDragged(MouseEvent e) {
                if (!gradientPanel.contains(e.getPoint())) return;
                if (e.getPoint().getX() >= rainbowPanel.getWidth()-7) return; // subtract slider width
                mouseRainbow.setLocation(e.getPoint());
                rainbowPanel.repaint();
            }
        });
        rainbowPanel.setPreferredSize(new Dimension(gradPanelSize.width, 30));
    
        JPanel gradientsContainer = new JPanel(new VerticalFlowLayout());
        gradientsContainer.add(gradientPanel);
        gradientsContainer.add(rainbowPanel);
        
        
        mainColorContainer.add(gradientsContainer);
        mainColorContainer.add(selectedClrAndRGBcontainer);
        //mainColorContainer.seP
        
        this.add(mainColorContainer);
    }
}

