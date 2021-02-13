package com.group12.project1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.group12.project1.db.AppDAO;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminActivity extends AppCompatActivity {
    private Button mEditUsersBtn;
    private Button mSavedJobs;
    public static List<Job> JOBS = new ArrayList<>();
    private List<String> mJobIds;
    private AppDAO mAppDAO;
    private List<User> mUsers;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        wireDisplay();
    }
    public void wireDisplay(){
        mAppDAO = Util.getDAO(this);
        mJobIds = new ArrayList<>();
        mUsers = mAppDAO.getAllUsers();

        mJobIds = getJobIds(mUsers);
        JOBS = getJobsFromAPI(mJobIds);

        mSavedJobs = findViewById(R.id.buttonAdminSavedJobs);
        mEditUsersBtn = findViewById(R.id.buttonEditUsers);
        mEditUsersBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(EditUsersActivity.intentFactory(getApplicationContext()));
            }
        });
        mSavedJobs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(AdminSavedJobsActivity.intentFactory(getApplicationContext()));
            }
        });
    }

    /**
     * Generates a unique list of jobIds from all users
     * @param users List of objects of type User
     * @return List of strings.
     */
    public List<String> getJobIds(List<User> users){
        List<String> jobIds = new ArrayList<>();
        for(User user: users){
            if(user.getSavedJobs()!=null) {
                for (String Id : user.getSavedJobs()) {
                    if (!jobIds.contains(Id)) {
                        jobIds.add(Id);
                    }
                }
            }
        }
        return jobIds;
    }

    /**
     * Takes a list of user Ids and returns a list of jobs from API
     * @param jobIds List of jobIds to be fed to API
     * @return  List of objects of type User
     */
    public List<Job> getJobsFromAPI(List<String> jobIds){
        List <Job> jobs = new ArrayList<>();
        for(String jobId : jobIds) {
            GitHubJobsAPI api = Util.getAPI();
            Call<Job> call = api.getJob(jobId);
            call.enqueue(new Callback<Job>() {
                @Override
                public void onResponse(Call<Job> call, Response<Job> response) {
                    if (!response.isSuccessful()) {
                        Log.e("HTTP Call fail", response.code() + ": " + response.message());
                        return;
                    }
                    Job job = response.body();
                    jobs.add(job);
                }

                @Override
                public void onFailure(Call<Job> call, Throwable t) {
                    Log.e("HTTP Call fail", t.getMessage());
                }
            });
        }
        return jobs;
    }
    /**
     * Factory pattern provided Intent to switch to this activity.
     * @param ctx the Context to switch from
     * @return    the Intent to switch to this activity
     */
    public static Intent intentFactory(Context ctx) {
        Intent intent = new Intent(ctx, AdminActivity.class);
        return intent;
    }
}