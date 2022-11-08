package com.groupx.simplenote.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.groupx.simplenote.R;
import com.groupx.simplenote.dao.AccountDao;
import com.groupx.simplenote.database.NoteDatabase;
import com.groupx.simplenote.entity.Account;

import org.json.JSONException;
import org.json.JSONObject;

public class LockActivity extends AppCompatActivity {

    private AccountDao accountDao;
    private Account currentAccount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock);
        SharedPreferences sharedPreferences = getSharedPreferences(LoginActivity.PREFS_NAME, 0);
        String us = sharedPreferences.getString("username", "");
        accountDao = NoteDatabase.getSNoteDatabase(getApplicationContext()).accountDao();
        this.currentAccount = accountDao.getAccountByEmail(us);
        setLock();
        ImageView back = findViewById(R.id.imageSettingLockBack);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    /**
     *
     */
    public void setLock(){
        Button lock = findViewById(R.id.set_lock);
        lock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(currentAccount.getSetting("lock_key").isEmpty()){
                    lockSetPopup();
                } else {
                    lockEditPopup();
                }
            }
        });
        Button del = findViewById(R.id.delete_lock);
        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lockDelPopup();
            }
        });
    }

    /**
     *
     */
    private void lockEditPopup(){
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.layout_edit_lock, null);


        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Lock password");
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
                EditText oLock = alertLayout.findViewById(R.id.old_password);

                if (!oLock.getText().toString().equals(currentAccount.getSetting("lock_key"))){
                    Toast.makeText(getApplicationContext(), "Wrong old lock password!", Toast.LENGTH_SHORT).show();
                    return;
                }
                EditText iLock = alertLayout.findViewById(R.id.new_password);
                EditText cLock = alertLayout.findViewById(R.id.confirm_password);
                if (iLock.getText().toString().equals(cLock.getText().toString())){
                    currentAccount.setSetting("lock_key", iLock.getText().toString());
                    accountDao.update(currentAccount);
                    accountDao.update(currentAccount);
                }else{
                    Toast.makeText(getApplicationContext(),"Password not match!",Toast.LENGTH_SHORT).show();
                }
            }
        });
        AlertDialog dialog = alert.create();
        dialog.show();
    }

    /**
     *
     */
    private void lockSetPopup(){
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.layout_set_lock, null);


        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Lock password");
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
                EditText iLock = alertLayout.findViewById(R.id.new_password);
                EditText cLock = alertLayout.findViewById(R.id.confirm_password);
                if (iLock.getText().toString().equals(cLock.getText().toString())){
                    currentAccount.setSetting("lock_key", iLock.getText().toString());
                    accountDao.update(currentAccount);
                }else{
                    Toast.makeText(getApplicationContext(),"Password not match!",Toast.LENGTH_SHORT).show();
                }
            }
        });
        AlertDialog dialog = alert.create();
        dialog.show();
    }

    /**
     *
     */
    private void lockDelPopup(){
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.layout_unset_lock, null);


        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Unset lock password");
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
                EditText check = alertLayout.findViewById(R.id.check_password);
                if (check.getText().toString().equals(currentAccount.getSetting("lock_key"))){
                    currentAccount.setSetting("lock_key", "");
                    accountDao.update(currentAccount);
                } else {
                    Toast.makeText(getApplicationContext(), "Wrong lock password", Toast.LENGTH_SHORT).show();
                }
            }
        });
        AlertDialog dialog = alert.create();
        dialog.show();
    }
}