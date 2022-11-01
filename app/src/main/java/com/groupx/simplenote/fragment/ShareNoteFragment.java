package com.groupx.simplenote.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.textfield.TextInputEditText;
import com.groupx.simplenote.R;
import com.groupx.simplenote.activity.CreateNoteActivity;
import com.groupx.simplenote.common.Const;
import com.groupx.simplenote.database.NoteDatabase;
import com.groupx.simplenote.entity.Account;

public class ShareNoteFragment extends BottomSheetDialogFragment {
    private CreateNoteActivity activity;
    private Button buttonShareCancel, buttonShare;
    private AutoCompleteTextView atcSharingPermisson;
    private TextInputEditText editTextEmailSharing;


    private final static String[] permissons = new String[]{
            Const.Permission.EDIT, Const.Permission.VIEW
    };

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
        editTextEmailSharing = view.findViewById(R.id.editTextEmailSharing);
        atcSharingPermisson = view.findViewById(R.id.autoCompleteNoteSharingPermission);
        buttonShare = view.findViewById(R.id.buttonShare);
        buttonShareCancel = view.findViewById(R.id.buttonShareCancel);

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

        buttonShareCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().onBackPressed();
            }
        });

        buttonShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareNote();
            }
        });
    }

    private void shareNote(){
        String email = editTextEmailSharing.getText().toString();
        Account account = NoteDatabase.getSNoteDatabase(getContext()).accountDao()
                .getAccountByEmail(email);
        if(account == null){
            editTextEmailSharing.setError("Account does not exist");
            return;
        }
        String permission = atcSharingPermisson.getText().toString();
        switch (permission){
            case Const.Permission.EDIT: permission = Const.StatusPermission.EDIT.toString(); break;
            case Const.Permission.VIEW: permission = Const.StatusPermission.VIEW.toString(); break;
        }
        activity.shareNote(account.getId(), permission);
    }

}
