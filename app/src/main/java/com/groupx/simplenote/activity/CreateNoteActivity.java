package com.groupx.simplenote.activity;

import com.groupx.simplenote.R;
import com.groupx.simplenote.common.Component;
import com.groupx.simplenote.common.Utils;
import com.groupx.simplenote.database.NoteDatabase;
import com.groupx.simplenote.entity.Note;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CreateNoteActivity extends AppCompatActivity {

    private ImageView imageNoteDetailBack, imageNoteDetailSave, imageNoteDetailColorOptionLens;
    private EditText editTextNoteSubtitle, editTextNoteTitle, editTextNoteContent;
    private TextView textViewNoteDetailDatetime;
    private LinearLayout layoutChoosingColor;
    private ConstraintLayout layoutNoteDetail;

    private String selectedNoteColor;
    private Date noteSince;

    private List<Note> noteList = new ArrayList<>();
    private Note alreadyNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_detail);

        imageNoteDetailBack = findViewById(R.id.imageNoteDetailBack);
        imageNoteDetailSave = findViewById(R.id.imageNoteDetailSave);
        imageNoteDetailColorOptionLens = findViewById(R.id.imageViewColorOptionLens);

        editTextNoteTitle = findViewById(R.id.editTextNoteTitle);
        editTextNoteSubtitle = findViewById(R.id.editTextNoteSubtitle);
        editTextNoteContent = findViewById(R.id.editTextNoteContent);

        textViewNoteDetailDatetime = findViewById(R.id.textViewNoteDetailDatetime);

        layoutChoosingColor = findViewById(R.id.layoutNoteChooseColorOption);
        layoutNoteDetail = findViewById(R.id.layoutNoteDetail);


        selectedNoteColor = Utils.ColorIntToString(getColor(R.color.noteColorDefault));

        Date currentTimer = new Date();
        noteSince = currentTimer;
        StringBuilder dateBuilder = new StringBuilder("Edited ");
        dateBuilder.append(Utils.DateTimeToString(currentTimer));
        textViewNoteDetailDatetime.setText(dateBuilder);
        imageNoteDetailBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();

            }
        });

        imageNoteDetailSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveNote();

                if (getIntent().getBooleanExtra("isViewOrUpdate", false)) {
                    updateNote();
                }
                Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_SHORT).show();
            }
        });

        layoutNoteDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (layoutChoosingColor.getVisibility() == View.VISIBLE) {
                    layoutChoosingColor.setVisibility(View.GONE);
                    final Drawable drawable = new ColorDrawable();
                    layoutNoteDetail.setForeground(drawable);
                }
            }
        });

        if (getIntent().getBooleanExtra("isViewOrUpdate", false)) {
            alreadyNote = (Note) getIntent().getSerializableExtra("note");
            setViewOrUpdateNote();
        }

        initChooseColorOption();
    }

    private void initChooseColorOption() {
        imageNoteDetailColorOptionLens.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layoutChoosingColor.setVisibility(View.VISIBLE);
                final Drawable drawable = new ColorDrawable(0x81000000);
                layoutNoteDetail.setForeground(drawable);
            }
        });

        layoutNoteDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (layoutChoosingColor.getVisibility() == View.VISIBLE) {
                    closeOptionMenu();

                }
            }
        });
    }

    private void closeOptionMenu() {
        layoutChoosingColor.setVisibility(View.GONE);
        final Drawable drawable = new ColorDrawable();
        layoutNoteDetail.setForeground(drawable);
    }

    private void saveNote() {
        String title = editTextNoteTitle.getText().toString().trim();
        String subtitle = editTextNoteSubtitle.getText().toString().trim();
        String content = editTextNoteContent.getText().toString();

        final Note note = new Note();
        note.setTitle(title);
        note.setNote(content);
        note.setSubTitle(subtitle);
        note.setColor(selectedNoteColor);
        note.setSince(new Date());
        note.setLastUpdate(new Date());

        NoteDatabase.getSNoteDatabase(getApplicationContext())
                .noteDao().insert(note);

        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
    }

    private void updateNote() {
        String title = editTextNoteTitle.getText().toString().trim();
        String subtitle = editTextNoteSubtitle.getText().toString().trim();
        String content = editTextNoteContent.getText().toString();

        if (alreadyNote == null) {
            alreadyNote = new Note();
            alreadyNote.setSince(new Date());
        }
        alreadyNote.setTitle(title);
        alreadyNote.setNote(content);
        alreadyNote.setSubTitle(subtitle);
        alreadyNote.setColor(selectedNoteColor);
        alreadyNote.setLastUpdate(new Date());

        NoteDatabase.getSNoteDatabase(getApplicationContext())
                .noteDao().update(alreadyNote);

        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
    }

    private void setBackGroundNoteColor(int color) {
        layoutNoteDetail.setBackgroundColor(color);

        // Convert color from int to string hex
        selectedNoteColor = Utils.ColorIntToString(color);
    }

    @SuppressLint("NonConstantResourceId")
    public void onClickColor(View view) {
        int color = new Component().getColorFromColorChooser(view, getApplicationContext());
        setBackGroundNoteColor(color);
    }

    private void setViewOrUpdateNote() {
        editTextNoteTitle.setText(alreadyNote.getSubTitle());
        editTextNoteSubtitle.setText(alreadyNote.getSubTitle());
        editTextNoteContent.setText(alreadyNote.getNote());
        StringBuilder dateBuilder = new StringBuilder("Edited ");
        dateBuilder.append(Utils.DateTimeToString(alreadyNote.getLastUpdate()));
        textViewNoteDetailDatetime.setText(dateBuilder);
    }

    @Override
    public void onBackPressed() {
        if (layoutChoosingColor.getVisibility() == View.VISIBLE)
            closeOptionMenu();
        else {
            super.onBackPressed();
        }
    }
}