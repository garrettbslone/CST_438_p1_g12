/**
 * @author: Garrett
 * @since: 2/2/2021
 * project: Project1
 * description:
 */
package com.group12.project1;

import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class UnitTests {
    @Test
    public void usernameIsValid() {
        String bad1 = " badusername11";
        String bad2 = "2bad";
        String bad3 = "## _#EFFE3_";

        assertFalse(CreateAccountActivity.usernameIsValid(bad1));
        assertFalse(CreateAccountActivity.usernameIsValid(bad2));
        assertFalse(CreateAccountActivity.usernameIsValid(bad3));

        String good1 = "MyUsername";
        String good2 = "Username241";
        String good3 = "testuser";

        assertTrue(CreateAccountActivity.usernameIsValid(good1));
        assertTrue(CreateAccountActivity.usernameIsValid(good2));
        assertTrue(CreateAccountActivity.usernameIsValid(good3));
    }

    @Test
    public void passwordIsValid() {
        String bad1 = "bad password";
        String bad2 = "2bad";
        String bad3 = "## _#_";

        assertFalse(CreateAccountActivity.passwordIsValid(bad1));
        assertFalse(CreateAccountActivity.passwordIsValid(bad2));
        assertFalse(CreateAccountActivity.passwordIsValid(bad3));

        String good1 = "Mypassword";
        String good2 = "testPAss#R34321##@!(M$#)";
        String good3 = "!((*#!BRNR#(**@#R)";

        assertTrue(CreateAccountActivity.passwordIsValid(good1));
        assertTrue(CreateAccountActivity.passwordIsValid(good2));
        assertTrue(CreateAccountActivity.passwordIsValid(good3));
    }

    @Test
    public void changeUserPrefs() {
        User user = new User("user", "pass", false);

        assertNull(user.getPrefs());

        SearchPreferences prefs = new SearchPreferences("lang", "loc", true);
        user.setPrefs(prefs);

        assertEquals(new SearchPreferences("lang", "loc", true), user.getPrefs());

        HashMap<String, String> qMap = new HashMap<>();
        qMap.put("description", "lang");
        qMap.put("location", "loc");
        qMap.put("full_time", "true");

        assertEquals(qMap, user.getPrefs().toQueryMap());
    }

}
