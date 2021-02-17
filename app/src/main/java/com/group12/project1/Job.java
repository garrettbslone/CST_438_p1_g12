package com.group12.project1;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**Job: POJO that saves the user and job information for later use.
 * Contributor: Jim Cabrera
 * */

public class Job {

    /**@Params: The variables and parameters match the variables and parameters from the
     * Jobs API.
     * The "how_to_apply" and "company_logo" variables were dropped as they were unneeded
     * for our purposes.
     * */

    private String id;
    private String type;
    private String url;
    private String created_at;
    private String company;
    private String company_url;
    private String location;
    private String title;
    private String description;

    public Job(String id, String type, String url, String created_at,
               String company, String company_url, String location,
               String title, String description)
    {
        this.id = id;
        this.type = type;
        this.url = url;
        this.created_at = created_at;
        this.company = company;
        this.company_url = company_url;
        this.location = location;
        this.title = title;
        this.description = description;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCompany_url() {
        return company_url;
    }

    public void setCompany_url(String company_url) {
        this.company_url = company_url;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Job{" +
                ", Id: " + id + '\'' +
                ", Type: " + type + '\'' +
                ", Url: " + url + '\'' +
                ", Created_at: " + created_at + '\'' +
                ", Company name: " + company + '\'' +
                ", Company url: " + company_url + '\'' +
                ", Location: " + location + '\'' +
                ", Job title: " + title + '\'' +
                ", Job description:" + description + '\'' +
                '}';
    }

    public static List<Job> getJobsFromAPI(List<String> jobIds) {
        List<Job> jobs = new ArrayList<>();
        for (String jobId : jobIds) {
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
}

