import java.util.*;

/**
 * @ClassName GameLV
 * @Description
 * @Author Vincent Yuan
 * @Date 2021/11/14 14:19
 */
public class GameLV extends GameRPG{
    /**
     * number of a team
     */
    private int heroNum;


    private HashMap<Hero,Integer> heroRowHashMap;
    private HashMap<Hero,Integer> heroColHashMap;
    private HashMap<Monster,Integer> monsterRowHashMap;
    private HashMap<Monster,Integer> monsterColHashMap;

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

    public GameLV(){
        heroNum = 3;
        team = new ArrayList<>();
        teamBackUp = new ArrayList<>();
        monsters = new ArrayList<>();
        heroColHashMap = new HashMap<>();
        heroRowHashMap = new HashMap<>();
        monsterColHashMap = new HashMap<>();
        monsterRowHashMap = new HashMap<>();
        this.grid = GridFactory.createGrid("LV");
        this.market = MarketFactory.createMarket("LMH");
        market.initiateMarket();
    }


    @Override
    public void play() {
        backgroundInfo();

        pickRoles();
        GamePrintUtil.printTeam(team);

        createMonsters();
        initializePosition();

        showGrid();

        while (true) {
            for (Hero hero : team) {
                boolean canMove = move(hero);
                grid.printGrid();

                if (canMove) {
                    stepInto(hero);
                    grid.printGrid();
                }

                grid.recoverCell(heroRowHashMap.get(hero), heroColHashMap.get(hero));
            }

            for (Monster monster : monsters) {
                monsterMove(monster);
            }
        }
    }




    @Override
    public void quit() {

    }

