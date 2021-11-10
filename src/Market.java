import java.util.ArrayList;

/**
 * @ClassName Market
 * @Description markets for all type of games
 * @Author Vincent Yuan
 */
public abstract class Market implements MarketPrinter{
    /**
     * props will be sold out in a market
     */
    private ArrayList<Prop> props;

    public Market() {
        this.props = new ArrayList<>();
    }

    public ArrayList<Prop> getProps() {
        return props;
    }

    public void setProps(ArrayList<Prop> props) {
        this.props = props;
    }
}
