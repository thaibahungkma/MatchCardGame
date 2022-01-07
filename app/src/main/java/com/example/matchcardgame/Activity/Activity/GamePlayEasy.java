package com.example.matchcardgame.Activity.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.matchcardgame.Activity.Activity.ConverTimeToInt.Conver;
import com.example.matchcardgame.Activity.Adapter.ImageAdapter;
import com.example.matchcardgame.Activity.Adapter.MusicAdapter;
import com.example.matchcardgame.R;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.TimeUnit;


public class GamePlayEasy extends AppCompatActivity {
    TextView timeCountTv;
    String levelSelect, type;
    int pointCounter;
    private boolean won = false;
    CountDownTimer countDownTimer;

    int vitri;
    View view;
    ArrayList<Integer> timlatlist;
    int image;
    GridView gridView;
    ImageView img_pause;
    Integer[] gameArray;
    ImageAdapter adapter;
    ArrayList<ImageView> activeCards;
    ArrayList<Integer> checkMarkIndexes;
    ArrayList<Integer> gameArrayList;
    int[] indexes;
    String sDuration;
    long duration;
    MusicAdapter musicAdapter;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;


    final  String KEY_PRE_HIGH_SOCCER="KEY_PRE_HIGH_SOCCER";
    final String KEY_HIGH_SOCCER_EASY="KEY_HIGH_SOCCER_EASY";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_play_easy);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        sharedPreferences = this.getSharedPreferences(KEY_PRE_HIGH_SOCCER, MODE_PRIVATE);
        editor = sharedPreferences.edit();
        initView();
        musicAdapter.CreateSound(this);
        pointCounter = 0;
        img_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              openPauseDialog(Gravity.CENTER);
            }
        });
        //Nhận data từ activity SelectLevel để set TimeCount
        levelSelect=getIntent().getStringExtra("level");
        type=getIntent().getStringExtra("type");
        if (type.equals("animal")& levelSelect.equals("easy")){
            adapter = new ImageAdapter(this, true, "animal", "easy");
        }else if (type.equals("emoji")& levelSelect.equals("easy")){
            adapter = new ImageAdapter(this, true, "emoji", "easy");
        }

        gameArray = adapter.getArray();
        activeCards = new ArrayList<>();
        gridView.setAdapter(adapter);
        indexes = new int[2];
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MusicAdapter.playSoundPool(1);
                checkGame(view, position);
            }
        });
        if (levelSelect.equals("easy")){
            timeCountEasy();
        }
    }
    void resumeTime(int time){

        countDownTimer=new CountDownTimer(time, 1000) {
            @Override
            public void onTick(long l) {

                //convert millisecond to minute and second
                sDuration= String.format(Locale.ENGLISH, "%02d : %02d",
                        TimeUnit.MILLISECONDS.toMinutes(l)
                        ,TimeUnit.MILLISECONDS.toSeconds(l)-
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(l)));
                timeCountTv.setText(sDuration);
                //set String to textview


            }

            @Override
            public void onFinish() { timeCountTv.setText("Over");
                MusicAdapter.playSoundPool(3);
                if (pointCounter<6){
                    openOverDialog(Gravity.CENTER);
                }


            }
        }.start();
    }
    private void openPauseDialog(int gravity) {
        countDownTimer.cancel();
        int time=toIntTime(sDuration)*1000;
        final Dialog dialog= new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_dialog_pause);

        Window window = dialog.getWindow();
        if (window ==null){
            return;
        }
        window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAttributes =window.getAttributes();
        windowAttributes.gravity =gravity;
        window.setAttributes(windowAttributes);
        ImageView playIv, BackIv;
        playIv=dialog.findViewById(R.id.play);
        BackIv=dialog.findViewById(R.id.back);

        playIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               resumeTime(time);
               dialog.dismiss();
            }
        });

        BackIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //set co the out dialog