    public boolean move(Hero hero){
        Scanner sc = new Scanner(System.in);
        String action;
        int size = grid.getSize();
        int curCol = heroColHashMap.get(hero);
        int curRow = heroRowHashMap.get(hero);

        while (true) {
            GamePrintUtil.printSystemNotification("Please enter your action (wW/aA/sS/dD/qQ/iI): ");
            action = sc.nextLine();

            //check if input is valid
            if (!"W".equalsIgnoreCase(action) && !"A".equalsIgnoreCase(action) &&
                    !"S".equalsIgnoreCase(action) && !"D".equalsIgnoreCase(action) &&
                    !"Q".equalsIgnoreCase(action) && !"I".equalsIgnoreCase(action)) {
                GamePrintUtil.printSystemNotification("Please input a valid action! (W/w, A/a, S/s, D/d, Q/q, I/i)!");
                continue;
            }

            //check if position is out of boundary
            if (("A".equalsIgnoreCase(action) && (curCol - 1 < 0)) ||
                    ("D".equalsIgnoreCase(action) && (curCol + 1 >= size)) ||
                    ("W".equalsIgnoreCase(action) && (curRow - 1 < 0)) ||
                    ("S".equalsIgnoreCase(action) && (curRow + 1 >= size))) {

                // Valid input, but cannot go outside of the map
                GamePrintUtil.printSystemNotification("Please move inside the grid!");
                continue;
            }

            // cannot move into an inaccessible space
            if (("A".equalsIgnoreCase(action) && (grid.getGrid()[curRow][curCol - 1] instanceof CellInaccessible)) ||
                    ("D".equalsIgnoreCase(action) && (grid.getGrid()[curRow][curCol + 1] instanceof CellInaccessible)) ||
                    ("W".equalsIgnoreCase(action) && (grid.getGrid()[curRow - 1][curCol] instanceof CellInaccessible)) ||
                    ("S".equalsIgnoreCase(action) && (grid.getGrid()[curRow + 1][curCol] instanceof CellInaccessible))) {
                GamePrintUtil.printSystemNotification("You cannot move into an inaccessible space!");
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

        heroRowHashMap.put(hero,curRow);
        heroColHashMap.put(hero,curCol);

        //after leaving a cell, need to recover the marker before
        grid.markAsHero(curRow, curCol);
        //System.out.println(Colors.setColor("You are now on a " + grid.getCellBefore().getMark(), Colors.WHITE));

        if ("Q".equalsIgnoreCase(action)) {
            quit();
        }
        if ("I".equalsIgnoreCase(action)) {
            // Print relevant stats
            GamePrintUtil.printTeam(team);
            return false;
        }
        return true;

    }

    private void initializePosition() {
        int col = 0;
        for (Hero hero : team) {
            heroRowHashMap.put(hero,0);
            heroColHashMap.put(hero,col);
            col += 6;
        }

        col = 1;
        for (Monster monster : monsters) {
            monsterRowHashMap.put(monster,7);
            monsterColHashMap.put(monster,col);
            col += 6;
        }

    }

    public void stepInto(Hero hero){
        if (grid.getCellBefore() instanceof CellCommon) {
            for (Monster monster : monsters) {
                if(judgeNear(hero,monster)){
                    fight(hero,monster);
                    break;
                }
            }
        }

        //step into a market cell
        if (grid.getCellBefore() instanceof Cell) {
            enterMarket();
        }
    }

    public void enterMarket(){
        Scanner sc = new Scanner(System.in);
        GamePrintUtil.printSystemNotification("Welcome to market! You can buy powerful props for your heroes!");

        // buy or sell props for each hero
        for (int i = 0; i < team.size(); i++) {
            GamePrintUtil.printSystemNotification("What do you want to do for your heroes?");
            System.out.println();
            GamePrintUtil.printTeam(team);
            GamePrintUtil.printSystemHint("1. Buy     2. Sell     3. Finish and Pass");

            GamePrintUtil.printSystemInfo("For hero" + (i + 1));

            //check if choice is valid
            int choice;
            while (true) {
                if (sc.hasNextInt()) {
                    choice = sc.nextInt();
                    if (choice < 1 || choice > 3) {
                        GamePrintUtil.printSystemNotification("Please input a valid number!(1-)" + market.getProps().size());
                    } else {
                        break;
                    }
                } else {
                    // If input is not an INTEGER
                    sc.next();
                    GamePrintUtil.printSystemNotification("Please input a valid number!(1-)" + market.getProps().size());
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
                    GamePrintUtil.printSystemNotification("Bye!");
                }
            }
        }
    }

    private boolean judgeNear(Hero hero, Monster monster){
        if(heroRowHashMap.get(hero) == monsterRowHashMap.get(monster)
                && heroColHashMap.get(hero) > heroColHashMap.get(monster)
                && heroColHashMap.get(hero) + 3 >=  monsterColHashMap.get(monster) ){
            return true;
        }
        return false;
    }

    public void fight(Hero hero, Monster monster){

    }

    public void monsterMove(Monster monster){

    }

    @Override
    public void playOrQuit() {
        GamePrintUtil.printSystemNotification("Press 'Q/q' to quit, or any other key(s) to continue!");

        Scanner sc = new Scanner(System.in);
        String decision = sc.nextLine();

        // Check if the player wants to start the game or quit
        if ("Q".equalsIgnoreCase(decision)) {
            quit();
        }
    }

    @Override
    public void backgroundInfo() {
        System.out.println(Colors.setColor("Welcome to Legends: Valor!", Colors.BLUE));
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
        GamePrintUtil.printSystemNotification("                    GAME START                     ");
    }

    @Override
    public void pickRoles() {
        HeroList.printHeroList();

        GamePrintUtil.printSystemNotification("Now you can form your own team!");
        System.out.println();
        GamePrintUtil.printSystemNotification("Input the number of your team(1-3): ");

        // check if the number is valid
        Scanner sc = new Scanner(System.in);

        // check if the number of heroes is valid when choosing
        for (int i = 0; i < heroNum; i++) {
            GamePrintUtil.printSystemInfo("Choose Hero " + i + ": ");
            int choice;
            while (true) {
                if (sc.hasNextInt()) {
                    choice = sc.nextInt();
                    if (choice < 1 || choice > HeroList.getHeroes().size()) {
                        GamePrintUtil.printSystemNotification("Please input a valid number(1-" + HeroList.getHeroes().size() + ")!");
                    } else {
                        team.add(HeroList.getHeroes().get(choice - 1));
                        break;
                    }
                } else {
                    sc.next();
                    GamePrintUtil.printSystemNotification("Please input a valid number(1-" + HeroList.getHeroes().size() + ")!");
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


    public void showGrid(){
        GamePrintUtil.printSystemInfo("Here is the grid of the world!");
        GamePrintUtil.printSystemHint(" ☆ " + " is your birth place");
        GamePrintUtil.printSystemHint(" \uD83D\uDEA9 " + " is your position");
        GamePrintUtil.printSystemHint(" \uD83C\uDF3F " + " is common space");
        GamePrintUtil.printSystemHint(" \uD83D\uDED2 " + " is market");
        GamePrintUtil.printSystemHint(" ❌ " + " is inaccessible space");
        grid.initiateGrid();
        grid.printGrid();

    }

    /**
     * pick props in the market
     * @return Prop
     */
    public Prop pickPropInMarket() {
        Scanner sc = new Scanner(System.in);

        // Input validation checking
        while (true) {
            GamePrintUtil.printSystemNotification("Please pick a prop you like!");
            int propNum;

            //check if number is valid
            if (sc.hasNextInt()) {
                propNum = sc.nextInt();

                if (propNum < 1 || propNum > market.getProps().size()) {
                    GamePrintUtil.printSystemNotification("Please input a valid prop number!(1-" + market.getProps().size() + ")");
                } else {
                    return market.getProps().get(propNum - 1);
                }
            } else {
                sc.next();
                GamePrintUtil.printSystemNotification("Please input a valid prop number!(1-" + market.getProps().size() + ")");
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
        GamePrintUtil.printSystemNotification("Please choose a Prop type!");
        GamePrintUtil.printSystemHint("W/w: Weapons");
        GamePrintUtil.printSystemHint("A/a: Armors");
        GamePrintUtil.printSystemHint("P/p: Potions");
        GamePrintUtil.printSystemHint("S/s: Spells");
        String type;
        while (true) {
            type = sc.nextLine();
            //check if type is valid
            if (!"W".equalsIgnoreCase(type) && !"A".equalsIgnoreCase(type) &&
                    !"P".equalsIgnoreCase(type) && !"S".equalsIgnoreCase(type)) {
                GamePrintUtil.printSystemNotification("Please input a valid action! (W/w, A/a, P/p, S/s)!");
                continue;
            }
            //check if having specific prop
            if (("W".equalsIgnoreCase(type) && hero.getBag().getWeapons().isEmpty()) ||
                    ("A".equalsIgnoreCase(type) && hero.getBag().getArmors().isEmpty()) ||
                    ("P".equalsIgnoreCase(type) && hero.getBag().getPotions().isEmpty()) ||
                    ("S".equalsIgnoreCase(type) && hero.getBag().getSpells().isEmpty())) {
                GamePrintUtil.printSystemNotification("You don't have this kind of prop!");
                continue;
            }

            int choice;
            GamePrintUtil.printSystemNotification("Please choose a Specific Prop!");

            // check if the number is valid
            while (true) {
                if (sc.hasNextInt()) {
                    choice = sc.nextInt();
                    if ("W".equalsIgnoreCase(type)) {
                        if (choice < 1 || choice > hero.getBag().getWeapons().size()) {
                            GamePrintUtil.printSystemNotification("Please input a valid prop number!(1-" + hero.getBag().getWeapons().size() + ")");
                        } else {
                            return hero.getBag().getWeapons().get(choice - 1);
                        }
                    } else if ("A".equalsIgnoreCase(type)) {
                        if (choice < 1 || choice > hero.getBag().getArmors().size()) {
                            GamePrintUtil.printSystemNotification("Please input a valid prop number!(1-" + hero.getBag().getArmors().size() + ")");
                        } else {
                            return hero.getBag().getArmors().get(choice - 1);
                        }
                    } else if ("P".equalsIgnoreCase(type)) {
                        if (choice < 1 || choice > hero.getBag().getPotions().size()) {
                            GamePrintUtil.printSystemNotification("Please input a valid prop number!(1-" + hero.getBag().getPotions().size() + ")");
                        } else {
                            return hero.getBag().getPotions().get(choice - 1);
                        }
                    } else if ("S".equalsIgnoreCase(type)) {
                        if (choice < 1 || choice > hero.getBag().getSpells().size()) {
                            GamePrintUtil.printSystemNotification("Please input a valid prop number!(1-" + hero.getBag().getSpells().size() + ")");
                        } else {
                            return hero.getBag().getSpells().get(choice - 1);
                        }
                    }
                } else {
                    GamePrintUtil.printSystemNotification("Please input a valid prop number!");
                }
            }
        }
    }


}
