package com.group12.project1.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.group12.project1.Converters;
import com.group12.project1.User;

import java.util.List;

@Database(entities = {User.class}, version = 1 )
@TypeConverters(Converters.class)
public abstract class AppDatabase extends RoomDatabase {
    public static final String DB_NAME = "P1_DATABASE";
    public static final String USER_TABLE = "USER_TABLE";
    public abstract AppDAO getDAO();
}
