package com.example.writeit.database;

import android.content.Context;


import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


import com.example.writeit.Entry;

@Database(entities = Entry.class, version = 1)
public abstract class EntryRoomDatabase extends RoomDatabase {
    public abstract EntryDao entryDao();


    private static volatile EntryRoomDatabase INSTANCE;


    // sync = only one thread at a time can access this method (for the sake of only one instance)
    public static EntryRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (EntryRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            EntryRoomDatabase.class, "entry_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }


}
