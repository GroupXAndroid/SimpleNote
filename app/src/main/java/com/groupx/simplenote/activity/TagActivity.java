package com.groupx.simplenote.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.groupx.simplenote.R;
import com.groupx.simplenote.adapter.TagAdapter;
import com.groupx.simplenote.database.NoteDatabase;
import com.groupx.simplenote.entity.Tag;
import com.groupx.simplenote.fragment.EditTagFragment;

import java.util.ArrayList;
import java.util.List;

public class TagActivity extends AppCompatActivity {
    private EditTagFragment editTag;

    public void setEditTagFragment(EditTagFragment fragment) {
        this.editTag = fragment;
    }

    private ImageView imageTagAdd, imageBack;
    private RecyclerView recyclerviewTag;
    private TagAdapter tagAdapter;
    private final List<Tag> tagList = new ArrayList<>();

    private void findView() {
        recyclerviewTag = findViewById(R.id.recyclerviewTag);
        imageTagAdd = findViewById(R.id.imageTagAdd);
        imageBack = findViewById(R.id.imageTagBack);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag);

        findView();
        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        imageTagAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initTagDialog();
            }
        });


        // Setup recyclerView
        recyclerviewTag.setLayoutManager(
                new LinearLayoutManager(getApplicationContext(),
                        LinearLayoutManager.VERTICAL,
                        false)
        );
        tagAdapter = new TagAdapter(tagList, this);

        recyclerviewTag.setAdapter(tagAdapter);

        getTags();
    }

    private void initTagDialog() {
        editTag = new EditTagFragment();
        Bundle args = new Bundle();
        args.putBoolean("isEditing", false);
        editTag.setArguments(args);
        editTag.show(getSupportFragmentManager(), "tagEdit");
    }

    public void onClickColor(View view) {
        editTag.onClickColor(view);
    }

    private void getTags() {
        SharedPreferences sharedPreferences = getSharedPreferences(LoginActivity.ACCOUNT_ID, 0);
        int accId = sharedPreferences.getInt("accountId", 0);
        if (tagList.size() == 0) {
            tagList.addAll(
                    NoteDatabase.getSNoteDatabase(getApplicationContext())
                            .tagDao().getAllTagsOfAccount(accId)
            );
            tagAdapter.notifyDataSetChanged();
        } else {
            tagList.add(0, NoteDatabase.getSNoteDatabase(getApplicationContext())
                    .tagDao().getAllTagsOfAccount(accId).get(0));
            tagAdapter.notifyItemInserted(0);
        }
        recyclerviewTag.smoothScrollToPosition(0);
    }

    public void deleteTag(Tag tag) {
        NoteDatabase.getSNoteDatabase(getApplicationContext())
                .tagDao().delete(tag);
        recreate();
    }
}
