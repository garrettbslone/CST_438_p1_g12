package com.group12.project1;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.group12.project1.db.AppDAO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminSavedJobsActivity extends AppCompatActivity {
    private List<User> mUsers;
    private String[] mJobNamesArr;
    private AppDAO mAppDAO;
    private SearchView mSearchView;
    private ListView mListView;
    private ArrayAdapter<String> mArrayAdapter;
    private List<Job> mJobs;
    private List<String> mJobIds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_saved_jobs);
        mAppDAO = Util.getDAO(this);
        mJobs = AdminActivity.JOBS;

        wireDisplay();
    }

    /**
     * Factory pattern provided Intent to switch to this activity.
     * @param ctx the Context to switch from
     * @return    the Intent to switch to this activity
     */
    public static Intent intentFactory(Context ctx) {
        Intent intent = new Intent(ctx, AdminSavedJobsActivity.class);
        return intent;
    }

    public void wireDisplay(){
        mUsers = mAppDAO.getAllUsers();
        Toast.makeText(getApplicationContext(), "size: !"+mJobs.size(), Toast.LENGTH_LONG).show();
        if(mJobs.size()>0) {

            getJobNames();
            mSearchView = findViewById(R.id.search_bar);
            mListView = findViewById(R.id.list_item);
            mArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, mJobNamesArr);
            mListView.setAdapter(mArrayAdapter);
            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    String name = adapterView.getItemAtPosition(i).toString();
                    String message = "";
                    for (Job job : mJobs) {
                        if (job.getCompany().equals(adapterView.getItemAtPosition(i).toString())) {
                            message = getJobInfo(job);
                            message += "Applicants\n";
                            for (User user : mUsers) {
                                if (isSaved(user, job)) {
                                    message += "    " + user.getUsername() + "\n";
                                }
                            }
                        }
                    }
                    AlertDialog.Builder builder = new AlertDialog.Builder(AdminSavedJobsActivity.this);
                    builder.setMessage(message).setCancelable(false)
                            .setPositiveButton("Close", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                }
                            })
                            .setNegativeButton("Delete User", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.setTitle(name);
                    alert.show();
                }
            });
        }
    }

    public void getJobNames() {
        mJobNamesArr = new String[mJobs.size()];
        for (int i = 0; i < mJobs.size(); i++) {
            mJobNamesArr[i] = mJobs.get(i).getCompany();
        }
    }

    public boolean isSaved(User user, Job job){
        List<String> savedJobs = new ArrayList<>();
        savedJobs = user.getSavedJobs();
        if(savedJobs!=null) {
            for (String jobId : savedJobs) {
                if (job.getId().equals(jobId)) {
                    Toast.makeText(getApplicationContext(), jobId, Toast.LENGTH_LONG).show();
                    return true;
                }
            }
        }
        return false;
    }

    public String getJobInfo(Job job){
        return( job.getTitle() + "\n    Type:" + job.getType() + "\n\n" );
    }

}