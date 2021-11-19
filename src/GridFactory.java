/**
 * @ClassName GridFactory
 * @Description a class used to create grid
 * @Author Vincent Yuan
 */
public class GridFactory {
    /**
     * according to what you want to create grids
     * @param type
     * @return Grid
     */
    public static Grid createGrid (String type) {
        Grid grid = null;
        //create grid in Legends
        if ("LMH".equalsIgnoreCase(type)) {
            grid = new GridLMH(8);
        } else if ("LV".equalsIgnoreCase(type)) {
            grid = new GridLV(8, 16);
        }
        return grid;
    }
}
