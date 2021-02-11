package com.group12.project1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.group12.project1.db.AppDAO;

public class MainActivity extends AppCompatActivity {

    private EditText mUsername;
    private EditText mPassword;

    private Button mlogin;
    private Button mCreateAccBtn;

    private AppDAO mAppDAO;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCreateAccBtn = findViewById(R.id.CreateAccBtn);

        mCreateAccBtn.setOnClickListener(v -> {
            startActivity(CreateAccountActivity.intentFactory(getApplicationContext()));
        });

        mUsername = findViewById(R.id.MainUsernameET);
        mPassword= findViewById(R.id.MainPasswordET);

        mlogin = findViewById(R.id.LogInBtn);
        mlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){

                User currentUser = verifyLogin();
                if(currentUser.equals(null) ){

                    mUsername.setText(null);
                    mPassword.setText(null);

                    Toast toast = Toast.makeText(getApplicationContext(), "Incorrect Username or Password", Toast.LENGTH_SHORT);
                    toast.show();

                }else{

                    String username = currentUser.getUsername();
                    Intent intent = new Intent(MainActivity.this, UserMainMenuActivity.class);
                    intent.putExtra("username", username);
                    startActivity(intent);

                }
            }
        });
    }

    private User verifyLogin(){

        mAppDAO = Util.getDAO(this);
        String username = mUsername.getText().toString();
        String password = mPassword.getText().toString();

        User user = mAppDAO.getUserByUsername(username);

        if(user == null)
        {
            return null;
        }

        String truePassword = user.getPassword();
        if(truePassword.equals(password))
        {
            return user;
        }

        return null;
    }

}