/**
 * Host the game. Interact with the player(s) and the specific game.
 * It is extendable to have more games to choose.
 */
public class GameHost{
    private static Game game;

    public static void run(){
        PlayMusic p=new PlayMusic();
        p.play();
        welcome();
        game.play();
        exitHost();
    }

    public static void welcome(){
        System.out.println(GamePrintUtil.DRAGON);
        //Choose the game.
        while (true){
            System.out.println("\u001B[31mGame Hoster:" + "\u001B[37m Please input a number to start ot exit:");
            System.out.println("             1.Start Legends: Monsters and Heroes");
            System.out.println("             2.Start Legends: Valor");
            System.out.println("             3.Exit");
            int i = UserInputUtil.getIntInput(1,3);
            if (i == 1) { game = new GameLMH();break; }
            if (i == 2) { game = new GameLV();break; }
            else if( i == 3) { exitHost();break; }
        }
        System.out.println("Welcome to "+ game.toString() + " game and Let's start!");
    }

    public static void exitHost(){
        System.out.println("Bye...");
        System.exit(0);
    }

    /**
     * exit function
     */
    public static void quit() {
        GamePrintUtil.printSystemNotification("Thank you for playing "+ game.toString()+ " !");
        run();
    }
}
