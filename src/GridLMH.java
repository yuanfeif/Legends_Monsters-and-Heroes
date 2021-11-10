import java.util.Random;

/**
 * @ClassName GridLMH
 * @Description grid for Legends
 * @Author Vincent Yuan
 */
public class GridLMH extends Grid{

    private int size;
    /**
     * type of the cell before a player passed
     */
    private Cell cellBefore;

    public GridLMH(int size) {
        super(size);
        this.size = size;
    }

    @Override
    public Cell getCellBefore() {
        return cellBefore;
    }

    public void setCellBefore(Cell cellBefore) {
        this.cellBefore = cellBefore;
    }

    @Override
    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    /**
     * initiate a grid for Legends
     */
    @Override
    public void initiateGrid() {
        Random seed = new Random();
        Cell[][] grid = getGrid();

        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                //birth place
                if (i == 0 && j == 0) {
                    grid[i][j] = new CellBirth();
                    continue;
                }

                //make sure player will not be stuck at the very beginning
                if (i < 3 && j < 3) {
                    if (i == 0 && j == 1) {
                        grid[i][j] = new CellMarket();
                        continue;
                    }
                    grid[i][j] = new CellCommon();
                    continue;
                }

                int temp = seed.nextInt(100) + 1;

                //20% non-accessible cells, 30% markets and 50% common cells
                if (1 <= temp && temp <= 20) {
                    grid[i][j] = new CellInaccessible();
                } else if (21 <= temp && temp <= 70) {
                    grid[i][j] = new CellCommon();
                } else {
                    grid[i][j] = new CellMarket();
                }
            }
        }
    }

    /**
     * print the whole grid
     */
    @Override
    public void printGrid() {
        Cell[][] grid = getGrid();

        System.out.println(Colors.setGroundColor(" Game Grid: ", Colors.BLACK, Colors.PURPLE_BG));

        StringBuilder boundary = new StringBuilder("--------------------------------------");
        for (Cell[] row : grid) {
            System.out.println(boundary);
            for (Cell cell : row) {
                System.out.print("|" + cell.getMark() + "");
            }
            System.out.println("|");
        }
        System.out.println(boundary);
        System.out.println();
    }

    /**
     * when a player step into the cell
     * the mark should be changed into a flag icon
     * @param row
     * @param col
     */
    @Override
    public void markAsHero(int row, int col) {
        cellBefore = getGrid()[row][col];
        getGrid()[row][col] = new CellHero();
    }

    /**
     * after a player leave the cell
     * the mark should be recovered
     * @param row
     * @param col
     */
    @Override
    public void recoverCell(int row, int col) {
        getGrid()[row][col] = cellBefore;
    }
}
