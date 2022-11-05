package com.groupx.simplenote.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.groupx.simplenote.R;
import com.groupx.simplenote.dao.AccountDao;
import com.groupx.simplenote.database.NoteDatabase;
import com.groupx.simplenote.entity.Account;
import com.groupx.simplenote.entity.Note;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class RegisterActivity extends AppCompatActivity {

    private EditText etUserName, etPassword, etAvatar, etFullName;
    private TextView tvDob;
    private Button btnRegister;
    private Button btnLogin;

    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_register);

        etUserName = findViewById(R.id.etUserName);
        etPassword = findViewById(R.id.etPassword);
        etAvatar = findViewById(R.id.etAvatar);
        etFullName = findViewById(R.id.etFullName);
        tvDob = findViewById(R.id.tvDob);
        btnRegister = findViewById(R.id.btnRegister);
        btnLogin = findViewById(R.id.btnLogin);
        /*
         * Date picker
         * */
        final Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        tvDob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog dialog = new DatePickerDialog(RegisterActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month + 1;
                        String date = year + "-" + month + "-" + day;
                        tvDob.setText(date);
                    }
                }, year, month, day);
                dialog.show();
            }
        });

        /*
         * Register
         * */
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Account accountEntity = new Account();
                accountEntity.setUsername(etUserName.getText().toString());
                accountEntity.setPassword(etPassword.getText().toString());
                accountEntity.setAvatarImagePath(etAvatar.getText().toString());
                try {
                    accountEntity.setDob(format.parse(tvDob.getText().toString()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                accountEntity.setFullName(etFullName.getText().toString());
                if(validateInput(accountEntity)){
                    //insert to database
                    NoteDatabase noteDatabase = NoteDatabase.getSNoteDatabase(getApplicationContext());
                    AccountDao accountDao = noteDatabase.accountDao();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            accountDao.registerAccount(accountEntity);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getApplicationContext(), "User Registered", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }).start();
                }else{
                    Toast.makeText(getApplicationContext(), "Fill all fields!", Toast.LENGTH_SHORT).show();
                }
            }
        });
/*        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });*/
    }
    private Boolean validateInput(Account accountEntity){
        if(accountEntity.getUsername().isEmpty()
                || accountEntity.getPassword().isEmpty()
                || accountEntity.getAvatarImagePath().isEmpty()
                || accountEntity.getDob() ==  null
                || accountEntity.getFullName().isEmpty()){
            return false;
        }
        return true;
    }
}










