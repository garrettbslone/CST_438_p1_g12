package com.group12.project1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

/**@Contributor: Jim Cabrera
 *
 * Results.java: This file receives api info from job search and displays it.
 * */
public class Results extends AppCompatActivity {

    private TextView cable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);


        String infoStream = getIntent().getStringExtra("JOB_API_DISPLAY");
        cable = findViewById(R.id.restList);
        cable.setText(infoStream);
    }
}