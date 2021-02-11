/**
 * @author: Garrett
 * @since: 2/2/2021
 * project: Project1
 * description:
 */
package com.group12.project1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.group12.project1.db.AppDAO;

import java.util.regex.Pattern;

public class CreateAccountActivity extends AppCompatActivity {
    private static Pattern usernameRegex = Pattern.compile(
           "^[a-zA-Z]" +       // starts with a letter
           "(?!.*\\s|.*\\W)" + // contains no whitespace/special chars
           ".{5,29}" +         // between 6 and 30 characters long
           "$");                // end of string

    private static Pattern passwordRegex = Pattern.compile(
            "^*" +       // start of string
            "[^\\s]" +   // contains no whitespace
            "{5,29}" +   // between 6 and 30 characters long
            "$");        // end of string

    private EditText mUsernameET;
    private EditText mPasswordET;
    private EditText mConfPasswordET;
    private Button mCreateBTN;

    private AppDAO mAppDAO;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        mUsernameET = findViewById(R.id.CreateAccUsernameET);
        mPasswordET = findViewById(R.id.CreateAccPasswordET);
        mConfPasswordET = findViewById(R.id.CreateAccConfPasswordET);
        mCreateBTN = findViewById(R.id.CreateAccCreateBTN);

        mAppDAO = Util.getDAO(this);

        mCreateBTN.setOnClickListener(v -> {
            String username = mUsernameET.getText().toString().trim();
            String password = mPasswordET.getText().toString().trim();
            String confPassword = mConfPasswordET.getText().toString().trim();

            if (!usernameIsValid(username)) {
                mUsernameET.setBackgroundColor(Color.RED);
            } else  {
                mUsernameET.setBackgroundColor(Color.WHITE);
            }

            if (!passwordIsValid(password)) {
                mPasswordET.setBackgroundColor(Color.RED);
            } else  {
                mPasswordET.setBackgroundColor(Color.WHITE);
            }

            if (!password.equals(confPassword)) {
                mConfPasswordET.setBackgroundColor(Color.RED);
            } else  {
                mConfPasswordET.setBackgroundColor(Color.WHITE);
            }

            String err;
            if ((err = createAccount(username, password)) != null) {
                Util.toastMaker(getApplicationContext(), err).show();
            } else {
                Util.toastMaker(getApplicationContext(), "account created!").show();
                startActivity(UserMainMenuActivity.intentFactory(getApplicationContext()));
            }
        });
    }

    /**
     * Checks the username against the predefined regex pattern.
     * @param username the username to check
     * @return         a boolean representing if the username is valid
     */
    public static boolean usernameIsValid(String username) {
        return usernameRegex.matcher(username).matches();
    }

    /**
     * Checks the password against the predefined regex pattern.
     * @param password the password to check
     * @return         a boolean representing if the username is valid
     */
    public static boolean passwordIsValid(String password) {
        return passwordRegex.matcher(password).matches();
    }

    /**
     * Creates a user account and saves it in the database.
     * @param username the username of the new account
     * @param password the password of the new account
     * @return         a boolean representing if the account was created and
     *                 its information is stored
     */
    public String createAccount(String username, String password) {
        User user = new User(username, password, false);
        return user.addToDB(mAppDAO);
    }

    /**
     * Factory pattern provided Intent to switch to this activity.
     * @param ctx the Context to switch from
     * @return    the Intent to switch to this activity
     */
    public static Intent intentFactory(Context ctx) {
        Intent intent = new Intent(ctx, CreateAccountActivity.class);
        return intent;
    }
}