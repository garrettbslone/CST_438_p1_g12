package com.group12.project1;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.group12.project1.db.AppDatabase;

import java.util.List;

@Entity(tableName = AppDatabase.USER_TABLE)
public class User {
    @PrimaryKey (autoGenerate = true)
    private int mUserId;
    private String mUsername;
    private String mPassword;
    private boolean mAdmin;


    public User(String mUsername, String mPassword, boolean mAdmin) {
        this.mUsername = mUsername;
        this.mPassword = mPassword;
        this.mAdmin = mAdmin;
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
