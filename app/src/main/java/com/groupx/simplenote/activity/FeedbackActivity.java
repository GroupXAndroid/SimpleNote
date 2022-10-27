package com.groupx.simplenote.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.groupx.simplenote.R;

public class FeedbackActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        Button buttonCancel = findViewById(R.id.buttonFeedBackCancel);
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Button buttonSend = findViewById(R.id.buttonFeedBackSend);
        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendFeedBackByMail();
            }
        });
    }

    private void sendFeedBackByMail(){
        TextView textViewName, textViewEmail, textViewComment;
        textViewName = findViewById(R.id.editTextFeedbackName);
        textViewEmail = findViewById(R.id.editTextFeedbackEmail);
        textViewComment = findViewById(R.id.editTextFeedbackComment);

        TextInputLayout layoutTextName, layoutTextEmail, layoutTextComment;
        layoutTextName = findViewById(R.id.inputLayoutTextFeedbackName);
        layoutTextEmail = findViewById(R.id.inputLayoutTextFeedbackEmail);
        layoutTextComment = findViewById(R.id.inputLayoutTextFeedbackComment);

        String name, email, comment;

        name = textViewName.getText().toString().trim();
        email = textViewEmail.getText().toString().trim();
        comment = textViewComment.getText().toString();
        if(name.isEmpty()){layoutTextName.setError("Empty Name");
        } else {
           layoutTextName.setErrorEnabled(false);
        }
        if(email.isEmpty()){
            layoutTextEmail.setError("Empty Email");
        }
        else {
            layoutTextEmail.setErrorEnabled(false);
        }
        if(comment.trim().isEmpty()){
            Toast.makeText(getApplicationContext(), "Empty Comment!", Toast.LENGTH_SHORT).show();
//            ((TextInputLayout)findViewById(R.id.inputLayoutTextFeedbackComment))
//                    .setError("Empty Comment");
        }
        if(!name.isEmpty() && !email.isEmpty() && !comment.trim().isEmpty()){
            sendEmail(email, name, comment);
        }
    }

    protected void sendEmail(String email, String name, String content) {
        String emailTeam = getString(R.string.team_email);
        String[] TO = {emailTeam};
        String[] CC = {email, emailTeam };
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);

        emailIntent.setType("*/*");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);

        String subjectBuilder = "[SimpleNote][Feedback] - " +
                name;
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subjectBuilder);
        emailIntent.putExtra(Intent.EXTRA_TEXT, content);

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            finish();
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }
}