package com.example.myfirstapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.myfirstapp.Entity.UserThemeViewModel;
import com.example.myfirstapp.R;

public class NewUserThemeActivity extends AppCompatActivity {

    public static final String EXTRA_REPLY_TITLE = "com.example.android.Userthemelistsql.REPLY_TITLE";
    public static final String EXTRA_REPLY_CHANCE_LIST = "com.example.android.Userthemelistsql.REPLY_CHANCE_LIST";

    private EditText inputNewTitle;
    private EditText[] inputList;
    public UserThemeViewModel mUserThemeViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user_theme);

        inputList = new EditText[]{
                findViewById(R.id.input_new_head),
                findViewById(R.id.input_new_neck),
                findViewById(R.id.input_new_arm_left),
                findViewById(R.id.input_new_arm_right),
                findViewById(R.id.input_new_hand_left),
                findViewById(R.id.input_new_hand_right),
                findViewById(R.id.input_new_torso),
                findViewById(R.id.input_new_stomach),
                findViewById(R.id.input_new_thigh_left),
                findViewById(R.id.input_new_thigh_right),
                findViewById(R.id.input_new_foot_left),
                findViewById(R.id.input_new_foot_right)
        };

        inputNewTitle = findViewById(R.id.input_new_title);

        for (EditText input : inputList) {
            input.setText(String.valueOf(100));
        }

        mUserThemeViewModel = new ViewModelProvider(this).get(UserThemeViewModel.class);

        final Button button = findViewById(R.id.button_save);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent replyIntent = new Intent();

                boolean acceptable = true;
                int[] newChanceList = new int[12];
                if (TextUtils.isEmpty(inputNewTitle.getText())) {
                    // setResult(RESULT_CANCELED, replyIntent);
                    Toast.makeText(
                            getApplicationContext(),
                            R.string.empty_not_saved,
                            Toast.LENGTH_LONG).show();
                    return;
                } else {
                    if (mUserThemeViewModel.getUserThemeByTitle(inputNewTitle.getText().toString()) != null) {
                        Toast.makeText(
                                getApplicationContext(),
                                R.string.duplicate_title,
                                Toast.LENGTH_LONG).show();
                        return;
                    } else {
                        for (int i = 0; i < newChanceList.length; i++) {
                            if (TextUtils.isEmpty(inputList[i].getText())) {
                                inputList[i].setText("0");
                            }
                            if (inputList[i].getText().toString().length() > 4 || Integer.parseInt(inputList[i].getText().toString()) > 1000) {
                                acceptable = false;
                                Toast.makeText(
                                        getApplicationContext(),
                                        R.string.userTheme_maxValExceeded,
                                        Toast.LENGTH_LONG).show();
                                break;
                            }
                            newChanceList[i] = Integer.parseInt(inputList[i].getText().toString());
                        }
                        if (acceptable) {
                            boolean positiveSum = false;
                            for (int chance : newChanceList) {
                                if (chance > 0) {
                                    positiveSum = true;
                                    break;
                                }
                            }
                            if (!positiveSum) {
                                Toast.makeText(
                                        getApplicationContext(),
                                        R.string.zero_not_saved,
                                        Toast.LENGTH_LONG).show();
                                return;
                            }

                            String title = inputNewTitle.getText().toString();
                            replyIntent.putExtra(EXTRA_REPLY_TITLE, title);
                            replyIntent.putExtra(EXTRA_REPLY_CHANCE_LIST, newChanceList);
                            setResult(RESULT_OK, replyIntent);
                            finish();
                        }
                    }
                }
            }
        });
    }
}