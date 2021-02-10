package com.group12.project1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AdminActivity extends AppCompatActivity {
    private Button mEditUsersBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);


    }
    public void wireDisplay(){
        mEditUsersBtn = findViewById(R.id.buttonEditUsers);
        mEditUsersBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
    /**
     * Factory pattern provided Intent to switch to this activity.
     * @param ctx the Context to switch from
     * @return    the Intent to switch to this activity
     */
    public static Intent intentFactory(Context ctx) {
        Intent intent = new Intent(ctx, AdminActivity.class);
        return intent;
    }
}