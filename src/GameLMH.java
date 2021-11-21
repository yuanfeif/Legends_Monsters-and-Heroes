import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * @ClassName GameLMH
 * @Description
 * @Author Vincent Yuan
 */
public class GameLMH extends GameRPG {
    private static final String SPLIT_LINE = Colors.setGroundColor(
            "---------------------------------------------------------------------------------------------------------",
            Colors.BLACK, Colors.PURPLE_BG) + "\n";

    /**
     * number of a team
     */
    private int heroNum;
    /**
     * current row of a player
     */
    private int curRow;
    /**
     * current column of a player
     */
    private int curCol;

    /**
     * player's team
     */
    private ArrayList<Hero> team;
    /**
     * back up player's team for revive and increase ability of a hero
     */
    private ArrayList<Hero> teamBackUp;
    /**
     * temp monsters when a player will meet
     */
    private ArrayList<Monster> monsters;

    /**
     * grid of a Legends game
     */
    private Grid grid;
    /**
     * market of a Legends game
     */
    private Market market;

    public GameLMH() {
        heroNum = 0;
        curRow = 0;
        curCol = 0;
        team = new ArrayList<>();
        teamBackUp = new ArrayList<>();
        monsters = new ArrayList<>();
        this.grid = GridFactory.createGrid("LMH");
        this.market = MarketFactory.createMarket("LMH");
        market.initiateMarket();
    }

    public int getHeroNum() {
        return heroNum;
    }

    public void setHeroNum(int heroNum) {
        this.heroNum = heroNum;
    }

    public int getCurRow() {
        return curRow;
    }

    public void setCurRow(int curRow) {
        this.curRow = curRow;
    }

    public int getCurCol() {
        return curCol;
    }

    public void setCurCol(int curCol) {
        this.curCol = curCol;
    }

    public ArrayList<Hero> getTeam() {
        return team;
    }

    public void setTeam(ArrayList<Hero> team) {
        this.team = team;
    }

    public ArrayList<Hero> getTeamBackUp() {
        return teamBackUp;
    }

    public void setTeamBackUp(ArrayList<Hero> teamBackUp) {
        this.teamBackUp = teamBackUp;
    }

    public ArrayList<Monster> getMonsters() {
        return monsters;
    }

    public void setMonsters(ArrayList<Monster> monsters) {
        this.monsters = monsters;
    }

    public Grid getGrid() {
        return grid;
    }

    public void setGrid(Grid grid) {
        this.grid = grid;
    }

    public Market getMarket() {
        return market;
    }

    public void setMarket(Market market) {
        this.market = market;
    }

    /**
     * use different colors to print different types of system information
     */
    public void printSystemNotification(String str) {
        System.out.println(Colors.setGroundColor(str, Colors.BLACK, Colors.RED_BG));
    }

    public void printSystemInfo(String str) {
        System.out.println(Colors.setColor(str, Colors.BLUE));
    }

    public void printSystemHint(String str) {
        System.out.println(Colors.setColor(str, Colors.WHITE));
    }

    /**
     * play a game
     */
    @Override
    public void play() {
        backgroundInfo();

        pickRoles();
        backUpTeam();
        printTeam();

        showGrid();

        while (true) {
            boolean canMove = move();
            grid.printGrid();

            if (canMove) {
                stepInto();
                grid.printGrid();
            }
            grid.recoverCell(curRow, curCol);
        }

    }


    /**
     * print background information
     */
    @Override
    public void backgroundInfo() {
        String backInfo = "The heroes and monsters live in a world represented by a square grid of fixed dimensions. \n" +
                "This world has three types of places to be; \n" +
                "Common space (either a safe zone or where heroes come across monsters and fight), \n" +
                "Inaccessible (places the heroes can't go), \n" +
                "and Markets (where items are bought and sold). \n" +
                "The heroes and monsters do not get along and therefore fight each other. \n" +
                "Heroes can use weapons, armors, potions, and spells against the monsters. \n" +
                "Every time the heroes win, they gain some experience and some money. \n" +
                "When they accumulate enough experience they level up which means that their skills become stronger.  \n" +
                "The goal of the game is for the heroes to gain experience and level up indefinitely.\n";
        System.out.println(Colors.setGroundColor(backInfo, Colors.BLACK, Colors.YELLOW_BG));
        playOrQuit();
        printSystemNotification("                    GAME START                     ");
    }

