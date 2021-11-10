import java.util.ArrayList;
import java.util.Scanner;

/**
 * @ClassName Hero
 * @Description heroes in Legends
 * @Author Vincent Yuan
 */
public class Hero extends GenericCharacter{

    private static final String SPLIT_LINE = Colors.setGroundColor(
            "----------------------------------------------------------------------------------",
            Colors.BLACK, Colors.PURPLE_BG) + "\n";

    private double mana;
    private double strength;
    private double dexterity;
    private double agility;
    private int exp;
    private int extraExp;
    private double dodgeChance;
    private Bag bag;

    private StrategyLevelUp strategyLevelUp;

    private Weapon equipedWeapon;
    private Armor equipedArmor;


    public Hero(String name, int level, double mana, double strength, double dexterity, double agility, int exp, Bag bag) {
        super(name, level);
        setHp(level * 1000);
        this.mana = mana;
        this.strength = strength;
        this.dexterity = dexterity;
        this.agility = agility;
        this.exp = exp;
        setDodgeChance();
        this.bag = bag;
        this.equipedWeapon = bag.getWeapons().get(0);
        this.equipedArmor = bag.getArmors().get(0);
    }

    public double getMana() {
        return mana;
    }

    public void setMana(double mana) {
        this.mana = mana;
    }

    public double getStrength() {
        return strength;
    }

    public void setStrength(double strength) {
        this.strength = strength;
    }

    public double getDexterity() {
        return dexterity;
    }

    public void setDexterity(double dexterity) {
        this.dexterity = dexterity;
    }

    public double getAgility() {
        return agility;
    }

