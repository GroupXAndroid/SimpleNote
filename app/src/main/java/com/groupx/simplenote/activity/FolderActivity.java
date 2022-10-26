package com.groupx.simplenote.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.groupx.simplenote.R;
import com.groupx.simplenote.adapter.FolderAdapter;
import com.groupx.simplenote.common.Component;
import com.groupx.simplenote.database.NoteDatabase;
import com.groupx.simplenote.entity.Folder;
import com.groupx.simplenote.fragment.EditFolderFragment;

import java.util.ArrayList;
import java.util.List;

public class FolderActivity extends AppCompatActivity {

    private ImageView imageFolderAdd;
    private RecyclerView recyclerviewFolder;
    private FolderAdapter folderAdapter;

    private EditText edittextEditFolder;
    private TextView textViewFolderDialogCancel;

    private final int REQUEST_CODE_CREATE = 1;

    private List<Folder> folderList = new ArrayList<>();

    private void findView() {
        recyclerviewFolder = findViewById(R.id.recyclerviewFolder);
        imageFolderAdd = findViewById(R.id.imageFolderAdd);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_folder);

        findView();

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
        folderAdapter = new FolderAdapter(folderList);
        recyclerviewFolder.setAdapter(folderAdapter);

        getFolders();
    }

    private void initFolderDialog(){
        EditFolderFragment editFolder = new EditFolderFragment();
        Bundle args = new Bundle();
        args.putInt("mode", REQUEST_CODE_CREATE);
        editFolder.setArguments(args);
        editFolder.show(getSupportFragmentManager(), "folderEdit");
    }

    public void onClickColor(View view) {
        Component component = new Component();
        component.getColorFromColorChooser(view, getApplicationContext());
    }

    private void getFolders() {
        if (folderList.size() == 0) {
            folderList.addAll(
                    NoteDatabase.getSNoteDatabase(getApplicationContext())
                            .folderDao().getAllMyFolder()
            );
            folderAdapter.notifyDataSetChanged();
        } else {
            folderList.add(0, NoteDatabase.getSNoteDatabase(getApplicationContext())
                    .folderDao().getAllMyFolder().get(0));
            folderAdapter.notifyItemInserted(0);
        }
        recyclerviewFolder.smoothScrollToPosition(0);
    }
}