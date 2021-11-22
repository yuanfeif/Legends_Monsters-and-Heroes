import javafx.application.Application;
import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

/**
 * Host the game. Interact with the player(s) and the specific game.
 * It is extendable to have more games to choose.
 */
public class GameHost{
    private static Game game;

    public static void run(){
//        JFXPanel fxPanel = new JFXPanel();
        PlayMusic p=new PlayMusic();
        p.play();
        welcome();
        game.play();
        exitHost();
    }

    public static void welcome(){
//        launch();
        //Choose the game.
        while (true){
            System.out.println("\u001B[31mGame Hoster:" + "\u001B[37m Please input a number to start ot exit:");
            System.out.println("             1.Start Legends: Monsters and Heroes");
            System.out.println("             2.Start Legends: Valor");
            System.out.println("             3.Exit");
            int i = UserInputUtil.getIntInput(1,2);
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
