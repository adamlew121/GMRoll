package com.example.myfirstapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.example.myfirstapp.Entity.UserTheme;
import com.example.myfirstapp.Fragment.UserThemeListFragment;
import com.example.myfirstapp.Listener.OnSwipeTouchListener;
import com.example.myfirstapp.R;

import java.util.concurrent.ThreadLocalRandom;

public class MainActivity extends AppCompatActivity implements UserThemeListFragment.FragmentUserThemeListListener {


    public static final String EXTRA_OPTIONS = "com.example.myfirstapp.OPTIONS";
    public static final String EXTRA_SUM = "com.example.myfirstapp.SUM";

    private UserThemeListFragment fragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SettingsActivity.loadDefaultData();

        setContentView(R.layout.activity_main);

        fragment = new UserThemeListFragment();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container_main_user_theme_list_fragment, fragment)
                .commit();

        View view = findViewById(R.id.mainLayout);

        view.setOnTouchListener(new OnSwipeTouchListener(this) {
            @Override
            public void onSwipeLeft() {
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }

            @Override
            public void onSwipeRight() {
//                Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
//                startActivity(intent);
            }
        });
        TextView textViewStyle = findViewById(R.id.text_view_style);
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            textViewStyle.setText(getString(R.string.theme_dark));

            Switch styleSwitch = findViewById(R.id.switch_style);
            styleSwitch.setChecked(true);
        } else {
            textViewStyle.setText(getString(R.string.theme_light));
        }
    }

    /**
     * Called when the user taps the Send button
     */
    public void rollResult(View view) {

        int tempRandom = ThreadLocalRandom.current().nextInt(1, SettingsActivity.SUM + 1);
        int rolledID = -1;
        String result = "";

        for (int i = 0; i < SettingsActivity.enteredData.length; i++) {
            if (SettingsActivity.enteredData[i] >= tempRandom) {
                rolledID = i;
                break;
            } else {
                tempRandom -= SettingsActivity.enteredData[i];
            }
        }

        int drawable;
        switch (rolledID) {
            case UserTheme.ID_HEAD:
                drawable = R.drawable.body_head;
                result = getString(R.string.label_head);
                break;
            case UserTheme.ID_NECK:
                drawable = R.drawable.body_neck;
                result = getString(R.string.label_neck);
                break;
            case UserTheme.ID_ARM_LEFT:
                drawable = R.drawable.body_arm_left;
                result = getString(R.string.label_arm_left);
                break;
            case UserTheme.ID_ARM_RIGHT:
                drawable = R.drawable.body_arm_right;
                result = getString(R.string.label_arm_right);
                break;
            case UserTheme.ID_HAND_LEFT:
                drawable = R.drawable.body_hand_left;
                result = getString(R.string.label_hand_left);
                break;
            case UserTheme.ID_HAND_RIGHT:
                drawable = R.drawable.body_hand_right;
                result = getString(R.string.label_hand_right);
                break;
            case UserTheme.ID_TORSO:
                drawable = R.drawable.body_torso;
                result = getString(R.string.label_torso);
                break;
            case UserTheme.ID_STOMACH:
                drawable = R.drawable.body_stomach;
                result = getString(R.string.label_stomach);
                break;
            case UserTheme.ID_THIGH_LEFT:
                drawable = R.drawable.body_thigh_left;
                result = getString(R.string.label_thigh_left);
                break;
            case UserTheme.ID_THIGH_RIGHT:
                drawable = R.drawable.body_thigh_right;
                result = getString(R.string.label_thigh_right);
                break;
            case UserTheme.ID_FOOT_LEFT:
                drawable = R.drawable.body_foot_left;
                result = getString(R.string.label_foot_left);
                break;
            case UserTheme.ID_FOOT_RIGHT:
                drawable = R.drawable.body_foot_right;
                result = getString(R.string.label_thigh_right);
                break;
            case -1:
                drawable = R.drawable.body_red;
                result = "ERROR";
                break;
            default:
                drawable = R.drawable.body_black;
                break;
        }

        ImageView image = (ImageView) findViewById(R.id.image_body);
        image.setImageResource(drawable);

        result = result.replace('_', ' ');

        TextView textViewResult = findViewById(R.id.textViewResult);
        textViewResult.setText(result);
    }

    @Override
    public void onThemeSelected(UserTheme theme) {
        SettingsActivity.TITLE = theme.getTitle();
        SettingsActivity.enteredData = theme.getChanceList();
        SettingsActivity.SUM = theme.calculateSum();
    }

    public void changeStyle(View view) {
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

        }
    }
}