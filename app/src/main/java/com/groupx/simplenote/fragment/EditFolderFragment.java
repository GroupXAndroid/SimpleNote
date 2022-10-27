package com.groupx.simplenote.fragment;

import android.os.Bundle;
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
import com.groupx.simplenote.common.Utils;
import com.groupx.simplenote.database.NoteDatabase;
import com.groupx.simplenote.entity.Folder;

public class EditFolderFragment extends DialogFragment {

    private String selectedFolderColor;
    private EditText edittextEditFolder;
    private TextView textViewFolderDialogCancel, textViewFolderDialogOk;

    private Folder alreadyFolder;
    private boolean isEditing = false;
    private String selectedColor = null;

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
        if (view != null) {
            textViewFolderDialogCancel = view.findViewById(R.id.textViewFolderDialogCancel);
            textViewFolderDialogOk = view.findViewById(R.id.textViewFolderDialogOk);
            edittextEditFolder = view.findViewById(R.id.edittextEditFolder);

            // Lấy ra argument bundle được truyền vào ở hành động nguồn gọi, cụ thể là FolderAdapter
            alreadyFolder = (Folder) getArguments().getSerializable("editFolder");
            isEditing = getArguments().getBoolean("isEditing");
            // Nếu fragment được gọi để edit thì thực hiện điền các trường đã có sẵn data
            initEditFolder(view);

            textViewFolderDialogCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getDialog().onBackPressed();
                }
            });
            textViewFolderDialogOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!isEditing) {
                        insertFolder(v);
                    } else {
                        updateFolder(v);
                    }
                }
            });
        }
    }

    private void initEditFolder(View view) {
        if (alreadyFolder != null && isEditing) {
            edittextEditFolder.setText(alreadyFolder.getFolderName());
            selectedColor = alreadyFolder.getColor();
            new Component().setSelectedColor(view, selectedColor, getContext());
        }
    }

    public void onClickColor(View view) {
        Component component = new Component();
        selectedColor = Utils.ColorIntToString(
                new Component().getColorFromColorChooser(view, getContext())
        );
    }

    private void insertFolder(View view) {
        String folderName = edittextEditFolder.getText().toString();

        if (folderName.trim().isEmpty()) {
            Toast.makeText(getContext(), "FolderName Empty", Toast.LENGTH_SHORT).show();
            return;
        }
        Folder folder = new Folder();
        folder.setFolderName(folderName);
        folder.setColor(selectedColor);
        NoteDatabase.getSNoteDatabase(getContext()).folderDao().insert(folder);

        reloadScreen();
    }

    private void updateFolder(View view) {
        if (alreadyFolder == null || !isEditing) {
            return;
        } else {
            alreadyFolder.setFolderName(edittextEditFolder.getText().toString());
            alreadyFolder.setColor(selectedColor);
            NoteDatabase.getSNoteDatabase(getContext()).folderDao().update(alreadyFolder);
            reloadScreen();
        }
    }

    private void reloadScreen() {
        getDialog().cancel();
        getActivity().recreate();
    }
}
