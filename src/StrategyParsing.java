import java.util.ArrayList;

/**
 * @ClassName StrategyParsing
 * @Description an interface used to realize strategy pattern for parsing files
 * @Author Vincent Yuan
 */
public interface StrategyParsing {
    ArrayList<Prop> parsingPropFile(String fileName);
}
