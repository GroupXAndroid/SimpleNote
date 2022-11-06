package com.groupx.simplenote.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.groupx.simplenote.R;

public class SearchActivity extends AppCompatActivity {

    private EditText etSearch;
    private TextView tvSearch;
    private Button btnSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        etSearch = findViewById(R.id.etSearch);
        tvSearch = findViewById(R.id.tvSearch);
        btnSearch = findViewById(R.id.btnSearch);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txtSearch = etSearch.getText().toString();
                Intent intent = new Intent(getApplicationContext(), NoteListActivity.class);
                intent.putExtra("txtSearch", "%" + txtSearch + "%");
                startActivity(intent);
            }
        });
    }
}
