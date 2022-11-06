package com.groupx.simplenote.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.groupx.simplenote.R;
import com.groupx.simplenote.database.NoteDatabase;
import com.groupx.simplenote.entity.Account;

import org.json.JSONException;
import org.json.JSONObject;

public class PremiumUpgradeActivity extends AppCompatActivity {

    private Button btnUpgrade, btnCancel;
    private Account currentUser = new Account();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_premium_upgrade);
        SharedPreferences sharedPreferences = getSharedPreferences(LoginActivity.ACCOUNT_ID, 0);
        currentUser.setId(sharedPreferences.getInt("accountId", 0));

        btnUpgrade = findViewById(R.id.buttonUpgrade);
        btnUpgrade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetPremiumAccount();
            }
        });
        btnCancel = findViewById(R.id.buttonCancelUpgrade);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void SetPremiumAccount() {

        Account account = NoteDatabase.getSNoteDatabase(getApplicationContext())
                .accountDao().getAccountById(currentUser.getId());
        JSONObject jsonSetting = account.getSetting();
        try {
            jsonSetting.put("premium", true);
        } catch (JSONException jex) {

        }
        account.setSetting(jsonSetting);
    }
}