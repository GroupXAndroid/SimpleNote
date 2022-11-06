package com.groupx.simplenote.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.groupx.simplenote.R;
import com.groupx.simplenote.activity.LoginActivity;
import com.groupx.simplenote.common.Component;
import com.groupx.simplenote.common.Utils;
import com.groupx.simplenote.database.NoteDatabase;
import com.groupx.simplenote.entity.Tag;

public class EditTagFragment extends DialogFragment {

    private String selectedTagColor;
    private EditText edittextEditTag;
    private View buttonTagDialogCancel, buttonTagDialogOk;

    private Tag alreadyTag;
    private boolean isEditing = false;
    private String selectedColor = null;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);


        return inflater.inflate(R.layout.dialog_edit_tag, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (view != null) {
            buttonTagDialogCancel = view.findViewById(R.id.buttonTagDialogCancel);
            buttonTagDialogOk = view.findViewById(R.id.buttonTagDialogOk);
            edittextEditTag = view.findViewById(R.id.edittextEditTag);

            // Lấy ra argument bundle được truyền vào ở hành động nguồn gọi, cụ thể là TagAdapter

            isEditing = getArguments().getBoolean("isEditing");
            if (isEditing) {
                alreadyTag = (Tag) getArguments().getSerializable("editTag");
            }

            // Nếu fragment được gọi để edit thì thực hiện điền các trường đã có sẵn data
            initEditTag(view);

            buttonTagDialogCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getDialog().onBackPressed();
                }
            });
            buttonTagDialogOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!isEditing) {
                        insertTag(v);
                    } else {
                        updateTag(v);
                    }
                }
            });
        }
    }

    private void initEditTag(View view) {
        if (alreadyTag != null && isEditing) {
            edittextEditTag.setText(alreadyTag.getTagName());
            selectedColor = alreadyTag.getColor();
            new Component().setSelectedColor(view, selectedColor, getContext());
        }
    }

    public void onClickColor(View view) {
        Component component = new Component();
        selectedColor = Utils.ColorIntToString(
                new Component().getColorFromColorChooser(view, getContext())
        );
    }

    private void insertTag(View view) {
        SharedPreferences sharedPreferences =getActivity().getSharedPreferences(LoginActivity.ACCOUNT_ID, 0);
        int accId = sharedPreferences.getInt("accountId", 0);
        String tagName = edittextEditTag.getText().toString();

        if (tagName.trim().isEmpty()) {
            Toast.makeText(getContext(), "TagName Empty", Toast.LENGTH_SHORT).show();
            return;
        }
        Tag tag = new Tag();
        tag.setAccountId(accId);
        tag.setTagName(tagName);
        tag.setColor(selectedColor);
        NoteDatabase.getSNoteDatabase(getContext()).tagDao().insertTag(tag);

        reloadScreen();
    }

    private void updateTag(View view) {
        if (alreadyTag == null || !isEditing) {
            return;
        } else {
            alreadyTag.setTagName(edittextEditTag.getText().toString());
            alreadyTag.setColor(selectedColor);
            NoteDatabase.getSNoteDatabase(getContext()).tagDao().update(alreadyTag);
            reloadScreen();
        }
    }

    private void reloadScreen() {
        getDialog().cancel();
        getActivity().recreate();
    }
}
