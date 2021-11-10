/**
 * @ClassName GenericCharacter
 * @Description represents characters in RPG games
 * @Author Vincent Yuan
 */
public class GenericCharacter {

    private String name;
    private int level;
    private double hp;

    public GenericCharacter(String name, int level) {
        this.name = name;
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public double getHp() {
        return hp;
    }

    public void setHp(double hp) {
        this.hp = hp;
    }

}
