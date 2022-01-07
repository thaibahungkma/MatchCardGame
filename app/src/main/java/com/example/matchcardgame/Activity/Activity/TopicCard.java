package com.example.matchcardgame.Activity.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import com.example.matchcardgame.Activity.Adapter.MusicAdapter;
import com.example.matchcardgame.R;

public class TopicCard extends AppCompatActivity {
    LinearLayout animalLinear, emojiLinear;
    MusicAdapter musicAdapter;
    Animation anim_turnLeft, anim_turnRight, anim_Top, anim_bot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_card);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        animalLinear = findViewById(R.id.animalLinear);
        emojiLinear = findViewById(R.id.emojiLinear);
        musicAdapter.CreateSound(this);

        //animation
        anim_turnRight= AnimationUtils.loadAnimation(this, R.anim.anim_turn_right);
        anim_turnLeft= AnimationUtils.loadAnimation(this, R.anim.anim_turn_left);
        anim_Top= AnimationUtils.loadAnimation(this, R.anim.anim_turn_top);
        anim_bot= AnimationUtils.loadAnimation(this, R.anim.anim_turn_bot);

        animalLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MusicAdapter.playSoundPool(1);
                TypeGame("animal");
                animalLinear.startAnimation(anim_turnLeft);
                emojiLinear.startAnimation(anim_turnRight);
            }
        });
        emojiLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MusicAdapter.playSoundPool(1);
                TypeGame("emoji");
                animalLinear.startAnimation(anim_turnLeft);
                emojiLinear.startAnimation(anim_turnRight);
            }
        });


    }

    public void TypeGame(String typeGame) {
        Intent intent = new Intent(this, SelectLevel.class);
        intent.putExtra("type", typeGame);
        startActivity(intent);
    }

}