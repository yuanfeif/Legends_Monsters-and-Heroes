import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @ClassName HeroList
 * @Description a class used to parse hero files and print all types of heroes
 * @Author Vincent Yuan
 */
public class HeroList {

    private static final String SPLIT_LINE = Colors.setGroundColor(
            "---------------------------------------------------------------------------------------------------------",
            Colors.BLACK, Colors.PURPLE_BG) + "\n";
    private static ArrayList<Hero> heroes;

    public HeroList() {

    }

    public static ArrayList<Hero> getHeroes() {
        return heroes;
    }

    /**
     * parse hero files
     * @param fileName
     * @return ArrayList<Hero>
     */
    public static ArrayList<Hero> parseFile(String fileName) {
        //parse a file
        String file = System.getProperty("user.dir") + "/src/ConfigFiles/" + fileName;
        List<String> lines = Collections.emptyList();
        try {
            lines = Files.readAllLines(Paths.get(file), StandardCharsets.UTF_8);
            lines.remove(0);
        } catch (IOException e) {
            System.out.println("Please enter the correct filepath");
            e.printStackTrace();
        }

        //parse strings in a file
        ArrayList<Hero> res = new ArrayList<>();
        for (String list: lines) {
            String[] s = list.split("\\s+");
            String name = s[0];
            double mana = Double.parseDouble(s[1]);
            double strength = Double.parseDouble(s[2]);
            double agility = Double.parseDouble(s[3]);
            double dexterity = Double.parseDouble(s[4]);
            int money = Integer.parseInt(s[5]);
            int exp = Integer.parseInt(s[6]);
            if ("Paladins.txt".equals(fileName)) {
                HeroPaladin hero = new HeroPaladin(name, 1, mana, strength, dexterity, agility, exp, new Bag(money));
                res.add(hero);
            } else if ("Sorcerers.txt".equals(fileName)) {
                HeroSorcerer hero = new HeroSorcerer(name, 1, mana, strength, dexterity, agility, exp, new Bag(money));
                res.add(hero);
            } else if ("Warriors.txt".equals(fileName)) {
                HeroWarrior hero = new HeroWarrior(name, 1, mana, strength, dexterity, agility, exp, new Bag(money));
                res.add(hero);
            }
        }
        return res;
    }

    /**
     * parse different files of different types of Heroes
     */
    public static void setHeroes() {
        heroes = new ArrayList<>();
        heroes.addAll(parseFile("Paladins.txt"));
        heroes.addAll(parseFile("Sorcerers.txt"));
        heroes.addAll(parseFile("Warriors.txt"));
    }

    /**
     * print the whole hero list
     */
    public static void printHeroList() {
        setHeroes(); 

        String name;
        double hp, strength, dexterity, agility, money, mana;
        int level, exp;

        System.out.println(Colors.setColor("Here is a hero list!", Colors.BLUE));

        // A line to split different kinds of heroes (i.e. warriors/sorcerers/paladins)
        String title = "Name                       HP    Level    Mana    Strength    Dexterity    Agility    Money    Exp";

        System.out.println(Colors.setColor("Paladins: ", Colors.GREEN));
        System.out.println(title);
        System.out.print(SPLIT_LINE);
        System.out.printf("%-25s %-8.0f %-6d %-9.0f %-12.0f %-11.0f %-9.0f %-8.0f %-5d", 1 + ". " + heroes.get(0).getName(), heroes.get(0).getHp(), heroes.get(0).getLevel(), heroes.get(0).getMana(), heroes.get(0).getStrength(),
                heroes.get(0).getDexterity(), heroes.get(0).getAgility() , heroes.get(0).getBag().getMoney(), heroes.get(0).getExp());
        System.out.println();
        for (int i = 1; i < heroes.size(); ++i) {
            if (heroes.get(i) instanceof HeroSorcerer && heroes.get(i - 1) instanceof HeroPaladin) {
                System.out.println(Colors.setColor("Sorcerers", Colors.GREEN));
                System.out.println(title);
                System.out.print(SPLIT_LINE);
            } else if (heroes.get(i) instanceof HeroWarrior && heroes.get(i - 1) instanceof HeroSorcerer) {
                System.out.println(Colors.setColor("Warriors", Colors.GREEN));
                System.out.println(title);
                System.out.print(SPLIT_LINE);
            }

            name = (i + 1) + ". " + heroes.get(i).getName();
            hp = heroes.get(i).getHp();
            level = heroes.get(i).getLevel();
            mana = heroes.get(i).getMana();
            strength = heroes.get(i).getStrength();
            dexterity = heroes.get(i).getDexterity();
            agility = heroes.get(i).getAgility();
            money = heroes.get(i).getBag().getMoney();
            exp = heroes.get(i).getExp();

            System.out.printf("%-25s %-8.0f %-6d %-9.0f %-12.0f %-11.0f %-9.0f %-8.0f %-5d", name, hp, level, mana, strength,
                    dexterity, agility , money, exp);
            System.out.println();

            if ((i + 1 < heroes.size()) && (!heroes.get(i).getClass().getName().equals
                    (heroes.get(i + 1).getClass().getName()))) {
                System.out.println(SPLIT_LINE);
            }
        }
        System.out.println(SPLIT_LINE);
    }

}
