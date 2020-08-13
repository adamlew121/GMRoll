package com.example.myfirstapp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myfirstapp.Entity.UserTheme;
import com.example.myfirstapp.Listener.OnSwipeTouchListener;
import com.example.myfirstapp.R;
import com.example.myfirstapp.UserThemeListFragment;

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

        View view  = findViewById(R.id.mainLayout);

        view.setOnTouchListener(new OnSwipeTouchListener(this) {
            @Override
            public void onSwipeLeft() {
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
            }

            @Override
            public void onSwipeRight() {
                Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
                startActivity(intent);
            }
        });
    }

    /** Called when the user taps the Send button */
    public void rollResult(View view) {

        int tempRandom = (int) (Math.random() * SettingsActivity.SUM);
        String result = "";

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

        System.out.println(" result = " + result);

        int drawable;
        switch(result) {
            case "head":
                drawable = R.drawable.body_head;
                break;
            case "neck":
                drawable = R.drawable.body_neck;
                break;
            case "torso":
                drawable = R.drawable.body_torso;
                break;
            case "stomach":
                drawable = R.drawable.body_stomach;
                break;
            case "arm_left":
                drawable = R.drawable.body_arm_left;
                break;
            case "hand_left":
                drawable = R.drawable.body_hand_left;
                break;
            case "arm_right":
                drawable = R.drawable.body_arm_right;
                break;
            case "hand_right":
                drawable = R.drawable.body_hand_right;
                break;
            case "thigh_left":
                drawable = R.drawable.body_thigh_left;
                break;
            case "foot_left":
                drawable = R.drawable.body_foot_left;
                break;
            case "thigh_right":
                drawable = R.drawable.body_thigh_right;
                break;
            case "foot_right":
                drawable = R.drawable.body_foot_right;
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
}