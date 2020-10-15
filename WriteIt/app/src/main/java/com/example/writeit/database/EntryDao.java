package com.example.writeit.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.writeit.Entry;

import java.util.List;

@Dao
public interface EntryDao {

    @Query("SELECT * FROM entry_table")
    LiveData<List<Entry>> getEntries();


    @Insert
    void insert(Entry entry);

    @Update
    void update(Entry edited);

    @Delete
    void delete(Entry entry);

    @Query("DELETE FROM entry_table")
    void deleteAll();
}
