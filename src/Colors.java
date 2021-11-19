/**
 * @ClassName Colors
 * @Description store some useful color to realize colorful console
 * @Author Vincent Yuan
 */
public class Colors {
    /**
     * foreground color
     */
    public static final String BLACK = "30";
    public static final String RED = "31";
    public static final String GREEN = "32";
    public static final String YELLOW = "33";
    public static final String BLUE = "34";
    public static final String PURPLE = "35";
    public static final String CYAN = "36";
    public static final String WHITE = "37";

    /**
     * background color
     */
    public static final String BLACK_BG = "40";
    public static final String RED_BG = "41";
    public static final String GREEN_BG = "42";
    public static final String YELLOW_BG = "43";
    public static final String BLUE_BG = "44";
    public static final String PURPLE_BG = "45";
    public static final String CYAN_BG = "46";
    public static final String WHITE_BG = "47";

    /**
     * just foreground color of a string
     * @param str
     * @param color
     * @return String
     */
    public static String setColor(String str, String color) {
        return "\033[" + color + "m" + str + "\033[0m";
    }

    /**
     * set foreground color and background color of a string
     * @param str
     * @param fore
     * @param back
     * @return String
     */
    public static String setGroundColor(String str, String fore, String back) {
        return "\033[" + fore + ";" + back + "m" + str + "\033[0m";
    }

    public static String justSetGroundColor(String str, String back) {
        return "\033[" + back + "m" + str + "\033[0m";
    }
}
