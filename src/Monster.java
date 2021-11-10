/**
 * @ClassName Monster
 * @Description monsters in Legends
 * @Author Vincent Yuan
 */
public class Monster extends GenericCharacter {
    private static final String SPLIT_LINE = Colors.setGroundColor(
            "----------------------------------------------------------------------------------",
            Colors.BLACK, Colors.PURPLE_BG) + "\n";
    private double damage;
    private double defense;
    private double dodgeChance;

    public Monster(String name, int level, double damage, double defense, double dodgeChance) {
        super(name, level);
        setHp(level * 100);
        this.damage = damage;
        this.defense = defense;
        setDodgeChance(dodgeChance);
    }


    public double getDamage() {
        return damage;
    }

    public void setDamage(double damage) {
        this.damage = damage;
    }

    public double getDefense() {
        return defense;
    }

    public void setDefense(double defense) {
        this.defense = defense;
    }

    public double getDodgeChance() {
        return dodgeChance;
    }

    public void setDodgeChance(double dodgeChance) {
        this.dodgeChance = dodgeChance * 0.01;
    }

    public boolean isDead() {
        return getHp() <= 0;
    }


    /**
     * attack to an hero
     * @param hero
     */
    public void attack(Hero hero) {
        double heroDodgeChance = hero.getDodgeChance();

        double rand = Math.random();
        //calculate if a hero can dodge
        if (rand <= heroDodgeChance) {
            System.out.println(Colors.setColor("Miss! You dodge monster's attack!", Colors.RED));
            return;
        }

        double totalDamage = damage;

        if (hero.getEquipedArmor() != null) {
            // damage defensed by an armor
            totalDamage -= hero.getEquipedArmor().getDamageReduction();

            if (totalDamage > 0) {
                System.out.println(Colors.setColor("You reduce some damage from monster!", Colors.RED));
            }
            else {
                System.out.println(Colors.setColor("You reduce entire damage from monster!", Colors.RED));
            }
        }

        hero.setHp(hero.getHp() - totalDamage);

        // attack info
        System.out.println(Colors.setColor(getName() + " cause " + totalDamage + " to " + hero.getName(), Colors.RED));
    }

    public void getMonsterStatus() {
        System.out.println(Colors.setGroundColor("Monster: " + getName() + (isDead() ? "is dead!" : "is still alive"), Colors.BLACK, Colors.CYAN));
        System.out.println(SPLIT_LINE);
        if (!isDead()) {
            System.out.println("HP:" + getHp());
            System.out.println("Damage: " + getDamage());
            System.out.println("defense: " + getDefense());
            System.out.println("Dodge chance: " + getDodgeChance());
        }
    }
}
