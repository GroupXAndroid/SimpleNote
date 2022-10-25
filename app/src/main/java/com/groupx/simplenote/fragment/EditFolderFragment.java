package com.groupx.simplenote.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.groupx.simplenote.R;
import com.groupx.simplenote.common.Component;
import com.groupx.simplenote.database.NoteDatabase;
import com.groupx.simplenote.entity.Folder;

import java.util.logging.Logger;

public class EditFolderFragment extends DialogFragment {

    private String selectedFolderColor;
    private EditText edittextEditFolder;
    private TextView textViewFolderDialogCancel, textViewFolderDialogOk;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.dialog_edit_folder, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        View view = getView();
        if(view != null) {
            textViewFolderDialogCancel = view.findViewById(R.id.textViewFolderDialogCancel);
            textViewFolderDialogOk = view.findViewById(R.id.textViewFolderDialogOk);

            edittextEditFolder = view.findViewById(R.id.edittextEditFolder);
            textViewFolderDialogCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   getDialog().onBackPressed();
                }
            });

            textViewFolderDialogOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    insertFolder();

                }
            });
        }
    }

    private void insertFolder(){
        String folderName = edittextEditFolder.getText().toString();
        if(folderName.trim().isEmpty()){
            Toast.makeText(getContext(), "FolderName Empty", Toast.LENGTH_SHORT).show();
            return;
        }
        Folder folder = new Folder();
        folder.setFolderName(folderName);
        NoteDatabase.getSNoteDatabase(getContext()).folderDao().insert(folder);

        getDialog().cancel();
        getActivity().recreate();
    }
}
