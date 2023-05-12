package UI;

import Lists.DishList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class DishUIManager {

    // Properties
    DishList dishList;
    boolean goBack = false;
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    // Init
    public DishUIManager(DishList dishList) {
        this.dishList = dishList;
    }

    // Functions
    // Runs the dish list's user interface, while the user doesn't want to go back.
    public void start() {
        try {
            // While the user doesn't want to go back.
            while (!goBack) {
                // Runs the dish list's user interface.
                showUI();
            }
        } catch (IOException error) {
            error.printStackTrace();
        }
    }

    // Shows the dish list's user interface.
    private void showUI() throws IOException {
        int action = 0;
        boolean error;

        do {
            error = false;

            try {
                // Dish list is presented.
                System.out.println("\nList of dishes of " + dishList.getMenuName() +":");
                dishList.print();

                // Dish list is empty.
                if (dishList.getListSize() == 0) {
                    System.out.println("\nThe dish list is empty.");
                }

                // Action menu is presented.
                System.out.print("""
                                        
                        Choose one of the following actions:
                        [1] Add a dish
                        [2] Modify a dish
                        [3] Delete a dish
                        [4] Go back
                                        
                        >>\s""");
                // User's action is obtained.
                action = Integer.parseInt(reader.readLine());

                // Checks if the given id is valid.
                if (action < 1 || action > 4) {
                    error = true;
                    // Error message.
                    System.out.println("\n[!] Please enter a valid action. Please try again.");
                }
                // Checks if the selected action is valid when the dish list is empty.
                else if (dishList.getListSize() == 0) {
                    if (action == 2 || action == 3) {
                        error = true;
                        // Error message.
                        switch (action) {
                            // Modify action.
                            case 2 -> System.out.println("\n[!] There are no dishes to modify.");

                            // Delete action.
                            case 3 -> System.out.println("\n[!] There are no dishes to delete.");
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
            // Add action.
            case 1 -> addDish();

            // Modify action.
            case 2 -> modifyDish();

            // Delete action.
            case 3 -> deleteDish();

            // Go back action.
            case 4 -> goBack();
        }
    }

    // Adds a new dish to the dish list.
    private void addDish() throws IOException {
        String name;
        double price = 0;
        boolean nameError;
        boolean priceError;

        // Name.
        do {
            nameError = false;

            // Gets the name of the new dish the user wants to add.
            System.out.print("""
                                    
                    Enter the name of the new dish.
                    >>\s""");
            name = reader.readLine();

            // Checks if the given name is available.
            if (dishList.checkIfMenuExists(name)) {
                // Name isn't available.
                nameError = true;
            }
        } while (nameError);

        // Price.
        do {
            priceError = false;

            try {
                // Gets the price of the new dish the user wants to add.
                System.out.print("""
                                        
                        Enter the price of the new dish.
                        >>\s""");
                price = Double.parseDouble(reader.readLine());
            } catch (NumberFormatException exception) {
                priceError = true;
                // Error message.
                System.out.println("\n[!] Please enter a valid price. Please try again.");
            }
        } while (priceError);

        // Dish is added.
        dishList.add(name, price);

        // Success message.
        System.out.println("\n[#] The dish was added successfully.");
    }

    // Modifies the name and price of a dish from the dish list.
    private void modifyDish() throws IOException {
        int dishId = 0;
        String name;
        double price = 0;
        boolean idError;
        boolean nameError;
        boolean priceError;

        // Id.
        do {
            idError = false;

            try {
                // Gets the id of the dish the user wants to modify.
                System.out.print("""
                                        
                        Enter the id of the dish you want to modify.
                        >>\s""");
                dishId = Integer.parseInt(reader.readLine());

                // Checks if the given id is valid.
                if (dishId < 1 || dishId > dishList.getListSize()) {
                    idError = true;
                    // Error message.
                    System.out.println("\n[!] A dish with the given id wasn't found. Please try again.");
                }
            } catch (NumberFormatException exception) {
                idError = true;
                // Error message.
                System.out.println("\n[!] Please enter a valid dish id. Please try again.");
            }
        } while (idError);

        // Name.
        do {
            nameError = false;

            // Gets the new name of the dish to modify it.
            System.out.print("""
                                    
                    Enter the new name of the dish.
                    >>\s""");
            name = reader.readLine();

            // Checks if the given name is available.
            if (dishList.checkIfMenuExists(name)) {
                // Name isn't available.
                nameError = true;
            }
        } while (nameError);

        // Price.
        do {
            priceError = false;

            try {
                // Gets the new price of the dish to modify it.
                System.out.print("""
                                        
                        Enter the new price of the dish.
                        >>\s""");
                price = Double.parseDouble(reader.readLine());
            } catch (NumberFormatException exception) {
                priceError = true;
                // Error message.
                System.out.println("\n[!] Please enter a valid price. Please try again.");
            }
        } while (priceError);

        // Dish is updated.
        dishList.update(dishId, name, price);

        // Success message.
        System.out.println("\n[#] The dish was updated successfully.");
    }

    // Deletes a dish from the dish list.
    private void deleteDish() throws IOException {
        int dishId = 0;
        boolean error;

        do {
            error = false;

            try {
                // Gets the id of the dish the user wants to delete.
                System.out.print("""
                                        
                        Enter the id of the dish you want to delete.
                        >>\s""");
                dishId = Integer.parseInt(reader.readLine());

                // Checks if the given id is valid.
                if (dishId < 1 || dishId > dishList.getListSize()) {
                    error = true;
                    // Error message.
                    System.out.println("\n[!] A dish with the given id wasn't found. Please try again.");
                }
            } catch (NumberFormatException exception) {
                error = true;
                // Error message.
                System.out.println("\n[!] Please enter a valid dish id. Please try again.");
            }
        } while (error);

        // Dish is deleted.
        dishList.delete(dishId);

        // Success message.
        System.out.println("\n[#] The dish was deleted successfully.");
    }

    // Makes the user go back to the previous view.
    private void goBack() {
        goBack = true;
    }
}
