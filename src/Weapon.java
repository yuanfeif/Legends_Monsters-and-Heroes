import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName Weapon
 * @Description weapons in Legends
 * @Author Vincent Yuan
 */
public class Weapon extends Prop implements StrategyParsing, isAttackable{

    private int baseDamage;
    private int requiredHands;

    public Weapon() {}

    public Weapon(String name, int price, int minRequiredLevel, int baseDamage, int requiredHands) {
        super(name, price, minRequiredLevel);
        this.baseDamage = baseDamage;
        this.requiredHands = requiredHands;
    }

    @Override
    public int getBaseDamage() {
        return baseDamage;
    }

    public void setBaseDamage(int baseDamage) {
        this.baseDamage = baseDamage;
    }

    public int getRequiredHands() {
        return requiredHands;
    }

    public void setRequiredHands(int requiredHands) {
        this.requiredHands = requiredHands;
    }

    /**
     * parse weapons files
     * @param fileName
     * @return ArrayList<Prop>
     */
    @Override
    public ArrayList<Prop> parsingPropFile(String fileName) {
        ArrayList<Prop> res = new ArrayList<>();
        List<String> lists = new ArrayList<>();
        lists = ParsingFile.parsingFile(fileName);
        for (String list: lists) {
            String[] s = list.split("\\s+");
            String name = s[0];
            int price = Integer.parseInt(s[1]);
            int minRequiredLevel = Integer.parseInt(s[2]);
            int baseDamage = Integer.parseInt(s[3]);
            int requiredHands = Integer.parseInt(s[4]);
            Weapon weapon = new Weapon(name, price, minRequiredLevel, baseDamage, requiredHands);
            res.add(weapon);
        }
        return res;
    }
}
