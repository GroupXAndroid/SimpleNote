package com.groupx.simplenote.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.groupx.simplenote.R;
import com.groupx.simplenote.dao.AccountDao;
import com.groupx.simplenote.database.NoteDatabase;
import com.groupx.simplenote.entity.Account;

public class LoginActivity extends AppCompatActivity {

    public static String PREFS_NAME = "MyPrefsFile";

    private EditText etUserName, etPassword;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUserName = findViewById(R.id.etUserName);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getSharedPreferences(LoginActivity.PREFS_NAME, 0);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                final String userName = etUserName.getText().toString();
                final String password = etPassword.getText().toString();
                if (userName.isEmpty() || password.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Fill all fields!", Toast.LENGTH_SHORT).show();
                } else {
                    NoteDatabase noteDatabase = NoteDatabase.getSNoteDatabase(getApplicationContext());
                    final AccountDao accountDao = noteDatabase.accountDao();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Account accountEntity = accountDao.login(userName, password);
                            if (accountEntity == null) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getApplicationContext(), "Invalid Credentials", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            } else {
                                editor.putBoolean("hasLoggedIn", true);
                                editor.commit();
                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                finish();
                            }
                        }
                    }).start();
                }
            }
        });
    }
}