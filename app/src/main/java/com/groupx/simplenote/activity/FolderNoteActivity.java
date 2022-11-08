package com.groupx.simplenote.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.groupx.simplenote.R;
import com.groupx.simplenote.adapter.FolderNoteAdapter;
import com.groupx.simplenote.common.Const;
import com.groupx.simplenote.database.NoteDatabase;
import com.groupx.simplenote.entity.Folder;
import com.groupx.simplenote.entity.Note;

import java.util.ArrayList;
import java.util.List;

public class FolderNoteActivity extends AppCompatActivity {

    public Folder currentFolder;
    private TextView textFoldertNoteTitle;
    private RecyclerView recyclerviewFolderNote;
    private List<Note> noteList = new ArrayList<>();
    private FolderNoteAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_folder_note);

        currentFolder = (Folder) getIntent().getSerializableExtra("folder");

        textFoldertNoteTitle = findViewById(R.id.textFoldertNoteTitle);
        textFoldertNoteTitle.setText(currentFolder.getFolderName());

        // Set up recyclerView
        recyclerviewFolderNote = findViewById(R.id.recyclerviewFolderNote);
        recyclerviewFolderNote.setLayoutManager
                (new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        adapter = new FolderNoteAdapter(noteList, this);
        recyclerviewFolderNote.setAdapter(adapter);
        getNotes(Const.NoteRequestCode.REQUEST_CODE_SHOW);

        findViewById(R.id.imageviewCreateNewNoteInFolder).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CreateNoteActivity.class);
                intent.putExtra("folder", currentFolder);
                intent.putExtra("mode", Const.NoteDetailActivityMode.CREATE);
                startActivityForResult(intent, Const.NoteRequestCode.REQUEST_CODE_CREATE);
            }
        });
    }

    private int noteClikedPosition;

    private void getNotes(int requestCode){
        List<Note> notes = NoteDatabase.getSNoteDatabase(getApplicationContext())
                .noteDao().getNotesByFolder(currentFolder.getId(), new int[] {Const.NoteStatus.BIN});
        if(requestCode == Const.NoteRequestCode.REQUEST_CODE_SHOW){
            noteList.addAll(notes);
            adapter.notifyDataSetChanged();
        }else if(requestCode == Const.NoteRequestCode.REQUEST_CODE_CREATE){
            noteList.add(0, notes.get(0));
            adapter.notifyItemInserted(0);
            recyclerviewFolderNote.smoothScrollToPosition(0);
        }else if(requestCode == Const.NoteRequestCode.REQUEST_CODE_UPDATE){
            noteList.remove(noteClikedPosition);
            noteList.add(noteClikedPosition, notes.get(noteClikedPosition));
            adapter.notifyItemChanged(noteClikedPosition);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK) {

            getNotes(requestCode);
        }
    }
}