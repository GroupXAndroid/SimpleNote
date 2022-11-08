package com.groupx.simplenote.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.groupx.simplenote.R;
import com.groupx.simplenote.activity.CreateNoteActivity;
import com.groupx.simplenote.activity.CreateReminderActivity;

public class ReminderDetailOptionFragment extends BottomSheetDialogFragment {

    private CreateReminderActivity activity;
    private View layoutDeleteNote, layoutAddFavourite, layoutAddArchive;

    public ReminderDetailOptionFragment(CreateReminderActivity activity) {
        super();
        this.activity = activity;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_reminder_detail_option, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (view != null) {

            layoutDeleteNote = view.findViewById(R.id.layoutDeleteNoteDetail);
            layoutDeleteNote.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new AlertDialog.Builder(getContext())
                            .setTitle("Delete Note")
                            .setMessage("Are you sure to move this note to bin?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    activity.deleteNote();
                                }
                            })
                            .setNegativeButton("No", null)
                            .show();

                    getDialog().cancel();
                }
            });

            layoutAddArchive = view.findViewById(R.id.layoutAddArchive);
            layoutAddArchive.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.archiveNote();
                }
            });

            layoutAddFavourite = view.findViewById(R.id.layoutAddFavourite);
            layoutAddFavourite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.favouriteNote();
                }
            });
        }
    }
}