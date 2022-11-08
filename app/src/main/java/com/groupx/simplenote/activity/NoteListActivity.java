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

    private RecyclerView rcvNoteList;
    private NoteAdapter adapter;
    private Account currentUser = new Account();

    private List<Note> noteList = new ArrayList<>();

    private int noteClickedPosition = -1;

    private AccountDao accountDao;
    private Account currentAccount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_list);
        SharedPreferences sharedPreferences = getSharedPreferences(LoginActivity.ACCOUNT_ID, 0);
        int accId = sharedPreferences.getInt("accountId", 0);
        currentUser.setId(accId);

        rcvNoteList = findViewById(R.id.recyclerviewNote);
        rcvNoteList.setLayoutManager(new StaggeredGridLayoutManager(2,  StaggeredGridLayoutManager.VERTICAL));
        adapter = new NoteAdapter(noteList, this, currentAccount, accountDao);
        rcvNoteList.setAdapter(adapter);

        getNotes(Const.NoteRequestCode.REQUEST_CODE_SHOW);
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