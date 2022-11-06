package com.groupx.simplenote.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.Toast;

import com.groupx.simplenote.R;
import com.groupx.simplenote.adapter.NoteAdapter;
import com.groupx.simplenote.adapter.NoteFullAdapter;
import com.groupx.simplenote.common.Const;
import com.groupx.simplenote.database.NoteDatabase;
import com.groupx.simplenote.entity.Note;
import com.groupx.simplenote.listener.NoteListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CalendarActivity extends AppCompatActivity implements NoteListener {

    private final int REQUEST_CODE_UPDATE_NOTE = 2;

    CalendarView calendarView;
    RecyclerView recyclerViewNoteList;
    List<Note> noteList;
//    private NoteAdapter noteAdapter;
    private NoteFullAdapter noteAdapter;
    ImageView imageBack, imageReminderAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        calendarView = findViewById(R.id.calendarView);
        recyclerViewNoteList = findViewById(R.id.recyclerviewNote);
        imageBack = findViewById(R.id.imageCalendarBack);
        imageReminderAdd = findViewById(R.id.imageReminderAdd);

        recyclerViewNoteList.setLayoutManager(
                new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        );

        noteList = new ArrayList<>();
        noteAdapter = new NoteFullAdapter(noteList, this);
        recyclerViewNoteList.setAdapter(noteAdapter);

        Date now = Calendar.getInstance().getTime();
        Date start = new Date(now.getYear(), now.getMonth(), now.getDate(), 0, 0);
        Date end = new Date(now.getYear(), now.getMonth(), now.getDate(), 23, 59);

        getNotes(start, end, Const.NoteStatus.NORMAL);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int date) {
                Calendar c = Calendar.getInstance();
                c.set(year, month,date);
                Date selected = c.getTime();

                Date start = new Date(selected.getYear(), selected.getMonth(), selected.getDate(), 0, 0);
                Date end = new Date(selected.getYear(), selected.getMonth(), selected.getDate(), 23, 59);
                getNotes(start, end, Const.NoteStatus.NORMAL);
            }
        });

        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        imageReminderAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CreateNoteActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onNoteClicked(Note note, int position) {
        Intent intent = new Intent(getApplicationContext(), CreateNoteActivity.class);
        intent.putExtra("note", note);
        intent.putExtra("isViewOrUpdate", true);
        startActivityForResult(intent, REQUEST_CODE_UPDATE_NOTE);
    }

    private void getNotes(Date start, Date end, int status) {
        noteList.clear();
        noteList.addAll(NoteDatabase.getSNoteDatabase(getApplicationContext())
                .noteDao().getTodayNote(start, end, status));
        noteAdapter.notifyDataSetChanged();
    }
}