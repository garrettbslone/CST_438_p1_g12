package com.group12.project1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.group12.project1.db.AppDAO;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JobSearchActivity extends AppCompatActivity {
    private TextView tv;
    private AppDAO mAppDAO;
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_search);
        tv = findViewById(R.id.JobSearchTV);
        tv.setText("Searching...");
        User user = getUser();
        SearchPreferences prefs= user.getPrefs();

        // TODO: get the search params from user's profile
        search(prefs.toQueryMap());
    }

    /**
     * Factory pattern provided Intent to switch to this activity.
     * @param ctx the Context to switch from
     * @return    the Intent to switch to this activity
     */
    public static Intent intentFactory(Context ctx) {
        Intent intent = new Intent(ctx, JobSearchActivity.class);
        return intent;
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
                    tv.setText("Job search failed + " + response.code());
                    return;
                }

                List<Job> jobs = response.body();
                tv.setText(jobs.toString());
            }

            @Override
            public void onFailure (Call<List<Job>> call, Throwable t) {
                Log.e("HTTP Call fail", t.getMessage());
                tv.setText("Failure: " + t.getMessage() + "\n" + t.getLocalizedMessage());
            }
        });
    }

    public User getUser() {
        SharedPreferences sharedPrefs = getSharedPreferences(User.PREFS_TBL_NAME, Context.MODE_PRIVATE);

        return User.getSignedInUser(sharedPrefs, mAppDAO);
    }
}