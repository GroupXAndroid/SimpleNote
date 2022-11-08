package com.groupx.simplenote.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.groupx.simplenote.R;
import com.groupx.simplenote.database.NoteDatabase;
import com.groupx.simplenote.entity.Account;
import com.groupx.simplenote.entity.Note;
import com.groupx.simplenote.entity.NoteAccount;

import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private Account currentUser = new Account();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        TextView tvn = findViewById(R.id.tvUserName);
        SharedPreferences sharedPreferences = getSharedPreferences(LoginActivity.ACCOUNT_ID, 0);
//        sharedPreferences.edit().remove("accountId");
//        sharedPreferences.edit().commit();
//        sharedPreferences = getSharedPreferences(LoginActivity.PREFS_NAME, 0);
//        sharedPreferences.edit().remove("username");
//        sharedPreferences.edit().remove("hasLoggedIn");
//        sharedPreferences.edit().commit();
        int accId = sharedPreferences.getInt("accountId", 0);
        currentUser.setId(accId);
//        if (accId != 0) {
//            tvn.setText("id = " + String.valueOf(accId));
//        } else {
//            tvn.setText("ID NOT FOUND, MAYBE YOU NOT LOGIN");
//        }

        currentUser = NoteDatabase.getSNoteDatabase(getApplicationContext())
                .accountDao().getAccountById(accId);
        if (currentUser == null){
            currentUser = NoteDatabase.getSNoteDatabase(getApplicationContext()).accountDao().getAccountByEmail(getSharedPreferences(LoginActivity.PREFS_NAME, 0).getString("username",""));
        }

        InitDrawerNavigationMenu();
        InitCreateNewNoteButton();
//        runtestFeature();
    }

    private void InitCreateNewNoteButton(){
        findViewById(R.id.imageviewCreateNewNote).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), CreateNoteActivity.class);
                        startActivity(intent);
                    }
                }
        );
    }

    private void InitDrawerNavigationMenu() {
        DrawerLayout drawerMainMenu = findViewById(R.id.drawerMainMenu);
        findViewById(R.id.imageViewMenu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerMainMenu.openDrawer(GravityCompat.START);
            }
        });


        NavigationView navigationViewMenu = findViewById(R.id.navViewMenu);
        TextView textAccountUserName = navigationViewMenu.getHeaderView(0).findViewById(R.id.textAccountUserName);
        textAccountUserName.setText(currentUser.getFullName());
        TextView textPremium = navigationViewMenu.getHeaderView(0).findViewById(R.id.textPremium);
        // TODO: set text premium if account is VIP


        navigationViewMenu.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                Intent intent;
                switch (id) {
                    case R.id.itemTestNoteDetail:
                        intent = new Intent(getApplicationContext(), CreateNoteActivity.class);
                        startActivity(intent);
                        return true;
                    case R.id.itemTestNoteListView:
                        intent = new Intent(getApplicationContext(), NoteListActivity.class);
                        startActivity(intent);
                        return true;
                    case R.id.itemTestManageFolder:
                        intent = new Intent(getApplicationContext(), FolderActivity.class);
                        startActivity(intent);
                        return true;
                    case R.id.itemTestFeedback:
                        intent = new Intent(getApplicationContext(), FeedbackActivity.class);
                        startActivity(intent);
                        return true;
                    case R.id.itemTestSwm:
                        intent = new Intent(getApplicationContext(), ShareWithMeActivity.class);
                        startActivity(intent);
                        return true;
                    case R.id.itemSetting:
                        intent = new Intent(getApplicationContext(), SettingActivity.class);
                        startActivity(intent);
                        return true;
                    case R.id.itemRegister:
                        intent = new Intent(getApplicationContext(), RegisterActivity.class);
                        startActivity(intent);
                        return true;
                    case R.id.itemSearchNote:
                        intent = new Intent(getApplicationContext(), SearchActivity.class);
                        startActivity(intent);
                        return true;
                    case R.id.itemTag:
                        intent = new Intent(getApplicationContext(), TagActivity.class);
                        startActivity(intent);
                        return true;
                    case R.id.itemTestUpgradeScreen:
                        intent = new Intent(getApplicationContext(), PremiumUpgradeActivity.class);
                        startActivity(intent);
                        return true;
                    case R.id.itemTestArchive:
                        intent = new Intent(getApplicationContext(), ArchiveActivity.class);
                        startActivity(intent);
                        return true;
                    case R.id.itemTestCalendar:
                        intent = new Intent(getApplicationContext(), CalendarActivity.class);
                        startActivity(intent);
                        return true;
                    case R.id.itemTestFavourite:
                        intent = new Intent(getApplicationContext(), FavouriteActivity.class);
                        startActivity(intent);
                        return true;
                    case R.id.itemTestBin:
                        intent = new Intent(getApplicationContext(), BinActivity.class);
                        startActivity(intent);
                        return true;
                    case R.id.itemTestRemindersList:
                        intent = new Intent(getApplicationContext(), ReminderListActivity.class);
                        startActivity(intent);
                        return true;
                    default:
                        return false;
                }
            }
        });
    }

//    private void runtestFeature() {
//
//        // Open noteDetail Activity
//        Button buttonTestNote = findViewById(R.id.buttonTestNoteDetail);
//        buttonTestNote.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getApplicationContext(), CreateNoteActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        // Open noteList Testing Activity
//        Button buttonTestNoteList = findViewById(R.id.buttonTestNoteListView);
//        buttonTestNoteList.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getApplicationContext(), NoteListActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        // Open manage folder activity
//        Button buttonTestManageFolder = findViewById(R.id.buttonTestManageFolder);
//        buttonTestManageFolder.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getApplicationContext(), FolderActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        // Open feedback activity
//        Button buttonTestFeedback = findViewById(R.id.buttonTestFeedback);
//        buttonTestFeedback.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getApplicationContext(), FeedbackActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        // Open setting activity
//        Button buttonSwm = findViewById(R.id.buttonTestSwm);
//        buttonSwm.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getApplicationContext(), ShareWithMeActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        // Open setting activity
//        Button buttonSetting = findViewById(R.id.btnSetting);
//        buttonSetting.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getApplicationContext(), SettingActivity.class);
//                startActivity(intent);
//            }
//        });
//
////        InsertSampleDate();
//        // Open register activity
//        Button buttonRegister = findViewById(R.id.btnRegister);
//        buttonRegister.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        // Open search activity
//        Button buttonSearch = findViewById(R.id.btnSearchNote);
//        buttonSearch.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
//                startActivity(intent);
//            }
//        });
//        Button buttonTag = findViewById(R.id.btnTag);
//        buttonTag.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getApplicationContext(), TagActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        Button buttonUpgrade = findViewById(R.id.btnTestUpgradeScreen);
//        buttonUpgrade.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getApplicationContext(), PremiumUpgradeActivity.class);
//                startActivity(intent);
//            }
//        });
//    }

}