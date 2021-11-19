import java.util.Random;

/**
 * @ClassName GridLV
 * @Description
 * @Author Vincent Yuan
 * @Date 2021/11/14 14:26
 */
public class GridLV extends Grid{

    private int height;
    private int width;
    private Cell cellBefore;

    public GridLV(int height, int width) {
        super(height, width);
        this.height = height;
        this.width = width;
    }

    @Override
    public int getSize() {
        return 0;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    @Override
    public Cell getCellBefore() {
        return cellBefore;
    }

    public void setCellBefore(Cell cellBefore) {
        this.cellBefore = cellBefore;
    }

    @Override
    public void markAsHero(int row, int col) {

    }

    @Override
    public void markAsHero(String name, int row, int col) {
        cellBefore = getGrid()[row][col];
        getGrid()[row][col] = new CellHero(name);
    }

    @Override
    public void recoverCell(int row, int col) {
        getGrid()[row][col] = cellBefore;
    }

    @Override
    public void initiateGrid() {
        Random seed = new Random();
        Cell[][] grid = getGrid();

        for (int i = 0; i < height; i++) {
            grid[i][4] = new CellInaccessible();
            grid[i][5] = new CellInaccessible();
            grid[i][10] = new CellInaccessible();
            grid[i][11] = new CellInaccessible();
        }

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (j == 4 || j == 5 || j == 10 || j == 11) {
                    continue;
                } else {
                    if (i == 0) {
                        grid[i][j] = new CellMonsterNexus();
                    } else if (i == height - 1) {
                        grid[i][j] = new CellHeroNexus();
                    } else {
                        int tmp = seed.nextInt(100) + 1;
                        if (1 <= tmp && tmp <= 20) {
                            grid[i][j] = new CellBush();
                        } else if (21 <= tmp && tmp <= 40) {
                            grid[i][j] = new CellCave();
                        } else if (41 <= tmp && tmp <= 60) {
                            grid[i][j] = new CellKoulou();
                        } else {
                            grid[i][j] = new CellPlain();
                        }
                    }
                }
            }
        }
    }

    @Override
    public void printGrid() {
        Cell[][] grid = getGrid();
        System.out.println(grid[0][0].getMark());

        System.out.println(Colors.setGroundColor(" Game Grid: ", Colors.BLACK, Colors.PURPLE_BG));

        StringBuilder boundary = new StringBuilder("--------------------------------------");
        for (Cell[] row : grid) {
            System.out.println(boundary);
            for (int i = 0; i < row.length; i++) {
                if (i % 2 == 0) {
                    System.out.print("||" + row[i].getMark() + "");
                } else {
                    System.out.print("|" + row[i].getMark() + "");
                }
            }
            System.out.println("||");
        }
        System.out.println(boundary);
        System.out.println();
    }
}
