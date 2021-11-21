/**
 * Entrnace of this project
 */
public class Main {
    public static void main(String args[]) {
//        Game game = new GameLMH();
//        game.play();
//        System.out.println(Colors.justSetGroundColor("   ", Colors.RED_BG));
        Grid grid = GridFactory.createGrid("LV");
        grid.initiateGrid();
        grid.printGrid();

        //Start Game via a host
        GameHost.run();

    }
}