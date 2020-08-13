package com.example.myfirstapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myfirstapp.R;

import java.util.HashMap;

public class NewUserThemeActivity extends AppCompatActivity {

    public static final String EXTRA_REPLY_TITLE = "com.example.android.Userthemelistsql.REPLY_TITLE";
    public static final String EXTRA_REPLY_HASHMAP = "com.example.android.Userthemelistsql.REPLY_HASHMAP";

    private EditText mEditUserThemeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user_theme);

        ViewGroup vg = findViewById(R.id.text_nodes_layout_new);
        for (int i = 0; i < vg.getChildCount(); i++) {
            View v = vg.getChildAt(i);
            if (v instanceof EditText) {
                ((EditText) v).setText("100");

            }
        }

        mEditUserThemeView = findViewById(R.id.title_new);

        final Button button = findViewById(R.id.button_save);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent replyIntent = new Intent();

                HashMap<String, Integer> themeHashMap = new HashMap<>();
                if (TextUtils.isEmpty(mEditUserThemeView.getText())) {
                    setResult(RESULT_CANCELED, replyIntent);
                } else {
                    for (int i = 0; i < vg.getChildCount(); i++) {
                        View v = vg.getChildAt(i);
                        if (v instanceof EditText && TextUtils.isEmpty(((EditText) v).getText())) {
                            setResult(RESULT_CANCELED, replyIntent);
                            finish();
                            break;
                        } else {
                            if (v instanceof EditText) {
                                int id = ((EditText) v).getId();
                                String name = v.getResources().getResourceEntryName(id);
                                themeHashMap.put(name, Integer.parseInt(((EditText) v).getText().toString()));
                            }
                        }
                    }


                    String title = mEditUserThemeView.getText().toString();
                    replyIntent.putExtra(EXTRA_REPLY_TITLE, title);
                    replyIntent.putExtra(EXTRA_REPLY_HASHMAP, themeHashMap);
                    setResult(RESULT_OK, replyIntent);
                }
                finish();
            }
        });

    }
}