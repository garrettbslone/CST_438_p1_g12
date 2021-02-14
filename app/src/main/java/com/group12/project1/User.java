package com.group12.project1;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
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
    // To be used to access the user and their preferences when needed.
    @Ignore
    private static User sSignedInUser = null;

    // SharedPreferences constants
    @Ignore
    public static final String PREFS_TBL_NAME = "github_job_prefs";
    @Ignore
    public static final String PREFS_UNAME = "uname";

    @PrimaryKey ()
    @NonNull
    private String mUsername;
    private String mPassword;
    private boolean mAdmin;
    private SearchPreferences mPrefs;
    private List<String> savedJobs;

    public User(String mUsername, String mPassword, boolean mAdmin) {
        this.mUsername = mUsername;
        this.mPassword = mPassword;
        this.mAdmin = mAdmin;
        this.mPrefs = null;
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

    public SearchPreferences getPrefs () {
        return mPrefs;
    }

    public void setPrefs (SearchPreferences prefs) {
        mPrefs = prefs;
    }

    public boolean isAdmin() {
        return mAdmin;
    }

    public void setAdmin(boolean mAdmin) {
        this.mAdmin = mAdmin;
    }

    /**
     * Sign in the current User object (for use throughout the app).
     */
    public void signIn (SharedPreferences.Editor prefEdit) {
        User.sSignedInUser = this;

        prefEdit.putString(User.PREFS_UNAME, this.mUsername);
        prefEdit.apply();
    }

    /**
     * Sign out the current User object (for use throughout the app).
     */
    public void signOut (SharedPreferences.Editor prefEdit) {
        User.sSignedInUser = null;

        prefEdit.remove(User.PREFS_UNAME);
        prefEdit.apply();
    }

    /**
     * Get the currently signed in User (for use throughout the app).
     * @return the User that is currently signed in
     */
    public static User getSignedInUser (SharedPreferences sharedPrefs, AppDAO dao) {
        if (User.sSignedInUser == null) {
             String username = sharedPrefs.getString(User.PREFS_UNAME, "");
             User.sSignedInUser = !username.isEmpty() ? dao.getUserByUsername(username) : null;
        }

        return User.sSignedInUser;
    }

    /**
     * Add the user to the database.
     * @param dao the dao to access the RoomDB
     * @return    on success the current User object is returned,
     *            on failure null is returned
     */
    public User addToDB(AppDAO dao) {
        if (dao.getUserByUsername(this.mUsername) != null) {
            Log.e("Create Account", "Username '" + this.mUsername + "' already exists!");
            return null;
        }

        dao.insert(this);

        if (dao.getUserByUsername(this.mUsername) == null) {
            Log.e("Create Account", "Error: Account could not be created!");
            return null;
        }

        return this;
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

    @Override
    public String toString () {
        return "User{" +
                "mUsername='" + mUsername + '\'' +
                '}';
    }
}
