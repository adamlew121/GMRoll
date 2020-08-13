package com.example.myfirstapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
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
import com.example.myfirstapp.UserThemeListFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class SettingsActivity extends AppCompatActivity implements UserThemeListFragment.FragmentUserThemeListListener {

    public static int SUM = 0;
    public static HashMap<String, Integer> enteredData = new HashMap<>();
    public static String TITLE = "";

    public static final int NEW_USER_THEME_ACTIVITY_REQUEST_CODE = 1;
    private UserThemeListFragment fragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        fragment = new UserThemeListFragment();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container_user_theme_list_fragment, fragment)
                .commit();


        View view = findViewById(R.id.settings_layout);
        view.setOnTouchListener(new OnSwipeTouchListener(this) {
            @Override
            public void onSwipeRight() {
                Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        ViewGroup vg = findViewById(R.id.text_nodes_layout);


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
            fragment.mUserThemeViewModel.insert(userTheme);
        } else {
            Toast.makeText(
                    getApplicationContext(),
                    R.string.empty_not_saved,
                    Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onThemeSelected(UserTheme userTheme) {
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
