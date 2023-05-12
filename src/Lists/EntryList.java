package Lists;

import Lists.Cells.EntryCell;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class EntryList {

    // Properties
    private String recordDate;
    private EntryCell firstEntry = null;
    private EntryCell lastEntry = null;
    private int listSize = 0;

    // Init
    public EntryList(String recordDate) {
        this.recordDate = recordDate;
        read();
    }

    // Functions
    // Adds a new entry to the entry list.
    public void add(String name, double price, int quantity) {
        EntryCell newEntry = new EntryCell(name, price, quantity);

        if (listSize != 0) {
            // listSize > 1.
            if (listSize != 1) {
                lastEntry.setNextEntry(newEntry);
                newEntry.setPreviousEntry(lastEntry);
            }
            // listSize = 1.
            else {
                firstEntry.setNextEntry(newEntry);
                newEntry.setPreviousEntry(firstEntry);
            }
        }
        // listSize = 0.
        else {
            firstEntry = newEntry;
        }

        lastEntry = newEntry;
        // List size increases.
        listSize++;
    }

    // Prints all the entries of the list.
    public void print() {
        EntryCell buffer = firstEntry;
        double totalProfit = 0;
        int totalQuantity = 0;

        // Prints entries.
        for (int counter = 1; counter <= listSize; counter++) {
            System.out.println("[" + counter + "] " + buffer.getName() + " (Price: $" + buffer.getPrice() + ", Quantity: " + buffer.getQuantity() + ", Profit: $"+ buffer.getPrice()*buffer.getQuantity() +")");
            totalProfit += buffer.getPrice()*buffer.getQuantity();
            totalQuantity += buffer.getQuantity();
            buffer = buffer.getNextEntry();
        }

        // Prints the total profit gained and the total amount of dishes sold.
        System.out.println("\nTotal profit gained: $" + totalProfit + "\nTotal dishes sold: " + totalQuantity);
    }

    // Updates the quantity of an entry.
    public void update(int dishId, int quantity) {
        EntryCell buffer = firstEntry;

        for (int counter = 1; counter < dishId; counter++) {
            buffer = buffer.getNextEntry();
        }

        // Entry's quantity is updated.
        buffer.setQuantity(quantity);
    }

    // Returns the entries of the list in the format that is used to save them.
    public String getEntries() {
        String entries = "";
        EntryCell buffer = firstEntry;

        // Entries are obtained.
        for (int counter = 0; counter < listSize; counter++) {
            entries = entries.concat(recordDate + ";" + buffer.getName() + ";" + buffer.getPrice() + ";" + buffer.getQuantity() + "\n");

            buffer = buffer.getNextEntry();
        }

        // Entries are returned.
        return entries;
    }

    // Returns the size of the dish list.
    public int getListSize() {
        return listSize;
    }

    // Returns the date of the record the entry list belongs to.
    public String getRecordDate() {
        return recordDate;
    }

    // Reads the entries stored on the entry text file.
    public void read() {
        // Text file path.
        String fileName = "entries.txt";
        String data;

        int stage;
        char buffer;
        String recordDate;
        String name;
        String price;
        String quantity;

        try {
            // Entry file is loaded.
            BufferedReader fileReader = new BufferedReader(new FileReader(fileName));

            while ((data = fileReader.readLine()) != null) {
                stage = 1;
                recordDate = "";
                name = "";
                price = "";
                quantity = "";

                // Entry text file's data is read.
                for (int counter = 0; counter < data.length(); counter++) {
                    buffer = data.charAt(counter);

                    // Record's date is obtained.
                    if (stage == 1) {
                        if (buffer != ';') {
                            recordDate = recordDate.concat(String.valueOf(buffer));
                        }
                        else {
                            stage++;
                        }
                    }
                    // Entry's name is obtained.
                    else if(stage == 2) {
                        if (buffer != ';') {
                            name = name.concat(String.valueOf(buffer));
                        }
                        else {
                            stage++;
                        }
                    }
                    // Entry's price is obtained.
                    else if (stage == 3){
                        if (buffer != ';') {
                            price = price.concat(String.valueOf(buffer));
                        }
                        else {
                            stage++;
                        }
                    }
                    // Entry's quantity is obtained.
                    else {
                        quantity = quantity.concat(String.valueOf(buffer));
                    }
                }

                // Checks if the loaded entry belongs to the record.
                if (recordDate.equals(this.recordDate)) {
                    add(name, Double.parseDouble(price), Integer.parseInt(quantity));
                }
            }

            // Reader is closed.
            fileReader.close();
        } catch (IOException error) {
            error.printStackTrace();
        }
    }
}
