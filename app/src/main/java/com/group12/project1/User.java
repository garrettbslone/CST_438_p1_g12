package com.group12.project1;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.group12.project1.db.AppDatabase;

import java.util.List;

/**
 * UserPOJO
 * This class is for the user's information. It stores the user's id, username, password, whether the user
 * is admin, and a list of all the jobIds the user has saved.
 */
@Entity(tableName = AppDatabase.USER_TABLE)
public class User {
    @PrimaryKey (autoGenerate = true)
    private int mUserId;
    private String mUsername;
    private String mPassword;
    private boolean mAdmin;
    private List<String> savedJobs;

    public User(String mUsername, String mPassword, boolean mAdmin) {
        this.mUsername = mUsername;
        this.mPassword = mPassword;
        this.mAdmin = mAdmin;
    }

    public List<String> getSavedJobs() {
        return savedJobs;
    }

    public void setSavedJobs(List<String> savedJobs) {
        this.savedJobs = savedJobs;
    }

    public int getUserId() {
        return mUserId;
    }

    public void setUserId(int mUserId) {
        this.mUserId = mUserId;
    }

    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String mUsername) {
        this.mUsername = mUsername;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String mPassword) {
        this.mPassword = mPassword;
    }

    public boolean isAdmin() {
        return mAdmin;
    }

    public void setAdmin(boolean mAdmin) {
        this.mAdmin = mAdmin;
    }

}
