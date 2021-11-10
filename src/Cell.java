/**
 * @ClassName Cell
 * @Description basic cell of a grid
 * @Author Vincent Yuan
 */
public class Cell {

    /**
     * mark of a cell
     * used to print on the console
     */
    private String mark;

    public Cell(String mark) {
        this.mark = mark;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }
}
