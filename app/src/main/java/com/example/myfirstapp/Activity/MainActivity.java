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

import java.util.Iterator;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements UserThemeListFragment.FragmentUserThemeListListener {


    public static final String EXTRA_OPTIONS = "com.example.myfirstapp.OPTIONS";
    public static final String EXTRA_SUM = "com.example.myfirstapp.SUM";

    private UserThemeListFragment fragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (SettingsActivity.enteredData.isEmpty()) {
            setContentView(R.layout.activity_settings);
            View view = findViewById(R.id.text_nodes_layout);
            SettingsActivity.loadDefaultData(view);
        }
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

        int tempRandom = (int) (Math.random() * SettingsActivity.SUM);
        String result = "";
        String finalResult = "";

        Iterator it = SettingsActivity.enteredData.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            int val = (int) pair.getValue();
            if (tempRandom - val <= 0) {
                result = pair.getKey().toString();
                break;
            } else {
                tempRandom -= val;
            }
        }

        int drawable;
        switch (result) {
            case "head":
                drawable = R.drawable.body_head;
                result =  getString(R.string.label_head);
                break;
            case "neck":
                drawable = R.drawable.body_neck;
                result =  getString(R.string.label_neck);
                break;
            case "torso":
                drawable = R.drawable.body_torso;
                result =  getString(R.string.label_torso);
                break;
            case "stomach":
                drawable = R.drawable.body_stomach;
                result =  getString(R.string.label_stomach);
                break;
            case "arm_left":
                drawable = R.drawable.body_arm_left;
                result =  getString(R.string.label_arm_left);
                break;
            case "hand_left":
                drawable = R.drawable.body_hand_left;
                result =  getString(R.string.label_hand_left);
                break;
            case "arm_right":
                drawable = R.drawable.body_arm_right;
                result =  getString(R.string.label_arm_right);
                break;
            case "hand_right":
                drawable = R.drawable.body_hand_right;
                result =  getString(R.string.label_hand_right);
                break;
            case "thigh_left":
                drawable = R.drawable.body_thigh_left;
                result =  getString(R.string.label_thigh_left);
                break;
            case "foot_left":
                drawable = R.drawable.body_foot_left;
                result =  getString(R.string.label_foot_left);
                break;
            case "thigh_right":
                drawable = R.drawable.body_thigh_right;
                result =  getString(R.string.label_thigh_right);
                break;
            case "foot_right":
                drawable = R.drawable.body_foot_right;
                result =  getString(R.string.label_thigh_right);
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
        SettingsActivity.enteredData = theme.getHashMap();
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