    @Override
    public void playOrQuit() {
        printSystemNotification("Press 'Q/q' to quit, or any other key(s) to continue!");

        Scanner sc = new Scanner(System.in);
        String decision = sc.nextLine();

        // Check if the player wants to start the game or quit
        if ("Q".equalsIgnoreCase(decision)) {
            GameHost.quit();
        }
    }

    /**
     * player picks roles to form a team
     */
    @Override
    public void pickRoles() {
        HeroList.printHeroList();

        printSystemNotification("Now you can form your own team!");
        System.out.println();
        printSystemNotification("Input the number of your team(1-3): ");

        // check if the number is valid
        Scanner sc = new Scanner(System.in);
        while (true) {
            if (sc.hasNextInt()) {
                heroNum = sc.nextInt();
                if (heroNum < 1 || heroNum > 3) {
                    printSystemNotification("Please input a valid number(1-3)!");
                } else {
                    break;
                }
            } else {
                sc.next();
                printSystemNotification("Please input a valid number(1-3)!");
            }
        }

        // check if the number of heroes is valid when choosing
        for (int i = 0; i < heroNum; i++) {
            printSystemInfo("Choose Hero " + i + ": ");
            int choice;
            while (true) {
                if (sc.hasNextInt()) {
                    choice = sc.nextInt();
                    if (choice < 1 || choice > HeroList.getHeroes().size()) {
                        printSystemNotification("Please input a valid number(1-" + HeroList.getHeroes().size() + ")!");
                    } else {
                        team.add(HeroList.getHeroes().get(choice - 1));
                        break;
                    }
                } else {
                    sc.next();
                    printSystemNotification("Please input a valid number(1-" + HeroList.getHeroes().size() + ")!");
                }
            }
        }
    }

    /**
     * print the whole team and all the information of each hero
     */
    public void printTeam() {
        String name;
        double hp, strength, dexterity, agility, money, mana;
        int level, exp;

        System.out.println();
        System.out.println(Colors.setColor("Here is your team!", Colors.BLUE));

        String title = "Name                       HP    Level    Mana    Strength    Dexterity    Agility    Money    Exp";
        System.out.println(title);
        System.out.print(SPLIT_LINE);
        System.out.printf("%-25s %-8.0f %-6d %-9.0f %-12.0f %-11.0f %-9.0f %-8.0f %-5d", 1 + ". " + team.get(0).getName(), team.get(0).getHp(), team.get(0).getLevel(), team.get(0).getMana(), team.get(0).getStrength(),
                team.get(0).getDexterity(), team.get(0).getAgility(), team.get(0).getBag().getMoney(), team.get(0).getExp());
        System.out.println();
        for (int i = 1; i < team.size(); ++i) {
            name = (i + 1) + ". " + team.get(i).getName();
            hp = team.get(i).getHp();
            level = team.get(i).getLevel();
            mana = team.get(i).getMana();
            strength = team.get(i).getStrength();
            dexterity = team.get(i).getDexterity();
            agility = team.get(i).getAgility();
            money = team.get(i).getBag().getMoney();
            exp = team.get(i).getExp();

            System.out.printf("%-25s %-8.0f %-6d %-9.0f %-12.0f %-11.0f %-9.0f %-8.0f %-5d", name, hp, level, mana, strength,
                    dexterity, agility, money, exp);
            System.out.println();

            if ((i + 1 < team.size()) && (!team.get(i).getClass().getName().equals
                    (team.get(i + 1).getClass().getName()))) {
                System.out.println(SPLIT_LINE);
            }
        }
        System.out.println(SPLIT_LINE);
    }

    /**
     * back up a team
     */
    public void backUpTeam() {
        teamBackUp.addAll(team);
    }


    /**
     * hints of the grid
     */
    public void showGrid() {
        printSystemInfo("Here is the grid of the world!");
        printSystemHint(" ☆ " + " is your birth place");
        printSystemHint(" \uD83D\uDEA9 " + " is your position");
        printSystemHint(" \uD83C\uDF3F " + " is common space");
        printSystemHint(" \uD83D\uDED2 " + " is market");
        printSystemHint(" ❌ " + " is inaccessible space");
        grid.initiateGrid();
        grid.printGrid();
    }

