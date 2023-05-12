package Lists.Cells;

public class EntryCell {

    // Properties
    private String name;
    private double price;
    private int quantity;
    private EntryCell nextEntry = null;
    private EntryCell previousEntry = null;

    // Init
    public EntryCell(String name, double price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    // Getters
    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public EntryCell getNextEntry() {
        return nextEntry;
    }

    public EntryCell getPreviousEntry() {
        return previousEntry;
    }

    // Setters
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setNextEntry(EntryCell nextEntry) {
        this.nextEntry = nextEntry;
    }

    public void setPreviousEntry(EntryCell previousEntry) {
        this.previousEntry = previousEntry;
    }
}
