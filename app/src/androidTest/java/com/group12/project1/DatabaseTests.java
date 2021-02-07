/*
 * author: Garrett
 * date: 2/7/2021
 * project: Project1
 * description:
 */
package com.group12.project1;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;

import com.group12.project1.db.AppDAO;

import org.junit.Test;

import static org.junit.Assert.*;

public class DatabaseTests {
    @Test
    public void createAccountTest () {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        AppDAO dao = Util.getDAO(appContext);

        User user = new User("testuser", "testpass", false);

        // test that user can be stored
        dao.delete(user);
        assertNull(user.addToDB(dao));
        assertEquals(user, dao.getUserByUsername(user.getUsername()));

        // test that duplicates can't be added
        assertNotNull(user.addToDB(dao));
    }
}
