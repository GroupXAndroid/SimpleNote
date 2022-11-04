package com.groupx.simplenote.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.groupx.simplenote.R;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        settingFeature();
    }

    /**
     *
     */
    private void settingFeature(){
        settingAchievement();
        settingNotification();
        settingBackground();
        settingLock();
        settingLanguage();
        settingFolder();
    }

    /**
     *  Open Setting Notification Activity
     */
    private void settingNotification(){

        Button btnNotification = findViewById(R.id.settingNotification);
        btnNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    /**
     * Open Setting Language Activity
     */
    private void settingLanguage(){

        Button btnLanguage = findViewById(R.id.settingLanguage);
        btnLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayAlertDialog();
                RadioGroup radioGroup = (RadioGroup)findViewById(R.id.radio_group);
            }
        });
    }
    public void displayAlertDialog() {
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.layout_custom_dialog, null);


        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Language");
        alert.setView(alertLayout);
        alert.setCancelable(false);
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        AlertDialog dialog = alert.create();
        dialog.show();
    }

    /**
     *
     */
    private void settingLock(){

    }

    /**
     *
     */
    private void settingBackground(){

    }

    /**
     *
     */
    private void settingFolder(){

    }

    /**
     *
     */
    private void settingAchievement(){

    }


}