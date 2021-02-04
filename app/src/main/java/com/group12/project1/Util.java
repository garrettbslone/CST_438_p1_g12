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

public class Util {
    /**
     * Returns a text Toast object for the context being passed in.
     * @param ctx the context that this method is being called from
     * @param str the message to be displayed in the Toast
     * @return    the Toast object with str message ready to be displayed centered in ctx
     */
    public static Toast toastMaker (Context ctx, String str) {
        Toast toast = Toast.makeText(ctx, str, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        return toast;
    }
}
