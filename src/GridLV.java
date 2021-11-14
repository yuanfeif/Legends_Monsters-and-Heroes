/**
 * @ClassName GridLV
 * @Description
 * @Author Vincent Yuan
 * @Date 2021/11/14 14:26
 */
public class GridLV extends Grid{
    public GridLV(int size) {
        super(size);
    }

    @Override
    public int getSize() {
        return 0;
    }

    @Override
    public Cell getCellBefore() {
        return null;
    }

    @Override
    public void markAsHero(int row, int col) {

    }

    @Override
    public void recoverCell(int row, int col) {

    }

    @Override
    public void initiateGrid() {

    }

    @Override
    public void printGrid() {

    }
}
