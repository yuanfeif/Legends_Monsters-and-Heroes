import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @ClassName ParsingFile
 * @Description Context class in strategy pattern
 * @Author Vincent Yuan
 */
public class ParsingFile {
    private StrategyParsing strategyParsing;

    public ParsingFile(StrategyParsing strategyParsing) {
        this.strategyParsing = strategyParsing;
    }

    public ArrayList<Prop> executeStrategy(String fileName) {
        return strategyParsing.parsingPropFile(fileName);
    }

    public static List<String> parsingFile(String fileName) {
        String file = System.getProperty("user.dir") + "/src/ConfigFiles/" + fileName;
        List<String> lines = Collections.emptyList();
        try {
            lines = Files.readAllLines(Paths.get(file), StandardCharsets.UTF_8);
            lines.remove(0);
        } catch (IOException e) {
            System.out.println("Please enter the correct filepath");
            e.printStackTrace();
        }
        return lines;
    }
}
