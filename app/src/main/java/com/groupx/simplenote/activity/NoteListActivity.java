package com.groupx.simplenote.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.groupx.simplenote.R;
import com.groupx.simplenote.adapter.NoteAdapter;
import com.groupx.simplenote.common.Const;
import com.groupx.simplenote.database.NoteDatabase;
import com.groupx.simplenote.entity.Account;
import com.groupx.simplenote.entity.Note;
import com.groupx.simplenote.listener.NoteListener;

import java.util.ArrayList;
import java.util.List;

public class NoteListActivity extends AppCompatActivity {
    private final int REQUEST_CODE_UPDATE_NOTE = 2;

    private RecyclerView rcvNoteList;
    private NoteAdapter adapter;
    private Account currentUser = new Account();

    private List<Note> noteList = new ArrayList<>();

    private int noteClickedPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_list);
        SharedPreferences sharedPreferences = getSharedPreferences(LoginActivity.ACCOUNT_ID, 0);
        int accId = sharedPreferences.getInt("accountId", 0);
        currentUser.setId(accId);

        rcvNoteList = findViewById(R.id.recyclerviewNote);
        rcvNoteList.setLayoutManager(new StaggeredGridLayoutManager(2,  StaggeredGridLayoutManager.VERTICAL));
        adapter = new NoteAdapter(noteList, this);
        rcvNoteList.setAdapter(adapter);

        getNotes(Const.NoteRequestCode.REQUEST_CODE_SHOW);
    }

    private int noteClikedPosition;

    private void getNotes(int requestCode){
        List<Note> notes = NoteDatabase.getSNoteDatabase(getApplicationContext())
                .noteDao().getAllMyNote(currentUser.getId(),new int[] {Const.NoteStatus.NORMAL, Const.NoteStatus.FAVORITE});
        if(requestCode == Const.NoteRequestCode.REQUEST_CODE_SHOW){
            noteList.addAll(notes);
            adapter.notifyDataSetChanged();
        }else if(requestCode == Const.NoteRequestCode.REQUEST_CODE_CREATE){
            noteList.add(0, notes.get(0));
            adapter.notifyItemInserted(0);
            rcvNoteList.smoothScrollToPosition(0);
        }else if(requestCode == Const.NoteRequestCode.REQUEST_CODE_UPDATE){
            noteList.remove(noteClikedPosition);
            noteList.add(noteClikedPosition, notes.get(noteClikedPosition));
            adapter.notifyItemChanged(noteClikedPosition);
        }
        else if(requestCode == Const.NoteRequestCode.REQUEST_CODE_DELETE){
            noteList.remove(noteClikedPosition);
            adapter.notifyItemRemoved(noteClikedPosition);
        }
    }
}