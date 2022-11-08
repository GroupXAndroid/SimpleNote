package com.groupx.simplenote.activity;

import androidx.appcompat.app.AppCompatActivity;
import com.groupx.simplenote.R;
import com.groupx.simplenote.dao.AccountDao;
import com.groupx.simplenote.database.NoteDatabase;
import com.groupx.simplenote.entity.Account;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;

public class NotificationActivity extends AppCompatActivity {
    Switch btnSwitch;
    private AccountDao accountDao;
    private Account currentAccount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        ImageView back = findViewById(R.id.imageSettingNotificationBack);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        SharedPreferences sharedPreferences = getSharedPreferences(LoginActivity.PREFS_NAME, 0);
        String us = sharedPreferences.getString("username", "");
        accountDao = NoteDatabase.getSNoteDatabase(getApplicationContext()).accountDao();
        this.currentAccount = accountDao.getAccountByEmail(us);
        switchNotification();
    }

    public void switchNotification(){
        btnSwitch = findViewById(R.id.switch_notification);
        if (currentAccount.getSetting("notification").equals("1")){
            btnSwitch.setChecked(true);
        }
        btnSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // do something, the isChecked will be
                // true if the switch is in the On position
                if (isChecked){
                    currentAccount.setSetting("notification", "1");
                } else {
                    currentAccount.setSetting("notification", "0");
                }
                accountDao.update(currentAccount);
            }
        });
    }
}