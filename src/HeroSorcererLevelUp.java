/**
 * @ClassName HeroSorcererLevelUp
 * @Description
 * @Author Vincent Yuan
 */
public class HeroSorcererLevelUp implements StrategyLevelUp{
    private Hero hero;

    public HeroSorcererLevelUp(Hero hero) {
        this.hero = hero;
    }

    @Override
    public boolean checkLevelUp() {
        return hero.getExtraExp() >= hero.getLevel() * 10;
    }

    /**
     * higher agility and dexterity
     */
    @Override
    public void levelUp() {
        if (checkLevelUp()) {
            hero.setExtraExp(hero.getExtraExp() - hero.getLevel() * 10);
            hero.setLevel(hero.getLevel() + 1);
            hero.setStrength(hero.getStrength() * 1.05);
            hero.setDexterity(hero.getDexterity() * 1.1);
            hero.setAgility(hero.getAgility() * 1.1);
        }
    }
}
