package com.groupx.simplenote.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.groupx.simplenote.R;
import com.groupx.simplenote.database.NoteDatabase;
import com.groupx.simplenote.entity.Note;

import java.util.List;

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
                Intent intent = new Intent(getApplicationContext(), NoteDetail.class);
                startActivity(intent);
            }
        });
    }
}