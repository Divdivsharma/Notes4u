package com.example.satyam.notes4u;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import java.util.List;

//mport notes.internshala.com.isnotes.database.DatabaseHandler;
//import notes.internshala.com.isnotes.database.models.Note;

/**
 * Created by Aseem on 21-07-2016.
 */
public class EditNoteActivity extends AppCompatActivity implements View.OnClickListener {

    EditText titleEditTextView, descriptionEditTextView;
    String title, description;
    int noteId;
    Boolean isUpdateMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        isUpdateMode = false;

        titleEditTextView = (EditText) this.findViewById(R.id.titleEditText);
        descriptionEditTextView = (EditText) this.findViewById(R.id.descriptionEditText);


        Button saveButton = (Button) findViewById(R.id.saveButton);
        saveButton.setOnClickListener(this);

        Button deleteButton = (Button) findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(this);

        Bundle bundle = getIntent().getExtras();

        if (bundle != null && bundle.containsKey("source")) {
            if (bundle.getString("source").equalsIgnoreCase("editPress")) {
                isUpdateMode = true;
                noteId = bundle.getInt("noteId");
                titleEditTextView.setText(bundle.getString("noteTitle"));
                descriptionEditTextView.setText(bundle.getString("noteDescription"));
                deleteButton.setVisibility(View.VISIBLE);
            } else if (bundle.getString("source").equalsIgnoreCase("addPress")) {
                isUpdateMode = false;
                deleteButton.setVisibility(View.GONE);
            } else {
                Toast.makeText(this, "Invalid Arguments", Toast.LENGTH_LONG).show();
                super.onBackPressed();
            }
        }

    }

    private void saveNote() {
        title = titleEditTextView.getText().toString();
        description = descriptionEditTextView.getText().toString();

        if (!isValidNote()) {
            return;
        }

        Note note = new Note();
        note.setTitle(title);
        note.setDescription(description);

        DatabaseHandler databaseHandler = new DatabaseHandler(this);
        if (!isUpdateMode) {
            databaseHandler.addNote(note);
            Toast.makeText(this, "Note Added Successfully", Toast.LENGTH_LONG).show();
        } else {
            note.setId(noteId);
            databaseHandler.updateNote(note);
            Toast.makeText(this, "Note Updated Successfully", Toast.LENGTH_LONG).show();
        }

        List<Note> notes = databaseHandler.getAllNotes();
        MainActivity.notesAdapter.clear();
        MainActivity.notesAdapter.addAll(notes);
        MainActivity.notesAdapter.notifyDataSetChanged();

        super.onBackPressed();
    }

    private void deleteNote() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Delete Note")
                .setMessage("Are you sure you want to delete this note?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DatabaseHandler databaseHandler = new DatabaseHandler(EditNoteActivity.this);

                        databaseHandler.deleteNote(String.valueOf(noteId));

                        List<Note> notes = databaseHandler.getAllNotes();
                        MainActivity.notesAdapter.clear();
                        MainActivity.notesAdapter.addAll(notes);
                        MainActivity.notesAdapter.notifyDataSetChanged();

                        EditNoteActivity.this.onBackPressed();
                    }

                })
                .setNegativeButton("No", null)
                .show();
    }

    private Boolean isValidNote() {
        if (title.isEmpty() && description.isEmpty()) {
            Toast.makeText(this, "Please Enter Title and Description", Toast.LENGTH_SHORT).show();
            return false;
        } else if (title.isEmpty()) {
            Toast.makeText(this, "Please Enter Title", Toast.LENGTH_SHORT).show();
            return false;
        } else if (description.isEmpty()) {
            Toast.makeText(this, "Please Enter Description", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.saveButton:
                saveNote();
                break;

            case R.id.deleteButton:
                deleteNote();
                break;
        }
    }
}
