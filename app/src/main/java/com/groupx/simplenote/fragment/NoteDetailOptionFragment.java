package com.groupx.simplenote.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.groupx.simplenote.R;
import com.groupx.simplenote.activity.CreateNoteActivity;

public class NoteDetailOptionFragment extends BottomSheetDialogFragment {

    private CreateNoteActivity activity;
    private View layoutDeleteNote;

    public NoteDetailOptionFragment(CreateNoteActivity activity) {
        super();
        this.activity = activity;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.layout_note_detail_option, container, false);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        View view = getView();
        if (view != null) {
            layoutDeleteNote = view.findViewById(R.id.layoutDeleteNoteDetail);
            layoutDeleteNote.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new AlertDialog.Builder(getContext())
                            .setIcon(android.R.drawable.ic_delete)
                            .setTitle("Delete Note")
                            .setMessage("Are you sure you want to delete?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    activity.deleteNote();
                                }
                            })
                            .setNegativeButton("No", null)
                            .show();
                }
            });
        }
    }
}
