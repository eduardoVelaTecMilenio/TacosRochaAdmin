package Lists.Cells;

import Lists.DishList;

public class MenuCell {

    // Properties
    private String name;
    private DishList dishList;
    private MenuCell nextMenu = null;
    private MenuCell previousMenu = null;

    // Init
    public MenuCell(String name) {
        this.name = name;
        this.dishList = new DishList(name);
    }

    // Getters
    public String getName() {
        return name;
    }

    public DishList getDishList() {
        return dishList;
    }

    public MenuCell getNextMenu() {
        return nextMenu;
    }

    public MenuCell getPreviousMenu() {
        return previousMenu;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setNextMenu(MenuCell nextMenu) {
        this.nextMenu = nextMenu;
    }

    public void setPreviousMenu(MenuCell previousMenu) {
        this.previousMenu = previousMenu;
    }
}
