package com.example.projectointegrador.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.widget.Toast;

import com.example.projectointegrador.util.DrakePlayer;
import com.example.projectointegrador.view.notification.CreateNotification;

import java.io.IOException;

public class NotificationActionService extends BroadcastReceiver {

    private DrakePlayer drakePlayer;

    @Override
    public void onReceive(Context context, Intent intent) {
        //toast(context);
        drakePlayer = DrakePlayer.getInstance();
        context.sendBroadcast(new Intent("TRACKS_TRACKS")
                .putExtra("actionname", intent.getAction()));

        String action = intent.getAction();

        switch (action) {
            case CreateNotification.ACTION_PREVIOUS:
                Toast.makeText(context, "se apreto atras", Toast.LENGTH_SHORT).show();
                try {
                    drakePlayer.prev(context);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case CreateNotification.ACTION_PLAY:
                Toast.makeText(context, "se apreto play/pause", Toast.LENGTH_SHORT).show();
                if(drakePlayer.getMediaPlayer().isPlaying()){
                    drakePlayer.pause(context);
                } else {
                    drakePlayer.start(context);
                }
                break;
            case CreateNotification.ACTION_NEXT:
                Toast.makeText(context, "se apreto siguiente", Toast.LENGTH_SHORT).show();
                try {
                    drakePlayer.next(context);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }

    }


    public void toast(Context context){
        MediaPlayer mediaPlayer = DrakePlayer.getInstance().getMediaPlayer();
        try {
            mediaPlayer.reset();
            mediaPlayer.setDataSource("https://cdns-preview-d.dzcdn.net/stream/c-deda7fa9316d9e9e880d2c6207e92260-8.mp3");
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Toast.makeText(context, "DASDASD", Toast.LENGTH_SHORT).show();
    }

//    @Override
//    protected void onHandleIntent(@Nullable Intent intent) {
//        context.sendBroadcast(new Intent("TRACKS_TRACKS").putExtra("actionname", intent.getAction()));
//    }

}
