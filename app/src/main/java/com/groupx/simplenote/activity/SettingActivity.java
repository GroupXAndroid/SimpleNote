package com.groupx.simplenote.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.os.LocaleListCompat;

import com.groupx.simplenote.R;
import com.groupx.simplenote.common.LocaleHelper;
import com.groupx.simplenote.dao.AccountDao;
import com.groupx.simplenote.dao.AccountDao_Impl;
import com.groupx.simplenote.database.NoteDatabase;
import com.groupx.simplenote.entity.Account;

import android.app.Activity;
import android.app.Notification;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SettingActivity extends AppCompatActivity {
    Context context;
    Resources resources;
    private AccountDao accountDao;
    private Account currentAccount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences = getSharedPreferences(LoginActivity.PREFS_NAME, 0);
        String us = sharedPreferences.getString("username", "");
        accountDao = NoteDatabase.getSNoteDatabase(getApplicationContext()).accountDao();
        this.currentAccount = accountDao.getAccountByEmail(us);
        setContentView(R.layout.activity_setting);
        settingFeature();
    }

    /**
     *
     */
    private void settingFeature(){
        ImageView back = findViewById(R.id.imageSettingBack);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
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
                Intent intent = new Intent(getApplicationContext(), NotificationActivity.class);
                startActivity(intent);
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
                RadioGroup radioGroup = (RadioGroup)findViewById(R.id.language_list);
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
                RadioGroup group = alertLayout.findViewById(R.id.language_list);
                int selectedId = group.getCheckedRadioButtonId();
                RadioButton selected = (RadioButton) alertLayout.findViewById(selectedId);
                if (selected.getText() == "Vietnamese"){
                    context = LocaleHelper.setLocale(SettingActivity.this, "vi");
                    resources = context.getResources();
                } else {
                    context = LocaleHelper.setLocale(SettingActivity.this, "en");
                    resources = context.getResources();
                }
            }
        });
        AlertDialog dialog = alert.create();
        dialog.show();
    }
    /**
     *
     */
    private void settingLock(){
        Button btnLock = findViewById(R.id.settingLock);
        btnLock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LockActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     *
     */
    private void settingBackground(){
        Button btnBackground = findViewById(R.id.settingBackground);
        btnBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), BackgroundActivity.class);
                startActivity(intent);
            }
        });
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
        Button btnAchievement = findViewById(R.id.achievement);
        btnAchievement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AchievementActivity.class);
                startActivity(intent);
            }
        });
    }


}