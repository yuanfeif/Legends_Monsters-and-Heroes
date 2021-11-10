import java.util.ArrayList;

/**
 * @ClassName Bag
 * @Description bags for each hero
 * @Author Vincent Yuan
 */
public class Bag {
    private static final String SPLIT_LINE = Colors.setGroundColor(
            "----------------------------------------------------------------------------------",
            Colors.BLACK, Colors.PURPLE_BG) + "\n";

    private double money;
    private ArrayList<Weapon> weapons;
    private ArrayList<Armor> armors;
    private ArrayList<Potion> potions;
    private ArrayList<Spell> spells;

    public Bag(int money) {
        this.money = money;
        weapons = new ArrayList<>();
        armors = new ArrayList<>();
        potions = new ArrayList<>();
        spells = new ArrayList<>();
        weapons.add(new Weapon("Wooden Sword", 0, 1, 200, 1));
        armors.add(new Armor("Wooden Shied", 0, 1, 70));
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public ArrayList<Weapon> getWeapons() {
        return weapons;
    }

    public void setWeapons(ArrayList<Weapon> weapons) {
        this.weapons = weapons;
    }

    public ArrayList<Armor> getArmors() {
        return armors;
    }

    public void setArmors(ArrayList<Armor> armors) {
        this.armors = armors;
    }

    public ArrayList<Potion> getPotions() {
        return potions;
    }

    public void setPotions(ArrayList<Potion> potions) {
        this.potions = potions;
    }

    public ArrayList<Spell> getSpells() {
        return spells;
    }

    public void setSpells(ArrayList<Spell> spells) {
        this.spells = spells;
    }

    /**
     * print all the weapons in the bag
     */
    public void printWeapons() {
        if (weapons.size() == 0) {
            System.out.println(Colors.setColor("No Weapon!", Colors.RED));
        } else {
            System.out.println(Colors.setGroundColor("Weapons: ", Colors.BLACK, Colors.GREEN_BG));
            System.out.println("Name                  Price    Required Level    Base Damage    Required Hands");
            System.out.print(SPLIT_LINE);

            for (int i = 0; i < weapons.size(); ++i) {
                String name = (i + 1) + ". " + weapons.get(i).getName();
                int price = weapons.get(i).getPrice();
                int level = weapons.get(i).getMinRequiredLevel();
                int damage = weapons.get(i).getBaseDamage();
                int hands = weapons.get(i).getRequiredHands();

                System.out.printf("%-22s %-14d %-14d %-17d %-5d", name, price, level, damage, hands);
                System.out.println();
            }
            System.out.println(SPLIT_LINE);
        }
    }

    /**
     * print all the armors in the bag
     */
    public void printArmors() {
        if (armors.size() == 0) {
            System.out.println(Colors.setColor("No Armor!", Colors.RED));
        } else {
            System.out.println(Colors.setGroundColor("Armors: ", Colors.BLACK, Colors.GREEN_BG));
            // Print armors
            System.out.println("Name                    Price    Required Level    Damage Reduction");
            System.out.print(SPLIT_LINE);

            for (int i = 0; i < armors.size(); ++i) {
                // Print all the stats of all the armors
                String name = (i + 1) + ". " + armors.get(i).getName();
                int price = armors.get(i).getPrice();
                int level = armors.get(i).getMinRequiredLevel();
                int damageReduction = armors.get(i).getDamageReduction();

                System.out.printf("%-24s %-14d %-15d %-14d", name, price, level, damageReduction);
                System.out.println();
            }
            System.out.println(SPLIT_LINE);
        }
    }

    /**
     * print all the potions in the bag
     */
    public void printPotions() {
        if (potions.size() == 0) {
            System.out.println(Colors.setColor("No Potion!", Colors.RED));
        } else {
            System.out.println(Colors.setGroundColor("Potions: ", Colors.BLACK, Colors.GREEN_BG));
            // Print potions
            System.out.println("Name                    Price    Required Level    Increased Amount    Affected Attr");
            System.out.print(SPLIT_LINE);

            for (int i = 0; i < potions.size(); ++i) {
                // Print all the stats of all the potions
                String name = (i + 1) + ". " + potions.get(i).getName();
                int price = potions.get(i).getPrice();
                int level = potions.get(i).getMinRequiredLevel();
                int increaseAttr = potions.get(i).getAttrIncreased();
                String affectedAttr = potions.get(i).getAttrAffected();

                System.out.printf("%-24s %-14d %-16d %-15d %-9s", name, price, level, increaseAttr, affectedAttr);
                System.out.println();
            }
            System.out.println(SPLIT_LINE);
        }
    }

    /**
     * print all the spells in the bag
     */
    public void printSpells() {
        if (spells.size() == 0) {
            System.out.println(Colors.setColor("No Spell!", Colors.RED));
        } else {
            System.out.println(Colors.setGroundColor("Spells: ", Colors.BLACK, Colors.GREEN_BG));
            // Print spells
            System.out.println("Name                    Price    Required Level    Base Damage    Mana Cost    Spell Type");
            System.out.print(SPLIT_LINE);

            for (int i = 0; i < spells.size(); ++i) {
                // Print all the stats of all the spells
                String name = (i + 1) + ". " + spells.get(i).getName();
                int price = spells.get(i).getPrice();
                int level = spells.get(i).getMinRequiredLevel();
                int damage = spells.get(i).getDamage();
                int manaCost = spells.get(i).getRequiredMana();
                String type;

                // Polymorphism implementation
                if (spells.get(i) instanceof SpellIce) {
                    type = "Ice";
                } else if (spells.get(i) instanceof SpellFire) {
                    type = Colors.setColor("Fire", Colors.RED);
                } else {
                    type = Colors.setColor("Lightning", Colors.BLUE);
                }

                System.out.printf("%-24s %-14d %-13d %-14d %-9d %-16s", name, price, level, damage, manaCost, type);
                System.out.println();
            }
            System.out.println(SPLIT_LINE);
        }
    }

    /**
     * print all the props in the bag
     */
    public void printBag() {
        System.out.println("\n\n");
        System.out.println(Colors.setGroundColor("                           This is your bag!                       ", Colors.BLACK, Colors.YELLOW_BG));

        printWeapons();

        printArmors();

        printPotions();

        printSpells();
    }
}
