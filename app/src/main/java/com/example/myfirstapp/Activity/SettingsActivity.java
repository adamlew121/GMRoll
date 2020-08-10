package com.example.myfirstapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfirstapp.Entity.UserTheme;
import com.example.myfirstapp.Entity.UserThemeViewModel;
import com.example.myfirstapp.Listener.OnSwipeTouchListener;
import com.example.myfirstapp.R;
import com.example.myfirstapp.UserThemeListAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class SettingsActivity extends AppCompatActivity {

    public static int SUM = 0;
    public static HashMap<String, Integer> enteredData = new HashMap<>();
    public static String TITLE = "";

    private UserThemeViewModel mUserThemeViewModel;
    public static final int NEW_USER_THEME_ACTIVITY_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        ViewGroup vg = findViewById(R.id.text_nodes_layout);
        vg.setOnTouchListener(new OnSwipeTouchListener(this) {
            @Override
            public void onSwipeRight() {
                Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        if (!enteredData.isEmpty()) {
            for (int i = 0; i < vg.getChildCount(); i++) {
                View v = vg.getChildAt(i);
                if (v instanceof EditText) {
                    int id = ((EditText)v).getId();
                    String name = v.getResources().getResourceEntryName(id);

                    if(enteredData.containsKey(name) && enteredData.get(name) != 0) {
                        ((EditText) v).setText(enteredData.get(name).toString());
                    }

                }
            }

        }

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final UserThemeListAdapter adapter = new UserThemeListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        mUserThemeViewModel = new ViewModelProvider(this).get(UserThemeViewModel.class);

        mUserThemeViewModel.getAllUserThemes().observe(this, new Observer<List<UserTheme>>() {
            @Override
            public void onChanged(List<UserTheme> userThemes) {
                adapter.setUserThemes(userThemes);
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingsActivity.this, NewUserThemeActivity.class);
                startActivityForResult(intent, NEW_USER_THEME_ACTIVITY_REQUEST_CODE);
            }
        });


    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_USER_THEME_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            UserTheme userTheme = new UserTheme(Objects.requireNonNull(data.getStringExtra(NewUserThemeActivity.EXTRA_REPLY_TITLE)),(HashMap<String, Integer>) data.getSerializableExtra(NewUserThemeActivity.EXTRA_REPLY_HASHMAP));
            mUserThemeViewModel.insert(userTheme);
        } else {
            Toast.makeText(
                    getApplicationContext(),
                    R.string.empty_not_saved,
                    Toast.LENGTH_LONG).show();
        }
    }

    public void onRecyclerViewClick(UserTheme userTheme) {
        System.out.println("got message: " + userTheme.getTitle());

        ViewGroup vg = findViewById(R.id.text_nodes_layout);

        for (int i = 0; i < vg.getChildCount(); i++) {
            View v = vg.getChildAt(i);
            if (v instanceof EditText) {
                int id = ((EditText) v).getId();
                String name = v.getResources().getResourceEntryName(id);

                HashMap<String, Integer> userThemeHashMap = userTheme.getHashMap();
                if (userThemeHashMap.containsKey(name)) {
                    ((EditText) v).setText(String.valueOf(userThemeHashMap.get(name)));
                }
                TITLE = userTheme.getTitle();



            }
        }
    }

    /** Called when the user taps the Send button */
    public void saveEnteredData(View view) {

        SUM = 0;
        enteredData = new HashMap<>();

        ViewGroup vg = findViewById(R.id.text_nodes_layout);
        for (int i = 0; i < vg.getChildCount(); i++) {
            View v = vg.getChildAt(i);
            if (v instanceof EditText) {
                int id = ((EditText)v).getId();
                String name = v.getResources().getResourceEntryName(id);

                int value = 0;

                if (!((EditText) v).getText().toString().equals("")) {
                    value = Integer.parseInt(((EditText) v).getText().toString());
                }


                enteredData.put(name, value);
                SUM += value;
                UserTheme newTheme = new UserTheme(TITLE, enteredData);
                mUserThemeViewModel.insert(newTheme);
            }
        }

//        Intent intent = new Intent(this, MainActivity.class);
//        startActivity(intent);
    }

    public static void loadDefaultData(View view) {
        SUM = 0;
        enteredData = new HashMap<>();

        ViewGroup vg = view.findViewById(R.id.text_nodes_layout);
        for (int i = 0; i < vg.getChildCount(); i++) {
            View v = vg.getChildAt(i);
            if (v instanceof EditText) {
                int id = ((EditText)v).getId();
                String name = v.getResources().getResourceEntryName(id);

                enteredData.put(name, 100);
                SUM += 100;
            }
        }
    }


}
