package UI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainUIManager {

    // Properties
    boolean exit;
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    // Functions
    // Runs the main user interface, while the user doesn't want exit the program.
    public void start() {
        try {
            // While the user doesn't want to exit the program.
            while (!exit) {
                // Runs the record list's user interface.
                showUI();
            }

            // Reader is closed.
            reader.close();
        } catch (IOException error) {
            error.printStackTrace();
        }
    }

    private void showUI() throws IOException {
        int action = 0;
        boolean error;

        do {
            error = false;

            try {
                // Action menu is presented.
                System.out.print("""
                        
                        Welcome the "Tacos Rocha" Administration app!
                                        
                        Choose one of the following actions:
                        [1] Manage records
                        [2] Manage menus
                        [3] Exit
                                        
                        >>\s""");
                // User's action is obtained.
                action = Integer.parseInt(reader.readLine());

                // Checks if the given id is valid.
                if (action < 1 || action > 3) {
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
            // View records action.
            case 1 -> viewRecords();

            // View menus action.
            case 2 -> viewMenus();

            // Exit program
            case 3 -> exit();
        }
    }

    private void viewMenus() {
        MenuUIManager menuUIManager = new MenuUIManager();
        menuUIManager.start();
    }

    private void viewRecords() {
        RecordUIManager recordUIManager = new RecordUIManager();
        recordUIManager.start();
    }

    private void exit() {
        exit = true;
    }
}
