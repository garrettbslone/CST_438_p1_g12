package com.group12.project1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.group12.project1.db.AppDAO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserMainMenuActivity extends AppCompatActivity {
    private Button mMenuSearchBtn;
    private Button mEditAccBtn;
    private Button mAdminBtn;
    private Button mSavedJobsBtn;
    private Button RecommendBtn;
    private User mUser;
    public static List<Job> SAVED_JOBS;
    public static List<Job> RECOMMENDED_JOBS;
    private AppDAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_main_menu);
        SAVED_JOBS = new ArrayList<>();
        RECOMMENDED_JOBS = new ArrayList<>();
        dao = Util.getDAO(this);
        mUser = getUser();

        if(mUser.getPrefs() !=null)
            search(mUser.getPrefs().toQueryMap());

        if (mUser.getSavedJobs() != null)
            SAVED_JOBS = Job.getJobsFromAPI(mUser.getSavedJobs());



        RecommendBtn = findViewById(R.id.RecommendBtn);
        mMenuSearchBtn = findViewById(R.id.MenuSearchBtn);
        mEditAccBtn = findViewById(R.id.EditAccBtn);
        mAdminBtn = findViewById(R.id.AdminBtn);
        mSavedJobsBtn = findViewById(R.id.SavedJobsBtn);

        RecommendBtn.setOnClickListener(v -> {
            EditAccountActivity.setSearch(false);
            startActivity(DisplayApiActivity.intentFactory(this));
        });

        mMenuSearchBtn.setOnClickListener(v -> {
            EditAccountActivity.setSearch(true);
            startActivity(EditAccountActivity.intentFactory(this));
        });

        mEditAccBtn.setOnClickListener(v -> {
            EditAccountActivity.setSearch(false);
            startActivity(EditAccountActivity.intentFactory(this));
        });

        mSavedJobsBtn.setOnClickListener(v -> {
            startActivity(SavedJobsActivity.intentFactory(this));
        });

        if (mUser.isAdmin())
            mAdminBtn.setVisibility(View.VISIBLE);
        mAdminBtn.setOnClickListener(v -> {
            startActivity(AdminActivity.intentFactory(this));
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

    /**
     * Search for Jobs using the GitHub jobs api.
     * @param params a HashMap of Strings representing the query parameters
     */
    public void search(HashMap<String, String> params) {
        GitHubJobsAPI api = Util.getAPI();
        Call<List<Job>> call = api.searchJobs(params);

        call.enqueue(new Callback<List<Job>>() {
            @Override
            public void onResponse (Call<List<Job>> call, Response<List<Job>> response) {
                if (!response.isSuccessful()) {
                    Log.e("HTTP Call fail", response.code() + ": " + response.message());
                    return;
                }

                RECOMMENDED_JOBS = response.body();
            }

            @Override
            public void onFailure (Call<List<Job>> call, Throwable t) {
                Log.e("HTTP Call fail", t.getMessage());
            }
        });
    }

}