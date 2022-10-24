package com.groupx.simplenote.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.groupx.simplenote.R;
import com.groupx.simplenote.entity.Note;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        runtestFeature();
    }

    private void runtestFeature(){

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
    }
}