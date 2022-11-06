package com.groupx.simplenote.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.groupx.simplenote.R;
import com.groupx.simplenote.adapter.NoteAdapter;
import com.groupx.simplenote.common.Const;
import com.groupx.simplenote.database.NoteDatabase;
import com.groupx.simplenote.entity.Note;
import com.groupx.simplenote.listener.NoteListener;

import java.util.ArrayList;
import java.util.List;

public class NoteListActivity extends AppCompatActivity implements NoteListener {
    private final int REQUEST_CODE_UPDATE_NOTE = 2;

    private RecyclerView notesRecyclerView;
    private NoteAdapter noteAdapter;

    private List<Note> noteList;

    private int noteClickedPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_list);

        notesRecyclerView = findViewById(R.id.recyclerviewNote);
        notesRecyclerView.setLayoutManager(
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        );

        noteList = new ArrayList<>();
        noteAdapter = new NoteAdapter(noteList, this);
        notesRecyclerView.setAdapter(noteAdapter);

        getNotes();
    }

    @Override
    public void onNoteClicked(Note note, int position) {
        noteClickedPosition = position;
        Intent intent = new Intent(getApplicationContext(), CreateNoteActivity.class);
        intent.putExtra("note", note);
        intent.putExtra("mode", Const.NoteDetailActivityMode.EDIT);
        startActivityForResult(intent, REQUEST_CODE_UPDATE_NOTE);

    }

    private void getNotes() {
        String txtSearch = getIntent().getStringExtra("txtSearch");
        List<Note> notes = new ArrayList<>();
        if(txtSearch == null || txtSearch == "" || txtSearch.isEmpty()){
            notes = NoteDatabase.getSNoteDatabase(getApplicationContext())
                    .noteDao().getAllMyNote();
        }else{
            notes = NoteDatabase.getSNoteDatabase(getApplicationContext())
                    .noteDao().searchNote(txtSearch);
            if(notes == null || notes.size() == 0){
                Toast.makeText(this, "NO NOTES FOUND", Toast.LENGTH_SHORT).show();
            }
        }
        if (noteList.size() == 0) {
            noteList.addAll(notes);
            noteAdapter.notifyDataSetChanged();
        } else {
            noteList.add(0, notes.get(0));
            noteAdapter.notifyItemInserted(0);

        }
        notesRecyclerView.smoothScrollToPosition(0);
    }
}