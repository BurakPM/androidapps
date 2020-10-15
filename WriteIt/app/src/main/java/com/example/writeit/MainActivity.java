package com.example.writeit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;

import androidx.recyclerview.widget.RecyclerView;


import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import com.google.android.material.snackbar.Snackbar;


public class MainActivity extends AppCompatActivity {
    public static final int ADD_NOTE_REQUEST = 1;
    public static final int EDIT_NOTE_REQUEST = 2;

    private EntryListViewModel mEntryListViewModel;
    private RecyclerView mEntryRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton fab = findViewById(R.id.add_entry_button);
        fab.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddEditEntryActivity.class);
            startActivityForResult(intent, ADD_NOTE_REQUEST);
        });


        mEntryListViewModel = new ViewModelProvider(this).get(EntryListViewModel.class);
        final EntryListAdapter mEntryListAdapter = new EntryListAdapter();
        mEntryRecyclerView = findViewById(R.id.recyclerview);
        mEntryRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mEntryRecyclerView.setAdapter(new EntryListAdapter());

        mEntryListViewModel.getEntries().observe(this, entries -> {
            mEntryListAdapter.submitList(entries);
            mEntryRecyclerView.setAdapter(mEntryListAdapter);
        });

        mEntryListAdapter.setOnItemClickListener(entry -> {
            Intent intent = new Intent(MainActivity.this, AddEditEntryActivity.class);

            intent.putExtra(AddEditEntryActivity.EXTRA_ID, entry.getEntry_id());
            intent.putExtra(AddEditEntryActivity.EXTRA_TITLE, entry.getTitle());
            intent.putExtra(AddEditEntryActivity.EXTRA_CONTENT, entry.getEntry());

            startActivityForResult(intent, EDIT_NOTE_REQUEST);


        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                mEntryListViewModel.deleteEntry(mEntryListAdapter.getEntryAt(viewHolder.getAdapterPosition()));
                Snackbar.make(findViewById(R.id.activity_parent_layout), R.string.delete_success, Snackbar.LENGTH_LONG)
                        .setAnchorView(R.id.add_entry_button)
                        .show();

            }
        }).attachToRecyclerView(mEntryRecyclerView);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_NOTE_REQUEST && resultCode == RESULT_OK) {
            String title = data.getStringExtra(AddEditEntryActivity.EXTRA_TITLE);
            String content = data.getStringExtra(AddEditEntryActivity.EXTRA_CONTENT);

            Entry entry = new Entry(content, title);
            mEntryListViewModel.addEntry(entry);

            Snackbar.make(findViewById(R.id.activity_parent_layout), R.string.save_success, Snackbar.LENGTH_LONG)
                    .setAnchorView(R.id.add_entry_button)
                    .show();
        } else if (requestCode == EDIT_NOTE_REQUEST && resultCode == RESULT_OK) {
            int id = data.getIntExtra(AddEditEntryActivity.EXTRA_ID, -1);

            if (id == -1) {
                Snackbar.make(findViewById(R.id.activity_parent_layout), R.string.update_fail, Snackbar.LENGTH_LONG)
                        .setAnchorView(R.id.add_entry_button)
                        .show();
                return;
            }

            String title = data.getStringExtra(AddEditEntryActivity.EXTRA_TITLE);
            String content = data.getStringExtra(AddEditEntryActivity.EXTRA_CONTENT);


            Entry entry = new Entry(content, title);
            entry.setEntry_id(id);

            mEntryListViewModel.updateEntry(entry);

            Snackbar.make(findViewById(R.id.activity_parent_layout), R.string.update_success, Snackbar.LENGTH_LONG)
                    .setAnchorView(R.id.add_entry_button)
                    .show();

        } else {
            Snackbar.make(findViewById(R.id.activity_parent_layout), R.string.not_saved, Snackbar.LENGTH_LONG)
                    .setAnchorView(R.id.add_entry_button)
                    .show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_all_notes:
                mEntryListViewModel.deleteAllEntry();
                Snackbar.make(findViewById(R.id.activity_parent_layout), R.string.delete_all_success, Snackbar.LENGTH_LONG)
                        .setAnchorView(R.id.add_entry_button)
                        .show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }


    }
}