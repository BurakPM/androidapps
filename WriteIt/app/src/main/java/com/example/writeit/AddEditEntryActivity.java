package com.example.writeit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;

public class AddEditEntryActivity extends AppCompatActivity {
    public static final String EXTRA_ID =
            "com.example.todolist.EXTRA_ID";


    public static final String EXTRA_TITLE =
            "com.example.todolist.EXTRA_TITLE";

    public static final String EXTRA_CONTENT =
            "com.example.todolist.EXTRA_CONTENT";


    private EditText editTextTitle;
    private EditText editTextContent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_entry);

        editTextTitle = findViewById(R.id.edit_text_title);
        editTextContent = findViewById(R.id.edit_text_content);


        Intent intent = getIntent();

        if (intent.hasExtra(EXTRA_ID)) {
            String titleEdit = getResources().getString(R.string.edit_note);
            setTitle(titleEdit);

            editTextTitle.setText(intent.getStringExtra(EXTRA_TITLE));
            editTextContent.setText((intent.getStringExtra(EXTRA_CONTENT)));
        } else {
            String titleAdd = getResources().getString(R.string.add_entry);
            setTitle(titleAdd);
        }

    }

    private void saveEntry() {
        String title = editTextTitle.getText().toString();
        String content = editTextContent.getText().toString();

        if (title.trim().isEmpty() || content.trim().isEmpty()) {

            Snackbar.make(findViewById(R.id.add_entry_layout),R.string.empty_fail, Snackbar.LENGTH_LONG)
                    .show();
            return;
        }


        Intent data = new Intent();
        data.putExtra(EXTRA_TITLE, title);
        data.putExtra(EXTRA_CONTENT, content);

        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        if (id != -1) {
            data.putExtra(EXTRA_ID, id);
        }

        setResult(RESULT_OK, data);
        finish();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_entry_menu, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_entry:
                saveEntry();
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}