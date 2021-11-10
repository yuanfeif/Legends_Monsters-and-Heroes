import java.util.ArrayList;

/**
 * @ClassName MarketLMH
 * @Description market for Legends
 * @Author Vincent Yuan
 */
public class MarketLMH extends Market{

    /**
     * 4 types of props in Legends
     */
    private ArrayList<Weapon> weapons;
    private ArrayList<Armor> armors;
    private ArrayList<Potion> potions;
    private ArrayList<Spell> spells;

    public MarketLMH() {
        super();
        weapons = new ArrayList<>();
        armors = new ArrayList<>();
        potions = new ArrayList<>();
        spells = new ArrayList<>();
        setProps(new ArrayList<>());
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
     * parse props files and initiate the market
     */
    @Override
    public void initiateMarket() {
        ParsingFile parsingFile = null;
        ArrayList<Prop> tmp = null;

        parsingFile = new ParsingFile(new Weapon());
        tmp = parsingFile.executeStrategy("Weaponry.txt");
        for (Prop weapon: tmp) {
            weapons.add((Weapon) weapon);
        }

        parsingFile = new ParsingFile(new Armor());
        tmp = parsingFile.executeStrategy("Armory.txt");
        for (Prop armor: tmp) {
            armors.add((Armor) armor);
        }

        parsingFile = new ParsingFile(new Potion());
        tmp = parsingFile.executeStrategy("Potions.txt");
        for (Prop potion: tmp) {
            potions.add((Potion) potion);
        }

        parsingFile = new ParsingFile(new Spell());
        tmp = parsingFile.executeStrategy("FireSpells.txt");
        for (Prop fireSpell: tmp) {
            spells.add((SpellFire) fireSpell);
        }
        tmp = parsingFile.executeStrategy("IceSpells.txt");
        for (Prop iceSpell: tmp) {
            spells.add((Spell) iceSpell);
        }
        tmp = parsingFile.executeStrategy("LightningSpells.txt");
        for (Prop lightningSpell: tmp) {
            spells.add((Spell) lightningSpell);
        }

        getProps().addAll(weapons);
        getProps().addAll(armors);
        getProps().addAll(potions);
        getProps().addAll(spells);
    }

    @Override
    public void printMarket() {
        System.out.println(Colors.setGroundColor("                      Prop Market                       ", Colors.BLACK, Colors.YELLOW_BG));

        String splitLine = Colors.setGroundColor(
                "----------------------------------------------------------------------------------",
                Colors.BLACK, Colors.PURPLE_BG) + "\n";

        // print weapons
        System.out.println(Colors.setGroundColor("Weapons: ", Colors.BLACK, Colors.GREEN_BG));
        System.out.println("Name                  Price    Required Level    Base Damage    Required Hands");
        System.out.print(splitLine);

        for (int i = 0; i < weapons.size(); ++i) {
            // Print all the stats of all the weapons
            String name = (i + 1) + ". " + weapons.get(i).getName();
            int price = weapons.get(i).getPrice();
            int level = weapons.get(i).getMinRequiredLevel();
            int damage = weapons.get(i).getBaseDamage();
            int hands = weapons.get(i).getRequiredHands();

            System.out.printf("%-22s %-14d %-14d %-17d %-5d", name, price, level, damage, hands);
            System.out.println();
        }
        System.out.println(splitLine);


        // print armors
        System.out.println(Colors.setGroundColor("Armors: ", Colors.BLACK, Colors.GREEN_BG));
        System.out.println("Name                    Price    Required Level    Damage Reduction");
        System.out.print(splitLine);

        for (int i = 0; i < armors.size(); ++i) {
            String name = (i + 1) + weapons.size() + ". " + armors.get(i).getName();
            int price = armors.get(i).getPrice();
            int level = armors.get(i).getMinRequiredLevel();
            int damageReduction = armors.get(i).getDamageReduction();

            System.out.printf("%-24s %-14d %-15d %-14d", name, price, level, damageReduction);
            System.out.println();
        }
        System.out.println(splitLine);

        // print potions
        System.out.println(Colors.setGroundColor("Potions: ", Colors.BLACK, Colors.GREEN_BG));
        System.out.println("Name                    Price    Required Level    Increased Amount    Affected Attr");
        System.out.print(splitLine);

        for (int i = 0; i < potions.size(); ++i) {
            String name = (i + 1) + weapons.size() + armors.size() + ". " + potions.get(i).getName();
            int price = potions.get(i).getPrice();
            int level = potions.get(i).getMinRequiredLevel();
            int increaseAttr = potions.get(i).getAttrIncreased();
            String affectedAttr = potions.get(i).getAttrAffected();

            System.out.printf("%-24s %-14d %-16d %-15d %-9s", name, price, level, increaseAttr, affectedAttr);
            System.out.println();
        }
        System.out.println(splitLine);


        // print spells
        System.out.println(Colors.setGroundColor("Spells: ", Colors.BLACK, Colors.GREEN_BG));
        System.out.println("Name                    Price    Required Level    Base Damage    Mana Cost    Spell Type");
        System.out.print(splitLine);

        for (int i = 0; i < spells.size(); ++i) {
            // Print all the stats of all the spells
            String name = (i + 1) + weapons.size() + armors.size() + potions.size() + ". " + spells.get(i).getName();
            int price = spells.get(i).getPrice();
            int level = spells.get(i).getMinRequiredLevel();
            int damage = spells.get(i).getDamage();
            int manaCost = spells.get(i).getRequiredMana();
            String type;

            //use different color to print different spells
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
        System.out.println(splitLine);
    }
}
