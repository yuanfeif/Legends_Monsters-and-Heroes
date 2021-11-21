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
    private boolean isHeroOn;
    private boolean isMonsterOn;

    public Cell(String mark) {
        this.mark = mark;
    }

    public String getMark() {
        if (isHeroOn) {
            return " H";
        }
        if (isMonsterOn) {
            return " M";
        }
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public boolean isHeroOn() {
        return isHeroOn;
    }

    public void setHeroOn(boolean heroOn) {
        isHeroOn = heroOn;
    }

    public boolean isMonsterOn() {
        return isMonsterOn;
    }

    public void setMonsterOn(boolean monsterOn) {
        isMonsterOn = monsterOn;
    }


}
