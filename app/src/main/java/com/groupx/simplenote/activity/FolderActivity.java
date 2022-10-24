package com.groupx.simplenote.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.groupx.simplenote.R;
import com.groupx.simplenote.adapter.FolderAdapter;
import com.groupx.simplenote.database.NoteDatabase;
import com.groupx.simplenote.entity.Folder;

import java.util.ArrayList;
import java.util.List;

public class FolderActivity extends AppCompatActivity {

    private RecyclerView recyclerviewFolder;
    private FolderAdapter folderAdapter;

    private List<Folder> folderList = new ArrayList<>();

    private void findView(){
        recyclerviewFolder.findViewById(R.id.recyclerviewFolder);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_folder);

        recyclerviewFolder.setLayoutManager(
                new LinearLayoutManager(getApplicationContext(),
                        LinearLayoutManager.VERTICAL,
                        false)
        );

      folderAdapter = new FolderAdapter(folderList);
      recyclerviewFolder.setAdapter(folderAdapter);
    }


    private void getFolders(){
        if(folderList.size() == 0){
            folderList.addAll(
                    NoteDatabase.getSNoteDatabase(getApplicationContext())
                            .folderDao().getAllMyFolder()
            );
            folderAdapter.notifyDataSetChanged();
        }else {
            folderList.add(0,NoteDatabase.getSNoteDatabase(getApplicationContext())
                    .folderDao().getAllMyFolder().get(0) );
            folderAdapter.notifyItemInserted(0);
        }
        recyclerviewFolder.smoothScrollToPosition(0);
    }
}