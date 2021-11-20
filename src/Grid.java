/**
 * @ClassName Grid
 * @Description grids for all type of games
 * @Author Vincent Yuan
 */
public abstract class Grid implements GridPrinter{

    /**
     * grid is base on cells
     */
    private Cell[][] grid;

    public Grid(int size) {
        this.grid = new Cell[size][size];
    }

    public Grid(int height, int width) {
        this.grid = new Cell[height][width];
    }

    public Cell[][] getGrid() {
        return grid;
    }

    public void setGrid(Cell[][] grid) {
        this.grid = grid;
    }

    /**
     * get the size of the grid
     * @return int
     */
    public abstract int getSize();

    /**
     * get the height of the grid
     * @return int
     */
    public abstract int getHeight();

    /**
     * get the width of the grid
     * @return int
     */
    public abstract int getWidth();

    /**
     * get the type of the cell before player passed
     * @return
     */
    public abstract Cell getCellBefore();

    /**
     * when a player step into the cell
     * the mark should be changed into a flag icon
     * @param row
     * @param col
     */
    public abstract void markAsHero(int row, int col);

    public abstract void markAsHero(String name, int row, int col);
    /**
     * after a player leave the cell
     * the mark should be recovered
     * @param row
     * @param col
     */
    public abstract void recoverCell(int row, int col);
}
