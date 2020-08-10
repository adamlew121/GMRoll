package com.example.myfirstapp.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.myfirstapp.Entity.UserTheme;
import com.example.myfirstapp.Entity.UserThemeViewModel;
import com.example.myfirstapp.R;
import com.example.myfirstapp.UserThemeListAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class TempRoomActivity extends AppCompatActivity {

    private UserThemeViewModel mUserThemeViewModel;
    public static final int NEW_USER_THEME_ACTIVITY_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp_room);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final UserThemeListAdapter adapter = new UserThemeListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mUserThemeViewModel = new ViewModelProvider(this).get(UserThemeViewModel.class);

        mUserThemeViewModel.getAllUserThemes().observe(this, new Observer<List<UserTheme>>() {
            @Override
            public void onChanged(List<UserTheme> userThemes) {
                adapter.setUserThemes(userThemes);
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TempRoomActivity.this, NewUserThemeActivity.class);
                startActivityForResult(intent, NEW_USER_THEME_ACTIVITY_REQUEST_CODE);
            }
        });


    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_USER_THEME_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            UserTheme userTheme = new UserTheme(data.getStringExtra(NewUserThemeActivity.EXTRA_REPLY_TITLE));
            mUserThemeViewModel.insert(userTheme);
        } else {
            Toast.makeText(
                    getApplicationContext(),
                    R.string.empty_not_saved,
                    Toast.LENGTH_LONG).show();
        }
    }
}