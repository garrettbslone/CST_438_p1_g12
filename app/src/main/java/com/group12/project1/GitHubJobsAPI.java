package com.group12.project1;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

public interface GitHubJobsAPI {
    public static final String BASE_URL = "https://jobs.github.com/";

    @GET("positions.json")
    Call<List<Job>> searchJobs(@QueryMap Map<String, String> params);

    @GET("positions/{id}.json")
    Call<Job> getJob(@Path("id") String id);
}
