package com.example.matchcardgame.Activity.Adapter;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.media.SoundPool;

import androidx.appcompat.app.AppCompatActivity;

import com.example.matchcardgame.R;

public class MusicAdapter extends AppCompatActivity {
    public static MediaPlayer mediaPlayerSoundBg;
    public static int soundStatus, soundPoolStatus;
    public static SoundPool soundPool;
    public static int great, wrong, click;

    public static void playSound(Context context) {
        mediaPlayerSoundBg = MediaPlayer.create(context, R.raw.nhacnen);
        mediaPlayerSoundBg.setVolume(50, 50);
        mediaPlayerSoundBg.setLooping(true);
        mediaPlayerSoundBg.setAudioAttributes(
                new AudioAttributes
                        .Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .build());
        mediaPlayerSoundBg.start();
        soundStatus = 0;

    }

    public static void stopSound() {
        if (mediaPlayerSoundBg != null)
            mediaPlayerSoundBg.stop();
            mediaPlayerSoundBg.release();
        soundStatus = 1;
    }

    public static void pauseSound(){
        if (mediaPlayerSoundBg!=null){
            mediaPlayerSoundBg.pause();
            soundStatus=2;
        }
    }
    public static void startSound(){
        if (mediaPlayerSoundBg!=null){
            mediaPlayerSoundBg.start();
            soundStatus=0;
        }
    }
    public static void CreateSound(Context context){
        AudioAttributes attrs = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();
        soundPool = new SoundPool.Builder()
                .setMaxStreams(10)
                .setAudioAttributes(attrs)
                .build();
        great= soundPool.load(context, R.raw.greate, 1);
        wrong= soundPool.load(context, R.raw.errosound, 1);
        click= soundPool.load(context, R.raw.click, 1);
        soundPoolStatus=1;

    }
    public static void playSoundPool(int v){
        switch (v){
            case 1:
                soundPool.play(click,1,1,0,0,1);
                break;
            case 2:
                soundPool.play(great,1,1,0,0,1);
                break;
            case 3:
                soundPool.play(wrong,1,1,0,0,1);
                break;
        }

    }
    public static void pauseSoundPool(){
        soundPool.setVolume(1,0f,0f);
        soundPool.setVolume(2,0f,0f);
        soundPool.setVolume(3,0f,0f);
        soundPoolStatus=0;

    }
    public static void resumeSoundPool(){
        soundPool.setVolume(1,1,1);
        soundPool.setVolume(2,1,1);
        soundPool.setVolume(3,1,1);
        soundPoolStatus=1;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        soundPool.release();
        soundPool=null;
    }
}
