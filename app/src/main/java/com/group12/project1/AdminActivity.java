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

        //To be deleted
        test();
        //

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

    //TO BE DELETED
    public void test(){
        mAppDAO = Util.getDAO(this);
        User testuser = new User("waterfall", "waterfall", true);
        User testuser2 = new User("success", "success", true);
        User testuser3 = new User("bob", "bob", false);
        User testuser4 = new User("wendy", "bob", false);
        User testuser5 = new User("john", "bob", false);
        User testuser6 = new User("bobby", "bob", false);

        List<String> saved = new ArrayList<>();
        saved.add("e28fcfec-778f-466f-997a-b8f46a0b8023");
        saved.add("c50578f3-26c6-4237-9777-b4e13f85cf21");
        saved.add("a0326666-9a5a-473d-bce3-546c69c676d5");
        saved.add("a23fa201-23fd-4981-900d-32fdb8b81da0");
        testuser.setSavedJobs(saved);
        testuser.addToDB(mAppDAO);

        List<String> saved2 = new ArrayList<>();
        saved2.add("5a59ed8b-69bc-4919-8ad9-9cec588fb0fd");
        saved2.add("c50578f3-26c6-4237-9777-b4e13f85cf21");
        saved2.add("a0326666-9a5a-473d-bce3-546c69c676d5");
        saved2.add("a23fa201-23fd-4981-900d-32fdb8b81da0");
        testuser2.setSavedJobs(saved2);
        testuser2.addToDB(mAppDAO);

        List<String> saved3 = new ArrayList<>();
        saved3.add("e28fcfec-778f-466f-997a-b8f46a0b8023");
        saved3.add("c50578f3-26c6-4237-9777-b4e13f85cf21");
        saved3.add("a0326666-9a5a-473d-bce3-546c69c676d5");
        saved3.add("a23fa201-23fd-4981-900d-32fdb8b81da0");
        testuser3.setSavedJobs(saved3);
        testuser3.addToDB(mAppDAO);

        List<String> saved4 = new ArrayList<>();
        saved4.add("e28fcfec-778f-466f-997a-b8f46a0b8023");
        saved4.add("c50578f3-26c6-4237-9777-b4e13f85cf21");
        saved4.add("a0326666-9a5a-473d-bce3-546c69c676d5");
        saved4.add("a23fa201-23fd-4981-900d-32fdb8b81da0");
        testuser4.setSavedJobs(saved4);
        testuser4.addToDB(mAppDAO);

        List<String> saved5 = new ArrayList<>();
        saved5.add("e28fcfec-778f-466f-997a-b8f46a0b8023");
        saved5.add("c50578f3-26c6-4237-9777-b4e13f85cf21");
        saved5.add("a0326666-9a5a-473d-bce3-546c69c676d5");
        saved5.add("a23fa201-23fd-4981-900d-32fdb8b81da0");
        testuser5.setSavedJobs(saved5);
        testuser5.addToDB(mAppDAO);

        List<String> saved6 = new ArrayList<>();
        saved6.add("e28fcfec-778f-466f-997a-b8f46a0b8023");
        saved6.add("c50578f3-26c6-4237-9777-b4e13f85cf21");
        saved6.add("a0326666-9a5a-473d-bce3-546c69c676d5");
        saved6.add("a23fa201-23fd-4981-900d-32fdb8b81da0");
        testuser6.setSavedJobs(saved6);
        testuser6.addToDB(mAppDAO);
    }
    //
}