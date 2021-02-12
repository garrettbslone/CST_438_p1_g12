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

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

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
        assertNull(user.addToDB(dao, appContext));
        assertEquals(user, dao.getUserByUsername(user.getUsername()));

        // test that duplicates can't be added
        assertNotNull(user.addToDB(dao, appContext));
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
}
