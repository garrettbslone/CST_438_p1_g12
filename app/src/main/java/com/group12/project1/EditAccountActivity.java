package com.group12.project1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.group12.project1.db.AppDAO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditAccountActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private static final String BASE_MSG = "Edit Account \n";
    public static boolean IS_SEARCH;
    public static List<Job> JOBS = new ArrayList<>();
    // possible languages for the spinner
    private String[] mLangsArr;
    private String mSelectedLang;
    private boolean mFullTime;

    private TextView mNameTv;
    private Spinner mLangSp;
    private EditText mLocEt;
    private Switch mTimeSw;
    private Button mSaveBtn;
    private SearchPreferences mPrefs;



    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_account);
        mPrefs = new SearchPreferences("", "", true);
        mNameTv = findViewById(R.id.EditAccNameTV);
        mLangSp = findViewById(R.id.EditAccLangSP);
        mLocEt = findViewById(R.id.EditAccLocET);
        mTimeSw = findViewById(R.id.EditAccTimeSW);
        mSaveBtn = findViewById(R.id.EditAccSaveBtn);

        mLangsArr = getResources().getStringArray(R.array.langs_arr);

        // get the signed in User object
        SharedPreferences sharedPrefs = getSharedPreferences(User.PREFS_TBL_NAME, Context.MODE_PRIVATE);
        AppDAO dao = Util.getDAO(getApplicationContext());

        User user = User.getSignedInUser(sharedPrefs, dao);

        if (user == null) {
            mNameTv.setText("Error: couldn't get user details");
        } else {

            autofillPrefs(user);
            if(IS_SEARCH) {
                mSaveBtn.setText("Search");
                mLocEt.setText("");
            }
            mTimeSw.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) {
                    mTimeSw.setText(R.string.yes_str);
                } else {
                    mTimeSw.setText(R.string.no_str);
                }

                this.mFullTime = isChecked;
            });

            mSaveBtn.setOnClickListener(v -> {
                String loc = mLocEt.getText().toString().trim();
                if(!IS_SEARCH) {
                    if (loc.isEmpty()) {
                        mLocEt.setBackgroundColor(Color.RED);
                        return;
                    } else {
                        mLocEt.setBackgroundColor(Color.WHITE);
                    }
                }

                if(IS_SEARCH) {
                    mPrefs = new SearchPreferences(mSelectedLang, loc, mFullTime);
                    Toast.makeText(EditAccountActivity.this, "Searching...",Toast.LENGTH_SHORT).show();
                    search(mPrefs.toQueryMap());

                }else {
                    user.setPrefs(new SearchPreferences(mSelectedLang, loc, mFullTime));
                    dao.update(user);
                    startActivity(UserMainMenuActivity.intentFactory(getApplicationContext()));
                }
            });
        }
    }

    /**
     * Auto fills the preferences for the user if they have them.
     * Displays a welcome message with the username.
     * @param user the User who is editing their preferences
     */
    private void autofillPrefs (User user) {
        if(!IS_SEARCH)
            mNameTv.setText(BASE_MSG + user.getUsername());
        else
            mNameTv.setText("SEARCH");
        fillSpinner();

        SearchPreferences prefs = user.getPrefs();

        if (prefs != null) {
            for (int i = 0; i < mLangsArr.length; ++i) {
                if (mLangsArr[i].equals(prefs.getLang())) {
                    mLangSp.setSelection(i);
                }
            }

            mLocEt.setText(prefs.getLoc());
            mTimeSw.setChecked(prefs.isFullTime());
        } else {
            mFullTime = true;
        }
    }

    /**
     * Fills the spinner with the programming languages
     * from the string-array "langs_arr" in strings.xml.
     */
    private void fillSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.langs_arr, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mLangSp.setAdapter(adapter);
        mLangSp.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected (AdapterView<?> parent, View view, int position, long id) {
        String item = parent.getItemAtPosition(position).toString();
        mSelectedLang = item;
    }

    @Override
    public void onNothingSelected (AdapterView<?> parent) {
        // Autogenerated stub
    }

    /**
     * Factory pattern provided Intent to switch to this activity.
     * @param ctx the Context to switch from
     * @return    the Intent to switch to this activity
     */
    public static Intent intentFactory(Context ctx) {
        Intent intent = new Intent(ctx, EditAccountActivity.class);
        return intent;
    }

    public static void setSearch(boolean bool){
        IS_SEARCH = bool;
    }
    /**
     * Search for Jobs using the GitHub jobs api.
     * @param params a HashMap of Strings representing the query parameters
     */
    public void search(HashMap<String, String> params) {
        GitHubJobsAPI api = Util.getAPI();
        Call<List<Job>> call = api.searchJobs(params);

        call.enqueue(new Callback<List<Job>>() {
            @Override
            public void onResponse (Call<List<Job>> call, Response<List<Job>> response) {
                if (!response.isSuccessful()) {
                    Log.e("HTTP Call fail", response.code() + ": " + response.message());
                    return;
                }
                JOBS = response.body();
                startActivity(DisplayApiActivity.intentFactory(EditAccountActivity.this));
            }

            @Override
            public void onFailure (Call<List<Job>> call, Throwable t) {
                Log.e("HTTP Call fail", t.getMessage());
            }
        });
    }
}