package com.group12.project1;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.group12.project1.db.AppDAO;

import java.util.List;

public class SavedJobsActivity extends AppCompatActivity {
    private List<User> mUsers;
    private String[] mJobNamesArr;
    private AppDAO mAppDAO;
    private ListView mListView;
    private ArrayAdapter<String> mArrayAdapter;
    private List<Job> mJobs;
    private User mUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_jobs);

        wireDisplay();
    }

    public void wireDisplay() {
        mAppDAO = Util.getDAO(this);
        mJobs = UserMainMenuActivity.SAVED_JOBS;
        mUser = getUser();
        mJobNamesArr = getJobNames(mJobs);
        mListView = findViewById(R.id.list_item);
        mArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, mJobNamesArr);
        mListView.setAdapter(mArrayAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String name = adapterView.getItemAtPosition(i).toString();
                String message = "";
                for (Job job : mJobs) {
                    if (job.getCompany().equals(name)) {
                        message = getJobInfo(job);
                    }
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(SavedJobsActivity.this);
                builder.setMessage(message).setCancelable(false)
                        .setPositiveButton("Close", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        })
                        .setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                for (Job job : mJobs) {
                                    if (job.getCompany().equals(name)) {
                                        mUser.rmJob(job.getId(), mAppDAO);
                                        dialog.cancel();
                                        Toast.makeText(SavedJobsActivity.this, name + " removed", Toast.LENGTH_SHORT).show();
                                        UserMainMenuActivity.updateJobs(mUser.getSavedJobs());
                                        onBackPressed();
                                    }
                                }
                            }
                        });
                AlertDialog alert = builder.create();
                alert.setTitle(name);
                alert.show();
            }
        });
    }
    /**
     * returns all the names of all the jobs that were applied to.
     *
     * @param jobs List of objects of Type Job.
     * @return Array of Strings of Jobs' company name.
     */
    public String[] getJobNames(List<Job> jobs) {
        String[] names = new String[jobs.size()];
        for (int i = 0; i < jobs.size(); i++) {
            names[i] = jobs.get(i).getCompany();
        }
        return names;
    }

    /**
     * Returns information about a job for the alert dialogue.
     *
     * @param job the job's information to be displayed
     * @return
     */
    public String getJobInfo(Job job) {
        String info = "Title: "+job.getTitle()+ "<br>Location: "+job.getLocation()+"<br>Type: "+job.getType()+"<br><br>Description<br>"+ job.getDescription();
        return Html.fromHtml(info).toString();
    }

    /**
     * Factory pattern provided Intent to switch to this activity.
     * @param ctx the Context to switch from
     * @return    the Intent to switch to this activity
     */
    public static Intent intentFactory(Context ctx) {
        Intent intent = new Intent(ctx, SavedJobsActivity.class);
        return intent;
    }

    public User getUser() {
        SharedPreferences sharedPrefs = getSharedPreferences(User.PREFS_TBL_NAME, Context.MODE_PRIVATE);

        return User.getSignedInUser(sharedPrefs, mAppDAO);
    }
}