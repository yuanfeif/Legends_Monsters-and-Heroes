/**
 * @ClassName GameRPG
 * @Description abstract class for all kinds of RPG games
 * @Author Vincent Yuan
 */
public abstract class GameRPG extends Game{

    public GameRPG() {}

    /**
     * print background information
     */
    public abstract void backgroundInfo();

    /**
     * players pick roles at the beginning of a game
     */
    public abstract void pickRoles();
}
