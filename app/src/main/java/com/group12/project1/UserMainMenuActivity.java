package com.group12.project1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class UserMainMenuActivity extends AppCompatActivity {
    private Button mMenuSearchBtn;

    private Button mRecommendedBtn;

    private Button mEditAccount;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_main_menu);

        mMenuSearchBtn = findViewById(R.id.MenuSearchBtn);

        mMenuSearchBtn.setOnClickListener(v -> {
            startActivity(JobSearchActivity.intentFactory(getApplicationContext()));
        });

        mRecommendedBtn = findViewById(R.id.RecommendBtn);

        mRecommendedBtn.setOnClickListener(v -> {
            Intent intent = new Intent(UserMainMenuActivity.this, RecommendedJobsActivity.class);
            startActivity(intent);
        });

        mEditAccount = findViewById(R.id.EditAccBtn);

        mEditAccount.setOnClickListener(v -> {
            Intent intent = new Intent(UserMainMenuActivity.this, UserEditAccountActivity.class);
            startActivity(intent);
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