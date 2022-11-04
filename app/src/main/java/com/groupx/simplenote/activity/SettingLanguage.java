package com.groupx.simplenote.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.groupx.simplenote.R;
import com.groupx.simplenote.common.Utils;
import com.groupx.simplenote.fragment.EditFolderFragment;
import com.groupx.simplenote.fragment.LanguageFragment;
import com.groupx.simplenote.fragment.placeholder.PlaceholderContent;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.PopupWindow;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SettingLanguage extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        Utils u = new Utils();
        JSONArray  langs = u.getLanguageList();
        List<String> languages = new ArrayList<>();
        for (int i=0;i<langs.length();i++){
            try {
                languages.add(langs.getString(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.setting_language)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                })
                .setSingleChoiceItems((ListAdapter) languages, 0, (DialogInterface.OnClickListener) (dialog, id) -> {

                });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}