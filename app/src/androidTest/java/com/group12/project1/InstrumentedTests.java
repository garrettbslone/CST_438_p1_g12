/*
 * author: Garrett
 * date: 2/7/2021
 * project: Project1
 * description:
 */
package com.group12.project1;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.test.platform.app.InstrumentationRegistry;

import com.group12.project1.db.AppDAO;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;

import static org.junit.Assert.*;

public class InstrumentedTests {
    private Context appContext;

    @Before
    public void init() {
        // Context of the app under test.
        appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
    }

    @Test
    public void createAccountTest() {
        AppDAO dao = Util.getDAO(appContext);

        User user = new User("testuser", "testpass", false);

        // test that user can be stored
        dao.delete(user);
        assertNotNull(user.addToDB(dao));
        assertEquals(user, dao.getUserByUsername(user.getUsername()));

        // test that duplicates can't be added
        assertNull(user.addToDB(dao));
    }

    @Test
    public void jobSearchTest() throws IOException {
        HashMap<String, String> params = new HashMap<>();
        GitHubJobsAPI api = Util.getAPI();
        
        Call<List<Job>> call = api.searchJobs(params);
        assertNotNull(call.execute());

        params.put("type","java");
        params.put("location", "sf");

        Call<List<Job>> call2 = api.searchJobs(params);
        assertNotNull(call2.execute());
    }

    @Test
    public void persistentSignInTest() {
        SharedPreferences sharedPrefs = appContext.getSharedPreferences(
                User.PREFS_TBL_NAME, Context.MODE_PRIVATE);
        AppDAO dao = Util.getDAO(appContext);

        // store the currently signed in User
        String signedInUsername = sharedPrefs.getString(User.PREFS_UNAME, "");
        SharedPreferences.Editor prefEdit = sharedPrefs.edit();
        prefEdit.remove(User.PREFS_UNAME);
        prefEdit.apply();

        // add the test User to the db
        User user = new User("testuser", "testpass", false);
        dao.delete(user);
        dao.insert(user);

        // make sure nobody is "signed in" yet
        assertNull(User.getSignedInUser(sharedPrefs, dao));

        // "sign in" the test User
        user.signIn(sharedPrefs.edit());
        assertEquals(user, User.getSignedInUser(sharedPrefs, dao));

        // "sign out" the test User
        user.signOut(sharedPrefs.edit());
        assertNull(User.getSignedInUser(sharedPrefs, dao));

        // restore the real signed in User
        if (!signedInUsername.isEmpty()) {
            prefEdit.putString(User.PREFS_UNAME, signedInUsername);
            prefEdit.apply();
        }
    }
}
