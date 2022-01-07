package com.example.matchcardgame.Activity.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.matchcardgame.Activity.Adapter.MusicAdapter;
import com.example.matchcardgame.R;

public class MainActivity extends AppCompatActivity {
    Button playGameBtn;
    ImageButton aboutUsBtn, settingBtn;
    private MusicAdapter musicAdapter;
    Animation anim_turnLeft, anim_turnRight, anim_Top, anim_bot;
    TextView gameNameIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        initView();
        musicAdapter.playSound(this);
        musicAdapter.CreateSound(this);

        //animation
        anim_turnRight= AnimationUtils.loadAnimation(this, R.anim.anim_turn_right);
        anim_turnLeft= AnimationUtils.loadAnimation(this, R.anim.anim_turn_left);
        anim_Top= AnimationUtils.loadAnimation(this, R.anim.anim_turn_top);

        //bat su kien Playgame
        playGameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MusicAdapter.playSoundPool(1);
                Intent intent=new Intent(MainActivity.this,TopicCard.class);
                startActivity(intent);
                aboutUsBtn.startAnimation(anim_turnLeft);
                settingBtn.startAnimation(anim_turnRight);
                gameNameIv.startAnimation(anim_Top);
            }
        });


        //bat su kien Setting
        settingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MusicAdapter.playSoundPool(1);
                openSettingDialog(Gravity.CENTER);
            }
        });

        //bat su kien AboutUs
        aboutUsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MusicAdapter.playSoundPool(1);
                Intent intent=new Intent(MainActivity.this, TutorialGame.class);
                startActivity(intent);
            }
        });
    }
    //tao dialog game Setting
    private void openSettingDialog(int gravity) {
        final Dialog dialog= new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_dialog_setting);

        Window window = dialog.getWindow();
        if (window ==null){
            return;
        }
        window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAttributes =window.getAttributes();
        windowAttributes.gravity =gravity;
        window.setAttributes(windowAttributes);
        //set co the out dialog
        if (Gravity.CENTER ==gravity){
            dialog.setCancelable(true);
        } else {
            dialog.setCancelable(false);
        }

        //int view setting
        LinearLayout linear_audio_eff =dialog.findViewById(R.id.linear_audio_eff);
        LinearLayout linear_audio_bg =dialog.findViewById(R.id.linear_audio_bg);
        ImageView audio_eff_iv =dialog.findViewById(R.id.audio_eff_iv);
        ImageView audio_bg_iv =dialog.findViewById(R.id.audio_bg_iv);
        TextView audio_bg_tv = dialog.findViewById(R.id.audio_bg_tv);
        TextView audio_eff_tv = dialog.findViewById(R.id.audio_eff_tv);
        ImageButton close_dialog_btn=dialog.findViewById(R.id.close_dialog_btn);

        if (musicAdapter.soundStatus==0){
            audio_bg_iv.setImageResource(R.drawable.ic_volume_on);
            audio_bg_tv.setText("Background Audio ON");
        } if (musicAdapter.soundStatus==2){
            audio_bg_iv.setImageResource(R.drawable.ic_volume_off_24);
            audio_bg_tv.setText("Background Audio OFF");
        }
        if (musicAdapter.soundPoolStatus==1){
            audio_eff_iv.setImageResource(R.drawable.ic_volume_on);
            audio_eff_tv.setText("Effect Audio ON");
        }
        if (musicAdapter.soundPoolStatus==0){
            audio_eff_iv.setImageResource(R.drawable.ic_volume_off_24);
            audio_eff_tv.setText("Effect Audio OFF");
        }

        //xu ly su kien dialog
        linear_audio_bg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MusicAdapter.playSoundPool(1);
                if (musicAdapter.soundStatus==0){
                    musicAdapter.pauseSound();
                    audio_bg_iv.setImageResource(R.drawable.ic_volume_off_24);
                    audio_bg_tv.setText("Background Audio OFF");
                    musicAdapter.soundStatus=2;
                } else {
                    musicAdapter.startSound();
                    audio_bg_iv.setImageResource(R.drawable.ic_volume_on);
                    audio_bg_tv.setText("Background Audio ON");
                    musicAdapter.soundStatus=0;

                }

            }
        });
        linear_audio_eff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MusicAdapter.soundPoolStatus==1){
                    MusicAdapter.pauseSoundPool();
                    audio_eff_iv.setImageResource(R.drawable.ic_volume_off_24);
                    audio_eff_tv.setText("Effect Audio OFF");
                    MusicAdapter.soundPoolStatus=0;
                }
                else {
                    MusicAdapter.resumeSoundPool();
                    audio_eff_iv.setImageResource(R.drawable.ic_volume_on);
                    audio_eff_tv.setText("Effect Audio ON");
                    MusicAdapter.soundPoolStatus=1;
                }


            }
        });


        close_dialog_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MusicAdapter.playSoundPool(1);
                dialog.dismiss();
            }
        });
        dialog.show();


    }
    //anh xa view
    private void initView() {
        playGameBtn = findViewById(R.id.playGameBtn);
        aboutUsBtn = findViewById(R.id.aboutUsBtn);
        settingBtn = findViewById(R.id.settingBtn);
        gameNameIv=findViewById(R.id.gameName);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        musicAdapter.stopSound();
        finish();
    }
}