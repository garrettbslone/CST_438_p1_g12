package com.group12.project1.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.group12.project1.Job;
import com.group12.project1.User;

@Database(entities = {User.class, Job.class}, version = 1 )
public abstract class AppDatabase extends RoomDatabase {
    public static final String DB_NAME = "P1_DATABASE";
    public static final String USER_TABLE = "USER_TABLE";
    public static final String JOBS_TABLE = "JOBS_TABLE";
    public abstract AppDAO getDAO();
}
