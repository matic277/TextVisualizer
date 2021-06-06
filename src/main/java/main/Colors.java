package main;

import java.awt.*;

public class Colors {
    
    public static Color HIGH_SENTIMENT = Color.decode("#34A853");
    public static Color MED_SENTIMENT = Color.decode("#E0E0E0");
    public static Color LOW_SENTIMENT = Color.decode("#ea4335");
    public static Color UNKNOWN_WORD = new Color(240, 240, 240);;
    
    public static Color HIGH_SENTIMENT_HIGHLIGHTED = HIGH_SENTIMENT.brighter();
    public static Color MED_SENTIMENT_HIGHLIGHTED = MED_SENTIMENT.brighter();
    public static Color LOW_SENTIMENT_HIGHLIGHTED = LOW_SENTIMENT.brighter();
    
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
    
}
