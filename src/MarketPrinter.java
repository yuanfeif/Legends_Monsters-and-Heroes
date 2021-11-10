/**
 * @ClassName MarketPrinter
 * @Description an interface used to print props in markets
 * @Author Vincent Yuan
 */
public interface MarketPrinter {
    /**
     * parse props files and initiate the market
     */
    void initiateMarket();

    /**
     * print all the props in a market
     */
    void printMarket();
}
