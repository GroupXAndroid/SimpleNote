package com.groupx.simplenote.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.groupx.simplenote.AlarmBroadcast;
import com.groupx.simplenote.R;
import com.groupx.simplenote.common.Component;
import com.groupx.simplenote.common.Const;
import com.groupx.simplenote.common.Utils;
import com.groupx.simplenote.database.NoteDatabase;
import com.groupx.simplenote.entity.Note;
import com.groupx.simplenote.entity.NoteAccount;
import com.groupx.simplenote.entity.NoteTag;
import com.groupx.simplenote.fragment.ChoosingNoteColorFragment;
import com.groupx.simplenote.fragment.NoteDetailOptionFragment;
import com.groupx.simplenote.fragment.ReminderDetailOptionFragment;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CreateReminderActivity extends AppCompatActivity {
    private ImageView imageNoteDetailBack, imageNoteDetailSave, imageNoteDetailColorOptionLens,
            imageNoteDetailOption;
    private EditText editTextNoteSubtitle, editTextNoteTitle, editTextNoteContent;
    private TextView textViewNoteDetailDatetime, edtReminderDate, edtReminderTime;
    private ConstraintLayout layoutNoteDetail;


    private String selectedNoteColor;
    private Note alreadyNote = new Note();
    private Set<Integer> tagIdList = new HashSet<>();
    private List<NoteTag> oldNoteTagForUpdate = new ArrayList<>();
    private Set<Integer> accountId = new HashSet<>();

    private short mode;

    public Note getAlreadyNote() {
        return this.alreadyNote;
    }

    public Set<Integer> getTagIdSet() {
        return this.tagIdList;
    }

    private void findView() {
        imageNoteDetailBack = findViewById(R.id.imageNoteDetailBack);
        imageNoteDetailSave = findViewById(R.id.imageNoteDetailSave);
        imageNoteDetailColorOptionLens = findViewById(R.id.imageViewColorOptionLens);
        imageNoteDetailOption = findViewById(R.id.imageNoteDetailOption);

        editTextNoteTitle = findViewById(R.id.editTextNoteTitle);
        editTextNoteSubtitle = findViewById(R.id.editTextNoteSubtitle);
        editTextNoteContent = findViewById(R.id.editTextNoteContent);
        edtReminderDate = findViewById(R.id.edtReminderDate);
        edtReminderTime = findViewById(R.id.edtReminderTime);

        textViewNoteDetailDatetime = findViewById(R.id.textViewNoteDetailDatetime);
        layoutNoteDetail = findViewById(R.id.layoutReminderDetail);
        selectedNoteColor = Utils.ColorIntToString(getColor(R.color.noteColorDefault));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_reminder);
        findView();
        mode = getIntent().getShortExtra("mode", Const.NoteDetailActivityMode.CREATE);

        Date currentTimer = new Date();
        StringBuilder dateBuilder = new StringBuilder("Edited ");
        dateBuilder.append(Utils.DateTimeToString(currentTimer));
        textViewNoteDetailDatetime.setText(dateBuilder);

        /*
         * Date picker
         * */
        final Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        final int hour = calendar.get(Calendar.HOUR_OF_DAY);
        final int minute = calendar.get(Calendar.MINUTE);



        String reminderDate = (currentTimer.getDate() < 10 ? "0"+currentTimer.getDate() : currentTimer.getDate()) + "/"
                + ((currentTimer.getMonth()+1) < 10 ? "0"+(currentTimer.getMonth()+1) : (currentTimer.getMonth()+1)) + "/" + (currentTimer.getYear()+1900);
        edtReminderDate.setText(reminderDate);
        edtReminderDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog dialog = new DatePickerDialog(view.getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month + 1;
                        String date = (dayOfMonth < 10 ? "0"+dayOfMonth : dayOfMonth) + "/"
                                + (month < 10 ? "0"+month : month) + "/" + year;
                        edtReminderDate.setText(date);
                    }
                }, year, month, day);
                dialog.show();
            }
        });

        String reminderTime = (currentTimer.getHours() < 10 ? "0"+currentTimer.getHours() : currentTimer.getHours()) + ":"
                + (currentTimer.getMinutes() < 10 ? "0"+currentTimer.getMinutes() : currentTimer.getMinutes());
        edtReminderTime.setText(reminderTime);
        edtReminderTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog dialog = new TimePickerDialog(view.getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                        String date = (hour < 10 ? "0"+hour : hour) + ":" + (minute < 10 ? "0"+minute : minute);
                        edtReminderTime.setText(date);
                    }
                }, hour, minute, true);
                dialog.show();
            }
        });

        imageNoteDetailBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        imageNoteDetailSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                saveOrUpdate();
                Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_SHORT).show();
            }
        });

        if (mode == Const.NoteDetailActivityMode.VIEW) {
            setOnlyView();
        }
        if (mode == Const.NoteDetailActivityMode.VIEW || mode == Const.NoteDetailActivityMode.EDIT) {
            alreadyNote = (Note) getIntent().getSerializableExtra("note");
            setViewAndEditNote();

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
            reminderDate = dateFormat.format(alreadyNote.getReminderTime());
            edtReminderDate.setText(reminderDate);
            reminderTime = timeFormat.format(alreadyNote.getReminderTime());
            edtReminderTime.setText(reminderTime);
        }

        if (getIntent().getBooleanExtra("isViewOrUpdate", false)) {
            alreadyNote = (Note) getIntent().getSerializableExtra("note");
            setViewAndEditNote();

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
            reminderDate = dateFormat.format(alreadyNote.getReminderTime());
            edtReminderDate.setText(reminderDate);
            reminderTime = timeFormat.format(alreadyNote.getReminderTime());
            edtReminderTime.setText(reminderTime);
        }
        initChooseColorOption();
        initOption();
    }

    private void saveOrUpdate() {
        if (mode == Const.NoteDetailActivityMode.EDIT) {
            updateNote();
        } else if (mode == Const.NoteDetailActivityMode.CREATE) {
            saveNote();
        }
    }

    private void setOnlyView() {
        imageNoteDetailSave.setVisibility(View.GONE);
        imageNoteDetailColorOptionLens.setVisibility(View.GONE);
        imageNoteDetailOption.setVisibility(View.GONE);

        Utils.disableEditText(editTextNoteTitle);
        Utils.disableEditText(editTextNoteSubtitle);
        Utils.disableEditText(editTextNoteContent);
    }

    private void initChooseColorOption() {
        ChoosingNoteColorFragment colorFragment = new ChoosingNoteColorFragment();
        Bundle args = new Bundle();
        colorFragment.setArguments(args);

        imageNoteDetailColorOptionLens.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                args.putString("selectedColor", selectedNoteColor);
                colorFragment.show(getSupportFragmentManager(), "colorFragment");
            }
        });
    }

    private void initOption() {
        ReminderDetailOptionFragment optionFragment = new ReminderDetailOptionFragment(this);

        imageNoteDetailOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                optionFragment.show(getSupportFragmentManager(), "optionFragment");
            }
        });
    }

    private Note saveNote() {
        String title = editTextNoteTitle.getText().toString().trim();
        String subtitle = editTextNoteSubtitle.getText().toString().trim();
        String content = editTextNoteContent.getText().toString();

        final Note note = new Note();
        note.setTitle(title);
        note.setNote(content);
        note.setSubTitle(subtitle);
        note.setColor(selectedNoteColor);
        note.setSince(new Date());
        note.setLastUpdate(new Date());

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String reminderTime = edtReminderDate.getText().toString() + " " + edtReminderTime.getText().toString();
        try {
            Date timeReminder = format.parse(reminderTime);
            note.setReminderTime(timeReminder);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        NoteDatabase.getSNoteDatabase(getApplicationContext())
                .noteDao().insert(note);
        Note currentNote = NoteDatabase.getSNoteDatabase(getApplicationContext())
                .noteDao().getNewestNote();
        alreadyNote = currentNote;
        NoteAccount noteAccount = new NoteAccount();
        noteAccount.setNoteId(currentNote.getId());
        noteAccount.setAccountId(1);
        noteAccount.setPermission(Const.StatusPermission.CREATED.toString());
        NoteDatabase.getSNoteDatabase(getApplicationContext())
                .noteDao().insertWithNoteAccount(noteAccount);

        setAlarm(alreadyNote);

        insertUpdateNoteTagId(currentNote);

//        Intent intent = new Intent();
//        setResult(RESULT_OK, intent);
//        finish();

        return currentNote;
    }

    private void insertUpdateNoteTagId(Note note) {
        if (!oldNoteTagForUpdate.isEmpty()) {
            NoteDatabase.getSNoteDatabase(getApplicationContext())
                    .noteDao().deleteAllTag(oldNoteTagForUpdate);
        }
        List<NoteTag> noteTagList = new ArrayList<>();
        tagIdList.forEach(e -> {
            NoteTag notetag = new NoteTag();
            notetag.setTagId(e);
            notetag.setNoteId(note.getId());
            noteTagList.add(notetag);
        });
        NoteDatabase.getSNoteDatabase(getApplicationContext())
                .noteDao().insertNoteTag(noteTagList);
    }

    private void updateNote() {
        String title = editTextNoteTitle.getText().toString().trim();
        String subtitle = editTextNoteSubtitle.getText().toString().trim();
        String content = editTextNoteContent.getText().toString();

        cancelAlarm(alreadyNote);

        if (alreadyNote == null) {
            alreadyNote = new Note();
            alreadyNote.setSince(new Date());
        }
        alreadyNote.setTitle(title);
        alreadyNote.setNote(content);
        alreadyNote.setSubTitle(subtitle);
        alreadyNote.setColor(selectedNoteColor);
        alreadyNote.setLastUpdate(new Date());

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String reminderTime = edtReminderDate.getText().toString() + " " + edtReminderTime.getText().toString();
        try {
            Date timeReminder = format.parse(reminderTime);
            alreadyNote.setReminderTime(timeReminder);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        NoteDatabase.getSNoteDatabase(getApplicationContext())
                .noteDao().update(alreadyNote);

        setAlarm(alreadyNote);

        insertUpdateNoteTagId(alreadyNote);

        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
    }

    private void setBackGroundNoteColor(int color) {
        layoutNoteDetail.setBackgroundColor(color);
        // Convert color from int to string hex
        selectedNoteColor = Utils.ColorIntToString(color);
    }

    public void onClickColor(View view) {
        int color = new Component().getColorFromColorChooser(view, getApplicationContext());
        setBackGroundNoteColor(color);
    }

    private void setViewAndEditNote() {
        editTextNoteTitle.setText(alreadyNote.getTitle());
        editTextNoteSubtitle.setText(alreadyNote.getSubTitle());
        editTextNoteContent.setText(alreadyNote.getNote());
        selectedNoteColor = alreadyNote.getColor();
        if (selectedNoteColor == null) {
            selectedNoteColor = "#FFFFFF";
        }
        setBackGroundNoteColor(Color.parseColor(selectedNoteColor));

        oldNoteTagForUpdate = NoteDatabase.getSNoteDatabase(getApplicationContext())
                .noteDao().findNoteTagOf(alreadyNote.getId());
        oldNoteTagForUpdate.forEach(e -> {
            getTagIdSet().add(e.getTagId());
        });

        StringBuilder dateBuilder = new StringBuilder("Edited ");
        dateBuilder.append(Utils.DateTimeToString(alreadyNote.getLastUpdate()));
        textViewNoteDetailDatetime.setText(dateBuilder);
    }

    public void deleteNote() {
        if (alreadyNote != null && mode == Const.NoteDetailActivityMode.EDIT) {
            cancelAlarm(alreadyNote);
            alreadyNote.setStatusKey(Const.NoteStatus.BIN);
            NoteDatabase.getSNoteDatabase(getApplicationContext())
                    .noteDao().update(alreadyNote);
            Toast.makeText(this, "Moved to bin", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, ReminderListActivity.class);
            startActivity(intent);
        }
    }

    public void favouriteNote() {
        if (alreadyNote != null) {
            alreadyNote.setStatusKey(Const.NoteStatus.FAVORITE);
            NoteDatabase.getSNoteDatabase(getApplicationContext())
                    .noteDao().update(alreadyNote);
        }
        Toast.makeText(this, "Moved to favourite", Toast.LENGTH_LONG).show();
    }

    public void archiveNote() {
        if (alreadyNote != null) {
            cancelAlarm(alreadyNote);
            alreadyNote.setStatusKey(Const.NoteStatus.ARCHIVE);
            NoteDatabase.getSNoteDatabase(getApplicationContext())
                    .noteDao().update(alreadyNote);
        }
        Toast.makeText(this, "Archived", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, ReminderListActivity.class);
        startActivity(intent);
    }

    private void setAlarm(Note note) {
        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);                   //assigning alarm manager object to set alarm
        Intent intent = new Intent(getApplicationContext(), AlarmBroadcast.class);
        SimpleDateFormat dateFormat = new SimpleDateFormat("d-M-yyyy");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        intent.putExtra("noteId", note.getId());                                                       //sending data to alarm class to create channel and notification
        intent.putExtra("time", dateFormat.format(note.getReminderTime()));
        intent.putExtra("date", timeFormat.format(note.getReminderTime()));
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, PendingIntent.FLAG_ONE_SHOT);
        String dateandtime = dateFormat.format(note.getReminderTime()) + " " + timeFormat.format(note.getReminderTime());
        DateFormat formatter = new SimpleDateFormat("d-M-yyyy HH:mm");
        try {
            Date date1 = formatter.parse(dateandtime);
            am.setExact(AlarmManager.RTC_WAKEUP, date1.getTime(), pendingIntent);
            Toast.makeText(getApplicationContext(), "Alarm set", Toast.LENGTH_SHORT).show();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Intent intentBack = new Intent(getApplicationContext(), ReminderListActivity.class);                //this intent will be called once the setting alarm is complete
        startActivity(intentBack);                                                                  //navigates from adding reminder activity to mainactivity
    }

    private void cancelAlarm(Note note) {
        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);                   //assigning alarm manager object to set alarm
        Intent intent = new Intent(getApplicationContext(), AlarmBroadcast.class);
        SimpleDateFormat dateFormat = new SimpleDateFormat("d-M-yyyy");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        intent.putExtra("noteId", note.getId());                                                       //sending data to alarm class to create channel and notification
        intent.putExtra("time", dateFormat.format(note.getReminderTime()));
        intent.putExtra("date", timeFormat.format(note.getReminderTime()));
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, PendingIntent.FLAG_ONE_SHOT);
        am.cancel(pendingIntent);
        Toast.makeText(getApplicationContext(), "Alarm canceled", Toast.LENGTH_SHORT).show();
    }
}