    /**
     * move event
     *
     * @return boolean
     */
    public boolean move() {
        Scanner sc = new Scanner(System.in);
        String action;
        int size = grid.getSize();

        while (true) {
            printSystemNotification("Please enter your action (wW/aA/sS/dD/qQ/iI): ");
            action = sc.nextLine();

            //check if input is valid
            if (!"W".equalsIgnoreCase(action) && !"A".equalsIgnoreCase(action) &&
                    !"S".equalsIgnoreCase(action) && !"D".equalsIgnoreCase(action) &&
                    !"Q".equalsIgnoreCase(action) && !"I".equalsIgnoreCase(action)) {
                printSystemNotification("Please input a valid action! (W/w, A/a, S/s, D/d, Q/q, I/i)!");
                continue;
            }
            //check if position is out of boundary
            if (("A".equalsIgnoreCase(action) && (curCol - 1 < 0)) ||
                    ("D".equalsIgnoreCase(action) && (curCol + 1 >= size)) ||
                    ("W".equalsIgnoreCase(action) && (curRow - 1 < 0)) ||
                    ("S".equalsIgnoreCase(action) && (curRow + 1 >= size))) {

                // Valid input, but cannot go outside of the map
                printSystemNotification("Please move inside the grid!");
                continue;
            }

            // cannot move into an inaccessible space
            if (("A".equalsIgnoreCase(action) && (grid.getGrid()[curRow][curCol - 1] instanceof CellInaccessible)) ||
                    ("D".equalsIgnoreCase(action) && (grid.getGrid()[curRow][curCol + 1] instanceof CellInaccessible)) ||
                    ("W".equalsIgnoreCase(action) && (grid.getGrid()[curRow - 1][curCol] instanceof CellInaccessible)) ||
                    ("S".equalsIgnoreCase(action) && (grid.getGrid()[curRow + 1][curCol] instanceof CellInaccessible))) {
                printSystemNotification("You cannot move into an inaccessible space!");
            } else {
                break;
            }
        }

        //move
        if ("A".equalsIgnoreCase(action)) {
            curCol--;
        } else if ("D".equalsIgnoreCase(action)) {
            curCol++;
        } else if ("W".equalsIgnoreCase(action)) {
            curRow--;
        } else if ("S".equalsIgnoreCase(action)) {
            curRow++;
        }

        //after leaving a cell, need to recover the marker before
        grid.markAsHero(curRow, curCol);
        System.out.println(Colors.setColor("You are now on a " + grid.getCellBefore().getMark(), Colors.WHITE));

        if ("Q".equalsIgnoreCase(action)) {
            GameHost.quit();
        }
        if ("I".equalsIgnoreCase(action)) {
            // Print relevant stats
            printTeam();
            return false;
        }
        return true;
    }

    /**
     * step into a cell
     */
    public void stepInto() {
        //step into a common cell
        if (getGrid().getCellBefore() instanceof CellCommon) {
            Random seed = new Random();
            int prob = seed.nextInt(3);
            //66% need a fight
            if (prob >= 1) {
                fight();
            } else {
                printSystemNotification("Nothing happens...");
            }
        }
        //step into a market cell
        if (getGrid().getCellBefore() instanceof CellMarket) {
            enterMarket();
        }
    }

    /**
     * enter a market
     */
    public void enterMarket() {
        Scanner sc = new Scanner(System.in);
        printSystemNotification("Welcome to market! You can buy powerful props for your heroes!");

        // buy or sell props for each hero
        for (int i = 0; i < team.size(); i++) {
            printSystemNotification("What do you want to do for your heroes?");
            System.out.println();
            printTeam();
            printSystemHint("1. Buy     2. Sell     3. Finish and Pass");

            printSystemInfo("For hero" + (i + 1));

            //check if choice is valid
            int choice;
            while (true) {
                if (sc.hasNextInt()) {
                    choice = sc.nextInt();
                    if (choice < 1 || choice > 3) {
                        printSystemNotification("Please input a valid number!(1-)" + market.getProps().size());
                    } else {
                        break;
                    }
                } else {
                    // If input is not an INTEGER
                    sc.next();
                    printSystemNotification("Please input a valid number!(1-)" + market.getProps().size());
                }
            }

            // buy
            if (choice == 1) {
                market.printMarket();

                if (team.get(i).buy(pickPropInMarket())) {
                    team.get(i).getBag().printBag();
                }
                --i;
            } //sell
            else if (choice == 2) {
                team.get(i).getBag().printBag();
                team.get(i).sell(pickPropInBag(team.get(i)));
                team.get(i).getBag().printBag();
                --i;
            } // pass
            else {
                if (i == heroNum - 1) {
                    printSystemNotification("Bye!");
                }
            }
        }
    }

