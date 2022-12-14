package com.groupx.simplenote.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.groupx.simplenote.R;
import com.groupx.simplenote.adapter.FolderAdapter;
import com.groupx.simplenote.database.NoteDatabase;
import com.groupx.simplenote.entity.Account;
import com.groupx.simplenote.entity.Folder;
import com.groupx.simplenote.fragment.EditFolderFragment;

import java.util.ArrayList;
import java.util.List;

public class FolderActivity extends AppCompatActivity {

    private EditFolderFragment editFolder;

    public void setEditFolderFragment(EditFolderFragment fragment) {
        this.editFolder = fragment;
    }

    private ImageView imageFolderAdd, imageBack;
    private RecyclerView recyclerviewFolder;
    private FolderAdapter folderAdapter;

    private final List<Folder> folderList = new ArrayList<>();
    private Account currentUser = new Account();

    private void findView() {
        recyclerviewFolder = findViewById(R.id.recyclerviewFolder);
        imageFolderAdd = findViewById(R.id.imageFolderAdd);
        imageBack = findViewById(R.id.imageFolderBack);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_folder);

        SharedPreferences sharedPreferences = getSharedPreferences(LoginActivity.ACCOUNT_ID, 0);
        currentUser.setId(sharedPreferences.getInt("accountId", 0));

        findView();
        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        imageFolderAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initFolderDialog();
            }
        });


        // Setup recyclerView
        recyclerviewFolder.setLayoutManager(
                new LinearLayoutManager(getApplicationContext(),
                        LinearLayoutManager.VERTICAL,
                        false)
        );
        folderAdapter = new FolderAdapter(folderList, this);

        recyclerviewFolder.setAdapter(folderAdapter);

        getFolders();
    }

    private void initFolderDialog() {
        editFolder = new EditFolderFragment(this);
        Bundle args = new Bundle();
        args.putBoolean("isEditing", false);
        editFolder.setArguments(args);
        editFolder.show(getSupportFragmentManager(), "folderEdit");
    }

    public void onClickColor(View view) {
        editFolder.onClickColor(view);
    }

    private void getFolders() {
        if (folderList.size() == 0) {
            folderList.addAll(
                    NoteDatabase.getSNoteDatabase(getApplicationContext())
                            .folderDao().getAllMyFolder(currentUser.getId())
            );
            folderAdapter.notifyDataSetChanged();
        } else {
            folderList.add(0, NoteDatabase.getSNoteDatabase(getApplicationContext())
                    .folderDao().getAllMyFolder(currentUser.getId()).get(0));
            folderAdapter.notifyItemInserted(0);
        }
        recyclerviewFolder.smoothScrollToPosition(0);
    }

    public void deleteFolder(Folder folder) {
        NoteDatabase.getSNoteDatabase(getApplicationContext())
                .folderDao().delete(folder);
        recreate();
    }

    public void insertFolder(Folder folder) {
        folder.setAccountId(currentUser.getId());
        NoteDatabase.getSNoteDatabase(getApplicationContext()).folderDao().insert(folder);
    }

    public void updateFolder(Folder folder) {
        NoteDatabase.getSNoteDatabase(getApplicationContext()).folderDao().update(folder);

    }
}