package com.groupx.simplenote.activity;
import com.groupx.simplenote.R;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

public class NoteDetail extends AppCompatActivity {

    private ImageView imageNoteDetailBack, imageNoteDetailSave;
    private EditText editTextNoteSubtitle, editTextNoteTitle, editTextNoteContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_detail);

        imageNoteDetailBack = findViewById(R.id.imageNoteDetailBack);
        imageNoteDetailSave = findViewById(R.id.imageNoteDetailSave);

        imageNoteDetailBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }


    private void saveNote(){
        editTextNoteSubtitle = findViewById(R.id.editTextNoteTitle);
        editTextNoteSubtitle = findViewById(R.id.editTextNoteSubtitle);
        editTextNoteContent = findViewById(R.id.editTextNoteContent);

    }


}