package com.example.matchcardgame.Activity.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.WindowManager;
import android.widget.ProgressBar;

import com.example.matchcardgame.R;

public class load_screen extends AppCompatActivity {
    private ProgressBar progressBar1;
    MediaPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_screen);

        //cai dat full man hinh
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        initView(); // anh xa view
        player.start();
        doProgressBar1();
        // xu ly man hinh load screen
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(load_screen.this, MainActivity.class));
                finish();
            }
        },3300);
    }

    //init view
    private void initView() {
        // khoi tao nhac nen
        player = MediaPlayer.create(this, R.raw.load);
        player.setLooping(false);
        player.setVolume(50,50);
        player.setAudioAttributes(
                new AudioAttributes
                        .Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .build());
        progressBar1 = findViewById(R.id.progressBar1);
    }

    @Override
    protected void onStop() {
        super.onStop();
        player.stop();
        player.release();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!player.isPlaying()){
            player.start();
        }
    }

    // xu ly thanh progressBar
    private void doProgressBar1() {
        final int MAX=110;
        this.progressBar1.setMax(MAX);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                for( int i =0; i < MAX; i++) {
                    final int progress = i + 1;
                    SystemClock.sleep(30);
                    progressBar1.setProgress(progress);

            }}
        });
        thread.start();
    }
}