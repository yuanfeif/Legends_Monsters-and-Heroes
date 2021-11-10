import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName Armor
 * @Description armors in Legends
 * @Author Vincent Yuan
 */
public class Armor extends Prop implements StrategyParsing{

    private int damageReduction;

    public Armor() {}

    public Armor(String name, int price, int minRequiredLevel, int damageReduction) {
        super(name, price, minRequiredLevel);
        this.damageReduction = damageReduction;
    }

    public int getDamageReduction() {
        return damageReduction;
    }

    public void setDamageReduction(int damageReduction) {
        this.damageReduction = damageReduction;
    }

    /**
     * parse armors files
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
            int damageReduction = Integer.parseInt(s[3]);
            Armor armor = new Armor(name, price, minRequiredLevel, damageReduction);
            res.add(armor);
        }
        return res;
    }
}
