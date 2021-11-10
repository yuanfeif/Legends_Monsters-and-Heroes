/**
 * @ClassName Prop
 * @Description props in Legends
 * @Author Vincent Yuan
 */
public class Prop {
    private String name;
    private int price;
    private int minRequiredLevel;

    public Prop() {}

    public Prop(String name, int price, int minRequiredLevel) {
        this.name = name;
        this.price = price;
        this.minRequiredLevel = minRequiredLevel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getMinRequiredLevel() {
        return minRequiredLevel;
    }

    public void setMinRequiredLevel(int minRequiredLevel) {
        this.minRequiredLevel = minRequiredLevel;
    }

}
