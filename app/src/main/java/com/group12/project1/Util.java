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

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.room.Room;

import com.group12.project1.db.AppDAO;
import com.group12.project1.db.AppDatabase;

public class Util {
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
    public static AppDAO getDAO (Context ctx) {
        return Room.databaseBuilder(ctx, AppDatabase.class, AppDatabase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getDAO();
    }
}
