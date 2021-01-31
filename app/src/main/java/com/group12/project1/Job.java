package com.group12.project1;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.group12.project1.db.AppDatabase;

@Entity(tableName = AppDatabase.JOBS_TABLE)
public class Job {
    @PrimaryKey ()
    @NonNull private String mId;
    private int mUserId;
    private String mTitle;
    private String mCompany;
    private String mType;
    private String mDescription;
    private String mLocation;
    private String mApply;

    public Job(String mId, int mUserId, String mTitle, String mCompany, String mType, String mDescription, String mLocation, String mApply) {
        this.mId = mId;
        this.mUserId = mUserId;
        this.mTitle = mTitle;
        this.mCompany = mCompany;
        this.mType = mType;
        this.mDescription = mDescription;
        this.mLocation = mLocation;
        this.mApply = mApply;
    }

    public String getId() {
        return mId;
    }

    public void setId(String mId) {
        this.mId = mId;
    }

    public int getUserId() {
        return mUserId;
    }

    public void setUserId(int mUserId) {
        this.mUserId = mUserId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getCompany() {
        return mCompany;
    }

    public void setCompany(String mCompany) {
        this.mCompany = mCompany;
    }

    public String getType() {
        return mType;
    }

    public void setType(String mType) {
        this.mType = mType;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public String getLocation() {
        return mLocation;
    }

    public void setLocation(String mLocation) {
        this.mLocation = mLocation;
    }

    public String getApply() {
        return mApply;
    }

    public void setApply(String mApply) {
        this.mApply = mApply;
    }
}