//        if (Gravity.CENTER ==gravity){
//            dialog.setCancelable(true);
//        } else {
//            dialog.setCancelable(false);
//        }
        dialog.setCancelable(false);
        dialog.show();


    }

    private void checkGame(View card, int position) {
//        final int[] music = new int[1];
        if ((int) (((ImageView) card).getTag()) != R.drawable.checkmark && activeCards.size() < 1) {
            view = card;
            vitri = position;
            image = gameArray[position];
            activeCards.add((ImageView) card);
            checkMarkIndexes = new ArrayList<>();
            indexes[0] = position;
            ObjectAnimator flip = ObjectAnimator.ofFloat(card, "rotationY", 0f, 180f);
            flip.setDuration(150);
            flip.start();



            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    ((ImageView) card).setImageResource(gameArray[position]);
                    ((ImageView) card).setTag(gameArray[position]);
                }

            }, 250);

        }

        //So user doesn't press same image twice
        else if ((int) (((ImageView) card).getTag()) != R.drawable.checkmark && !((ImageView) card).equals(activeCards.get(0))) {
            activeCards.add((ImageView) card);
            indexes[1] = position;
            view = card;
            ObjectAnimator flip = ObjectAnimator.ofFloat(card, "rotationY", 0f, 180f);
            flip.setDuration(250);
            flip.start();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    ((ImageView) card).setImageResource(gameArray[position]);
                    ((ImageView) card).setTag(gameArray[position]);
//                    music[0] = gameArray[position];
                }
            }, 250);
        }

        if (activeCards.size() > 1) {
            gridView.setEnabled(false);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    int size = activeCards.size();

                    //player doesn't choose correct
                    ObjectAnimator flip;
                    if (!(activeCards.get(0).getTag().equals(activeCards.get(1).getTag()))) {
                        for (int i = 0; i < size; i++) {

                            flip = ObjectAnimator.ofFloat(activeCards.get(0), "rotationY", 0f, 180f);
                            flip.setDuration(250);
                            flip.start();

                            activeCards.get(0).setImageResource(R.drawable.card);
                            activeCards.get(0).setTag(R.drawable.card);
                            activeCards.remove(0);

                        }
                    }
                    //Player choose correct two cards

                    else if ((int) activeCards.get(0).getTag() != R.drawable.checkmark && (int) activeCards.get(1).getTag() != R.drawable.checkmark) {
                        pointCounter++;
//                        points.setText(Integer.toString(pointCounter));
                        MusicAdapter.playSoundPool(2);

                        for (int i = 0; i < activeCards.size(); i++) {

                            gridView.getAdapter().getView(indexes[i], null, gridView).setTag(R.drawable.checkmark);
                            checkMarkIndexes.add(indexes[i]);
                            activeCards.get(i).setTag(R.drawable.checkmark);
                        }
                        //clear when 2 card equal and done handle
                        activeCards.clear();



                    }
                    gridView.setEnabled(true);

                    if (isWon()) {
                        showWonDialog(Gravity.CENTER);
                    }

                }

            }, 500);

        }

    }

    private void showWonDialog(int gravity) {
        countDownTimer.cancel();

        final Dialog dialog= new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_dialog_won);
        TextView timePlayIv=dialog.findViewById(R.id.timePlayIv);
        int diem=toIntTime(sDuration);
        if(getIntShare(KEY_HIGH_SOCCER_EASY)<diem){
            putIntShare(KEY_HIGH_SOCCER_EASY,diem);
        }

        timePlayIv.setText(""+(60-diem)+" Seconds");
        Window window = dialog.getWindow();
        if (window ==null){
            return;
        }
        window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAttributes =window.getAttributes();
        windowAttributes.gravity =gravity;
        window.setAttributes(windowAttributes);
        ImageView returnIv, restartGameIv;
        returnIv=dialog.findViewById(R.id.returnIv);

        restartGameIv=dialog.findViewById(R.id.restartGameIv);

        returnIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        restartGameIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }
        });

        //set co the out dialog
//        if (Gravity.CENTER ==gravity){
//            dialog.setCancelable(true);
//        } else {
//            dialog.setCancelable(false);
//        }
        dialog.setCancelable(false);
        dialog.show();


    }

    void putIntShare(String key, int value) {
        editor.putInt(key, value);
        editor.apply();
    }

    int getIntShare(String key) {
        return  sharedPreferences.getInt(key, 0);
    }



    private void timeCountEasy() {

         duration = TimeUnit.MINUTES.toMillis(1);

        countDownTimer=new CountDownTimer(duration, 1000) {
            @Override
            public void onTick(long l) {

                //convert millisecond to minute and second
                sDuration= String.format(Locale.ENGLISH, "%02d : %02d",
                        TimeUnit.MILLISECONDS.toMinutes(l)
                        ,TimeUnit.MILLISECONDS.toSeconds(l)-
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(l)));
                timeCountTv.setText(sDuration);
                //set String to textview


            }

            @Override
            public void onFinish() { timeCountTv.setText("Over");
            MusicAdapter.playSoundPool(3);
            if (pointCounter<6){
                openOverDialog(Gravity.CENTER);
            }


            }
        }.start();
    }


    public void shuffleArray() {
        Random random = new Random();
        for (int i = gameArrayList.size() - 1; i > 0; i--) {
            int index = random.nextInt(i + 1);

            int a = gameArrayList.get(index);
            gameArrayList.set(index, gameArrayList.get(i));
            gameArrayList.set(i, a);
        }
    }

    private void initView() {
        timeCountTv=findViewById(R.id.timeCount);
        img_pause=findViewById(R.id.img_pause);
        gridView=findViewById(R.id.game_layout);
    }
    public boolean isWon() {
        if (pointCounter > 5) {
            won = true;
        }
        return won;
    }

    private void openOverDialog(int gravity) {
        final Dialog dialog= new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_dialog_over);

        Window window = dialog.getWindow();
        if (window ==null){
            return;
        }
        window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAttributes =window.getAttributes();
        windowAttributes.gravity =gravity;
        window.setAttributes(windowAttributes);
        ImageView returnIv, restartGameIv;
        returnIv=dialog.findViewById(R.id.returnIv);
        restartGameIv=dialog.findViewById(R.id.restartGameIv);

        returnIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        restartGameIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }
        });

        //set co the out dialog
//        if (Gravity.CENTER ==gravity){
//            dialog.setCancelable(true);
//        } else {
//            dialog.setCancelable(false);
//        }
        dialog.setCancelable(false);
        dialog.show();


    }

    public  int toIntTime(@NonNull String time){
        String[] arr = time.replaceAll(" ","").split(":");
        return Integer.parseInt(arr[0])*60+Integer.parseInt(arr[1]);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
}