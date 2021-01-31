package com.group12.project1.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.group12.project1.Job;
import com.group12.project1.User;

import java.util.List;

@Dao
public interface AppDAO {
    @Insert
    void insert(User... users);

    @Update
    void update(User... users);

    @Delete
    void delete(User user);

    @Query("SELECT * FROM " + AppDatabase.USER_TABLE)
    List<User> getAllUsers();

    @Query("SELECT * FROM " + AppDatabase.USER_TABLE + " WHERE mUsername =:username")
    User getUserByUsername(String username);

    @Query("SELECT * FROM "+ AppDatabase.USER_TABLE + " WHERE mUserId =:userId")
    User getUserByUserId(int userId);

    @Insert
    void insert(Job... jobs);

    @Delete
    void delete(Job job);

    @Query("SELECT * FROM " + AppDatabase.JOBS_TABLE + " WHERE mUserId =:userId")
    List<Job> getSavedJobs(int userId);

    @Query("SELECT * FROM " + AppDatabase.JOBS_TABLE + " WHERE mUserId =:userId AND mId =:Id")
    Job getJobIfSaved(int userId, String Id );
}
