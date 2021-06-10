package main;

import com.ibm.icu.number.IntegerWidth;
import org.apache.commons.lang3.Range;

import javax.swing.border.Border;
import java.awt.*;
import java.util.Random;

public class Utils {
    
    public static final Color bgColor = new Color(245, 245, 250);
    public static final Color TITLE_BACKGROUND = new Color(213, 216, 222);
    public static final Color borderColor = new Color(227, 227, 227);
    public static final Color RED = Color.decode("#ea4335");
    public static final Color BACKGROUND_RED = new Color(255, 242, 242);
    public static final Color GRAY = Color.decode("#BFBDBD");
    public static final Color GRAY2 = Color.decode("#E0E0E0");
    public static final Color GRAY3 = new Color(243, 243, 243);
    public static final Color GREEN = Color.decode("#34A853");
    public static final Color BACKGROUND_GREEN = new Color(236, 255, 236);
    public static final Color LIGHT_GRAY = new Color(243, 243, 243);
    public static final Color MEUN_COLORS = Color.decode("#E0DDDD");
    
    public static final Range<Integer> SENTENCE_WIDTH_RANGE = Range.between(1, 300);
    public static final Range<Integer> SENTENCE_HEIGHT_RANGE = Range.between(1, 300);
    public static final Dimension SENTENCE_SIZE = new Dimension(5, 50);
    
    Color c = new Color(187, 187, 187, 133);
    
    public static final Dimension MENU_BUTTON_SIZE = new Dimension(40, 40);
    public static final Dimension MENU_BUTTON_SIZE_WIDE = new Dimension(100, 40);
    public static final Dimension MENU_CHECKBOX_SIZE = new Dimension(125, 15);
    
    public static final int INITIAL_SLIDER_WIDTH = 120;
    
    public static final Stroke PLAIN_STROKE = new BasicStroke(1);
    public static final Stroke BOLD_STROKE = new BasicStroke(1.7f);
    public static final Stroke BOLDER_STROKE = new BasicStroke(2f);
    public static final Stroke BOLDEST_STROKE = new BasicStroke(5f);
    
    public static final int INITIAL_WINDOW_WIDTH = 1600;
    public static final int INITIAL_WINDOW_HEIGHT = 1200;
    public static final int INITIAL_LEFT_MENU_WIDTH = 700;
//    public static final int MAXIMUM_LEFT_MENU_WIDTH = 0;
    public static final int INITIAL_BOTTOM_MENU_HEIGHT = 300;
    
    
    public static final Random RAND = new Random();
    
    public static Font getBoldFont(int size) { return new Font("Roboto Mono bold", Font.BOLD, size); }
    
    public static Font getFont(int size) { return new Font("Roboto Mono medium", Font.PLAIN, size); }
    
    public static Font getTableHeaderFont(int size) { return new Font("Roboto Mono medium", Font.PLAIN, size); }
    
    public static Font getWordFont(int size) { return new Font("Roboto Mono medium", Font.PLAIN, size); }
    
    public static Font getButtonFont(int size) { return new Font("Source sans pro", Font.PLAIN, size); }
    
    // expected input: "255, 255, 255"
    // 3 ints split by ","
    // spaces are optional
    public static Color parseColor(String str) throws RuntimeException {
        String[] tokens = str.trim().split(",");
    
        int r = Integer.parseInt(tokens[0].trim());
        int g = Integer.parseInt(tokens[1].trim());
        int b = Integer.parseInt(tokens[2].trim());
        
        if ((r >= 0 && r <= 255) &&
                (g >= 0 && g <= 255) &&
                (b >= 0 && b <= 255)) {
            return new Color(r, g, b);
        }
        throw new RuntimeException("Color value out of range.");
    }
    
    public static String colorToString(Color c) {
        return "RGBa("+c.getRed()+","+c.getGreen()+","+c.getBlue()+","+c.getAlpha()+")";
    }
    
    
    // SF UI  Text
    // SF UI  Text 2
    // SF UI  Text Med
    // SF UI  Text SemBd
    
    public static String randomString(int len) {
        StringBuilder str = new StringBuilder();
        while (len-- > 0) {
            str.append((char)(97 + RAND.nextInt(25)));
        }
        return str.toString();
    }
    
    public static class RoundBorder implements Border {
        private final Color clr;
        public Color bgClr;
        private final int rad;
        private final Stroke stroke;
        public RoundBorder(Color clr, Color bgClr, Stroke stroke, int rad) { this.rad = rad; this.clr = clr; this.stroke = stroke; this.bgClr = bgClr;}
        
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            // anti-aliasing
            Graphics2D gr = (Graphics2D) g;
            gr.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            gr.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            
            if (bgClr != null) {
                gr.setColor(bgClr);
                gr.fillRoundRect(x + 1, y + 3, width - 3, height - 6, rad, rad);
            }
            gr.setColor(clr);
            gr.setStroke(stroke);
            gr.drawRoundRect(x+1, y+1, width-3, height-3, rad, rad); // these offsets should probably depend on Stroke thickness
        }
        public boolean isBorderOpaque() { return true; }
        public Insets getBorderInsets(Component c) { return new Insets(4, 8, 4, 8); }
    }
    
    public static Color getRandomColor() {
        return new Color(RAND.nextInt(256), RAND.nextInt(256), RAND.nextInt(256));
    }
    
    public static void sleep(int ms) {
        try { Thread.sleep(ms); }
        catch (Exception e) { e.printStackTrace(); }
    }
    
    public static void sleep(long ms) {
        try { Thread.sleep(ms); }
        catch (Exception e) { e.printStackTrace(); }
    }
}
