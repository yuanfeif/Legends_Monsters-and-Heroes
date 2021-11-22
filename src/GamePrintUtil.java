import java.util.ArrayList;

public class GamePrintUtil {
    private static final String SPLIT_LINE = Colors.setGroundColor(
            "---------------------------------------------------------------------------------------------------------",
            Colors.BLACK, Colors.PURPLE_BG) + "\n";
    /**
     * print the whole team and all the information of each hero
     */
    public static void printTeam(ArrayList<Hero> team) {
        String name;
        double hp, strength, dexterity, agility, money, mana;
        int level, exp;

        System.out.println();
        System.out.println(Colors.setColor("Here is your team!", Colors.BLUE));

        String title = "Name                       HP    Level    Mana    Strength    Dexterity    Agility    Money    Exp";
        System.out.println(title);
        System.out.print(SPLIT_LINE);
        System.out.printf("%-25s %-8.0f %-6d %-9.0f %-12.0f %-11.0f %-9.0f %-8.0f %-5d", 1 + ". " + team.get(0).getName(), team.get(0).getHp(), team.get(0).getLevel(), team.get(0).getMana(), team.get(0).getStrength(),
                team.get(0).getDexterity(), team.get(0).getAgility(), team.get(0).getBag().getMoney(), team.get(0).getExp());
        System.out.println();
        for (int i = 1; i < team.size(); ++i) {
            name = (i + 1) + ". " + team.get(i).getName();
            hp = team.get(i).getHp();
            level = team.get(i).getLevel();
            mana = team.get(i).getMana();
            strength = team.get(i).getStrength();
            dexterity = team.get(i).getDexterity();
            agility = team.get(i).getAgility();
            money = team.get(i).getBag().getMoney();
            exp = team.get(i).getExp();

            System.out.printf("%-25s %-8.0f %-6d %-9.0f %-12.0f %-11.0f %-9.0f %-8.0f %-5d", name, hp, level, mana, strength,
                    dexterity, agility, money, exp);
            System.out.println();

            if ((i + 1 < team.size()) && (!team.get(i).getClass().getName().equals
                    (team.get(i + 1).getClass().getName()))) {
                System.out.println(SPLIT_LINE);
            }
        }
        System.out.println(SPLIT_LINE);
    }


    /**
     * use different colors to print different types of system information
     */
    public static void printSystemNotification(String str) {
        System.out.println(Colors.setGroundColor(str, Colors.BLACK, Colors.RED_BG));
    }

    public static void printSystemInfo(String str) {
        System.out.println(Colors.setColor(str, Colors.BLUE));
    }

    public static void printSystemHint(String str) {
        System.out.println(Colors.setColor(str, Colors.WHITE));
    }
    public static final String ANSI_RED = "\u001B[31m";

    public static String DRAGON = ANSI_RED +  "___________________________________________________\n" +
            "@@@@@@@@@@@@@@@@@@@@@**^^\"\"~~~\"^@@^*@*@@**@@@@@@@@@\n" +
            "@@@@@@@@@@@@@*^^'\"~   , - ' '; ,@@b. '  -e@@@@@@@@@\n" +
            "@@@@@@@@*^\"~      . '     . ' ,@@@@(  e@*@@@@@@@@@@\n" +
            "@@@@@^~         .       .   ' @@@@@@, ~^@@@@@@@@@@@\n" +
            "@@@~ ,e**@@*e,  ,e**e, .    ' '@@@@@@e,  \"*@@@@@'^@\n" +
            "@',e@@@@@@@@@@ e@@@@@@       ' '*@@@@@@    @@@'   0\n" +
            "@@@@@@@@@@@@@@@@@@@@@',e,     ;  ~^*^'    ;^~   ' 0\n" +
            "@@@@@@@@@@@@@@@^\"\"^@@e@@@   .'           ,'   .'  @\n" +
            "@@@@@@@@@@@@@@'    '@@@@@ '         ,  ,e'  .    ;@\n" +
            "@@@@@@@@@@@@@' ,&&,  ^@*'     ,  .  i^\"@e, ,e@e  @@\n" +
            "@@@@@@@@@@@@' ,@@@@,          ;  ,& !,,@@@e@@@@ e@@\n" +
            "@@@@@,~*@@*' ,@@@@@@e,   ',   e^~^@,   ~'@@@@@@,@@@\n" +
            "@@@@@@, ~\" ,e@@@@@@@@@*e*@*  ,@e  @@\"\"@e,,@@@@@@@@@\n" +
            "@@@@@@@@ee@@@@@@@@@@@@@@@\" ,e@' ,e@' e@@@@@@@@@@@@@\n" +
            "@@@@@@@@@@@@@@@@@@@@@@@@\" ,@\" ,e@@e,,@@@@@@@@@@@@@@\n" +
            "@@@@@@@@@@@@@@@@@@@@@@@~ ,@@@,,0@@@@@@@@@@@@@@@@@@@\n" +
            "@@@@@@@@@@@@@@@@@@@@@@@@,,@@@@@@@@@@@@@@@@@@@@@@@@@\n" +
            "\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\""
            + ANSI_RED;
}