    public void setAgility(double agility) {
        this.agility = agility;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public double getDodgeChance() {
        return dodgeChance;
    }

    public void setDodgeChance() {
        this.dodgeChance = 0.001 * agility;
    }

    public Bag getBag() {
        return bag;
    }

    public void setBag(Bag bag) {
        this.bag = bag;
    }

    public Weapon getEquipedWeapon() {
        return equipedWeapon;
    }

    public void setEquipedWeapon(Weapon equipedWeapon) {
        this.equipedWeapon = equipedWeapon;
    }

    public Armor getEquipedArmor() {
        return equipedArmor;
    }

    public void setEquipedArmor(Armor equipedArmor) {
        this.equipedArmor = equipedArmor;
    }

    public int getExtraExp() {
        return extraExp;
    }

    public void setExtraExp(int extraExp) {
        this.extraExp = extraExp;
    }

    public StrategyLevelUp getStrategyLevelUp() {
        return strategyLevelUp;
    }

    public void setStrategyLevelUp(StrategyLevelUp strategyLevelUp) {
        this.strategyLevelUp = strategyLevelUp;
    }

    public boolean canLevelUp() {
        return strategyLevelUp.checkLevelUp();
    }

    /**
     * a hero levels up
     */
    public void executeLevelUp() {
        if (canLevelUp()) {
            System.out.println(Colors.setGroundColor("Hero: " + getName() + "Level Up!", Colors.BLACK, Colors.CYAN));
            strategyLevelUp.levelUp();

            setHp(getLevel() * 100);
            setMana(getMana() * 1.1);
        }
    }

    public void getHeroStatus() {
        System.out.println(Colors.setGroundColor("Hero: " + getName() + (isFaint() ? "is dead!" : "is still alive"), Colors.BLACK, Colors.CYAN));
        System.out.println(SPLIT_LINE);
        if (!isFaint()) {
            System.out.println("HP:" + getHp());
            System.out.println("Mana: " + getMana());
        }
    }

    /**
     * check if a hero is fainted
     * @return boolean
     */
    public boolean isFaint() {
        return getHp() <= 0;
    }

    /**
     * a hero can buy in a market
     * @param prop
     * @return
     */
    public boolean buy(Prop prop) {
        // check if having enough money
        if (prop.getPrice() > bag.getMoney()) {
            System.out.println(Colors.setColor("Sorry! You don't have enough money to buy this prop!", Colors.RED));
            return false;
        }
        //check minimum required level
        if (prop.getMinRequiredLevel() > getLevel()) {
            System.out.println(Colors.setColor("Sorry! Your level doesn't reach the minimum requirement to buy this prop!", Colors.RED));
            return false;
        }
        System.out.println(Colors.setColor("Successful!", Colors.RED));

        //add the prop into the bag
        bag.setMoney(bag.getMoney() - prop.getPrice());
        if (prop instanceof Weapon) {
            bag.getWeapons().add((Weapon) prop);
        } else if (prop instanceof Armor) {
            bag.getArmors().add((Armor) prop);
        } else if (prop instanceof Potion) {
            bag.getPotions().add((Potion) prop);
        } else if (prop instanceof Spell) {
            bag.getSpells().add((Spell) prop);
        }

        return true;
    }

    /**
     * sell props in a market
     * @param prop
     */
    public void sell(Prop prop) {
        System.out.println(Colors.setColor("Successful!", Colors.RED));

        //get half of the money back
        bag.setMoney(bag.getMoney() + prop.getPrice() * 0.5);
        if (prop instanceof Weapon) {
            bag.getWeapons().remove(prop);
        } else if (prop instanceof Armor) {
            bag.getArmors().remove(prop);
        } else if (prop instanceof Potion) {
            bag.getPotions().remove(prop);
        } else if (prop instanceof Spell) {
            bag.getSpells().remove(prop);
        }
    }

    /**
     * a normal attack from a hero
     * (strength + weapon damage) * 0.05
     * @param monster
     */
    public void normalAttack(Monster monster) {
        double attackDamage = equipedWeapon == null ? strength * 0.05 : (strength + equipedWeapon.getBaseDamage()) * 0.05;

        double rand = Math.random();

        // monster may dodge
        if (rand <= monster.getDodgeChance()) {
            // The monster has dodged the attack
            System.out.println(Colors.setColor("Miss! Monster dodges your attack!", Colors.RED));
            return;
        } else {
            monster.setHp(monster.getHp() - attackDamage);
        }

        System.out.println(Colors.setColor(getName() + " cause " + attackDamage + " to " + monster.getName(), Colors.RED));
    }

    /**
     * choose a weapon in the bag
     * @return Weapon
     */
    public Weapon chooseWeapon() {

        //check null
        if (bag.getWeapons().isEmpty()) {
            System.out.println(Colors.setColor("No Weapon!", Colors.RED));
            return null;
        }

        System.out.println(Colors.setColor("Please choose a Weapon!", Colors.RED));

        Scanner sc = new Scanner(System.in);
        int choice;

        //check if the choice is valid
        while (true) {
            if (sc.hasNextInt()) {
                choice = sc.nextInt();

                if (choice < 1 || choice > bag.getWeapons().size()) {
                    System.out.println(Colors.setColor("Please input a valid index!", Colors.RED));
                } else {
                    return bag.getWeapons().remove(choice - 1);
                }
            } else {
                sc.next();
                System.out.println(Colors.setColor("Please input a valid index!", Colors.RED));
            }
        }
    }

    /**
     * change a weapon
     * @return boolean
     */
    public boolean changeWeapon() {
        Weapon tmp = chooseWeapon();

        //check null
        if (tmp == null) {
            return false;
        }

        if (equipedWeapon != null) {
            bag.getWeapons().add(equipedWeapon);
        }

        equipedWeapon = tmp;
        return true;
    }


    /**
     * choose an armor from the bag
     * @return Armor
     */
    public Armor chooseArmor() {

        //check null
        if (bag.getArmors().isEmpty()) {
            System.out.println(Colors.setColor("No Armor!", Colors.RED));
            return null;
        }

        System.out.println(Colors.setColor("Please choose an Armor!", Colors.RED));

        Scanner sc = new Scanner(System.in);
        int choice;

        //check if choice is valid
        while (true) {
            if (sc.hasNextInt()) {
                choice = sc.nextInt();

                if (choice < 1 || choice > bag.getArmors().size()) {
                    System.out.println(Colors.setColor("Please input a valid index!", Colors.RED));
                } else {
                    return bag.getArmors().remove(choice - 1);
                }
            } else {
                sc.next();
                System.out.println(Colors.setColor("Please input a valid index!", Colors.RED));
            }
        }
    }

    /**
     * change an armor
     * @return boolean
     */
    public boolean changeArmor() {
        Armor tmp = chooseArmor();

        //check null
        if (tmp == null) {
            return false;
        }

        if (equipedArmor != null) {
            bag.getArmors().add(equipedArmor);
        }

        equipedArmor = tmp;
        return true;
    }

    /**
     * choose a potion from the bag
     * @return
     */
    public Potion choosePotion() {

        //check null
        if (bag.getPotions().isEmpty()) {
            System.out.println(Colors.setColor("No Potion!", Colors.RED));
            return null;
        }

        System.out.println(Colors.setColor("Please choose a Potion!", Colors.RED));

        Scanner sc = new Scanner(System.in);
        int choice;

        //check if the choice is valid
        while (true) {
            if (sc.hasNextInt()) {
                choice = sc.nextInt();

                if (choice < 1 || choice > bag.getPotions().size()) {
                    System.out.println(Colors.setColor("Please input a valid index!", Colors.RED));
                } else {
                    return bag.getPotions().remove(choice - 1);
                }
            } else {
                sc.next();
                System.out.println(Colors.setColor("Please input a valid index!", Colors.RED));
            }
        }
    }

    /**
     * use the chosen potion
     * @return boolean
     */
    public boolean usePotion() {
        Potion p = choosePotion();

        //check null
        if (p == null) {
            return false;
        }

        // according to the affected attribution from the file
        // to increase different ability
        int inc = p.getAttrIncreased();
        String effect = p.getAttrAffected();
        if (effect.startsWith("All")) {
            setHp(getHp() + inc);
            setStrength(getStrength() + inc);
            setMana(getMana() + inc);
            setAgility(getAgility() + inc);
            setDexterity(getDexterity() + inc);
        }
        if (effect.contains("Health")) {
            setHp(getHp() + inc);
        }
        if (effect.contains("Strength")) {
            setStrength(getStrength() + inc);
        }
        if (effect.contains("Mana")) {
            setMana(getMana() + inc);
        }
        if (effect.contains("Agility")) {
            setAgility(getAgility() + inc);
        }
        if (effect.contains("Dexterity")) {
            setDexterity(getDexterity() + inc);
        }


        bag.getPotions().remove(p);
        return true;
    }

    /**
     * cast a spell to a monster
     * @param monster
     * @return boolean
     */
    public boolean castSpell(Monster monster) {
        Spell s = chooseSpell();

        //check null
        if (s == null) {
            return false;
        }

        if (mana < s.getRequiredMana()) {
            System.out.println(Colors.setColor("Sorry! You don't have enough mana to cast this spell!", Colors.RED));
            return false;
        } else {
            setMana(mana -= s.getRequiredMana());
        }

        //basic damage
        double spellDamage = s.getDamage() + (dexterity / 10000) * s.getDamage();

        double rand = Math.random();

        if (rand <= monster.getDodgeChance()) {
            // The monster has dodged the attack
            System.out.println(Colors.setColor("Miss! Monster dodges your spell!", Colors.RED));
            return true;
        } else {
            if (spellDamage > monster.getDefense()) {
                monster.setHp(monster.getHp() - monster.getDefense() + spellDamage);
            }
        }

        // calculate effect according to different types of spell
        if (s instanceof SpellFire) {
            monster.setDefense(monster.getDefense() * 0.9);
        } else if (s instanceof SpellIce) {
            monster.setDamage(monster.getDamage() * 0.9);
        } else if (s instanceof SpellLightning) {
            monster.setDodgeChance(monster.getDodgeChance() * 0.9);
        }

        System.out.println(Colors.setColor(getName() + " cause " + spellDamage + " to " + monster.getName(), Colors.RED));
        return true;
    }


    /**
     * choose a spell form bag
     * @return Spell
     */
    public Spell chooseSpell() {

        //check null
        if (bag.getSpells().isEmpty()) {
            System.out.println(Colors.setColor("No Spell!", Colors.RED));
            return null;
        }

        System.out.println(Colors.setColor("Please choose a Spell!", Colors.RED));

        Scanner sc = new Scanner(System.in);
        int choice;

        // check if the choice is valid
        while (true) {
            if (sc.hasNextInt()) {
                choice = sc.nextInt();

                if (choice < 1 || choice > bag.getSpells().size()) {
                    System.out.println(Colors.setColor("Please input a valid index!", Colors.RED));
                } else {
                    return bag.getSpells().remove(choice - 1);
                }
            } else {
                sc.next();
                System.out.println(Colors.setColor("Please input a valid index!", Colors.RED));
            }
        }
    }

}
