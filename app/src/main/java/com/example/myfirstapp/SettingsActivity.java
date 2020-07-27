package com.example.myfirstapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myfirstapp.Listeners.OnSwipeTouchListener;

import java.util.HashMap;

public class SettingsActivity extends AppCompatActivity {

    public static int SUM = 0;
    public static HashMap<String, Integer> enteredData = new HashMap<>();

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

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
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
