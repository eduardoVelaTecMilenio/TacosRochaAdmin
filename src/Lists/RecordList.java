package Lists;

import Lists.Cells.RecordCell;

import java.io.*;

public class RecordList {

    // Properties
    private RecordCell firstRecord = null;
    private RecordCell lastRecord = null;
    private int listSize = 0;

    public RecordList() {
        read();
    }

    // Functions
    // Adds a new record to the record list.
    public void add(String date) {
        RecordCell newRecord = new RecordCell(date);

        if (listSize != 0) {
            // listSize > 1.
            if (listSize != 1) {
                lastRecord.setNextRecord(newRecord);
                newRecord.setPreviousRecord(lastRecord);
            }
            // listSize = 1.
            else {
                firstRecord.setNextRecord(newRecord);
                newRecord.setPreviousRecord(firstRecord);
            }
        }
        // listSize = 0.
        else {
            firstRecord = newRecord;
        }

        lastRecord = newRecord;
        listSize++;

        // Record list is saved.
        save();
    }

    // Prints all the records of the list.
    public void print() {
        RecordCell buffer = firstRecord;

        // Prints records
        for (int counter = 1; counter <= listSize; counter++) {
            System.out.println("[" + counter + "] " + buffer.getDate());
            buffer = buffer.getNextRecord();
        }
    }

    // Returns a record depending on the given id.
    public RecordCell searchRecord(int recordId) {
        RecordCell buffer = firstRecord;

        // Finds the record.
        for (int counter = 0; counter < recordId-1; counter++) {
            buffer = buffer.getNextRecord();
        }

        // Returns the record.
        return buffer;
    }

    // Returns the id of a record depending on the given date.
    public int getRecordId(String date) {
        RecordCell buffer = firstRecord;

        // Finds the record.
        for (int counter = 1; counter <= listSize; counter++) {
            // Returns the id of the record.
            if (buffer.getDate().equals(date)) {
                return counter;
            }

            buffer = buffer.getNextRecord();
        }

        // Returns -1 if the record with the given id wasn't found.
        return -1;
    }

    // Returns the size of the record list.
    public int getListSize() {
        return listSize;
    }

    // Deletes a record from the record list.
    public void delete(int menuId) {
        if (listSize != 1) {
            // listSize > 2.
            if (listSize != 2) {
                if (menuId != 1) {
                    // A record between the first and the last one is deleted.
                    if (menuId != listSize) {
                        RecordCell buffer = firstRecord;

                        for (int counter = 1; counter < menuId; counter++) {
                            buffer = buffer.getNextRecord();
                        }

                        buffer.getPreviousRecord().setNextRecord(buffer.getNextRecord());
                        buffer.getNextRecord().setPreviousRecord(buffer.getPreviousRecord());
                    }
                    // Last record is deleted.
                    else {
                        lastRecord = lastRecord.getPreviousRecord();
                        lastRecord.setNextRecord(null);
                    }
                }
                // First record is deleted.
                else {
                    firstRecord = firstRecord.getNextRecord();
                    firstRecord.setPreviousRecord(null);
                }
            }
            // listSize = 2.
            else {
                // Last record is deleted.
                if (menuId != 1) {
                    lastRecord = firstRecord;
                    firstRecord.setNextRecord(null);
                }
                // First record is deleted.
                else {
                    firstRecord = lastRecord;
                    lastRecord.setPreviousRecord(null);
                }
            }
        }
        // listSize = 1.
        else {
            firstRecord = null;
            lastRecord = null;
        }

        listSize--;

        // Record list is saved.
        save();
    }

    // Saves the record and entry list to a text file.
    public void save() {
        // File paths
        String recordFileName = "records.txt";
        String entryFileName = "entries.txt";

        RecordCell buffer = firstRecord;

        try {
            // Record and entry text files are refreshed
            BufferedWriter recordFileWriter = new BufferedWriter(new FileWriter(recordFileName));
            BufferedWriter entryFileWriter = new BufferedWriter(new FileWriter(entryFileName));

            // Record and entry text files are written to
            for (int counter = 0; counter < listSize; counter++) {
                // Record is written to the record text file.
                recordFileWriter.write(buffer.getDate());
                recordFileWriter.newLine();

                // Record's entries are written to the entry text file.
                entryFileWriter.write(buffer.getEntryList().getEntries());

                buffer = buffer.getNextRecord();
            }

            // Writers are closed.
            recordFileWriter.close();
            entryFileWriter.close();
        } catch (IOException error) {
            error.printStackTrace();
        }
    }

    // Reads the records stored on the record text file.
    public void read() {
        // Text file path.
        String fileName = "records.txt";
        String data;

        try {
            // Menu text file is loaded
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

    // Loads the records stored on the record text file.
    public void load(String date) {
        RecordCell newRecord = new RecordCell(date);

        if (listSize != 0) {
            // listSize > 1.
            if (listSize != 1) {
                lastRecord.setNextRecord(newRecord);
                newRecord.setPreviousRecord(lastRecord);
            }
            // listSize = 1.
            else {
                firstRecord.setNextRecord(newRecord);
                newRecord.setPreviousRecord(firstRecord);
            }
        }
        // listSize = 0.
        else {
            firstRecord = newRecord;
        }

        lastRecord = newRecord;
        // List size increases.
        listSize++;
    }
}
