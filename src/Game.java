import java.util.Scanner;

/**
 * @ClassName Game
 * @Description abstract class for all kinds of games
 * @Author Vincent Yuan
 * @Date 2021/11/8 12:19
 */
public abstract class Game {
    public abstract void play();

    /**
     * check if players want to continue or not
     */
    public void playOrQuit() {
        GamePrintUtil.printSystemNotification("Press 'Q/q' to quit, or any other key(s) to continue!");

        Scanner sc = new Scanner(System.in);
        String decision = sc.nextLine();
        // Check if the player wants to start the game or quit
        if ("Q".equalsIgnoreCase(decision)) {
            GameHost.quit();
        }
    }
}
