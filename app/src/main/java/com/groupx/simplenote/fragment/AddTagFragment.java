package com.groupx.simplenote.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.groupx.simplenote.R;
import com.groupx.simplenote.activity.CreateNoteActivity;
import com.groupx.simplenote.adapter.AddTagAdapter;
import com.groupx.simplenote.database.NoteDatabase;
import com.groupx.simplenote.entity.Note;
import com.groupx.simplenote.entity.Tag;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class AddTagFragment extends DialogFragment {

    private RecyclerView rvNoteAddTag;
    private AddTagAdapter addTagAdapter;
    private List<Tag> tagList = new ArrayList<>();
    private CreateNoteActivity activity;

    public AddTagFragment (CreateNoteActivity activity){
        this.activity = activity;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.layout_add_tag, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvNoteAddTag = view.findViewById(R.id.recyclerviewNoteDetailAddTag);


        rvNoteAddTag.setLayoutManager(
                new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        tagList = new ArrayList<>();
        addTagAdapter = new AddTagAdapter(tagList, activity, this);
        rvNoteAddTag.setAdapter(addTagAdapter);

        getTags();
    }

    private void getTags(){
        if(tagList.isEmpty()) {
            tagList.addAll(NoteDatabase.getSNoteDatabase(getContext()).tagDao().getAllTagsOfAccount(activity.currentUser.getId()));
            addTagAdapter.notifyDataSetChanged();
        }
    }
}
