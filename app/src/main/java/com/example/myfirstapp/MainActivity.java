package com.example.myfirstapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myfirstapp.Listeners.OnSwipeTouchListener;

import java.util.Iterator;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_OPTIONS = "com.example.myfirstapp.OPTIONS";
    public static final String EXTRA_SUM = "com.example.myfirstapp.SUM";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (SettingsActivity.enteredData.isEmpty()) {
            setContentView(R.layout.activity_settings);
            View view = findViewById(R.id.text_nodes_layout);
            SettingsActivity.loadDefaultData(view);
        }
        setContentView(R.layout.activity_main);

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
            case "left_arm":
                drawable = R.drawable.body_arm_left;
                break;
            case "left_hand":
                drawable = R.drawable.body_hand_left;
                break;
            case "right_arm":
                drawable = R.drawable.body_arm_right;
                break;
            case "right_hand":
                drawable = R.drawable.body_hand_right;
                break;
            case "left_thigh":
                drawable = R.drawable.body_thigh_left;
                break;
            case "left_foot":
                drawable = R.drawable.body_foot_left;
                break;
            case "right_thigh":
                drawable = R.drawable.body_thigh_right;
                break;
            case "right_foot":
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

}