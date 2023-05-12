package Lists.Cells;

public class DishCell {

    // Properties
    private String name;
    private double price;
    private DishCell nextDish = null;
    private DishCell previousDish = null;

    // Init
    public DishCell(String name, double price) {
        this.name = name;
        this.price = price;
    }

    // Getters
    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public DishCell getNextDish() {
        return nextDish;
    }

    public DishCell getPreviousDish() {
        return previousDish;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setNextDish(DishCell nextDish) {
        this.nextDish = nextDish;
    }

    public void setPreviousDish(DishCell previousDish) {
        this.previousDish = previousDish;
    }
}
