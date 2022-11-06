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
import com.groupx.simplenote.activity.FolderActivity;
import com.groupx.simplenote.common.Component;
import com.groupx.simplenote.common.Utils;
import com.groupx.simplenote.database.NoteDatabase;
import com.groupx.simplenote.entity.Folder;

public class EditFolderFragment extends DialogFragment {

    private String selectedFolderColor;
    private EditText edittextEditFolder;
    private View buttonFolderDialogCancel, buttonFolderDialogOk;

    private Folder alreadyFolder;
    private boolean isEditing = false;
    private String selectedColor = null;

    private final FolderActivity activity;

    public EditFolderFragment(FolderActivity activity) {
        this.activity = activity;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);


        return inflater.inflate(R.layout.dialog_edit_folder, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (view != null) {
            buttonFolderDialogCancel = view.findViewById(R.id.buttonFolderDialogCancel);
            buttonFolderDialogOk = view.findViewById(R.id.buttonFolderDialogOk);
            edittextEditFolder = view.findViewById(R.id.edittextEditFolder);

            // Lấy ra argument bundle được truyền vào ở hành động nguồn gọi, cụ thể là FolderAdapter

            isEditing = getArguments().getBoolean("isEditing");
            if (isEditing) {
                alreadyFolder = (Folder) getArguments().getSerializable("editFolder");
            }

            // Nếu fragment được gọi để edit thì thực hiện điền các trường đã có sẵn data
            initEditFolder(view);

            buttonFolderDialogCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getDialog().onBackPressed();
                }
            });
            buttonFolderDialogOk.setOnClickListener(new View.OnClickListener() {
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
        activity.insertFolder(folder);
        reloadScreen();
    }

    private void updateFolder(View view) {
        if (alreadyFolder == null || !isEditing) {
            return;
        } else {
            alreadyFolder.setFolderName(edittextEditFolder.getText().toString());
            alreadyFolder.setColor(selectedColor);
            activity.updateFolder(alreadyFolder);
            reloadScreen();
        }
    }

    private void reloadScreen() {
        getDialog().cancel();
        getActivity().recreate();
    }
}
