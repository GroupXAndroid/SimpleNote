package com.groupx.simplenote.activity;

import com.groupx.simplenote.R;
import com.groupx.simplenote.database.NoteDatabase;
import com.groupx.simplenote.entity.Note;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class NoteDetail extends AppCompatActivity {

    private ImageView imageNoteDetailBack, imageNoteDetailSave;
    private EditText editTextNoteSubtitle, editTextNoteTitle, editTextNoteContent;
    private TextView textViewNoteDetailDatetime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_detail);

        imageNoteDetailBack = findViewById(R.id.imageNoteDetailBack);
        imageNoteDetailSave = findViewById(R.id.imageNoteDetailSave);

        editTextNoteSubtitle = findViewById(R.id.editTextNoteTitle);
        editTextNoteSubtitle = findViewById(R.id.editTextNoteSubtitle);
        editTextNoteContent = findViewById(R.id.editTextNoteContent);

        textViewNoteDetailDatetime = findViewById(R.id.textViewNoteDetailDatetime);

        Date currentTimer = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM HH:mm", Locale.getDefault());
        StringBuilder dateBuilder = new StringBuilder("Edited ");
        dateBuilder.append(formatter.format(currentTimer));
        textViewNoteDetailDatetime.setText(dateBuilder);
        imageNoteDetailBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }


    private void saveNote() {
        String title = editTextNoteTitle.getText().toString().trim();
        String subtitle = editTextNoteSubtitle.getText().toString().trim();
        String content = editTextNoteContent.getText().toString();

        Note note = new Note();
        note.setTitle(title);
        note.setNote(content);
        note.setSubTitle(subtitle);

        class SaveNoteTask extends AsyncTask<Void, Void, Void> {
            @Override
            protected void onPostExecute(Void unused) {
                super.onPostExecute(unused);
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
            }

            @Override
            protected Void doInBackground(Void... voids) {
                NoteDatabase.getSNoteDatabase(getApplicationContext())
                        .noteDao();
                return null;
            }
        }

        new SaveNoteTask().execute();
    }


}