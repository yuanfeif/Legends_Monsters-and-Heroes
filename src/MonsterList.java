import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @ClassName MonsterList
 * @Description a class used to parse monster files and print all types of monsters
 * @Author Vincent Yuan
 */
public class MonsterList {
    private static final String SPLIT_LINE = Colors.setGroundColor(
            "----------------------------------------------------------------------------------",
            Colors.BLACK, Colors.PURPLE_BG) + "\n";
    private static ArrayList<Monster> monsters;

    public MonsterList() {

    }

    public static ArrayList<Monster> getMonsters() {
        return monsters;
    }

    /**
     * parse monster files
     * @param fileName
     * @return
     */
    public static ArrayList<Monster> parseFile(String fileName) {
        String file = System.getProperty("user.dir") + "/src/ConfigFiles/" + fileName;
        List<String> lines = Collections.emptyList();
        try {
            lines = Files.readAllLines(Paths.get(file), StandardCharsets.UTF_8);
            lines.remove(0);
        } catch (IOException e) {
            System.out.println("Please enter the correct filepath");
            e.printStackTrace();
        }

        ArrayList<Monster> res = new ArrayList<>();
        for (String list: lines) {
            String[] s = list.split("\\s+");
            String name = s[0];
            int level = Integer.parseInt(s[1]);
            double damage = Double.parseDouble(s[2]);
            double defense = Double.parseDouble(s[3]);
            double dodgeChance = Double.parseDouble(s[4]);
            if ("Dragons.txt".equals(fileName)) {
                MonsterDragon monster = new MonsterDragon(name, level, damage, defense, dodgeChance);
                res.add(monster);
            } else if ("Exoskeletons.txt".equals(fileName)) {
                MonsterExoskeleton monster = new MonsterExoskeleton(name, level, damage, defense, dodgeChance);
                res.add(monster);
            } else if ("Spirits.txt".equals(fileName)) {
                MonsterSpirit monster = new MonsterSpirit(name, level, damage, defense, dodgeChance);
                res.add(monster);
            }
        }
        return res;
    }

    /**
     * parse different files of different types of Monsters
     */
    public static void setMonsters() {
        monsters = new ArrayList<>();
        monsters.addAll(parseFile("Dragons.txt"));
        monsters.addAll(parseFile("Exoskeletons.txt"));
        monsters.addAll(parseFile("Spirits.txt"));
    }
}
