import java.util.*;

/**
 * @ClassName GameLV
 * @Description
 * @Author Vincent Yuan
 * @Date 2021/11/14 14:19
 */
public class GameLV extends GameRPG{

    private HashMap<Hero,Integer> heroRowHashMap;
    private HashMap<Hero,Integer> heroColHashMap;
    private HashMap<Monster,Integer> monsterRowHashMap;
    private HashMap<Monster,Integer> monsterColHashMap;

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
        grid.initiateGrid();
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
            boolean roundEndFlag = false;
            for (Hero hero : team) {
                move(hero);
                grid.printGrid();
                stepInto(hero);
                if (makejudge()) roundEndFlag = true;
                grid.printGrid();
            }
            if(roundEndFlag) continue;

            for (Monster monster : monsters) {
                monsterMove(monster);
                GamePrintUtil.printSystemNotification(monster.getName() + " move forward!");
                if (makejudge()) break;
            }
            grid.printGrid();
        }
    }

    private boolean makejudge() {
        for (Hero hero : team) {
            int curRow = heroRowHashMap.get(hero);
            int curCol = heroColHashMap.get(hero);
            if(grid.getGrid()[curRow][curCol] instanceof CellMonsterNexus){
                heroWin();
                return true;
            }
        }
        for (Monster monster : monsters) {
            int curRow = monsterRowHashMap.get(monster);
            int curCol = monsterColHashMap.get(monster);
            if(grid.getGrid()[curRow][curCol] instanceof CellHeroNexus){
                monsterWin();
                return true;
            }
        }
        return false;
    }


    public void move(Hero hero){
        Scanner sc = new Scanner(System.in);
        String action;
        int curCol = heroColHashMap.get(hero);
        int curRow = heroRowHashMap.get(hero);

        while (true) {
            GamePrintUtil.printSystemNotification("Hero " + hero.getName()+ " Action");
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
                    ("D".equalsIgnoreCase(action) && (curCol + 1 >= grid.getWidth())) ||
                    ("W".equalsIgnoreCase(action) && (curRow - 1 < 0)) ||
                    ("S".equalsIgnoreCase(action) && (curRow + 1 >= grid.getHeight()))) {

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

        grid.getGrid()[curRow][curCol].setHeroOn(false);
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
        grid.getGrid()[curRow][curCol].setHeroOn(true);
        System.out.println(Colors.setColor("You are now on a " + grid.getGrid()[curRow][curCol].getClass(), Colors.WHITE));

        if ("Q".equalsIgnoreCase(action)) {
            GameHost.quit();
        }
        if ("I".equalsIgnoreCase(action)) {
            // Print relevant stats
            GamePrintUtil.printTeam(team);
        }
    }

    private void initializePosition() {
        //Eliminate all the existing positions && Initialize the init position
        int col = 0;
        for (Hero hero : team) {
            if(heroRowHashMap.containsKey(hero)){
                int curRow = heroRowHashMap.get(hero);
                int curCol = heroColHashMap.get(hero);
                grid.getGrid()[curRow][curCol].setHeroOn(false);
            }
            heroRowHashMap.put(hero,7);
            heroColHashMap.put(hero,col);
            grid.getGrid()[7][col].setHeroOn(true);
            col += 6;
        }

        col = 1;
        for (Monster monster : monsters) {
            if(monsterRowHashMap.containsKey(monster)){
                int curRow = monsterRowHashMap.get(monster);
                int curCol = monsterColHashMap.get(monster);
                grid.getGrid()[curRow][curCol].setMonsterOn(false);

            }
            monsterRowHashMap.put(monster,0);
            monsterColHashMap.put(monster,col);
            grid.getGrid()[0][col].setMonsterOn(true);
            col += 6;
        }


    }

    public void stepInto(Hero hero){
        int curRow = heroRowHashMap.get(hero);
        int curCol = heroColHashMap.get(hero);
        for (Monster monster : monsters) {
            if(judgeNear(hero,monster)){
                fight(hero,monster);
                break;
            }
        }

        //step into a hero nexus, market can be entered
        if (grid.getGrid()[curRow][curCol] instanceof CellHeroNexus || grid.getGrid()[curRow][curCol] instanceof CellMonsterNexus) {
            enterMarket();
        }


    }

    private void heroWin() {
        GamePrintUtil.printSystemNotification("Heroes win this round!"); // Add something here
        createMonsters();
        initializePosition();
    }

    private void monsterWin() {
        GamePrintUtil.printSystemNotification("Monsters win this round!"); // Add something here
        createMonsters();
        initializePosition();
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
        if(heroRowHashMap.get(hero) == monsterRowHashMap.get(monster)){
            //If there is a wall between, not fight happen.
            int heroCol = heroColHashMap.get(hero);
            int monsterCol = monsterColHashMap.get(monster);
            if(heroCol >= monsterCol){
                int temp = heroCol;
                heroCol = monsterCol;
                monsterCol = temp;
            }
            for(int i = heroCol; i <= monsterCol;i++){
                if(grid.getGrid()[heroRowHashMap.get(hero)][i] instanceof CellInaccessible){ return false; }
            }
            return true;
        }
        return false;
    }

    public void fight(Hero hero, Monster monster){
        Scanner sc = new Scanner(System.in);
        String action;
        while (true) {
            GamePrintUtil.printSystemNotification("Hero " + hero.getName() + " Action");
            GamePrintUtil.printSystemNotification("Please enter your battle action (aA/sS): ");
            action = sc.nextLine();

            //check if input is valid
            if (!"A".equalsIgnoreCase(action) && !"S".equalsIgnoreCase(action) ) {
                GamePrintUtil.printSystemNotification("Please input a valid action! (W/w, A/a, S/s, D/d, Q/q, I/i)!");
            }
            else {
                break;
            }
        }

        if("A".equalsIgnoreCase(action)){
            hero.normalAttack(monster);
        }
        else if("S".equalsIgnoreCase(action)){
            hero.castSpell(monster);
        }

        monster.attack(hero);
    }

    public void monsterMove(Monster monster){
        int curRow = monsterRowHashMap.get(monster);
        int curCol = monsterColHashMap.get(monster);
        if(curRow < grid.getHeight() - 1){
            grid.getGrid()[curRow][curCol].setMonsterOn(false);
            curRow++;
            grid.getGrid()[curRow][curCol].setMonsterOn(true);
            monsterRowHashMap.put(monster,curRow);
        }
    }

    @Override
    public void backgroundInfo() {
        String backInfo = "The heroes and monsters live in a world represented by a square grid of fixed dimensions. \n" +
                "This world has different types of places to be; \n" +
                "The heroes and monsters do not get along and therefore fight each other. \n" +
                "Heroes can use weapons, armors, potions, and spells against the monsters. \n" +
                "Every time the heroes win, they gain some experience and some money. \n" +
                "When they accumulate enough experience they level up which means that their skills become stronger.  \n" +
                "The goal of the game is for the heroes to get into monsters' nexus.\n";
        System.out.println(Colors.setGroundColor(backInfo, Colors.BLACK, Colors.YELLOW_BG));
        playOrQuit();
        GamePrintUtil.printSystemNotification("                    GAME START                     ");
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
        GamePrintUtil.printSystemHint(Colors.justSetGroundColor("   ", Colors.RED_BG) + " is Heroes' Nexus");
        GamePrintUtil.printSystemHint(Colors.justSetGroundColor("   ", Colors.BLUE_BG) + " is Monsters' Nexus");
        GamePrintUtil.printSystemHint(" \uD83C\uDF33 " + " is a bush cell");
        GamePrintUtil.printSystemHint(" \uD83D\uDD73 " + " is a cave cell");
        GamePrintUtil.printSystemHint(" \uD83D\uDEAB " + " is inaccessible space");
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

    @Override
    public String toString(){
        return "Legends: Valor";
    }


}
