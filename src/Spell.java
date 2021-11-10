import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName Spell
 * @Description spells in Legends
 * @Author Vincent Yuan
 */
public class Spell extends Prop implements StrategyParsing{

    private int damage;
    /**
     * required mana if using th spell
     */
    private int requiredMana;

    public Spell() {}

    public Spell(String name, int price, int minRequiredLevel, int damage, int requiredMana) {
        super(name, price, minRequiredLevel);
        this.damage = damage;
        this.requiredMana = requiredMana;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getRequiredMana() {
        return requiredMana;
    }

    public void setRequiredMana(int requiredMana) {
        this.requiredMana = requiredMana;
    }

    /**
     * parse spells files
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
            int damage = Integer.parseInt(s[3]);
            int requiredMana = Integer.parseInt(s[4]);
            if ("FireSpells.txt".equals(fileName)) {
                SpellFire spell = new SpellFire(name, price, minRequiredLevel, damage, requiredMana);
                res.add(spell);
            } else if ("IceSpells.txt".equals(fileName)) {
                SpellIce spell = new SpellIce(name, price, minRequiredLevel, damage, requiredMana);
                res.add(spell);
            } else if ("LightningSpells.txt".equals(fileName)) {
                SpellLightning spell = new SpellLightning(name, price, minRequiredLevel, damage, requiredMana);
                res.add(spell);
            }
        }
        return res;
    }
}
