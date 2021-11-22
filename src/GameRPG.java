import java.util.ArrayList;
import java.util.Scanner;

/**
 * @ClassName GameRPG
 * @Description abstract class for all kinds of RPG games
 * @Author Vincent Yuan
 */
public abstract class GameRPG extends Game{
    /**
     * number of a team
     */
    protected int heroNum;

    /**
     * player's team
     */
    protected ArrayList<Hero> team;

    public GameRPG() {}

    /**
     * print background information
     */
    public abstract void backgroundInfo();

    /**
     * players pick roles at the beginning of a game
     */
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
}
