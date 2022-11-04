package com.groupx.simplenote.activity;

import androidx.appcompat.app.AppCompatActivity;
import com.groupx.simplenote.R;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;

public class NotificationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        ImageView back = findViewById(R.id.imageSettingNotificationBack);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        switchNotification();
    }

    public void switchNotification(){
        Switch btnSwitch = findViewById(R.id.switch_notification);
        btnSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // do something, the isChecked will be
                // true if the switch is in the On position
                if (isChecked){

                } else {

                }
            }
        });
    }
}