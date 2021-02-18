package com.group12.project1;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;

import com.group12.project1.db.AppDAO;

public class MainActivity extends AppCompatActivity {
    private Button mCreateAccBtn;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // go to the main menu if they are signed in
        if (this.resume()) {
            startActivity(UserMainMenuActivity.intentFactory(getApplicationContext()));
        }

        mCreateAccBtn = findViewById(R.id.CreateAccBtn);

        mCreateAccBtn.setOnClickListener(v -> {
            startActivity(CreateAccountActivity.intentFactory(getApplicationContext()));
        });
    }

    /**
     * Check a see if a User is still signed in or not and register the object
     * associated with them if they are.
     * @return a boolean representing whether or not a user is signed in
     */
    @SuppressLint ("CommitPrefEdits")
    private boolean resume() {
        SharedPreferences sharedPrefs = getSharedPreferences(User.PREFS_TBL_NAME, Context.MODE_PRIVATE);
        AppDAO dao = Util.getDAO(getApplicationContext());

        User user;
        if ((user = User.getSignedInUser(sharedPrefs, dao)) != null) {
            user.signIn(sharedPrefs.edit());
            return true;
        }

        return false;
    }
}