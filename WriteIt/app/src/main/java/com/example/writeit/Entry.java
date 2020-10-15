package com.example.writeit;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity(tableName = "entry_table")
public class Entry {


    @PrimaryKey(autoGenerate = true)
    private int entry_id;

    @ColumnInfo(name = "entry_content") // column name
    private String mEntry;

    private String mTitle;

    public Entry(String entry, String title) {
        this.mEntry = entry;
        this.mTitle = title;
    }

    public String getEntry() {
        return this.mEntry;
    }

    public int getEntry_id() {
        return this.entry_id;
    }

    public void setEntry_id(int entry_id) {
        this.entry_id = entry_id;
    }

    public String getTitle() {
        return this.mTitle;
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Entry entry = (Entry) o;
        return entry_id == entry.entry_id &&
                mEntry.equals(entry.mEntry) &&
                mTitle.equals(entry.mTitle);
    }

    @Override
    public int hashCode() {
        return Objects.hash(entry_id, mEntry, mTitle);
    }

}
