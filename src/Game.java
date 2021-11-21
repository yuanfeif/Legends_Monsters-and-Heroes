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
    public abstract void playOrQuit();
}
