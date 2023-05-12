package Lists.Cells;

import Lists.EntryList;

public class RecordCell {

    // Properties
    private String date;
    private EntryList entryList;
    private RecordCell nextRecord = null;
    private RecordCell previousRecord = null;

    // Init
    public RecordCell(String date) {
        this.date = date;
        this.entryList = new EntryList(date);
    }

    // Getters
    public String getDate() {
        return date;
    }

    public EntryList getEntryList() {
        return entryList;
    }

    public RecordCell getNextRecord() {
        return nextRecord;
    }

    public RecordCell getPreviousRecord() {
        return previousRecord;
    }

    // Setters
    public void setDate(String date) {
        this.date = date;
    }

    public void setNextRecord(RecordCell nextRecord) {
        this.nextRecord = nextRecord;
    }

    public void setPreviousRecord(RecordCell previousRecord) {
        this.previousRecord = previousRecord;
    }
}
