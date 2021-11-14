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

    }


    @Override
    public void play() {
        backgroundInfo();

        pickRoles();
        GamePrintUtil.printTeam(team);

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

        //after leaving a cell, need to recover the marker before
        grid.markAsHero(curRow, curCol);
        System.out.println(Colors.setColor("You are now on a " + grid.getCellBefore().getMark(), Colors.WHITE));

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

    public void showGrid(){

    }
}
