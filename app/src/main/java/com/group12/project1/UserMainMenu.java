package com.group12.project1;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class UserMainMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_main_menu);

        Intent intent = getIntent();
        final int userId = Integer.parseInt(intent.getStringExtra("userId"));
        String username = intent.getStringExtra("username");
    }
}
