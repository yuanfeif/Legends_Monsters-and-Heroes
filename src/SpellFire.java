import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName SpellFile
 * @Description one type of spell
 * @Author Vincent Yuan
 */
public class SpellFire extends Spell{
    public SpellFire(String name, int price, int minRequiredLevel, int damage, int requiredMana) {
        super(name, price, minRequiredLevel, damage, requiredMana);
    }
}
