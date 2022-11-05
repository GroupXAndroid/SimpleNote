package com.groupx.simplenote.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.groupx.simplenote.R;
import com.groupx.simplenote.common.Const;
import com.groupx.simplenote.database.NoteDatabase;
import com.groupx.simplenote.entity.Account;
import com.groupx.simplenote.entity.Note;
import com.groupx.simplenote.entity.NoteAccount;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        runtestFeature();
    }

    private void runtestFeature() {

        // Open noteDetail Activity
        Button buttonTestNote = findViewById(R.id.buttonTestNoteDetail);
        buttonTestNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CreateNoteActivity.class);
                startActivity(intent);
            }
        });

        // Open noteList Testing Activity
        Button buttonTestNoteList = findViewById(R.id.buttonTestNoteListView);
        buttonTestNoteList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), NoteListActivity.class);
                startActivity(intent);
            }
        });

        // Open manage folder activity
        Button buttonTestManageFolder = findViewById(R.id.buttonTestManageFolder);
        buttonTestManageFolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), FolderActivity.class);
                startActivity(intent);
            }
        });

        // Open feedback activity
        Button buttonTestFeedback = findViewById(R.id.buttonTestFeedback);
        buttonTestFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), FeedbackActivity.class);
                startActivity(intent);
            }
        });

        // Open setting activity
        Button buttonSwm = findViewById(R.id.buttonTestSwm);
        buttonSwm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ShareWithMeActivity.class);
                startActivity(intent);
            }
        });

        // Open setting activity
        Button buttonSetting = findViewById(R.id.btnSetting);
        buttonSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SettingActivity.class);
                startActivity(intent);
            }
        });

        // Open calendar activity
        Button btnCalendar = findViewById(R.id.btnCalendar);
        btnCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CalendarActivity.class);
                startActivity(intent);
            }
        });

//        InsertSampleDate();
    }

    private void InsertSampleDate() {
        Account account = new Account();
        account.setFullName("KHuy");
        account.setUsername("Khuymail.com");

        NoteDatabase.getSNoteDatabase(getApplicationContext()).accountDao().insert(account);

        Note note = new Note();
        note.setTitle("Note with account");
//        note.setSubTitle("Note subtitle");
//        note.setColor("#123456");
        note.setLastUpdate(new Date());
        NoteDatabase.getSNoteDatabase(getApplicationContext()).noteDao().insert(note);
        note = NoteDatabase.getSNoteDatabase(getApplicationContext()).noteDao().getNewestNote();

        NoteAccount noteAccount = new NoteAccount();
        noteAccount.setNoteId(note.getId());
        noteAccount.setAccountId(1);
        noteAccount.setPermission(Const.StatusPermission.VIEW.toString());
        NoteDatabase.getSNoteDatabase(getApplicationContext()).noteDao().insertWithNoteAccount(noteAccount);
    }
}