    /**
     * pick props in the market
     * @return Prop
     */
    public Prop pickPropInMarket() {
        Scanner sc = new Scanner(System.in);

        // Input validation checking
        while (true) {
            printSystemNotification("Please pick a prop you like!");
            int propNum;

            //check if number is valid
            if (sc.hasNextInt()) {
                propNum = sc.nextInt();

                if (propNum < 1 || propNum > market.getProps().size()) {
                    printSystemNotification("Please input a valid prop number!(1-" + market.getProps().size() + ")");
                } else {
                    return market.getProps().get(propNum - 1);
                }
            } else {
                sc.next();
                printSystemNotification("Please input a valid prop number!(1-" + market.getProps().size() + ")");
            }
        }
    }

    /**
     * pick props out before selling them
     * @param hero
     * @return Prop
     */
    public Prop pickPropInBag(Hero hero) {
        Scanner sc = new Scanner(System.in);
        printSystemNotification("Please choose a Prop type!");
        printSystemHint("W/w: Weapons");
        printSystemHint("A/a: Armors");
        printSystemHint("P/p: Potions");
        printSystemHint("S/s: Spells");
        String type;
        while (true) {
            type = sc.nextLine();
            //check if type is valid
            if (!"W".equalsIgnoreCase(type) && !"A".equalsIgnoreCase(type) &&
                    !"P".equalsIgnoreCase(type) && !"S".equalsIgnoreCase(type)) {
                printSystemNotification("Please input a valid action! (W/w, A/a, P/p, S/s)!");
                continue;
            }
            //check if having specific prop
            if (("W".equalsIgnoreCase(type) && hero.getBag().getWeapons().isEmpty()) ||
                    ("A".equalsIgnoreCase(type) && hero.getBag().getArmors().isEmpty()) ||
                    ("P".equalsIgnoreCase(type) && hero.getBag().getPotions().isEmpty()) ||
                    ("S".equalsIgnoreCase(type) && hero.getBag().getSpells().isEmpty())) {
                printSystemNotification("You don't have this kind of prop!");
                continue;
            }

            int choice;
            printSystemNotification("Please choose a Specific Prop!");

            // check if the number is valid
            while (true) {
                if (sc.hasNextInt()) {
                    choice = sc.nextInt();
                    if ("W".equalsIgnoreCase(type)) {
                        if (choice < 1 || choice > hero.getBag().getWeapons().size()) {
                            printSystemNotification("Please input a valid prop number!(1-" + hero.getBag().getWeapons().size() + ")");
                        } else {
                            return hero.getBag().getWeapons().get(choice - 1);
                        }
                    } else if ("A".equalsIgnoreCase(type)) {
                        if (choice < 1 || choice > hero.getBag().getArmors().size()) {
                            printSystemNotification("Please input a valid prop number!(1-" + hero.getBag().getArmors().size() + ")");
                        } else {
                            return hero.getBag().getArmors().get(choice - 1);
                        }
                    } else if ("P".equalsIgnoreCase(type)) {
                        if (choice < 1 || choice > hero.getBag().getPotions().size()) {
                            printSystemNotification("Please input a valid prop number!(1-" + hero.getBag().getPotions().size() + ")");
                        } else {
                            return hero.getBag().getPotions().get(choice - 1);
                        }
                    } else if ("S".equalsIgnoreCase(type)) {
                        if (choice < 1 || choice > hero.getBag().getSpells().size()) {
                            printSystemNotification("Please input a valid prop number!(1-" + hero.getBag().getSpells().size() + ")");
                        } else {
                            return hero.getBag().getSpells().get(choice - 1);
                        }
                    }
                } else {
                    printSystemNotification("Please input a valid prop number!");
                }
            }
        }
    }

    /**
     * when player enters a common space, it is possible to meet monsters
     * they need to fight with them
     */
    public void createMonsters() {
        monsters = new ArrayList<>();
        MonsterList.setMonsters();
        Random seed = new Random();

        for (int i = 0; i < team.size(); i++) {
            int idx = seed.nextInt(MonsterList.getMonsters().size());
            monsters.add(MonsterList.getMonsters().get(idx));
        }
    }

