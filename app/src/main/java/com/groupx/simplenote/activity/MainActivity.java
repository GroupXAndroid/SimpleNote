package com.groupx.simplenote.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.groupx.simplenote.R;
import com.groupx.simplenote.database.NoteDatabase;
import com.groupx.simplenote.database.SimpleNoteDatabase_Impl;
import com.groupx.simplenote.entity.Note;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getNotes();
    }

    private void getNotes(){
        class GetNotesTask extends AsyncTask<Void, Void, List<Note>> {
            @Override
            protected List<Note> doInBackground(Void... voids) {
                return NoteDatabase.getSNoteDatabase(getApplicationContext()).noteDao().getAllMyNote();
            }

            @Override
            protected void onPostExecute(List<Note> notes) {
                super.onPostExecute(notes);
                Log.d("Note", "a");
            }
        }

        new GetNotesTask().execute();
    }
}