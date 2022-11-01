package com.groupx.simplenote.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.groupx.simplenote.R;
import com.groupx.simplenote.common.Component;
import com.groupx.simplenote.common.Const;
import com.groupx.simplenote.common.Utils;
import com.groupx.simplenote.database.NoteDatabase;
import com.groupx.simplenote.entity.Account;
import com.groupx.simplenote.entity.Note;
import com.groupx.simplenote.entity.NoteAccount;
import com.groupx.simplenote.fragment.ChoosingNoteColorFragment;
import com.groupx.simplenote.fragment.NoteDetailOptionFragment;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CreateNoteActivity extends AppCompatActivity {

    private ImageView imageNoteDetailBack, imageNoteDetailSave, imageNoteDetailColorOptionLens,
    imageNoteDetailOption;
    private EditText editTextNoteSubtitle, editTextNoteTitle, editTextNoteContent;
    private TextView textViewNoteDetailDatetime;
    private LinearLayout layoutChoosingColor;
    private ConstraintLayout layoutNoteDetail;

    private String selectedNoteColor;
    private Date noteSince;

    private final List<Note> noteList = new ArrayList<>();
    private Note alreadyNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_detail);

        imageNoteDetailBack = findViewById(R.id.imageNoteDetailBack);
        imageNoteDetailSave = findViewById(R.id.imageNoteDetailSave);
        imageNoteDetailColorOptionLens = findViewById(R.id.imageViewColorOptionLens);
        imageNoteDetailOption = findViewById(R.id.imageNoteDetailOption);

        editTextNoteTitle = findViewById(R.id.editTextNoteTitle);
        editTextNoteSubtitle = findViewById(R.id.editTextNoteSubtitle);
        editTextNoteContent = findViewById(R.id.editTextNoteContent);

        textViewNoteDetailDatetime = findViewById(R.id.textViewNoteDetailDatetime);
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

        if (getIntent().getBooleanExtra("isViewOrUpdate", false)) {
            alreadyNote = (Note) getIntent().getSerializableExtra("note");
            setViewOrUpdateNote();
        }

        initChooseColorOption();
        initOption();
    }


    private void initChooseColorOption() {
        ChoosingNoteColorFragment colorFragment = new ChoosingNoteColorFragment();
        Bundle args = new Bundle();
        colorFragment.setArguments(args);

        imageNoteDetailColorOptionLens.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                args.putString("selectedColor", selectedNoteColor);
                colorFragment.show(getSupportFragmentManager(), "colorFragment");
            }
        });
    }

    private void initOption(){
        NoteDetailOptionFragment optionFragment = new NoteDetailOptionFragment(this);

        imageNoteDetailOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                optionFragment.show(getSupportFragmentManager(), "optionFragment");
            }
        });
    }

    private Note saveNote() {
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
        Note currentNote =  NoteDatabase.getSNoteDatabase(getApplicationContext())
                .noteDao().getNewestNote();
        alreadyNote = currentNote;
        NoteAccount noteAccount = new NoteAccount();
        noteAccount.setNoteId(currentNote.getId());
        noteAccount.setAccountId(1);
        noteAccount.setPermission(Const.StatusPermission.CREATED.toString());

        NoteDatabase.getSNoteDatabase(getApplicationContext())
                .noteDao().insertWithNoteAccount(noteAccount);

//        Intent intent = new Intent();
//        setResult(RESULT_OK, intent);
//        finish();

        return currentNote;
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

    public void onClickColor(View view) {
        int color = new Component().getColorFromColorChooser(view, getApplicationContext());
        setBackGroundNoteColor(color);
    }

    private void setViewOrUpdateNote() {
        editTextNoteTitle.setText(alreadyNote.getSubTitle());
        editTextNoteSubtitle.setText(alreadyNote.getSubTitle());
        editTextNoteContent.setText(alreadyNote.getNote());
        selectedNoteColor = alreadyNote.getColor();
        setBackGroundNoteColor(Color.parseColor(selectedNoteColor));

        StringBuilder dateBuilder = new StringBuilder("Edited ");
        dateBuilder.append(Utils.DateTimeToString(alreadyNote.getLastUpdate()));
        textViewNoteDetailDatetime.setText(dateBuilder);
    }

    public void deleteNote(){
        if(alreadyNote != null && getIntent().getBooleanExtra("isViewOrUpdate", false)) {
            NoteDatabase.getSNoteDatabase(getApplicationContext())
                    .noteDao().deleteNote(alreadyNote);
        }
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
    }

    public void shareNote(int accountId, String permisson){

        NoteAccount noteAccount = new NoteAccount();
        noteAccount.setAccountId(accountId);
        noteAccount.setPermission(permisson);
        if(alreadyNote != null ) {
            noteAccount.setNoteId(alreadyNote.getId());
        }

        NoteDatabase.getSNoteDatabase(getApplicationContext())
                .noteDao().insertWithNoteAccount(noteAccount);
    }
}