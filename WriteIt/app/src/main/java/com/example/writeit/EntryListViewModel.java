package com.example.writeit;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;


import java.util.List;

public class EntryListViewModel extends AndroidViewModel {
    private EntryRepository mEntryRepository;
    private LiveData<List<Entry>> mEntries;

    public EntryListViewModel(Application application) {
        super(application);
        mEntryRepository = new EntryRepository(application);
        mEntries = mEntryRepository.getEntries();

    }

    LiveData<List<Entry>> getEntries() {
        return mEntries;
    }

    public void addEntry(Entry entry) {
        mEntryRepository.addEntry(entry);
    }

    public void deleteEntry(Entry entry) {
        mEntryRepository.deleteEntry(entry);
    }

    public void updateEntry(Entry entry) {
        mEntryRepository.updateEntry(entry);
    }

    public void deleteAllEntry() { mEntryRepository.deleteAll();}
}
