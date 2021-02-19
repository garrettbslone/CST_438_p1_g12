package com.group12.project1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.group12.project1.db.AppDAO;

import java.util.ArrayList;
import java.util.List;

public class UserMainMenuActivity extends AppCompatActivity {
    private Button mMenuSearchBtn;
    private Button mEditAccBtn;
    private Button mAdminBtn;
    private Button mSavedJobsBtn;
    private Button mLogOutBtn;
    private User mUser;
    public static List<Job> SAVED_JOBS;
    private AppDAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_main_menu);
        SAVED_JOBS = new ArrayList<>();
        dao = Util.getDAO(this);
        mUser = getUser();

        if (mUser.getSavedJobs() != null)
            SAVED_JOBS = Job.getJobsFromAPI(mUser.getSavedJobs());


        mMenuSearchBtn = findViewById(R.id.MenuSearchBtn);
        mEditAccBtn = findViewById(R.id.EditAccBtn);
        mAdminBtn = findViewById(R.id.AdminBtn);
        mSavedJobsBtn = findViewById(R.id.SavedJobsBtn);
        mLogOutBtn = findViewById(R.id.LogOutBtn1);


        mMenuSearchBtn.setOnClickListener(v -> {
            startActivity(JobSearchActivity.intentFactory(getApplicationContext()));
        });

        mEditAccBtn.setOnClickListener(v -> {
            startActivity(EditAccountActivity.intentFactory(getApplicationContext()));
        });

        mSavedJobsBtn.setOnClickListener(v -> {
            startActivity(SavedJobsActivity.intentFactory(this));
        });

        if (mUser.isAdmin())
            mAdminBtn.setVisibility(View.VISIBLE);
        mAdminBtn.setOnClickListener(v -> {
            startActivity(AdminActivity.intentFactory(this));
        });

        mLogOutBtn.setOnClickListener(view -> {
            SharedPreferences sharedPrefs = getSharedPreferences(User.PREFS_TBL_NAME, Context.MODE_PRIVATE);
            mUser.signOut(sharedPrefs.edit());
            startActivity(MainActivity.intentFactory(this));
        });
    }

    /**
     * Factory pattern provided Intent to switch to this activity.
     *
     * @param ctx the Context to switch from
     * @return the Intent to switch to this activity
     */
    public static Intent intentFactory(Context ctx) {
        Intent intent = new Intent(ctx, UserMainMenuActivity.class);
        return intent;
    }

    public static void updateJobs(List<String> jobIds) {
        SAVED_JOBS = Job.getJobsFromAPI(jobIds);
    }

    public User getUser() {
        SharedPreferences sharedPrefs = getSharedPreferences(User.PREFS_TBL_NAME, Context.MODE_PRIVATE);

        return User.getSignedInUser(sharedPrefs, dao);
    }
}