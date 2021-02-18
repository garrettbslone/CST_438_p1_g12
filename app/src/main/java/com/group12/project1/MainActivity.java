package com.group12.project1;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.group12.project1.db.AppDAO;

public class MainActivity extends AppCompatActivity {

    EditText mUsername;
    EditText mPassword;

    private Button loginBtn;
    private Button mCreateAccBtn;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // go to the main menu if they are signed in
        if (this.resume()) {
            startActivity(UserMainMenuActivity.intentFactory(getApplicationContext()));
        }

        mUsername = findViewById(R.id.MainUsernameET);
        mPassword= findViewById(R.id.MainPasswordET);
        loginBtn = findViewById(R.id.LogInBtn);

        loginBtn.setOnClickListener(view -> {
                String username = mUsername.getText().toString();
                String password = mPassword.getText().toString();

                if(verifyLogin(username,password)){
                    startActivity(UserMainMenuActivity.intentFactory((getApplicationContext())));
                }
                else{
                    Util.toastMaker(this, "Try Again");
                }

        });

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

    public static Intent intentFactory(Context ctx) {
        Intent intent = new Intent(ctx, MainActivity.class);
        return intent;
    }

     private boolean verifyLogin(String username, String password){
        AppDAO dao = Util.getDAO(getApplicationContext());
        SharedPreferences sharedPrefs = getSharedPreferences(User.PREFS_TBL_NAME, Context.MODE_PRIVATE);
        User user = dao.getUserByUsername(username);

        if(user == null){
            Util.toastMaker(this, "Invalid Username").show();
            mUsername.setText(null);
            mPassword.setText(null);
            return false;
        }

        String truePassword = user.getPassword();
        if(truePassword.equals(password)){
            user.signIn(sharedPrefs.edit());
            return true;
        }
        else{
            Util.toastMaker(this, "Invalid Password").show();
            mPassword.setText(null);
            return false;
        }
    }
}