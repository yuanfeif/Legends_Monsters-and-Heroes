/**
 * @ClassName CellHero
 * @Description position of a player
 * @Author Vincent Yuan
 */
public class CellHero extends Cell{

    public CellHero() {
        super(Colors.setColor(" P ", Colors.YELLOW ));
    }

    public CellHero(String name) {
        super(name + " ");
    }
}
