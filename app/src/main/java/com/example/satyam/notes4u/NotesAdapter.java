package com.example.satyam.notes4u;

/**
 * Created by Satyam on 25-Oct-17.
 */

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import com.example.satyam.notes4u.EditNoteActivity;
import com.example.satyam.notes4u.R;
//import com.example.satyam.notes4u.database.models.Note;

/**
 * Created by Aseem on 20-07-2016.
 */
public class NotesAdapter extends ArrayAdapter<Note> {

    private List<Note> noteList;
    private Context context;

    public NotesAdapter(Context context, List<Note> noteList) {
        super(context, R.layout.list_notes, noteList);
        this.noteList = noteList;
        this.context = context;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (v == null) {
            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.list_notes, null);
        }

        final Note note = noteList.get(position);

        if (note != null) {
            TextView title = (TextView) v.findViewById(R.id.title);
            TextView description = (TextView) v.findViewById(R.id.description);
            TextView index = (TextView) v.findViewById(R.id.index);
            if (title != null) {
                title.setText(note.getTitle());
                index.setText((position + 1) + ".");
            }
            if (description != null) {
                description.setText(note.getDescription());
            }
        }

        //Click Listener
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(context, EditNoteActivity.class);
                Bundle bundle = new Bundle();

                Note note = noteList.get(position);

                bundle.putString("source", "editPress");
                bundle.putString("noteTitle", note.getTitle());
                bundle.putString("noteDescription", note.getDescription());
                bundle.putInt("noteId", note.getId());

                myIntent.putExtras(bundle);

                context.startActivity(myIntent);
            }
        });

        return v;
    }
}
