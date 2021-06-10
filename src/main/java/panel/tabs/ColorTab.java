package panel.tabs;

import com.formdev.flatlaf.ui.FlatRoundBorder;
import com.formdev.flatlaf.ui.FlatUIUtils;
import panel.VerticalFlowLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;

public class ColorTab extends JPanel {
    
    TabsPanel parent;
    
    public ColorTab(TabsPanel parent) {
        this.parent = parent;
        
        //this.add(new JLabel("Unsupported"));
        this.setLayout(new VerticalFlowLayout());
        
        initColorPanel();
        
        this.setMinimumSize(new Dimension(0,0));
        
        this.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0,1,1,0, new Color(0,0,0,0)),
                BorderFactory.createMatteBorder(0,1,1,1, Color.lightGray)));
    }
    
    ColorChooserContext clrContext = new ColorChooserContext();
    private static class ColorChooserContext {
        final Dimension gradPanelSize = new Dimension(200, 200);
    
        // defaults at the start
        Color rainbowSelectedClr = Color.PINK;
        Color gradientSelectecClr = new Color(126, 106, 106);
        
        BufferedImage gradientImg = new BufferedImage(gradPanelSize.width, gradPanelSize.height, BufferedImage.TYPE_INT_RGB);
        BufferedImage rainbowImg = new BufferedImage(gradPanelSize.width, 30, BufferedImage.TYPE_INT_RGB);
        
        final Point mouseGradient = new Point(100, 100);
        final Point mouseRainbow = new Point(0, 0);
    
        // overwrite default border of FlatLaf, to decrease arc
        final FlatRoundBorder rgbValuesBorder = new FlatRoundBorder() {
            protected final int arc = 10;
            protected int getArc(Component c) {
                if (this.isCellEditor(c)) return 0;
                Boolean roundRect = FlatUIUtils.isRoundRect(c);
                return roundRect != null ? (roundRect ? 32767 : 0) : this.arc;
            }
        };
    }
    
    
    private void initColorPanel() {
        final Dimension containerSize = new Dimension(370, 260);
        JPanel mainColorContainer = new JPanel();
        mainColorContainer.setBorder(new FlatRoundBorder());
        mainColorContainer.setPreferredSize(containerSize);
        //mainColorContainer.setMaximumSize(containerSize);
        //mainColorContainer.setMinimumSize(containerSize);
        mainColorContainer.setLayout(new FlowLayout(FlowLayout.CENTER));
        
        
        // RGB CONTAINER
        final Dimension rgbSize = new Dimension(36, 25);
        JLabel txtR = new JLabel("R");
        JTextField clrR = new JTextField("0");
        clrR.setPreferredSize(rgbSize);
        clrR.setBorder(clrContext.rgbValuesBorder);
        JLabel txtG = new JLabel("G");
        JTextField clrG = new JTextField("0");
        clrG.setPreferredSize(rgbSize);
        clrG.setBorder(clrContext.rgbValuesBorder);
        JLabel txtB = new JLabel("B");
        JTextField clrB = new JTextField("0");
        clrB.setPreferredSize(rgbSize);
        clrB.setBorder(clrContext.rgbValuesBorder);
        
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
        rgbContainer.setLayout(new VerticalFlowLayout(VerticalFlowLayout.CENTER, VerticalFlowLayout.CENTER, 0, 10));
        //rgbContainer.setBorder(new FlatRoundBorder());
        rgbContainer.add(containerR);
        rgbContainer.add(containerG);
        rgbContainer.add(containerB);
        
        
        
        // SELECTED COLOR CIRCLE
        JPanel selectedColorPanel = new JPanel(){
            @Override public void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D gr = (Graphics2D) g;
                gr.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                gr.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                gr.setPaint(clrContext.gradientSelectecClr);
                gr.fillOval(1, 1, getWidth()-2, getHeight()-2);
                // border
                gr.setColor(Color.lightGray);
                gr.drawOval(0, 0, getWidth()-1, getHeight()-1);
            }};
        selectedColorPanel.setPreferredSize(new Dimension(75, 75));
        
        JPanel selectedClrAndRGBcontainer = new JPanel(new VerticalFlowLayout(VerticalFlowLayout.CENTER, VerticalFlowLayout.TOP));
        selectedClrAndRGBcontainer.add(rgbContainer);
        selectedClrAndRGBcontainer.add(selectedColorPanel);
        //selectedClrAndRGBcontainer.setBorder(brd);
        
        
        
        // MAIN GRADIENT PANEL
        final GradientPaint shade = new GradientPaint(
                0f, 0f, new Color(0, 0, 0, 0),
                0f, clrContext.gradPanelSize.height, new Color(0, 0, 0, 255));
        // thanks to https://stackoverflow.com/questions/13771575/java-3-color-gradient
        JPanel gradientPanel = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                // anti-aliasing
                Graphics2D gr = (Graphics2D) g;
                gr.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                gr.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                
                GradientPaint primary = new GradientPaint(
                        0f, 0f, Color.WHITE, clrContext.gradPanelSize.width, 0f, clrContext.rainbowSelectedClr);
                
                // render gradientImg so that we can extract colors from it
                Graphics2D imgG = clrContext.gradientImg.createGraphics();
                imgG.setPaint(primary);
                imgG.fillRect(0, 0, getWidth(), getHeight());
                imgG.setPaint(shade);
                imgG.fillRect(0, 0, getWidth(), getHeight());
                
                gr.drawImage(clrContext.gradientImg, null, 0, 0);
                
                // border
                gr.setColor(Color.lightGray);
                gr.drawRect(0, 0, getWidth()-1, getHeight()-1);
                
                // draw indicator
                gr.setStroke(new BasicStroke(3));
                gr.setColor(Color.YELLOW);
                gr.drawOval(clrContext.mouseGradient.x-5, clrContext.mouseGradient.y-5, 15, 15);
            }
        };
        gradientPanel.addMouseMotionListener(new MouseMotionListener() {
            @Override public void mouseMoved(MouseEvent e) { }
            @Override public void mouseDragged(MouseEvent e) {
                if (!gradientPanel.contains(e.getPoint())) return;
                clrContext.mouseGradient.setLocation(e.getPoint().x-2, e.getPoint().y-2);
                
                // extract color
                clrContext.gradientSelectecClr = new Color(clrContext.gradientImg.getRGB(e.getPoint().x, e.getPoint().y));
                
                clrR.setText(clrContext.gradientSelectecClr.getRed()+"");
                clrG.setText(clrContext.gradientSelectecClr.getGreen()+"");
                clrB.setText(clrContext.gradientSelectecClr.getBlue()+"");
                
                selectedColorPanel.repaint();
                gradientPanel.repaint();
            }
        });
        gradientPanel.setPreferredSize(clrContext.gradPanelSize);
        
        
        
        // RAINBOW PANEL
        Dimension rainbowPanelSize = new Dimension(new Dimension(clrContext.gradPanelSize.width, 30));
        JPanel rainbowPanel = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D gr = (Graphics2D) g;
                gr.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                gr.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                
                // render rainbowImg so that we can extract colors from it
                Graphics2D imgG = clrContext.rainbowImg.createGraphics();
                
                // modified, thanks to
                // https://stackoverflow.com/questions/27641641/creating-a-jlabel-with-a-gradient
                LinearGradientPaint lgp = new LinearGradientPaint(
                        new Point(0, 0),
                        new Point(getWidth(), 0),
                        new float[]{0.072f, 0.214f, 0.356f, 0.498f, 0.68f, 0.782f, 0.917f, 1f}, // red twice, otherwise the pure red color can't get selected
                        new Color[]{Color.PINK, Color.MAGENTA, Color.BLUE, Color.GREEN, Color.YELLOW, Color.ORANGE, Color.RED, Color.RED});
                imgG.setPaint(lgp);
                imgG.fill(new Rectangle(0, 0, getWidth(), getHeight()));
                
                clrContext.rainbowImg = makeRoundedCorner(clrContext.rainbowImg, 20);
                gr.drawImage(clrContext.rainbowImg, null, 0, 0);
                
                // border
                gr.setColor(Color.lightGray);
                gr.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 20, 20);
                
                // draw slider
                gr.setColor(Color.darkGray);
                gr.fillRoundRect(clrContext.mouseRainbow.x, 0, 8, getHeight(), 10, 10);
            }
        };
        rainbowPanel.addMouseMotionListener(new MouseMotionListener() {
            @Override public void mouseMoved(MouseEvent e) { }
            @Override public void mouseDragged(MouseEvent e) {
                if (!rainbowPanel.contains(e.getPoint())) return;
                if (e.getPoint().getX() >= rainbowPanel.getWidth()-7) return; // subtract slider width
    
                clrContext.mouseRainbow.setLocation(e.getPoint());
                // extract color
                clrContext.rainbowSelectedClr = new Color(clrContext.rainbowImg.getRGB(clrContext.mouseRainbow.x, clrContext.mouseRainbow.y));
                rainbowPanel.repaint();
                gradientPanel.repaint();
                
                // force generate (simulate) a call to mouseDragged
                // so that function exectues, recalculating selected color
                // for selectedColorPanel
                // +2 on coords, beucase that method subtracts 2
                gradientPanel.getListeners(MouseMotionListener.class)[0]
                        .mouseDragged(new MouseEvent(
                                gradientPanel,0,0,0,
                                clrContext.mouseGradient.x+2, clrContext.mouseGradient.y+2,
                                0,0, 0, false, 0));
            }
        });
        rainbowPanel.setPreferredSize(rainbowPanelSize);
        
        
        JPanel gradientsContainer = new JPanel(new VerticalFlowLayout());
        gradientsContainer.add(gradientPanel);
        gradientsContainer.add(rainbowPanel);
    
        //selectedClrAndRGBcontainer.setMinimumSize(new Dimension(100, 400));
        selectedClrAndRGBcontainer.setPreferredSize(new Dimension(100, 250));
        mainColorContainer.add(gradientsContainer);
        mainColorContainer.add(selectedClrAndRGBcontainer);
        
        this.add(mainColorContainer);
    }
    
    // thanks to https://stackoverflow.com/questions/7603400/how-to-make-a-rounded-corner-image-in-java
    public static BufferedImage makeRoundedCorner(BufferedImage image, int cornerRadius) {
        int w = image.getWidth();
        int h = image.getHeight();
        BufferedImage output = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = output.createGraphics();
        
        // so instead fake soft-clipping by first drawing the desired clip shape
        // in fully opaque white with antialiasing enabled...
        g2.setComposite(AlphaComposite.Src);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(Color.WHITE);
        g2.fill(new RoundRectangle2D.Float(0, 0, w, h, cornerRadius, cornerRadius));
        
        // ... then compositing the image on top,
        // using the white shape from above as alpha source
        g2.setComposite(AlphaComposite.SrcAtop);
        g2.drawImage(image, 0, 0, null);
        
        g2.dispose();
        return output;
    }
}

