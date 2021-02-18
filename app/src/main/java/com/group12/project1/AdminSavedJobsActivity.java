package com.group12.project1;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.group12.project1.db.AppDAO;

import java.util.ArrayList;
import java.util.List;


public class AdminSavedJobsActivity extends AppCompatActivity {
    private List<User> mUsers;
    private String[] mJobNamesArr;
    private AppDAO mAppDAO;
    private SearchView mSearchView;
    private ListView mListView;
    private ArrayAdapter<String> mArrayAdapter;
    private List<Job> mJobs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_saved_jobs);


        wireDisplay();
    }

    public void wireDisplay() {
        mAppDAO = Util.getDAO(this);
        mUsers = mAppDAO.getAllUsers();
        mJobs = AdminActivity.JOBS;

        mJobNamesArr = getJobNames(mJobs);
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
                        message += "Applicants:";
                        for (User user : mUsers) {
                            if (isSaved(user, job)) {
                                message += " " + user.getUsername() + ", ";
                            }
                        }
                        message = message.substring(0, message.length() - 2);
                    }
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(AdminSavedJobsActivity.this);
                builder.setMessage(message).setCancelable(false)
                        .setPositiveButton("Close", new DialogInterface.OnClickListener() {
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
     * Searches through a user's saved jobs to find if parameter is saved.
     *
     * @param user contains list to be searched.
     * @param job  job in search of.
     * @return
     */
    public boolean isSaved(User user, Job job) {
        List<String> savedJobs = new ArrayList<>();
        savedJobs = user.getSavedJobs();
        if (savedJobs != null) {
            for (String jobId : savedJobs) {
                if (job.getId().equals(jobId)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Factory pattern provided Intent to switch to this activity.
     *
     * @param ctx the Context to switch from
     * @return the Intent to switch to this activity
     */
    public static Intent intentFactory(Context ctx) {
        Intent intent = new Intent(ctx, AdminSavedJobsActivity.class);
        return intent;
    }

    /**
     * Returns information about a job for the alert dialogue.
     *
     * @param job the job's information to be displayed
     * @return
     */
    public String getJobInfo(Job job) {
        String info = (job.getTitle()+"<br>Type:"+job.getType()+"<br><br>");
        return Html.fromHtml(info).toString();
    }

}