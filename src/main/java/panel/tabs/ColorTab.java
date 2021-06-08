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
    
    private void initColorPanel() {
        final Dimension containerSize = new Dimension(370, 260);
        JPanel mainColorContainer = new JPanel();
        mainColorContainer.setBorder(new FlatRoundBorder());
        mainColorContainer.setPreferredSize(containerSize);
        //mainColorContainer.setMaximumSize(containerSize);
        //mainColorContainer.setMinimumSize(containerSize);
        mainColorContainer.setLayout(new FlowLayout(FlowLayout.CENTER));
        
        //Color gradientColor = Color.RED;
        Point mouseGradient = new Point(100, 100);
        final var rainbowSelectedClr = new Object() { Color clr = Color.red; };
        final var selectedColor = new Object() { Color clr = Color.yellow; };
        
        // overwrite default border of FlatLaf, to decrease arc
        final FlatRoundBorder brd = new FlatRoundBorder() {
            protected final int arc = 10;
            protected int getArc(Component c) {
                if (this.isCellEditor(c)) return 0;
                Boolean roundRect = FlatUIUtils.isRoundRect(c);
                return roundRect != null ? (roundRect ? 32767 : 0) : this.arc;
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
        rgbContainer.setLayout(new VerticalFlowLayout(VerticalFlowLayout.CENTER, VerticalFlowLayout.CENTER, 0, 10));
        rgbContainer.setBorder(new FlatRoundBorder());
        rgbContainer.add(containerR);
        rgbContainer.add(containerG);
        rgbContainer.add(containerB);
    
        //BufferedImage gradientImgBufferedImage = null;
        
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
        
        JPanel selectedClrAndRGBcontainer = new JPanel(new VerticalFlowLayout(VerticalFlowLayout.CENTER, VerticalFlowLayout.TOP));
        selectedClrAndRGBcontainer.add(rgbContainer);
        selectedClrAndRGBcontainer.add(selectedColorPanel);
        selectedClrAndRGBcontainer.setBorder(brd);
        
        
        
        // MAIN GRADIENT PANEL
        Dimension gradPanelSize = new Dimension(200, 200);
        
        GradientPaint shade = new GradientPaint(
                0f, 0f, new Color(0, 0, 0, 0),
                0f, gradPanelSize.height, new Color(0, 0, 0, 255));
        BufferedImage gradientImg = new BufferedImage(gradPanelSize.width, gradPanelSize.height, BufferedImage.TYPE_INT_RGB);
        // thanks to https://stackoverflow.com/questions/13771575/java-3-color-gradient
        JPanel gradientPanel = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                // anti-aliasing
                Graphics2D gr = (Graphics2D) g;
                gr.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                gr.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                
                //gr.setPaint(primary);
                //gr.fillRect(0, 0, getWidth(), getHeight());
                //gr.setPaint(shade);
                //gr.fillRect(0, 0, getWidth(), getHeight());
                
                GradientPaint primary = new GradientPaint(
                        0f, 0f, Color.WHITE, gradPanelSize.width, 0f, rainbowSelectedClr.clr);
                
                // render gradientImg so that we can extract colors from it
                Graphics2D imgG = gradientImg.createGraphics();
                imgG.setPaint(primary);
                imgG.fillRect(0, 0, getWidth(), getHeight());
                imgG.setPaint(shade);
                imgG.fillRect(0, 0, getWidth(), getHeight());
                
                gr.drawImage(gradientImg, null, 0, 0);
                
                // border
                gr.setColor(Color.lightGray);
                gr.drawRect(0, 0, getWidth()-1, getHeight()-1);
                
                // draw indicator
                gr.setStroke(new BasicStroke(3));
                gr.setColor(Color.YELLOW);
                gr.drawOval(mouseGradient.x-5, mouseGradient.y-5, 15, 15);
            }
        };
        //Robot finalRbt = rbt;
        gradientPanel.addMouseMotionListener(new MouseMotionListener() {
            @Override public void mouseMoved(MouseEvent e) { }
            @Override public void mouseDragged(MouseEvent e) {
                if (!gradientPanel.contains(e.getPoint())) return;
                mouseGradient.setLocation(e.getPoint().x-2, e.getPoint().y-2);
                
                // extract color
                selectedColor.clr = new Color(gradientImg.getRGB(e.getPoint().x, e.getPoint().y));
                
                selectedColorPanel.repaint();
                gradientPanel.repaint();
            }
        });
        gradientPanel.setPreferredSize(gradPanelSize);
        
        
        
        // RAINBOW PANEL
        Point mouseRainbow = new Point(0, 0);
        Dimension rainbowPanelSize = new Dimension(new Dimension(gradPanelSize.width, 30));
        final var rainbowImg = new Object() { BufferedImage img = new BufferedImage(gradPanelSize.width, 30, BufferedImage.TYPE_INT_RGB); };
        JPanel rainbowPanel = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D gr = (Graphics2D) g;
                gr.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                gr.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                
                // rainbow fill (TODO)
                //gr.setPaint(primary);
                //gr.fillRoundRect(0, 0, getWidth()-1, getHeight(), 20, 20);
                //gr.setPaint(shade);
                //gr.fillRoundRect(0, 0, getWidth()-1, getHeight(), 20, 20);
                
                // render rainbowImg so that we can extract colors from it
                Graphics2D imgG = rainbowImg.img.createGraphics();
                //imgG.setPaint(primary);
                //imgG.fillRect(0, 0, getWidth(), getHeight());
                //imgG.setPaint(shade);
                //imgG.fillRect(0, 0, getWidth(), getHeight());
    
                LinearGradientPaint lgp = new LinearGradientPaint(
                        new Point(0, 0),
                        new Point(getWidth(), 0),
                        new float[]{0.112f, 0.254f, 0.396f, 0.538f, 0.68f, 0.822f, 1f},
                        new Color[]{Color.PINK, Color.MAGENTA, Color.BLUE, Color.GREEN, Color.YELLOW, Color.ORANGE, Color.RED});
                imgG.setPaint(lgp);
                imgG.fill(new Rectangle(0, 0, getWidth(), getHeight()));
    
                rainbowImg.img = makeRoundedCorner(rainbowImg.img, 20);
                gr.drawImage(rainbowImg.img, null, 0, 0);
                
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
                if (!rainbowPanel.contains(e.getPoint())) return;
                if (e.getPoint().getX() >= rainbowPanel.getWidth()-7) return; // subtract slider width
                
                mouseRainbow.setLocation(e.getPoint());
                // extract color
                rainbowSelectedClr.clr = new Color(rainbowImg.img.getRGB(mouseRainbow.x, mouseRainbow.y));
                rainbowPanel.repaint();
                gradientPanel.repaint();
                
                // force generate (simulate) a call to mouseDragged
                // so that function exectues, recalculating selected color
                // for selectedColorPanel
                // +2 on coords, beucase that method subtracts 2
                gradientPanel.getListeners(MouseMotionListener.class)[0]
                        .mouseDragged(new MouseEvent(
                                gradientPanel,0,0,0,
                                mouseGradient.x+2, mouseGradient.y+2,
                                0,0, 0, false, 0));
            }
        });
        rainbowPanel.setPreferredSize(rainbowPanelSize);
        
        
        JPanel gradientsContainer = new JPanel(new VerticalFlowLayout());
        gradientsContainer.add(gradientPanel);
        gradientsContainer.add(rainbowPanel);
        
        mainColorContainer.add(gradientsContainer);
        mainColorContainer.add(selectedClrAndRGBcontainer);
        
        this.add(mainColorContainer);
    }
    
    public static BufferedImage makeRoundedCorner(BufferedImage image, int cornerRadius) {
        int w = image.getWidth();
        int h = image.getHeight();
        BufferedImage output = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        
        Graphics2D g2 = output.createGraphics();
        
        // This is what we want, but it only does hard-clipping, i.e. aliasing
        // g2.setClip(new RoundRectangle2D ...)
        
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

