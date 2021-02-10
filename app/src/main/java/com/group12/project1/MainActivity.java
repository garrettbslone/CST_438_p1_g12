package com.group12.project1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

import com.group12.project1.db.AppDAO;

public class MainActivity extends AppCompatActivity {
    private Button mCreateAccBtn;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //delete this
        AppDAO appDAO = Util.getDAO(this);

        if(appDAO.getAllUsers().size()==0){
            appDAO.insert(new User("testuser","testuser", false), new User("admin", "admin", true));
        }
        startActivity(AdminActivity.intentFactory(this));
        // delete
        mCreateAccBtn = findViewById(R.id.CreateAccBtn);

        mCreateAccBtn.setOnClickListener(v -> {
            startActivity(CreateAccountActivity.intentFactory(getApplicationContext()));
        });
    }
}