    public void printMonsters() {
        if (monsters.isEmpty()) {
            return;
        }

        printSystemInfo("Here are monsters!");

        System.out.println("Name                       HP    Level    Damage    Defense    Dodge Chance");
        System.out.println(SPLIT_LINE);

        for (Monster monster : monsters) {
            System.out.printf("%-25s %-8.0f %-7d %-9.0f %-11.0f %-6.2f",
                    monster.getName(),
                    monster.getHp(),
                    monster.getLevel(),
                    monster.getDamage(),
                    monster.getDefense(),
                    monster.getDodgeChance());
            System.out.println();
        }

    }

    /**
     * fight with monsters
     */
    public void fight() {
        createMonsters();

        Scanner sc = new Scanner(System.in);
        printSystemNotification("You meet monsters! Start to Fight!");

        while (true) {
            int hIdx, mIdx;
            // heroes and monsters are one on one until one of them is dead
            for (hIdx = 0, mIdx = 0; hIdx < team.size() && mIdx < monsters.size(); ++hIdx) {
                // print information
                printTeam();
                printMonsters();

                printSystemNotification(team.get(hIdx).getName() + " VS " + monsters.get(mIdx).getName());
                team.get(hIdx).getBag().printBag();

                //hero turn
                if (!chooseWayToFight(team.get(hIdx), monsters.get(mIdx))) {
                    --hIdx;
                    continue;
                }

                // monster turn
                if (!monsters.get(mIdx).isDead()) {
                    monsters.get(mIdx).attack(team.get(hIdx));
                }

                //check if both of them are dead
                if (team.get(hIdx).isFaint() || monsters.get(mIdx).isDead()) {
                    if (monsters.get(mIdx).isDead()) {
                        --hIdx;
                    }
                }
            }

            //check when to break
            boolean heroesAllFaint = true;
            boolean monstersAllDead = true;

            for (Hero hero : team) {
                heroesAllFaint = heroesAllFaint && hero.isFaint();
            }
            for (Monster monster : monsters) {
                monstersAllDead = monstersAllDead && monster.isDead();
            }

            if (heroesAllFaint) {
                printSystemNotification("All the heroes are dead...");
                GameHost.quit();
            } else {
                if (monstersAllDead) {
                    afterFight();
                    break;
                }
            }
        }

    }

    /**
     * player chooses a way to fight
     * @param hero
     * @param monster
     * @return boolean
     */
    public boolean chooseWayToFight(Hero hero, Monster monster) {
        printSystemNotification("Please choose a way to fight!");
        Scanner sc = new Scanner(System.in);

        printSystemNotification("1.attack 2.use a potion 3.cast a spell 4.change a weapon 5.change a armor");
        int choice;
        //check if choice is valid
        while (true) {
            if (sc.hasNextInt()) {
                choice = sc.nextInt();
                if (choice < 1 || choice > 5) {
                    printSystemNotification("Please input a valid number(1-5)");
                } else {
                    if (choice == 1) {
                        hero.normalAttack(monster);
                        return true;
                    } else if (choice == 2) {
                        return hero.usePotion();
                    } else if (choice == 3) {
                        return hero.castSpell(monster);
                    } else if (choice == 4) {
                        return hero.changeWeapon();
                    } else {
                        return hero.changeArmor();
                    }
                }
            } else {
                printSystemNotification("Please input a valid number(1-5)");
            }
        }
    }

    /**
     * heroes will gain exp and money after a fight
     */
    public void afterFight() {
        printSystemNotification("The game is being settled......");

        for (int i = 0; i < team.size(); i++) {
            Hero hero = team.get(i);
            if (hero.isFaint()) {
                hero.setHp(teamBackUp.get(i).getHp() * 0.5);
                hero.setMana(teamBackUp.get(i).getMana() * 0.5);
            } else {
                hero.setHp(teamBackUp.get(i).getHp());
                hero.setMana(teamBackUp.get(i).getMana());
                hero.getBag().setMoney(100 * monsters.get(i).getLevel());
                hero.setExtraExp(hero.getExp() + 2);
                hero.executeLevelUp();
            }
        }
    }

    @Override
    public String toString(){
        return "Legends: Monsters and Heroes";
    }

}
