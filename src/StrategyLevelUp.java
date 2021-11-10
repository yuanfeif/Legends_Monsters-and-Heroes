/**
 * @ClassName StrategyLevelUp
 * @Description an interface used to realize strategy pattern for level up strategies of different heroes
 * @Author Vincent Yuan
 */
public interface StrategyLevelUp {
    /**
     * check if a hero can level up
     * @return boolean
     */
    boolean checkLevelUp();

    /**
     * level up
     */
    void levelUp();
}
