package com.example.matchcardgame.Activity.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.matchcardgame.Activity.Adapter.MusicAdapter;
import com.example.matchcardgame.R;

public class SelectLevel extends AppCompatActivity {
    LinearLayout levelEasy, levelMedium, levelHard;
    MusicAdapter musicAdapter;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    TextView diemcaoez,txt_medium,txt_hard;
    RatingBar starMedium,starHard,starEz;
    Animation anim_turnLeft, anim_turnRight, anim_Top, anim_bot;


    final  String KEY_PRE_HIGH_SOCCER="KEY_PRE_HIGH_SOCCER";
    final String KEY_HIGH_SOCCER_EASY="KEY_HIGH_SOCCER_EASY";
    final String KEY_HIGH_SOCCER_HARD="KEY_HIGH_SOCCER_HARD";
    final String KEY_HIGH_SOCCER_MEDIUM="KEY_HIGH_SOCCER_MEDIUM";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_level);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        musicAdapter.CreateSound(this);
        initView();
        //animation
        anim_turnRight= AnimationUtils.loadAnimation(this, R.anim.anim_turn_right);
        anim_turnLeft= AnimationUtils.loadAnimation(this, R.anim.anim_turn_left);
        anim_Top= AnimationUtils.loadAnimation(this, R.anim.anim_turn_top);
        anim_bot= AnimationUtils.loadAnimation(this, R.anim.anim_turn_bot);

        sharedPreferences = this.getSharedPreferences(KEY_PRE_HIGH_SOCCER, MODE_PRIVATE);
        editor = sharedPreferences.edit();
        int ez=getIntShare(KEY_HIGH_SOCCER_EASY);


        int medium=getIntShare(KEY_HIGH_SOCCER_MEDIUM);
        int hard=getIntShare(KEY_HIGH_SOCCER_HARD);
        if(getIntShare(KEY_HIGH_SOCCER_EASY)!=0)
            diemcaoez.setText(""+(60-ez)+" Second");
        if(getIntShare(KEY_HIGH_SOCCER_MEDIUM)!=0)
        txt_medium.setText(""+(180-medium)+" Second");
        if(getIntShare(KEY_HIGH_SOCCER_HARD)!=0)
        txt_hard.setText(""+(240-hard)+" Second");
        if(ez>40){
            starEz.setRating(3);
        }
        else {
            if(ez<40&&ez>20) {
                starEz.setRating(2);
            }
            else  if(ez<20&&ez>0) {
                starEz.setRating(1);
            }else   {
                starEz.setRating(0);
            }
        }
        if(medium>150){
            starMedium.setRating(3);
        }
        else {
            if(medium<150&&medium>50) {
                starMedium.setRating(2);
            }
            else  if(medium<50&&medium>0) {
                starMedium.setRating(1);
            }else   {
                starMedium.setRating(0);
            }
        }
        if(hard>200){
            starHard.setRating(3);
        }
        else {
            if(hard<200&&hard>50) {
                starHard.setRating(2);
            }
            else  if(hard<50&&hard>0) {
                starHard.setRating(1);
            }else   {
                starHard.setRating(0);
            }
        }
        levelEasy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MusicAdapter.playSoundPool(1);
                LevelGameEasy("easy");
                levelMedium.startAnimation(anim_Top);
                levelHard.startAnimation(anim_bot);
            }
        });
        levelMedium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MusicAdapter.playSoundPool(1);
                LevelGameMedium("medium");
                levelEasy.startAnimation(anim_Top);
                levelHard.startAnimation(anim_bot);
            }
        });
        levelHard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MusicAdapter.playSoundPool(1);
                LevelGameHard("hard");
                levelEasy.startAnimation(anim_Top);
                levelHard.startAnimation(anim_bot);
            }
        });
    }

    private void initView() {
        levelEasy = findViewById(R.id.levelEasy);
        levelMedium = findViewById(R.id.levelMedium);
        levelHard = findViewById(R.id.levelHard);
        diemcaoez = findViewById(R.id.diemcaoez);
        starMedium = findViewById(R.id.starMedium);
        starHard = findViewById(R.id.starHard);
        starEz = findViewById(R.id.starEz);
        txt_medium = findViewById(R.id.txt_medium);
        txt_hard = findViewById(R.id.txt_hard);
    }

    public void LevelGameEasy(String levelGame) {
        Intent intent = new Intent(this, GamePlayEasy.class);
        intent.putExtra("level", levelGame);
        String type = getIntent().getStringExtra("type");
        intent.putExtra("type", type);
        startActivity(intent);
    }

    public void LevelGameMedium(String levelGame) {
        Intent intent = new Intent(this, GamePlayMedium.class);
        intent.putExtra("level", levelGame);
        String type = getIntent().getStringExtra("type");
        intent.putExtra("type", type);
        startActivity(intent);
    }

    public void LevelGameHard(String levelGame) {
        Intent intent = new Intent(this, GamePlayHard.class);
        intent.putExtra("level", levelGame);
        String type = getIntent().getStringExtra("type");
        intent.putExtra("type", type);
        startActivity(intent);
    }

    int getIntShare(String key) {
        return  sharedPreferences.getInt(key, 0);
    }
}