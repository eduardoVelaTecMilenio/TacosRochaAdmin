package UI;

import Lists.EntryList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class EntryUIManager {

    // Properties
    EntryList entryList;
    boolean goBack = false;
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    // Init
    public EntryUIManager(EntryList entryList) {
        this.entryList = entryList;
    }

    // Functions
    // Runs the entry list's user interface, while the user doesn't want to go back.
    public void start() {
        try {
            // While the user doesn't want to go back.
            while (!goBack) {
                // Runs the entry list's user interface.
                showUI();
            }
        } catch (IOException error) {
            error.printStackTrace();
        }
    }

    // Shows the entry list's user interface.
    private void showUI() throws IOException {
        int action = 0;
        boolean error;

        do {
            error = false;

            try {
                // Entry list is presented.
                System.out.println("\nList of entries of " + entryList.getRecordDate() + ":");
                entryList.print();

                // Action menu is presented.
                System.out.print("""
                                        
                        Choose one of the following actions:
                        [1] Modify an entry
                        [2] Go back
                                        
                        >>\s""");
                // User's action is obtained.
                action = Integer.parseInt(reader.readLine());

                // Checks if the given id is valid.
                if (action < 1 || action > 2) {
                    error = true;
                    // Error message.
                    System.out.println("\n[!] Please enter a valid action. Please try again.");
                }
            } catch (NumberFormatException exception) {
                error = true;
                // Error message.
                System.out.println("\n[!] Please enter a valid action. Please try again.");
            }
        } while (error);

        // User's action is selected.
        switch (action) {
            // Modify action.
            case 1 -> modifyDish();

            // Go back action.
            case 2 -> goBack();
        }
    }

    // Modifies the quantity of an entry from the entry list.
    private void modifyDish() throws IOException {
        int entryId = 0;;
        int quantity = 0;
        boolean idError;
        boolean quantityError;

        do {
            idError = false;

            try {
                // Gets the id of the entry the user wants to modify.
                System.out.print("""
                                        
                        Enter the id of the entry you want to modify.
                        >>\s""");
                entryId = Integer.parseInt(reader.readLine());

                // Checks if the given id is valid.
                if (entryId < 1 || entryId > entryList.getListSize()) {
                    idError = true;
                    // Error message.
                    System.out.println("\n[!] An entry with the given id wasn't found. Please try again.");
                }
            } catch (NumberFormatException exception) {
                idError = true;
                // Error message.
                System.out.println("\n[!] Please enter a valid entry id. Please try again.");
            }
        } while (idError);

        do {
            quantityError = false;

            try {
                // Gets the new quantity of the entry to modify it.
                System.out.print("""
                                        
                        Enter the new quantity of the entry.
                        >>\s""");
                quantity = Integer.parseInt(reader.readLine());
            } catch (NumberFormatException exception) {
                quantityError = true;
                // Error message.
                System.out.println("\n[!] Please enter a valid quantity. Please try again.");
            }
        } while (quantityError);

        // Entry is updated.
        entryList.update(entryId, quantity);
    }

    // Makes the user go back to the previous view.
    private void goBack() {
        goBack = true;
    }
}
