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
        JOBS = Job.getJobsFromAPI(mJobIds);

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
     * Factory pattern provided Intent to switch to this activity.
     * @param ctx the Context to switch from
     * @return    the Intent to switch to this activity
     */
    public static Intent intentFactory(Context ctx) {
        Intent intent = new Intent(ctx, AdminActivity.class);
        return intent;
    }
}