package com.example.myfirstapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.myfirstapp.Entity.UserTheme;
import com.example.myfirstapp.Entity.UserThemeViewModel;
import com.example.myfirstapp.Fragment.UserThemeListFragment;
import com.example.myfirstapp.Listener.OnSwipeTouchListener;
import com.example.myfirstapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.HashMap;
import java.util.Objects;

public class SettingsActivity extends AppCompatActivity implements UserThemeListFragment.FragmentUserThemeListListener {

    public static int SUM = 0;
    public static HashMap<String, Integer> enteredData = new HashMap<>();
    public static String TITLE = "";
    public static UserTheme selectedUserTheme;

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


//        if (!enteredData.isEmpty()) {
//            for (int i = 0; i < vg.getChildCount(); i++) {
//                View v = vg.getChildAt(i);
//                if (v instanceof EditText) {
//                    int id = ((EditText)v).getId();
//                    String name = v.getResources().getResourceEntryName(id);
//
//                    if(enteredData.containsKey(name) && enteredData.get(name) != 0) {
//                        ((EditText) v).setText(enteredData.get(name).toString());
//                    }
//
//                }
//            }
//
//
//        }

        FloatingActionButton fab = findViewById(R.id.fab_new_userTheme);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingsActivity.this, NewUserThemeActivity.class);
                startActivityForResult(intent, NEW_USER_THEME_ACTIVITY_REQUEST_CODE);
            }
        });

        loadDataNull(findViewById(R.id.text_nodes_layout));


    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_USER_THEME_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            UserTheme userTheme = new UserTheme(Objects.requireNonNull(data.getStringExtra(NewUserThemeActivity.EXTRA_REPLY_TITLE)), (HashMap<String, Integer>) data.getSerializableExtra(NewUserThemeActivity.EXTRA_REPLY_HASHMAP));
            UserTheme dbTheme = fragment.mUserThemeViewModel.getUserThemeByTitle(userTheme.getTitle());
            if (dbTheme != null) {
                Toast.makeText(
                        getApplicationContext(),
                        R.string.duplicate_title,
                        Toast.LENGTH_LONG).show();
            } else {
                fragment.mUserThemeViewModel.insert(userTheme);
            }
        } else {
            Toast.makeText(
                    getApplicationContext(),
                    R.string.empty_not_saved,
                    Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onThemeSelected(UserTheme userTheme) {

        ViewGroup vg = findViewById(R.id.text_nodes_layout);

        for (int i = 0; i < vg.getChildCount(); i++) {
            View v = vg.getChildAt(i);
            if (v instanceof CardView) {
                for (int j = 0; j < ((CardView) v).getChildCount(); j++) {
                    View v2 = ((CardView) v).getChildAt(j);
                    if (v2 instanceof EditText) {
                        ((EditText) v2).setFocusableInTouchMode(true);
                        ((EditText) v2).setFocusable(true);
                        int id = ((EditText) v2).getId();
                        String name = v2.getResources().getResourceEntryName(id);
                        String nameSub = name.substring(name.indexOf("input_") +6);

                        HashMap<String, Integer> userThemeHashMap = userTheme.getHashMap();
                        if (userThemeHashMap.containsKey(nameSub)) {
                            ((EditText) v2).setText(String.valueOf(userThemeHashMap.get(nameSub)));
                        }
                    }
                }
            }
        }
        TITLE = userTheme.getTitle();
        selectedUserTheme = userTheme;
    }


    public void deleteSelectedUserTheme(View view) {
        if (selectedUserTheme != null) {
            if (selectedUserTheme.getTitle().equals(UserThemeViewModel.DEFAULT_USERTHEME_TITLE)) {
                Toast.makeText(
                        getApplicationContext(),
                        R.string.default_not_deleted,
                        Toast.LENGTH_LONG).show();
            } else {
                fragment.onThemeDeleted();
                fragment.mUserThemeViewModel.delete(selectedUserTheme);
                loadDataNull(findViewById(R.id.text_nodes_layout));
            }
        }


    }

    /**
     * Called when the user taps the Send button
     */
    public void saveEnteredData(View view) {

        if (selectedUserTheme != null) {
            if (selectedUserTheme.getTitle().equals(UserThemeViewModel.DEFAULT_USERTHEME_TITLE)) {
                Toast.makeText(
                        getApplicationContext(),
                        R.string.default_not_edited,
                        Toast.LENGTH_LONG).show();
            } else {
                SUM = 0;
                enteredData = new HashMap<>();

                ViewGroup vg = findViewById(R.id.text_nodes_layout);
                for (int i = 0; i < vg.getChildCount(); i++) {
                    View v = vg.getChildAt(i);
                    if (v instanceof CardView) {
                        for (int j = 0; j < ((CardView) v).getChildCount(); j++) {
                            View v2 = ((CardView) v).getChildAt(j);
                            if (v2 instanceof EditText) {
                                ((EditText) v2).setFocusableInTouchMode(true);
                                ((EditText) v2).setFocusable(true);
                                int id = ((EditText) v2).getId();
                                String name = v2.getResources().getResourceEntryName(id);
                                String nameSub = name.substring(name.indexOf("input_") + 6);

                                int value = 0;

                                if (!((EditText) v2).getText().toString().equals("")) {
                                    value = Integer.parseInt(((EditText) v2).getText().toString());
                                }


                                enteredData.put(nameSub, value);
                                SUM += value;
                            }

                        }
                    }
                }
                fragment.mUserThemeViewModel.insert(new UserTheme(selectedUserTheme.getTitle(), enteredData));
                System.out.println("eeeo");
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
            if (v instanceof CardView) {
                for (int j = 0; j < ((CardView) v).getChildCount(); j++) {
                    View v2 = ((CardView) v).getChildAt(j);
                    if (v2 instanceof EditText) {
                        ((EditText) v2).setFocusableInTouchMode(true);
                        ((EditText) v2).setFocusable(true);
                        int id = ((EditText) v2).getId();
                        String name = v2.getResources().getResourceEntryName(id);
                        String nameSub = name.substring(name.indexOf("input_") + 6);

                        enteredData.put(nameSub, 100);
                        SUM += 100;
                    }
                }
            }
        }
    }

    public static void loadDataNull(View view) {
        SUM = 0;
        enteredData = new HashMap<>();
        selectedUserTheme = null;

        ViewGroup vg = view.findViewById(R.id.text_nodes_layout);
        for (int i = 0; i < vg.getChildCount(); i++) {
            View v = vg.getChildAt(i);
            if (v instanceof CardView) {
                for (int j = 0; j < ((CardView) v).getChildCount(); j++) {
                    View v2 = ((CardView) v).getChildAt(j);
                    if (v2 instanceof EditText) {
                        ((EditText) v2).setText("0");
                        ((EditText) v2).setFocusable(false);
                        ((EditText) v2).setFocusableInTouchMode(false);
                    }
                }

            }
        }
    }


}
