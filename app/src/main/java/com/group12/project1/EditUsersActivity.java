package com.group12.project1;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import android.widget.Toast;

import com.group12.project1.db.AppDAO;

import java.util.List;

public class EditUsersActivity extends AppCompatActivity {
    private List<User> mUsers;
    private String[] mNamesArr;
    private AppDAO mAppDAO;
    private ListView mListView;
    private ArrayAdapter<String> mArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_users);
        wireDisplay();
    }

    public void wireDisplay() {
        mAppDAO = Util.getDAO(this);
        mUsers = mAppDAO.getAllUsers();
        getNames();
        mListView = findViewById(R.id.list_item);

        mArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, mNamesArr);
        mListView.setAdapter(mArrayAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                User user = mAppDAO.getUserByUsername(adapterView.getItemAtPosition(i).toString());
                String msg = getUserInfo(user);
                String text = "Make Admin";
                if (user.isAdmin()) {
                    text = "Remove Admin";
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(EditUsersActivity.this);
                builder.setMessage(msg).setCancelable(true)
                        .setPositiveButton(text, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if (!user.isAdmin()) {
                                    user.setAdmin(true);
                                    Toast.makeText(EditUsersActivity.this, user.getUsername() + "'s privileges elevated", Toast.LENGTH_SHORT).show();
                                } else {
                                    user.setAdmin(false);
                                    Toast.makeText(EditUsersActivity.this, user.getUsername() + "'s privileges revoked", Toast.LENGTH_SHORT).show();
                                }
                                mAppDAO.update(user);

                                dialogInterface.cancel();
                            }
                        })
                        .setNegativeButton("Delete User", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                mAppDAO.delete(user);
                                Toast.makeText(EditUsersActivity.this, user.getUsername() + " deleted", Toast.LENGTH_SHORT).show();
                                dialogInterface.cancel();
                                onBackPressed();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.setTitle(user.getUsername());
                alert.show();
            }
        });
    }

    /**
     * Factory pattern provided Intent to switch to this activity.
     *
     * @param ctx the Context to switch from
     * @return the Intent to switch to this activity
     */
    public static Intent intentFactory(Context ctx) {
        Intent intent = new Intent(ctx, EditUsersActivity.class);
        return intent;
    }

    /**
     * Populates array with users'l usernames.
     */
    private void getNames() {
        mNamesArr = new String[mUsers.size()];
        for (int i = 0; i < mUsers.size(); i++) {
            mNamesArr[i] = mUsers.get(i).getUsername();
        }
    }

    private String getUserInfo(User user){
        String msg;
        SearchPreferences sp = user.getPrefs();

        if(user.isAdmin())
                msg="Admin\n"+"Password: "+user.getPassword()+"\n";
        else
            msg = "User\n"+"Password: "+user.getPassword()+"\n";
        if(sp!=null)
            msg+=(
                    "Language: "+sp.getLang()+"\n"+
                    "Location: "+sp.getLoc()+"\n"+
                    "Full-time: "+sp.isFullTime()
                    );
        return msg;
    }
}