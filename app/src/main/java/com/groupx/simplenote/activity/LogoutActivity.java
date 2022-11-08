package com.groupx.simplenote.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class LogoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences share = getSharedPreferences(LoginActivity.PREFS_NAME, 0);
        share.edit().clear().commit();
        Intent intent = new Intent(LogoutActivity.this, LoginActivity.class);
        startActivity(intent);
    }

}
