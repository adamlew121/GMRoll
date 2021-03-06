package com.example.myfirstapp.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myfirstapp.Entity.UserTheme;
import com.example.myfirstapp.Entity.UserThemeViewModel;
import com.example.myfirstapp.Fragment.UserThemeListFragment;
import com.example.myfirstapp.Listener.OnSwipeTouchListener;
import com.example.myfirstapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Arrays;
import java.util.Objects;

public class SettingsActivity extends AppCompatActivity implements UserThemeListFragment.FragmentUserThemeListListener {

    public static final int NEW_USER_THEME_ACTIVITY_REQUEST_CODE = 1;

    public static int SUM = 0;
    public static int[] enteredData = new int[12];
    public static String TITLE = "";
    public static UserTheme selectedUserTheme;

    private EditText[] inputList;
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
                overridePendingTransition(R.anim.slide_int_left, R.anim.slide_out_right);

            }
        });

        View scrollView = findViewById(R.id.scrollView_text_nodes_layout);
        scrollView.setOnTouchListener(new OnSwipeTouchListener(this) {
            final int MIN_DISTANCE = 700;
            float downX, downY, upX, upY;

            @Override
            public void onSwipeRight() {
                Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_int_left, R.anim.slide_out_right);
            }

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                super.onTouch(v, event);
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        downX = event.getX();
                        downY = event.getY();
                        //   return true;
                    }
                    case MotionEvent.ACTION_UP: {
                        upX = event.getX();
                        upY = event.getY();

                        float deltaX = downX - upX;
                        float deltaY = downY - upY;

                        // swipe horizontal?
                        if (Math.abs(deltaX) > MIN_DISTANCE) {
                            // left or right
                            if (deltaX < 0) {
                                this.onSwipeRight();
                                return true;
                            }
                            if (deltaX > 0) {
                                this.onSwipeLeft();
                                return true;
                            }
                        }
                    }
                }
                return false;
            }
        });

        inputList = new EditText[]{
                findViewById(R.id.input_head),
                findViewById(R.id.input_neck),
                findViewById(R.id.input_arm_left),
                findViewById(R.id.input_arm_right),
                findViewById(R.id.input_hand_left),
                findViewById(R.id.input_hand_right),
                findViewById(R.id.input_torso),
                findViewById(R.id.input_stomach),
                findViewById(R.id.input_thigh_left),
                findViewById(R.id.input_thigh_right),
                findViewById(R.id.input_foot_left),
                findViewById(R.id.input_foot_right)
        };

        FloatingActionButton fab = findViewById(R.id.fab_new_userTheme);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingsActivity.this, NewUserThemeActivity.class);
                startActivityForResult(intent, NEW_USER_THEME_ACTIVITY_REQUEST_CODE);
            }
        });
        loadDataNull();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_USER_THEME_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            UserTheme userTheme = new UserTheme(Objects.requireNonNull(data.getStringExtra(NewUserThemeActivity.EXTRA_REPLY_TITLE)), Objects.requireNonNull(data.getIntArrayExtra(NewUserThemeActivity.EXTRA_REPLY_CHANCE_LIST)));
            UserTheme dbTheme = fragment.mUserThemeViewModel.getUserThemeByTitle(userTheme.getTitle());
            if (dbTheme != null) {
                Toast.makeText(
                        getApplicationContext(),
                        R.string.duplicate_title,
                        Toast.LENGTH_LONG).show();
            } else {
                fragment.mUserThemeViewModel.insert(userTheme);
                Toast.makeText(
                        getApplicationContext(),
                        R.string.userTheme_create,
                        Toast.LENGTH_LONG).show();
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

        int[] chances = userTheme.getChanceList();
        enteredData = chances;

        for (int i = 0; i < chances.length; i++) {
            inputList[i].setText(String.valueOf(chances[i]));
            inputList[i].setFocusable(true);
            inputList[i].setFocusableInTouchMode(true);
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
                loadDataNull();
            }
        }
    }

    public void saveEnteredData(View view) {
        boolean acceptable = true;

        if (selectedUserTheme != null) {
            if (selectedUserTheme.getTitle().equals(UserThemeViewModel.DEFAULT_USERTHEME_TITLE)) {
                Toast.makeText(
                        getApplicationContext(),
                        R.string.default_not_edited,
                        Toast.LENGTH_LONG).show();
            } else {
                SUM = 0;
                enteredData = new int[12];

                for (int i = 0; i < enteredData.length; i++) {
                    if (TextUtils.isEmpty(inputList[i].getText())) {
                        enteredData[i] = 0;
                    } else {
                        if (inputList[i].getText().toString().length() > 4 || Integer.parseInt(inputList[i].getText().toString()) > 1000) {
                            acceptable = false;
                            Toast.makeText(
                                    getApplicationContext(),
                                    R.string.userTheme_maxValExceeded,
                                    Toast.LENGTH_LONG).show();
                            break;
                        }
                        enteredData[i] = Integer.parseInt(inputList[i].getText().toString());
                    }
                    SUM += enteredData[i];
                    inputList[i].setFocusableInTouchMode(true);
                    inputList[i].setFocusable(true);
                }
                if (acceptable) {
                    if (SUM > 0) {
                        fragment.mUserThemeViewModel.insert(new UserTheme(selectedUserTheme.getTitle(), enteredData));
                        Toast.makeText(
                                getApplicationContext(),
                                R.string.userTheme_update,
                                Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(
                                getApplicationContext(),
                                R.string.zero_not_saved,
                                Toast.LENGTH_LONG).show();
                    }
                }
            }
        }
    }

    public static void loadDefaultData() {
        enteredData = new int[12];
        Arrays.fill(enteredData, 100);
        SUM = enteredData.length * 100;
    }

    public void loadDataNull() {
        SUM = 0;
        enteredData = new int[12];
        Arrays.fill(enteredData, 0);
        selectedUserTheme = null;

        for (EditText input : inputList) {
            input.setText(String.valueOf(0));
            input.setFocusable(false);
            input.setFocusableInTouchMode(false);
        }
    }
}