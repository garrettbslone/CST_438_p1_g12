/**
 * @author: Garrett
 * @since: 2/2/2021
 * project: Project1
 * description:
 */
package com.group12.project1;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

import androidx.room.Room;

import com.group12.project1.db.AppDAO;
import com.group12.project1.db.AppDatabase;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Util {
    private static GitHubJobsAPI apiSingleton = null;

    /**
     * Returns a text Toast object for the context being passed in.
     * @param ctx the context that this method is being called from
     * @param str the message to be displayed in the Toast
     * @return    the Toast object with str message ready to be displayed centered in ctx
     */
    public static Toast toastMaker(Context ctx, String str) {
        Toast toast = Toast.makeText(ctx, str, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        return toast;
    }

    /**
     * Build the RoomDB and reutrn the associated dao.
     * @param ctx the Context we are being called from
     * @return    the AppDAO ready for queries
     */
    public static AppDAO getDAO(Context ctx) {
        return Room.databaseBuilder(ctx, AppDatabase.class, AppDatabase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getDAO();
    }

    /**
     * Get the Retrofit API wrapper singleton object,
     * and create it if it doesn't exist.
     * @return the GitHubJobsAPI interface ready to make calls
     */
    public static GitHubJobsAPI getAPI() {
        if (Util.apiSingleton == null) {
            Util.apiSingleton = new Retrofit.Builder()
                    .baseUrl(GitHubJobsAPI.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(GitHubJobsAPI.class);
        }

        return Util.apiSingleton;
    }
}
