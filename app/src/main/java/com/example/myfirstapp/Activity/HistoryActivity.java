package com.example.myfirstapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myfirstapp.Listener.OnSwipeTouchListener;
import com.example.myfirstapp.PlayerConfig;
import com.example.myfirstapp.R;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class HistoryActivity extends YouTubeBaseActivity {

    private Button button;
    private YouTubePlayerView youTubePlayerView;
    YouTubePlayer.OnInitializedListener onInitializedListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*
        setContentView(R.layout.activity_history);

        View view = findViewById(R.id.historyLayout);

        view.setOnTouchListener(new OnSwipeTouchListener(this) {
            @Override
            public void onSwipeLeft() {
                Intent intent = new Intent(HistoryActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        youTubePlayerView = (YouTubePlayerView) findViewById(R.id.youtubePlayer_view);
        button = (Button) findViewById(R.id.buttonYoutube);

        onInitializedListener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                // youTubePlayer.setFullscreen(true);
                // youTubePlayer.loadVideo("dQw4w9WgXcQ");
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        };

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                youTubePlayerView.initialize(PlayerConfig.API_KEY, onInitializedListener);
            }
        });

        youTubePlayerView.initialize(PlayerConfig.API_KEY, onInitializedListener);
        */
    }
}