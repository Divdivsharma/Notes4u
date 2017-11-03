package com.example.satyam.notes4u;

/**
 * Created by Satyam on 25-Oct-17.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aseem on 20-07-2016.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    public DatabaseHandler(Context context) {
        super(context, DatabaseValues.DATABASE_NAME, null, DatabaseValues.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DatabaseValues.TABLE_NOTES_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DatabaseValues.TABLE_NOTES_DROP);
        onCreate(db);
    }

    public void addNote(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues noteValues = new ContentValues();
        noteValues.put(DatabaseValues.NOTES_TITLE, note.getTitle());
        noteValues.put(DatabaseValues.NOTES_DESCRIPTION, note.getDescription());

        db.insert(DatabaseValues.TABLE_NOTES, null, noteValues);
        db.close();
    }

    public void updateNote(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues noteValues = new ContentValues();
        noteValues.put(DatabaseValues.NOTES_TITLE, note.getTitle());
        noteValues.put(DatabaseValues.NOTES_DESCRIPTION, note.getDescription());

        db.update(DatabaseValues.TABLE_NOTES, noteValues, DatabaseValues.NOTES_ID + " = ?",
                new String[]{String.valueOf(note.getId())});
        db.close();
    }

    public void deleteNote(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String deleteQuery = "DELETE FROM " + DatabaseValues.TABLE_NOTES + " WHERE " + DatabaseValues.NOTES_ID + " = '" + id + "'";

        db.execSQL(deleteQuery);
        db.close();
    }

    public List<Note> getAllNotes() {
        List<Note> notes = new ArrayList<Note>();

        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + DatabaseValues.TABLE_NOTES;
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {

                Note note = new Note();
                note.setId(Integer.parseInt(cursor.getString(0)));
                note.setTitle(cursor.getString(1));
                note.setDescription(cursor.getString(2));

                notes.add(note);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return notes;
    }
}
