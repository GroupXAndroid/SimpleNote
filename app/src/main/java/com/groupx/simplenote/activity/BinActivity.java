package com.groupx.simplenote.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.groupx.simplenote.R;
import com.groupx.simplenote.adapter.NoteArchiveAdapter;
import com.groupx.simplenote.adapter.NoteBinAdapter;
import com.groupx.simplenote.common.Const;
import com.groupx.simplenote.database.NoteDatabase;
import com.groupx.simplenote.entity.Note;
import com.groupx.simplenote.listener.NoteListener;

import java.util.ArrayList;
import java.util.List;

public class BinActivity extends AppCompatActivity implements NoteListener {

    private final int REQUEST_CODE_UPDATE_NOTE = 2;

    private RecyclerView notesRecyclerView;
    private NoteBinAdapter noteAdapter;
    ImageView imageBack;

    private List<Note> noteList;

    private int noteClickedPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bin);

        notesRecyclerView = findViewById(R.id.recyclerviewNote);
        imageBack = findViewById(R.id.imageBinBack);

        notesRecyclerView.setLayoutManager(
                new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        );

        noteList = new ArrayList<>();
        noteAdapter = new NoteBinAdapter(noteList, this);
        notesRecyclerView.setAdapter(noteAdapter);

        getNotes();

        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
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
        notes = NoteDatabase.getSNoteDatabase(getApplicationContext())
                .noteDao().getNoteByStatus(Const.NoteStatus.BIN);
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