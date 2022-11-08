package com.groupx.simplenote.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.google.android.material.navigation.NavigationView;
import com.groupx.simplenote.R;
import com.groupx.simplenote.adapter.NoteAdapter;
import com.groupx.simplenote.common.Const;
import com.groupx.simplenote.database.NoteDatabase;
import com.groupx.simplenote.entity.Account;
import com.groupx.simplenote.entity.Note;
import com.groupx.simplenote.entity.NoteAccount;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Account currentUser = new Account();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        TextView tvn = findViewById(R.id.tvUserName);
        SharedPreferences sharedPreferences = getSharedPreferences(LoginActivity.ACCOUNT_ID, 0);
        int accId = sharedPreferences.getInt("accountId", 0);
        currentUser.setId(accId);


        currentUser = NoteDatabase.getSNoteDatabase(getApplicationContext())
                .accountDao().getAccountById(accId);

        InitDrawerNavigationMenu();
        InitCreateNewNoteButton();
        InitRecyclerViewNote();
    }

    private RecyclerView rcvNoteList;
    private NoteAdapter adapter;
    private List<Note> noteList = new ArrayList<>();

    private void InitRecyclerViewNote(){
        rcvNoteList = findViewById(R.id.rcvNoteList);
        rcvNoteList.setLayoutManager(new StaggeredGridLayoutManager(2,  StaggeredGridLayoutManager.VERTICAL));
        adapter = new NoteAdapter(noteList, this);
        rcvNoteList.setAdapter(adapter);

        getNotes(Const.NoteRequestCode.REQUEST_CODE_SHOW);
    }

    private int noteClikedPosition;
    private void getNotes(int requestCode){
        List<Note> notes = NoteDatabase.getSNoteDatabase(getApplicationContext())
                .noteDao().getAllMyNote(currentUser.getId(),new int[] {Const.NoteStatus.NORMAL, Const.NoteStatus.FAVORITE});
        if(requestCode == Const.NoteRequestCode.REQUEST_CODE_SHOW){
            noteList.addAll(notes);
            adapter.notifyDataSetChanged();
        }else if(requestCode == Const.NoteRequestCode.REQUEST_CODE_CREATE){
            noteList.add(0, notes.get(0));
            adapter.notifyItemInserted(0);
            rcvNoteList.smoothScrollToPosition(0);
        }else if(requestCode == Const.NoteRequestCode.REQUEST_CODE_UPDATE){
            noteList.remove(noteClikedPosition);
            noteList.add(noteClikedPosition, notes.get(noteClikedPosition));
            adapter.notifyItemChanged(noteClikedPosition);
        }
        else if(requestCode == Const.NoteRequestCode.REQUEST_CODE_DELETE){
            noteList.remove(noteClikedPosition);
            adapter.notifyItemRemoved(noteClikedPosition);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK) {

            int myRequestCode = data.getIntExtra("myRequestCode", Const.NoteRequestCode.REQUEST_CODE_EXCEPTION);
            if(myRequestCode != Const.NoteRequestCode.REQUEST_CODE_EXCEPTION){
                requestCode = myRequestCode;
            }
            if(requestCode == Const.NoteRequestCode.REQUEST_CODE_UPDATE || requestCode == Const.NoteRequestCode.REQUEST_CODE_DELETE){
                noteClikedPosition = data.getIntExtra("position", 0);
            }
            getNotes(requestCode);
        }
    }

    private void InitCreateNewNoteButton(){
        findViewById(R.id.imageviewCreateNewNote).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), CreateNoteActivity.class);
                        intent.putExtra("mode", Const.NoteDetailActivityMode.CREATE);
                        startActivityForResult(intent, Const.NoteRequestCode.REQUEST_CODE_CREATE);
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

        navigationViewMenu.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
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
                    case R.id.itemLogout:
                        intent = new Intent(getApplicationContext(), LogoutActivity.class);
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