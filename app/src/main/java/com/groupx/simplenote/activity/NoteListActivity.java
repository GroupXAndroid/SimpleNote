package com.groupx.simplenote.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.groupx.simplenote.R;
import com.groupx.simplenote.adapter.NoteAdapter;
import com.groupx.simplenote.common.Const;
import com.groupx.simplenote.dao.AccountDao;
import com.groupx.simplenote.database.NoteDatabase;
import com.groupx.simplenote.entity.Account;
import com.groupx.simplenote.entity.Note;
import com.groupx.simplenote.listener.NoteListener;

import java.util.ArrayList;
import java.util.List;

public class NoteListActivity extends AppCompatActivity {
    private final int REQUEST_CODE_UPDATE_NOTE = 2;

    private RecyclerView notesRecyclerView;
    private NoteAdapter noteAdapter;

    private List<Note> noteList;

    private int noteClickedPosition = -1;

    private AccountDao accountDao;
    private Account currentAccount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_list);
        SharedPreferences sharedPreferences = getSharedPreferences(LoginActivity.PREFS_NAME, 0);
        String us = sharedPreferences.getString("username", "");
        accountDao = NoteDatabase.getSNoteDatabase(getApplicationContext()).accountDao();
        this.currentAccount = accountDao.getAccountByEmail(us);
        notesRecyclerView = findViewById(R.id.recyclerviewNote);
        notesRecyclerView.setLayoutManager(
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        );

        noteList = new ArrayList<>();
//        noteAdapter = new NoteAdapter(noteList, this);
//        notesRecyclerView.setAdapter(noteAdapter);

//        getNotes();
    }

//    @Override
//    public void onNoteClicked(Note note, int position) {
//        noteClickedPosition = position;
//        if (note.getStatusKey() == Const.NoteStatus.ARCHIVE && !currentAccount.getSetting("lock_key").isEmpty()){
//            LayoutInflater inflater = getLayoutInflater();
//            View alertLayout = inflater.inflate(R.layout.layout_check_lock, null);
//
//
//            AlertDialog.Builder alert = new AlertDialog.Builder(this);
//            alert.setTitle("Action needed: enter password");
//            alert.setView(alertLayout);
//            alert.setCancelable(false);
//            alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//
//                }
//            });
//
//            alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    EditText check = alertLayout.findViewById(R.id.check_password);
//                    if (check.getText().toString().equals(currentAccount.getSetting("lock_key"))){
//                        Intent intent = new Intent(getApplicationContext(), CreateNoteActivity.class);
//                        intent.putExtra("note", note);
//                        intent.putExtra("mode", Const.NoteDetailActivityMode.EDIT);
//                        startActivityForResult(intent, REQUEST_CODE_UPDATE_NOTE);
//                    } else {
//                        Toast.makeText(getApplicationContext(), "Wrong lock password", Toast.LENGTH_SHORT).show();
//                    }
//                }
//            });
//            AlertDialog dialog = alert.create();
//            dialog.show();
//            return;
//        }
//        Intent intent = new Intent(getApplicationContext(), CreateNoteActivity.class);
//        intent.putExtra("note", note);
//        intent.putExtra("mode", Const.NoteDetailActivityMode.EDIT);
//        startActivityForResult(intent, REQUEST_CODE_UPDATE_NOTE);
//    }
//    @Override
//    public void onNoteClicked(Note note, int position) {
//        noteClickedPosition = position;
//        Intent intent = new Intent(getApplicationContext(), CreateNoteActivity.class);
//        intent.putExtra("note", note);
//        intent.putExtra("mode", Const.NoteDetailActivityMode.EDIT);
//        startActivityForResult(intent, REQUEST_CODE_UPDATE_NOTE);
//    }

//    private void getNotes() {
//        String txtSearch = getIntent().getStringExtra("txtSearch");
//        List<Note> notes = new ArrayList<>();
//        if(txtSearch == null || txtSearch == "" || txtSearch.isEmpty()){
//            notes = NoteDatabase.getSNoteDatabase(getApplicationContext())
//                    .noteDao().getAllMyNote();
//        }else{
//            notes = NoteDatabase.getSNoteDatabase(getApplicationContext())
//                    .noteDao().searchNote(txtSearch);
//            if(notes == null || notes.size() == 0){
//                Toast.makeText(this, "NO NOTES FOUND", Toast.LENGTH_SHORT).show();
//            }
//        }
//        if (noteList.size() == 0) {
//            noteList.addAll(notes);
//            noteAdapter.notifyDataSetChanged();
//        } else {
//            noteList.add(0, notes.get(0));
//            noteAdapter.notifyItemInserted(0);
//
//        }
//        notesRecyclerView.smoothScrollToPosition(0);
//    }
}