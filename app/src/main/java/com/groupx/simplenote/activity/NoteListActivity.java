package com.groupx.simplenote.activity;

import android.content.Intent;
import android.os.Bundle;

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
        if (noteList.size() == 0) {
            noteList.addAll(NoteDatabase.getSNoteDatabase(getApplicationContext())
                    .noteDao().getAllMyNote());
            noteAdapter.notifyDataSetChanged();
        } else {
            noteList.add(0, NoteDatabase.getSNoteDatabase(getApplicationContext())
                    .noteDao().getAllMyNote().get(0));
            noteAdapter.notifyItemInserted(0);

        }
        notesRecyclerView.smoothScrollToPosition(0);
    }
}