package com.example.writeit;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.writeit.database.EntryDao;
import com.example.writeit.database.EntryRoomDatabase;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class EntryRepository {
    private EntryDao mEntryDao;
    private LiveData<List<Entry>> mAllEntries;
    private Executor mExecutor = Executors.newSingleThreadExecutor();

    EntryRepository(Application application) {
        EntryRoomDatabase db = EntryRoomDatabase.getDatabase(application);
        mEntryDao = db.entryDao();
        mAllEntries = mEntryDao.getEntries();
    }

    LiveData<List<Entry>> getEntries() {
        return mAllEntries;
    }

    void addEntry(final Entry entry) {
        mExecutor.execute(() -> {
            try {
                mEntryDao.insert(entry);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    void updateEntry(final Entry entry) {
        mExecutor.execute(() -> {
            try {
                mEntryDao.update(entry);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

    }

    void deleteEntry(final Entry entry) {
        mExecutor.execute(() -> {
            try {
                mEntryDao.delete(entry);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    void deleteAll() {
        mExecutor.execute(() -> {
            try {
                mEntryDao.deleteAll();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

    }
}
