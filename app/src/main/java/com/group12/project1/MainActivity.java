package com.group12.project1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText mUsername;
    EditText mPassword;

    Button login;
    Button createAcc;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mUsername = findViewById(R.id.MainUsernameET);
        mPassword= findViewById(R.id.MainPasswordET);

        login = findViewById(R.id.LogInBtn);
        createAcc = findViewById(R.id.CreateAccBtn);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){

                int userId = verifyLogin();
                if(userId>=0){

                    Intent intent = new Intent(MainActivity.this, UserMainMenu.class);
                    intent.putExtra("userId",Integer.toString(userId));
                    intent.putExtra("username", "din_djarin");
                    startActivity(intent);

                } else{
                    mUsername.setText(null);
                    mPassword.setText(null);

                    Toast toast = Toast.makeText(getApplicationContext(), "Incorrect Username or Password", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
    }

    private int verifyLogin(){

        String username = mUsername.getText().toString();
        String password = mPassword.getText().toString();

        String trueUserName = "din_djarin";
        String truePassword = "baby_yoda_ftw";

        if(trueUserName.equals(username)){
            if(truePassword.equals(password))
            {
                return 1;
            }
        }

        return -1;
    }
}