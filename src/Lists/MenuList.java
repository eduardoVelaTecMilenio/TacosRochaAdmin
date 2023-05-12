package Lists;

import Lists.Cells.MenuCell;

import java.io.*;

public class MenuList {

    // Properties
    private MenuCell firstMenu = null;
    private MenuCell lastMenu = null;
    private int listSize = 0;

    // Init
    public MenuList() {
        read();
    }

    // Functions
    // Adds a new menu to the menu list.
    public void add(String name) {
        MenuCell newMenu = new MenuCell(name);

        if (listSize != 0) {
            // listSize > 1.
            if (listSize != 1) {
                lastMenu.setNextMenu(newMenu);
                newMenu.setPreviousMenu(lastMenu);
            }
            // listSize = 1.
            else {
                firstMenu.setNextMenu(newMenu);
                newMenu.setPreviousMenu(firstMenu);
            }
        }
        // listSize = 0.
        else {
            firstMenu = newMenu;
        }

        lastMenu = newMenu;
        // List size increases.
        listSize++;

        // Menu list is saved.
        save();
    }

    // Prints all the menus of the list.
    public void print() {
        MenuCell buffer = firstMenu;

        // Prints menus.
        for (int counter = 1; counter <= listSize; counter++) {
            System.out.println("[" + counter + "] " + buffer.getName());
            buffer = buffer.getNextMenu();
        }
    }

    // Returns a menu depending on the given id.
    public MenuCell searchMenu(int menuId) {
        MenuCell buffer = firstMenu;

        // Finds the menu.
        for (int counter = 1; counter < menuId; counter++) {
            buffer = buffer.getNextMenu();
        }

        // Returns the menu.
        return buffer;
    }

    // Checks if a menu of the menu list already has a given name.
    public boolean checkIfMenuExists(String name) {
        MenuCell buffer = firstMenu;
        boolean exists = false;

        // Checks all the menus.
        for (int counter = 0; counter < listSize; counter++) {
            // A menu has the given name.
            if (buffer.getName().equals(name)) {
                // Error message.
                System.out.println("\n[!] A menu already has that name. Please try again.");
                exists = true;
                break;
            }

            buffer = buffer.getNextMenu();
        }

        return exists;
    }

    // Returns the size of the menu list.
    public int getListSize() {
        return listSize;
    }

    // Updates the name of a menu.
    public void update(int menuId, String name) {
        MenuCell buffer = firstMenu;

        // Finds the menu.
        for (int counter = 1; counter < menuId; counter++) {
            buffer = buffer.getNextMenu();
        }

        // Menu's name is changed.
        buffer.setName(name);
        // Menu's dish list gets notified of the change.
        buffer.getDishList().setMenuName(name);

        // Menu list is saved.
        save();
    }

    // Deletes a menu from the menu list.
    public void delete(int menuId) {
        if (listSize != 1) {
            // listSize > 2.
            if (listSize != 2) {
                if (menuId != 1) {
                    // A menu between the first and the last one is deleted.
                    if (menuId != listSize) {
                        MenuCell buffer = firstMenu;

                        for (int counter = 1; counter < menuId; counter++) {
                            buffer = buffer.getNextMenu();
                        }

                        buffer.getPreviousMenu().setNextMenu(buffer.getNextMenu());
                        buffer.getNextMenu().setPreviousMenu(buffer.getPreviousMenu());
                    }
                    // Last menu is deleted.
                    else {
                        lastMenu = lastMenu.getPreviousMenu();
                        lastMenu.setNextMenu(null);
                    }
                }
                // First menu is deleted.
                else {
                    firstMenu = firstMenu.getNextMenu();
                    firstMenu.setPreviousMenu(null);
                }
            }
            // listSize = 2.
            else {
                // Last menu is deleted.
                if (menuId != 1) {
                    lastMenu = firstMenu;
                    firstMenu.setNextMenu(null);
                }
                // First menu is deleted.
                else {
                    firstMenu = lastMenu;
                    lastMenu.setPreviousMenu(null);
                }
            }
        }
        // listSize = 1.
        else {
            firstMenu = null;
            lastMenu = null;
        }

        // List size decreases.
        listSize--;

        // Menu list is saved.
        save();
    }

    // Saves the menu and dish list to a text file.
    public void save() {
        // Text file paths.
        String menuFileName = "menus.txt";
        String dishFileName = "dishes.txt";

        MenuCell buffer = firstMenu;

        try {
            // Menu and dish text files are refreshed.
            BufferedWriter menuFileWriter = new BufferedWriter(new FileWriter(menuFileName));
            BufferedWriter dishFileWriter = new BufferedWriter(new FileWriter(dishFileName));

            // Menu and dish text files are written to.
            for (int counter = 0; counter < listSize; counter++) {
                // Menu is written to the menu text file.
                menuFileWriter.write(buffer.getName());
                menuFileWriter.newLine();

                // Menu's dishes are written to the dish text file.
                dishFileWriter.write(buffer.getDishList().getDishes());

                buffer = buffer.getNextMenu();
            }

            // Writers are closed.
            menuFileWriter.close();
            dishFileWriter.close();
        } catch (IOException error) {
            error.printStackTrace();
        }
    }

    // Reads the menus stored on the menu text file.
    public void read() {
        // Text file path.
        String fileName = "menus.txt";
        String data;

        try {
            // Menu text file is loaded.
            BufferedReader fileReader = new BufferedReader(new FileReader(fileName));

            // Menu text file's data is read.
            while ((data = fileReader.readLine()) != null) {
                load(data);
            }

            // Reader is closed.
            fileReader.close();
        } catch (IOException error) {
            error.printStackTrace();
        }
    }

    // Loads the menus stored on the menu text file.
    private void load(String name) {
        MenuCell newMenu = new MenuCell(name);

        if (listSize != 0) {
            // listSize > 1.
            if (listSize != 1) {
                lastMenu.setNextMenu(newMenu);
                newMenu.setPreviousMenu(lastMenu);
            }
            // listSize = 1.
            else {
                firstMenu.setNextMenu(newMenu);
                newMenu.setPreviousMenu(firstMenu);
            }
        }
        // listSize = 0.
        else {
            firstMenu = newMenu;
        }

        lastMenu = newMenu;
        // List size increases.
        listSize++;
    }
}
