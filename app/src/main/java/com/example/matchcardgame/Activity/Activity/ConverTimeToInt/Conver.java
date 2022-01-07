package com.example.matchcardgame.Activity.Activity.ConverTimeToInt;

public class Conver {
    public  int toInt(String time){
        int i=0;
       while (i<time.length()||!String.valueOf(time.indexOf(i)).equals(":")) i++;
         String phut=time.substring(i).trim();
         String giay=time.substring(i+1,time.length()).trim();

        return Integer.parseInt(phut)*60+Integer.parseInt(giay);
    }
}
