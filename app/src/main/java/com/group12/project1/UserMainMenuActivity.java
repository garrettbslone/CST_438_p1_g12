package com.group12.project1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class UserMainMenuActivity extends AppCompatActivity {
    private Button mMenuSearchBtn;
    private Button mEditAccBtn;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_main_menu);

        mMenuSearchBtn = findViewById(R.id.MenuSearchBtn);
        mEditAccBtn = findViewById(R.id.EditAccBtn);

        mMenuSearchBtn.setOnClickListener(v -> {
            startActivity(JobSearchActivity.intentFactory(getApplicationContext()));
        });

        mEditAccBtn.setOnClickListener(v -> {
            startActivity(EditAccountActivity.intentFactory(getApplicationContext()));
        });
    }

    /**
     * Factory pattern provided Intent to switch to this activity.
     * @param ctx the Context to switch from
     * @return    the Intent to switch to this activity
     */
    public static Intent intentFactory(Context ctx) {
        Intent intent = new Intent(ctx, UserMainMenuActivity.class);
        return intent;
    }
}