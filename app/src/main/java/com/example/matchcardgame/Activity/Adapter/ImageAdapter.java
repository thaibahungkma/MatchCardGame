package com.example.matchcardgame.Activity.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.matchcardgame.R;

import java.util.Random;


public class ImageAdapter extends BaseAdapter {

    Context context;
    boolean check = false;
    Integer[] androidPhotos;
    int sizeOfSolved;

    @Override
    public int getCount() {
        return androidPhotos.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public ImageAdapter(Context context, boolean shuffle, String typeGame, String level) {
        this.context = context;
        if (check == false) {
            if (typeGame.equals("emoji") & level.equals("easy")) {
                androidPhotos = new Integer[]{
                        R.drawable.e1,
                        R.drawable.e2,
                        R.drawable.e3,
                        R.drawable.e4,
                        R.drawable.e5,
                        R.drawable.e6,
                        R.drawable.e1,
                        R.drawable.e2,
                        R.drawable.e3,
                        R.drawable.e4,
                        R.drawable.e5,
                        R.drawable.e6,

                };
            }
            if (typeGame.equals("emoji") & level.equals("medium")) {
                androidPhotos = new Integer[]{
                        R.drawable.e1,
                        R.drawable.e2,
                        R.drawable.e3,
                        R.drawable.e4,
                        R.drawable.e5,
                        R.drawable.e6,
                        R.drawable.e1,
                        R.drawable.e2,
                        R.drawable.e3,
                        R.drawable.e4,
                        R.drawable.e5,
                        R.drawable.e6,
                        R.drawable.e7,
                        R.drawable.e7,
                        R.drawable.e8,
                        R.drawable.e8,
                        R.drawable.e9,
                        R.drawable.e9,

                };
            }
            if (typeGame.equals("emoji") & level.equals("hard")) {
                androidPhotos = new Integer[]{
                        R.drawable.e1,
                        R.drawable.e2,
                        R.drawable.e3,
                        R.drawable.e4,
                        R.drawable.e5,
                        R.drawable.e6,
                        R.drawable.e1,
                        R.drawable.e2,
                        R.drawable.e3,
                        R.drawable.e4,
                        R.drawable.e5,
                        R.drawable.e6,
                        R.drawable.e7,
                        R.drawable.e7,
                        R.drawable.e8,
                        R.drawable.e8,
                        R.drawable.e9,
                        R.drawable.e9,
                        R.drawable.e10,
                        R.drawable.e10,
                        R.drawable.e11,
                        R.drawable.e11,
                        R.drawable.e12,
                        R.drawable.e12,

                };
            }
            else if (typeGame.equals("animal")& level.equals("easy")){
                androidPhotos = new Integer[]{
                        R.drawable.bear,
                        R.drawable.buffalo,
                        R.drawable.frog,
                        R.drawable.giraffe,
                        R.drawable.goat,
                        R.drawable.gorilla,
                        R.drawable.bear,
                        R.drawable.buffalo,
                        R.drawable.frog,
                        R.drawable.giraffe,
                        R.drawable.goat,
                        R.drawable.gorilla,

                };
            }
            else if (typeGame.equals("animal")& level.equals("medium")){
                androidPhotos = new Integer[]{
                        R.drawable.bear,
                        R.drawable.bear,
                        R.drawable.buffalo,
                        R.drawable.buffalo,
                        R.drawable.frog,
                        R.drawable.frog,
                        R.drawable.giraffe,
                        R.drawable.giraffe,
                        R.drawable.goat,
                        R.drawable.goat,
                        R.drawable.gorilla,
                        R.drawable.gorilla,
                        R.drawable.panda,
                        R.drawable.panda,
                        R.drawable.parrot,
                        R.drawable.parrot,
                        R.drawable.pig,
                        R.drawable.pig,

                };
            }
            else if (typeGame.equals("animal")& level.equals("hard")){
                androidPhotos = new Integer[]{
                        R.drawable.bear,
                        R.drawable.bear,
                        R.drawable.buffalo,
                        R.drawable.buffalo,
                        R.drawable.frog,
                        R.drawable.frog,
                        R.drawable.giraffe,
                        R.drawable.giraffe,
                        R.drawable.goat,
                        R.drawable.goat,
                        R.drawable.gorilla,
                        R.drawable.gorilla,
                        R.drawable.panda,
                        R.drawable.panda,
                        R.drawable.parrot,
                        R.drawable.parrot,
                        R.drawable.pig,
                        R.drawable.pig,
                        R.drawable.rabbit,
                        R.drawable.rabbit,
                        R.drawable.elephant,
                        R.drawable.elephant,
                        R.drawable.penguin,
                        R.drawable.penguin,


                };
            }

        }

        if (shuffle) {
            shuffleArray();
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView card;
        if (convertView == null) {
            card = new ImageView(context);
            card.setLayoutParams(new GridView.LayoutParams(50, 50));
            card.setScaleType(ImageView.ScaleType.CENTER_CROP);
        } else {
            card = (ImageView) convertView;
        }

        if (check) {
            if (position < sizeOfSolved) {
                card.setImageResource(androidPhotos[position]);
                card.setTag(R.drawable.checkmark);
            } else {
                card.setImageResource(R.drawable.card);
                card.setTag(R.drawable.card);
            }
        } else {
            card.setImageResource(R.drawable.card);
            card.setTag(R.drawable.card);
        }

        ViewGroup.LayoutParams imageLayout = card.getLayoutParams();
        imageLayout.width = 250;
        imageLayout.height = 250;

        return card;
    }

    public void shuffleArray() {
        Random random = new Random();
        for (int i = androidPhotos.length - 1; i > 0; i--) {
            int index = random.nextInt(i + 1);

            int a = androidPhotos[index];
            androidPhotos[index] = androidPhotos[i];
            androidPhotos[i] = a;
        }
    }

    public Integer[] getArray() {
        return androidPhotos;
    }

    public void setArray(Integer[] androidPhotos) {
        this.androidPhotos = androidPhotos;
    }

    public void updateAdapter(Integer[] newArray, int newSize) {
        androidPhotos = null;
        androidPhotos = newArray;
        check = true;
        sizeOfSolved = newSize;
        notifyDataSetChanged();
    }
}
