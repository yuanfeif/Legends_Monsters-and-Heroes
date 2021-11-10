/**
 * @ClassName MarketFactory
 * @Description a class used to create market
 * @Author Vincent Yuan
 */
public class MarketFactory {
    /**
     * according to what you want to create markets
     * @param type
     * @return Market
     */
    public static Market createMarket (String type) {
        Market market = null;
        //create market in Legends
        if ("LMH".equalsIgnoreCase(type)) {
            market = new MarketLMH();
        }
        return market;
    }
}
