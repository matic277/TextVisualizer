package main;

import java.awt.*;
import java.util.Random;

public class Utils {
    
    public static final Color bgColor = new Color(245, 245, 250);
    public static final Color borderColor = new Color(227, 227, 227);
    public static final Color RED = Color.decode("#ea4335");
    public static final Color GRAY = Color.decode("#BFBDBD");
    public static final Color GRAY2 = Color.decode("#E0E0E0");
    public static final Color GRAY3 = new Color(243, 243, 243);
    public static final Color GREEN = Color.decode("#34A853");
    public static final Color MEUN_COLORS = Color.decode("#E0DDDD");
    
    public static final Dimension MENU_BUTTON_SIZE = new Dimension(40, 40);
    public static final Dimension MENU_BUTTON_SIZE_WIDE = new Dimension(100, 40);
    public static final Dimension MENU_CHECKBOX_SIZE = new Dimension(125, 15);
    
    public static final Dimension SENTENCE_SIZE = new Dimension(7, 50);
    
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
    
    public static Font getBoldFont(int size) {
        return new Font("Roboto Mono SemiBold", Font.BOLD, size);
    }
    
    public static Font getFont(int size) { return new Font("Roboto Mono Medium", Font.PLAIN, size); }
    
    public static String randomString(int len) {
        StringBuilder str = new StringBuilder();
        while (len-- > 0) {
            str.append((char)(97 + RAND.nextInt(25)));
        }
        return str.toString();
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
