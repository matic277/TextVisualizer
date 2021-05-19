package panel;

import main.Pair;
import main.Sentence;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.Map;

public class SlidingWindow extends Rectangle {
    
    TopPanel parent;
    JPanel drawingParent;
    Map<Pair<Integer, String>, List<Sentence>> chapters;
//    SlidingWindowListener listener;
    
    Color color = Color.black;
    
    public SlidingWindow(TopPanel parent) {
        this.parent = parent;
//        this.chapters = parent.chapters;
        
        // transparent
//        this.setOpaque(true);
//        this.setBackground(new Color(255, 0, 0, 100));
        
//        this.setBorder(new StrokeBorder(new BasicStroke(2)));
        
        this.setBounds(30, 100, 100, 50);
//        this.setPreferredSize(new Dimension(100, 50));

        init();
    }
    
    public void paint(Graphics g) {
        g.setColor(color);
        g.drawRect(x, y, width, height);
        
        g.drawString("[" + x + ", " + y + "]", x, y-4);
    }
    
    public void init() {
//        JPanel content = new JPanel();
//        content.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
//        content.setBackground(Color.black);
        
        initListener();
    }
    
    private void initListener() {
//        listener = new SlidingWindowListener();
//        this.addMouseMotionListener(listener);
//        this.addMouseListener(listener);
    }
    
    public void setParent(JPanel sliderPanel) {
        this.drawingParent = sliderPanel;
    }
    
    public void setColor(Color color) {
        this.color = color;
    }
}

class OverlayLayoutTest extends JPanel
        implements ActionListener
{
    JPanel green;
    JPanel red;
    JLabel greenLabel;
    JLabel redLabel;
    JComboBox  greenAlignmentX;
    JComboBox  greenAlignmentY;
    JComboBox  redAlignmentX;
    JComboBox  redAlignmentY;
    
    public OverlayLayoutTest()
    {
        setLayout( new BorderLayout(10, 10) );
        add(createNorthPanel(), BorderLayout.NORTH);
        add(createCenterPanel(), BorderLayout.CENTER);
        add(createSouthPanel(), BorderLayout.SOUTH);
    }
    
    private JPanel createNorthPanel()
    {
        JPanel panel = new JPanel();
        
        panel.add( new JLabel("Green:") );
        greenLabel = new JLabel();
        panel.add( greenLabel );
        
        panel.add( new JLabel("Red:") );
        redLabel = new JLabel();
        panel.add( redLabel );
        
        return panel;
    }
    
    private JPanel createCenterPanel()
    {
        JPanel panel = new JPanel();
        panel.setLayout( new OverlayLayout(panel) );
        panel.setBackground( Color.ORANGE );
        panel.setPreferredSize( new Dimension(200, 200) );
        
        red = new JPanel();
        red.setBackground( new Color(255, 0, 0, 100));
        red.setPreferredSize( new Dimension(50, 50) );
        red.setMaximumSize( red.getPreferredSize() );
        red.setMinimumSize( red.getPreferredSize() );
        panel.add( red );
        
        green = new JPanel();
        green.setBackground( Color.GREEN );
        green.setPreferredSize( new Dimension(100, 100) );
        green.setMaximumSize( green.getPreferredSize() );
        green.setMinimumSize( green.getPreferredSize() );
        panel.add( green );
        
        JPanel wrap = new JPanel();
        wrap.add( panel );
        return wrap;
    }
    
    private JPanel createSouthPanel()
    {
        JPanel panel = new JPanel( new GridLayout(1, 0, 10, 10) );
        
        JPanel green = new JPanel(new GridLayout(0, 2, 5, 5) );
        green.setBorder( new TitledBorder("Green Alignment") );
        green.add( new JLabel("X Alignment:") );
        greenAlignmentX = createComboBox();
        green.add( greenAlignmentX );
        green.add( new JLabel("Y Alignment:") );
        greenAlignmentY = createComboBox();
        green.add( greenAlignmentY );
        panel.add( green );
        
        JPanel red = new JPanel(new GridLayout(0, 2, 5, 5) );
        red.setBorder( new TitledBorder("Red Alignment") );
        red.add( new JLabel("X Alignment:") );
        redAlignmentX = createComboBox();
        red.add( redAlignmentX );
        red.add( new JLabel("Y Alignment:") );
        redAlignmentY = createComboBox();
        red.add( redAlignmentY );
        panel.add( red );
        
        JButton reset = new JButton("Reset Alignment");
        reset.addActionListener( this );
        panel.add( reset );
        
        
        return panel;
    }
    
    public void actionPerformed(ActionEvent e)
    {
        green.setAlignmentX( ((Float)greenAlignmentX.getSelectedItem()) );
        green.setAlignmentY( ((Float)greenAlignmentY.getSelectedItem()) );
        red.setAlignmentX( ((Float)redAlignmentX.getSelectedItem()) );
        red.setAlignmentY( ((Float)redAlignmentY.getSelectedItem()) );
        JPanel parent = (JPanel)green.getParent();
        parent.revalidate();
/*
        System.out.print(green.getAlignmentX() + " : ");
        System.out.print(green.getAlignmentY() + " : ");
        System.out.print(red.getAlignmentX() + " : ");
        System.out.print(red.getAlignmentY() + " : ");
        System.out.println();
*/
        SwingUtilities.invokeLater(new Runnable()
        {
            public void run()
            {
                greenLabel.setText( green.getLocation().toString() );
                redLabel.setText( red.getLocation().toString() );
            }
        });
        
    }
    
    private JComboBox createComboBox()
    {
        JComboBox<Float> comboBox = new JComboBox<Float>();
        
        comboBox.addItem( new Float(0f) );
        comboBox.addItem( new Float(0.25f) );
        comboBox.addItem( new Float(0.5f) );
        comboBox.addItem( new Float(0.75f) );
        comboBox.addItem( new Float(1.0f) );
        comboBox.setSelectedItem(0.5f);
        
        return comboBox;
    }
    
    private static void createAndShowUI()
    {
        JFrame frame = new JFrame("OverlayLayoutTest");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add( new OverlayLayoutTest() );
        frame.pack();
        frame.setLocationByPlatform( true );
        frame.setVisible( true );
    }
    
    public static void main(String[] args)
    {
        EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                createAndShowUI();
            }
        });
    }
}
