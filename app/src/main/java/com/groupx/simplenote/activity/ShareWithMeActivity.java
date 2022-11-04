package com.groupx.simplenote.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.groupx.simplenote.R;
import com.groupx.simplenote.adapter.SwmNoteAdapter;
import com.groupx.simplenote.common.Const;
import com.groupx.simplenote.database.NoteDatabase;
import com.groupx.simplenote.dto.NoteShareWithMeDTO;

import java.util.ArrayList;
import java.util.List;

public class ShareWithMeActivity extends AppCompatActivity {

    private RecyclerView recyclerviewShareWithMe;
    SwmNoteAdapter adapter;
    private List<NoteShareWithMeDTO> noteSwmList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_with_me);

        recyclerviewShareWithMe = findViewById(R.id.recyclerviewShareWithMe);
        recyclerviewShareWithMe.setLayoutManager(
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        noteSwmList = new ArrayList<>();

        adapter = new SwmNoteAdapter(noteSwmList, this);
        recyclerviewShareWithMe.setAdapter(adapter);
        getNoteSwm();
    }

    private void getNoteSwm() {
        if (noteSwmList.isEmpty()) {
            noteSwmList.addAll(NoteDatabase.getSNoteDatabase(getApplicationContext()).noteDao().getNoteShareForMe(1,
                    new String[]{Const.StatusPermission.VIEW.toString(), Const.StatusPermission.EDIT.toString()}));
            adapter.notifyDataSetChanged();
        }
    }
}