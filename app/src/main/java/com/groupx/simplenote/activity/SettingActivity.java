package com.groupx.simplenote.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.groupx.simplenote.R;

import android.app.Activity;
import android.app.Notification;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.DisplayMetrics;
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
                    setLocale(SettingActivity.this, "vi");
                } else {
                    setLocale(SettingActivity.this, "en");
                }
                finish();
                startActivity(getIntent());
            }
        });
        AlertDialog dialog = alert.create();
        dialog.show();
    }
    private void setLocale(Activity activity, String lang) {
        Locale myLocale = new Locale(lang);
        myLocale.setDefault(myLocale);
        Resources resources = getResources();
        Configuration config = resources.getConfiguration();
        config.setLocale(myLocale);
        resources.updateConfiguration(config, resources.getDisplayMetrics());
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