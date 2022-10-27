package com.groupx.simplenote.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.groupx.simplenote.R;
import com.groupx.simplenote.activity.CreateNoteActivity;
import com.groupx.simplenote.common.Const;

public class ShareNoteFragment extends BottomSheetDialogFragment {
    private CreateNoteActivity activity;

    private AutoCompleteTextView atcSharingPermisson;

    public ShareNoteFragment(CreateNoteActivity activity) {
        this.activity = activity;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.layout_share_note, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        atcSharingPermisson = view.findViewById(R.id.autoCompleteNoteSharingPermission);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_list_item_1, permissons);
        atcSharingPermisson.setAdapter(arrayAdapter);

        atcSharingPermisson.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                Toast.makeText(getContext(), item, Toast.LENGTH_SHORT).show();
            }
        });

    }

    private final static String[] permissons = new String[]{
            Const.Permission.EDIT, Const.Permission.VIEW
    };
}
