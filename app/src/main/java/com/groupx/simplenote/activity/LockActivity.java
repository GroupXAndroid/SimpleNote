package com.groupx.simplenote.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.groupx.simplenote.R;

public class LockActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock);
        setLock();
        ImageView back = findViewById(R.id.imageSettingLockBack);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    public void setLock(){
        Button lock = findViewById(R.id.set_lock);
        lock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lockSetPopup();
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
            }
        });
        AlertDialog dialog = alert.create();
        dialog.show();
    }

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
            }
        });
        AlertDialog dialog = alert.create();
        dialog.show();
    }
}