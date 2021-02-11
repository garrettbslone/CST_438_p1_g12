package com.group12.project1;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.group12.project1.db.AppDAO;
import com.group12.project1.db.AppDatabase;

import java.util.List;
import java.util.Objects;

/**
 * UserPOJO
 * This class is for the user's information. It stores the user's id, username, password, whether the user
 * is admin, and a list of all the jobIds the user has saved.
 */
@Entity(tableName = AppDatabase.USER_TABLE)
public class User {
    @PrimaryKey ()
    @NonNull
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

    /**
     * Add the user to the database.
     * @param dao the dao to access the RoomDB
     * @return    on error, a String containing the corresponding message is returned,
     *            on success, null is returned
     */
    public String addToDB(AppDAO dao) {
        if (dao.getUserByUsername(this.mUsername) != null) {
            return "Username '" + this.mUsername + "' already exists!";
        }

        dao.insert(this);

        if (dao.getUserByUsername(this.mUsername) == null) {
            return "Error: Account could not be created!";
        }

        return null;
    }

    @Override
    public boolean equals (Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(mUsername, user.mUsername);
    }

    @Override
    public int hashCode () {
        return Objects.hash(mUsername);
    }
}
