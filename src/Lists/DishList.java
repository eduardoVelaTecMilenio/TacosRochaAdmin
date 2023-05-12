package Lists;

import Lists.Cells.DishCell;
import Lists.Cells.MenuCell;

import java.io.*;

public class DishList {

    // Properties
    private String menuName;
    private DishCell firstDish = null;
    private DishCell lastDish = null;
    private int listSize = 0;

    // Init
    public DishList(String menuName) {
        this.menuName = menuName;
        read();
    }

    // Functions
    // Adds a new dish to the menu list.
    public void add(String name, double price) {
        DishCell newDish = new DishCell(name, price);

        if (listSize != 0) {
            // listSize > 1.
            if (listSize != 1) {
                lastDish.setNextDish(newDish);
                newDish.setPreviousDish(lastDish);
            }
            // listSize = 1.
            else {
                firstDish.setNextDish(newDish);
                newDish.setPreviousDish(firstDish);
            }
        }
        // listSize = 0.
        else {
            firstDish = newDish;
        }

        lastDish = newDish;
        // List size increases.
        listSize++;
    }

    // Prints all the dishes of the list.
    public void print() {
        DishCell buffer = firstDish;

        // Prints dishes.
        for (int counter = 1; counter <= listSize; counter++) {
            System.out.println("[" + counter + "] " + buffer.getName() + " (Price: $" + buffer.getPrice() + ")");
            buffer = buffer.getNextDish();
        }
    }

    // Updates the name and price of a dish.
    public void update(int dishId, String name, double price) {
        DishCell buffer = firstDish;

        // Finds the dish.
        for (int counter = 1; counter < dishId; counter++) {
            buffer = buffer.getNextDish();
        }

        // Dish's name and price is changes.
        buffer.setName(name);
        buffer.setPrice(price);
    }

    // Deletes a dish from the dish list.
    public void delete(int dishId) {
        if (listSize != 1) {
            // listSize > 2.
            if (listSize != 2) {
                if (dishId != 1) {
                    // A dish between the first and the last one is deleted.
                    if (dishId != listSize) {
                        DishCell buffer = firstDish;

                        for (int counter = 1; counter < dishId; counter++) {
                            buffer = buffer.getNextDish();
                        }

                        buffer.getPreviousDish().setNextDish(buffer.getNextDish());
                        buffer.getNextDish().setPreviousDish(buffer.getPreviousDish());
                    }
                    // Last dish is deleted.
                    else {
                        lastDish = lastDish.getPreviousDish();
                        lastDish.setNextDish(null);
                    }
                }
                // First dish is deleted.
                else {
                    firstDish = firstDish.getNextDish();
                    firstDish.setPreviousDish(null);
                }
            }
            // listSize = 2.
            else {
                // Last dish is deleted.
                if (dishId != 1) {
                    lastDish = firstDish;
                    firstDish.setNextDish(null);
                }
                // First dish is deleted.
                else {
                    firstDish = lastDish;
                    lastDish.setPreviousDish(null);
                }
            }
        }
        // listSize = 1.
        else {
            firstDish = null;
            lastDish = null;
        }

        // List size decreases.
        listSize--;
    }

    // Checks if a menu of the menu list already has a given name.
    public boolean checkIfMenuExists(String name) {
        DishCell buffer = firstDish;
        boolean exists = false;

        // Checks all the menus.
        for (int counter = 0; counter < listSize; counter++) {
            // A menu has the given name.
            if (buffer.getName().equals(name)) {
                // Error message.
                System.out.println("\n[!] A dish already has that name. Please try again.");
                exists = true;
                break;
            }

            buffer = buffer.getNextDish();
        }

        return exists;
    }

    // Returns the dishes of the list in the format that is used to save them.
    public String getDishes() {
        String dishes = "";
        DishCell buffer = firstDish;

        // Dishes are obtained.
        for (int counter = 0; counter < listSize; counter++) {
            dishes = dishes.concat(menuName + ";" + buffer.getName() + ";" + buffer.getPrice() + "\n");

            buffer = buffer.getNextDish();
        }

        // Dishes are returned.
        return dishes;
    }

    // Returns a dish depending on the given id.
    public DishCell getDish(int dishId) {
        DishCell buffer = firstDish;

        // Finds the dish.
        for (int counter = 1; counter < dishId; counter++) {
            buffer = buffer.getNextDish();
        }

        // Returns the dish.
        return buffer;
    }

    // Returns the size of the dish list.
    public int getListSize() {
        return listSize;
    }

    // Returns the menu name the dish list belongs to.
    public String getMenuName() {
        return menuName;
    }

    // Changes the name of the menu the dish list belongs to, so it saves the dishes using the new name.
    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    // Reads the dishes stored on the dish text file.
    public void read() {
        // Text file path.
        String fileName = "dishes.txt";
        String data;

        int stage;
        char buffer;
        String menuName;
        String name;
        String price;

        try {
            // Dish text file is loaded.
            BufferedReader fileReader = new BufferedReader(new FileReader(fileName));

            while ((data = fileReader.readLine()) != null) {
                stage = 1;
                menuName = "";
                name = "";
                price = "";

                // Dish text file's data is read.
                for (int counter = 0; counter < data.length(); counter++) {
                    buffer = data.charAt(counter);

                    // Menu's name is obtained.
                    if (stage == 1) {
                        if (buffer != ';') {
                            menuName = menuName.concat(String.valueOf(buffer));
                        }
                        else {
                            stage++;
                        }
                    }
                    // Dish's name is obtained.
                    else if(stage == 2) {
                        if (buffer != ';') {
                            name = name.concat(String.valueOf(buffer));
                        }
                        else {
                            stage++;
                        }
                    }
                    // Dish's price is obtained.
                    else {
                        price = price.concat(String.valueOf(buffer));
                    }
                }

                // Checks if the loaded dish belongs to the menu.
                if (menuName.equals(this.menuName)) {
                    add(name, Double.parseDouble(price));
                }
            }

            // Reader is closed.
            fileReader.close();
        } catch (IOException error) {
            error.printStackTrace();
        }
    }
}
