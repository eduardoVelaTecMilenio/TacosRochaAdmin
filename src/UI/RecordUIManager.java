package UI;

import Lists.Cells.DishCell;
import Lists.DishList;
import Lists.EntryList;
import Lists.MenuList;
import Lists.RecordList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class RecordUIManager {

    // Properties
    RecordList recordList = new RecordList();
    MenuList menuList = new MenuList();
    boolean goBack = false;
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    // Functions
    // Runs the record list's user interface, while the user doesn't want to go back.
    public void start() {
        try {
            // While the user doesn't want to go back.
            while (!goBack) {
                // Runs the record list's user interface.
                showUI();
            }
        } catch (IOException error) {
            error.printStackTrace();
        }
    }

    // Shows the record list's user interface.
    private void showUI() throws IOException {
        int action = 0;
        boolean error;

        do {
            error = false;

            try {
                // Record list is presented.
                System.out.println("\nList of records:");
                recordList.print();

                // Record list is empty.
                if (recordList.getListSize() == 0) {
                    System.out.println("\nThe record list is empty.");
                }

                // Action menu is presented.
                System.out.print("""
                                        
                        Choose one of the following actions:
                        [1] View a record
                        [2] Add a record
                        [3] Delete a record
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
                // Checks if the selected action is valid when the record list is empty.
                else if (recordList.getListSize() == 0) {
                    if (action == 1 || action == 3) {
                        error = true;
                        // Error message.
                        switch (action) {
                            // View action.
                            case 1 -> System.out.println("\n[!] There are no records to view.");

                            // Delete action.
                            case 3 -> System.out.println("\n[!] There are no records to delete.");
                        }
                    }
                }
                else if (action == 2 && menuList.getListSize() == 0) {
                    error = true;
                    // Error message.
                    System.out.println("\n[!] There are no menus available to create a new record.");
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
            case 1 -> viewRecord();

            // Add action.
            case 2 -> addRecord();

            // Delete action.
            case 3 -> deleteRecord();

            // Go back action.
            case 4 -> goBack();
        }
    }

    // Runs the entry list's user interface of the selected record.
    private void viewRecord() throws IOException {
        String date;
        EntryList recordEntryList;
        int recordId;
        boolean error;

        do {
            error = false;

            // Gets the date of the record the user wants to view.
            System.out.print("""
                                    
                    Enter the date of the record you want to view.
                    >>\s""");
            date = reader.readLine();

            // Record's id is obtained.
            recordId = recordList.getRecordId(date);

            // Checks if the given id is valid.
            if (recordId == -1) {
                error = true;
                // Error message.
                System.out.println("\n[!] A record with the given date wasn't found. Please try again.");
            }
        } while (error);

        // Gets the entries of the record the user wants to view.
        recordEntryList = recordList.searchRecord(recordId).getEntryList();

        // Goes to entry view.
        EntryUIManager entryUIManager = new EntryUIManager(recordEntryList);
        entryUIManager.start();

        // Refreshes the record and entry lists.
        recordList.save();
    }

    // Adds a new record to the record list.
    private void addRecord() throws IOException {
        String date;
        int menuId = 0;
        DishList menuDishList = null;
        DishCell menuDish;
        EntryList recordEntryList;
        boolean dateError;
        boolean menuError;
        boolean quantityError;

        // Date.
        do {
            dateError = false;

            // Gets the date of the new record the user wants to add.
            System.out.print("""
                                    
                    Enter the date of the new record.
                    >>\s""");
            date = reader.readLine();

            // Checks if the given name is available.
            if (recordList.getRecordId(date) != -1) {
                dateError = true;
                // Error message.
                System.out.println("\n[!] A record already has that date. Please try again.");
            }
        } while (dateError);

        // Record is added.
        recordList.add(date);

        // Gets the entry list of the new record.
        recordEntryList = recordList.searchRecord(recordList.getRecordId(date)).getEntryList();

        // Menu list is presented.
        System.out.println("\nList of menus:");
        menuList.print();

        // Menu.
        do {
            menuError = false;

            try {
                // Gets the id of the menu the user wants to use.
                System.out.print("""
                                        
                        Enter the id of the menu you want to use.
                        >>\s""");
                menuId = Integer.parseInt(reader.readLine());

                // Checks if the given id is valid.
                if (menuId < 1 || menuId > menuList.getListSize()) {
                    menuError = true;
                    // Error message.
                    System.out.println("\n[!] A menu with the given id wasn't found. Please try again.");
                }

                // Gets the dishes of the menu the user wants to view.
                menuDishList = menuList.searchMenu(menuId).getDishList();

                // Checks if the selected menu isn't empty.
                if (menuDishList.getListSize() == 0) {
                    menuError = true;
                    // Error message.
                    System.out.println("\n[!] The menu you choose is empty. Please try again.");
                }
            } catch (NumberFormatException exception) {
                menuError = true;
                // Error message.
                System.out.println("\n[!] Please enter a valid menu id. Please try again.");
            }
        } while (menuError);

        // Gets all the entries of the record.
        for (int counter = 1; counter <= menuDishList.getListSize(); counter++) {
            int quantity = 0;
            menuDish = menuDishList.getDish(counter);

            // Quantity.
            do {
                quantityError = false;

                try {
                    // Gets the quantity of an entry.
                    System.out.print("\nEnter the quantity of \"" + menuDish.getName() + " (Price: $" + menuDish.getPrice() + ")\" that were sold.\n>> ");
                    quantity = Integer.parseInt(reader.readLine());
                } catch (NumberFormatException exception) {
                    quantityError = true;
                    // Error message.
                    System.out.println("\n[!] Please enter a valid quantity. Please try again.");
                }
            } while (quantityError);

            // Entry is added.
            recordEntryList.add(menuDish.getName(), menuDish.getPrice(), quantity);
        }

        // Success message.
        System.out.println("\n[#] The record and its entries were added successfully.");
    }

    // Deletes a record from the record list.
    private void deleteRecord() throws IOException {
        int recordId;
        String date;
        boolean error;

        do {
            error = false;

            // Gets the date of the record the user wants to delete.
            System.out.print("""
                                    
                    Enter the date of the record you want to delete.
                    >>\s""");
            date = reader.readLine();

            // Record's id is obtained.
            recordId = recordList.getRecordId(date);

            // Checks if the given id is valid.
            if (recordId == -1) {
                error = true;
                // Error message.
                System.out.println("\n[!] A record with the given date wasn't found. Please try again.");
            }
        } while (error);

        // Record is deleted.
        recordList.delete(recordId);

        // Success message.
        System.out.println("\n[#] The record was deleted successfully.");
    }

    // Makes the user go back to the previous view.
    private void goBack() {
        goBack = true;
    }
}
