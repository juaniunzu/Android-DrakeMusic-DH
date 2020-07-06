package com.example.projectointegrador.service;

import android.app.IntentService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.projectointegrador.util.DrakePlayer;
import com.example.projectointegrador.view.notification.CreateNotification;

import java.io.IOException;

//16097
public class NotificationActionService extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        toast(context);
        context.sendBroadcast(new Intent("TRACKS_TRACKS")
        .putExtra("actionname", intent.getAction()));


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
