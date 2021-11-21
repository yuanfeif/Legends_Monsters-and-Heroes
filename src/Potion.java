import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName Potion
 * @Description potions in Legends
 * @Author Vincent Yuan
 */
public class Potion extends Prop implements StrategyParsing, isUsable{
    /**
     * specific ability will be affected
     * use string to represent
     * parse the string when using a potion
     */
    private String attrAffected;
    private int attrIncreased;

    public Potion() {}

    public Potion(String name, int price, int minRequiredLevel, String attrAffected, int attrIncreased) {
        super(name, price, minRequiredLevel);
        this.attrAffected = attrAffected;
        this.attrIncreased = attrIncreased;
    }

    @Override
    public String getAttrAffected() {return attrAffected;}

    public void setAttrAffected(String attrAffected) {
        this.attrAffected = attrAffected;
    }

    @Override
    public int getAttrIncreased() {
        return attrIncreased;
    }

    public void setAttrIncreased(int attrIncreased) {
        this.attrIncreased = attrIncreased;
    }

    /**
     * parse potions files
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
            int attrIncreased = Integer.parseInt(s[3]);
            String attrAffected = s[4];
            Potion potion = new Potion(name, price, minRequiredLevel, attrAffected, attrIncreased);
            res.add(potion);
        }
        return res;
    }
}
