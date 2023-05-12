package UI;

import Lists.DishList;
import Lists.MenuList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MenuUIManager {

    // Properties
    MenuList menuList = new MenuList();
    boolean goBack = false;
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    // Functions
    // Runs the menu list's user interface, while the user doesn't want to go back.
    public void start() {
        try {
            // While the user doesn't want to go back.
            while (!goBack) {
                // Runs the menu list's user interface.
                showUI();
            }
        } catch (IOException error) {
            error.printStackTrace();
        }
    }

    // Shows the menu list's user interface.
    private void showUI() throws IOException {
        int action = 0;
        boolean error;

        do {
            error = false;
            
            try {
                // Menu list is presented.
                System.out.println("\nList of menus:");
                menuList.print();

                // Menu list is empty.
                if (menuList.getListSize() == 0) {
                    System.out.println("\nThe menu list is empty.");
                }

                // Action menu is presented.
                System.out.print("""
                                        
                        Choose one of the following actions:
                        [1] View a menu
                        [2] Add a menu
                        [3] Modify a menu
                        [4] Delete a menu
                        [5] Go back
                                        
                        >>\s""");
                // User's action is obtained.
                action = Integer.parseInt(reader.readLine());

                // Checks if the given id is valid.
                if (action < 1 || action > 5) {
                    error = true;
                    // Error message.
                    System.out.println("\n[!] Please enter a valid action. Please try again.");
                }
                // Checks if the selected action is valid when the menu list is empty.
                else if (menuList.getListSize() == 0) {
                    if (action == 1 || action == 3 || action == 4) {
                        error = true;
                        // Error message.
                        switch (action) {
                            // View action.
                            case 1 -> System.out.println("\n[!] There are no menus to view.");

                            // Modify action.
                            case 3 -> System.out.println("\n[!] There are no menus to modify.");

                            // Delete action.
                            case 4 -> System.out.println("\n[!] There are no menus to delete.");
                        }
                    }
                }
            } catch (NumberFormatException exception) {
                error = true;
                // Error message.
                System.out.println("\n[!] Please enter a valid action. Please try again.");
            }
        } while (error);

        // User's action is selected.
        switch (action) {
            // View action.
            case 1 -> viewMenu();

            // Add action.
            case 2 -> addMenu();

            // Modify action.
            case 3 -> modifyMenu();

            // Delete action.
            case 4 -> deleteMenu();

            // Go back action.
            case 5 -> goBack();
        }
    }

    // Runs the dish list's user interface of the selected menu.
    private void viewMenu() throws IOException {
        int menuId = 0;
        DishList menuDishList;
        boolean error;

        do {
            error = false;

            try {
                // Gets the id of the menu the user wants to view.
                System.out.print("""
                                        
                        Enter the id of the menu you want to view.
                        >>\s""");
                menuId = Integer.parseInt(reader.readLine());

                // Checks if the given id is valid.
                if (menuId < 1 || menuId > menuList.getListSize()) {
                    error = true;
                    // Error message.
                    System.out.println("\n[!] A menu with the given id wasn't found. Please try again.");
                }
            } catch (NumberFormatException exception) {
                error = true;
                // Error message.
                System.out.println("\n[!] Please enter a valid menu id. Please try again.");
            }
        } while (error);

        // Gets the dishes of the menu the user wants to view.
        menuDishList = menuList.searchMenu(menuId).getDishList();

        // Goes to dish list's user interface.
        DishUIManager dishUIManager = new DishUIManager(menuDishList);
        dishUIManager.start();

        // Refreshes the menu and dish lists.
        menuList.save();
    }

    // Adds a new menu to the menu list.
    private void addMenu() throws IOException {
        String name;
        boolean error;

        do {
            error = false;
            // Gets the name of the new menu the user wants to add.
            System.out.print("""
                                    
                    Enter the name of the new menu.
                    >>\s""");
            name = reader.readLine();
            // Checks if the given name is available.
            if (menuList.checkIfMenuExists(name)) {
                // Name isn't available.
                error = true;
            }
        } while (error);

        // Menu is added.
        menuList.add(name);

        // Success message.
        System.out.println("\n[#] The menu was added successfully.");
    }

    // Modifies the name of a menu from the menu list.
    private void modifyMenu() throws IOException {
        int menuId = 0;
        String name;
        boolean idError;
        boolean nameError;

        // Id.
        do {
            idError = false;

            try {
                // Gets the id of the menu the user wants to modify.
                System.out.print("""
                                        
                        Enter the id of the menu you want to modify.
                        >>\s""");
                menuId = Integer.parseInt(reader.readLine());

                // Checks if the given id is valid.
                if (menuId < 1 || menuId > menuList.getListSize()) {
                    idError = true;
                    // Error message.
                    System.out.println("\n[!] A menu with the given id wasn't found. Please try again.");
                }
            } catch (NumberFormatException exception) {
                idError = true;
                // Error message.
                System.out.println("\n[!] Please enter a valid menu id. Please try again.");
            }
        } while (idError);

        // Name.
        do {
            nameError = false;
            
            // Gets the new name of the menu to modify it.
            System.out.print("""
                                    
                    Enter the new name of the menu.
                    >>\s""");
            name = reader.readLine();

            // Checks if the given name is available.
            if (menuList.checkIfMenuExists(name)) {
                // Name isn't available.
                nameError = true;
            }
        } while (nameError);

        // Menu is updated.
        menuList.update(menuId, name);

        // Success message.
        System.out.println("\n[#] The menu was updated successfully.");
    }

    // Deletes a menu from the menu list.
    private void deleteMenu() throws IOException {
        int menuId = 0;
        boolean error;

        do {
            error = false;

            try {
                // Gets the id of the menu the user wants to delete.
                System.out.print("""
                                        
                        Enter the id of the menu you want to delete.
                        >>\s""");

                menuId = Integer.parseInt(reader.readLine());

                // Checks if the given id is valid.
                if (menuId < 1 || menuId > menuList.getListSize()) {
                    error = true;
                    // Error message.
                    System.out.println("\n[!] A menu with the given id wasn't found. Please try again.");
                }
            } catch (NumberFormatException exception) {
                error = true;
                // Error message.
                System.out.println("\n[!] Please enter a valid menu id. Please try again.");
            }
        } while (error);

        // Menu is deleted.
        menuList.delete(menuId);

        // Success message.
        System.out.println("\n[#] The menu was deleted successfully.");
    }

    // Makes the user go back to the previous view.
    private void goBack() {
        goBack = true;
    }
}
