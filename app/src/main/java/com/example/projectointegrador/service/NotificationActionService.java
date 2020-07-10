package com.example.projectointegrador.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;

import com.example.projectointegrador.util.DrakePlayer;
import com.example.projectointegrador.view.notification.CreateNotification;

import java.io.IOException;

public class NotificationActionService extends BroadcastReceiver {

    private DrakePlayer drakePlayer;

    @Override
    public void onReceive(Context context, Intent intent) {
        drakePlayer = DrakePlayer.getInstance();
        context.sendBroadcast(new Intent("TRACKS_TRACKS")
                .putExtra("actionname", intent.getAction()));

        String action = intent.getAction();
        drakePlayer.getMediaPlayer().setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                drakePlayer.setTemaSiguienteEnLista(context);
            }
        });

        switch (action) {
            case CreateNotification.ACTION_PREVIOUS:
                try {
                    drakePlayer.prev(context);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case CreateNotification.ACTION_PLAY:
                if(drakePlayer.getMediaPlayer().isPlaying()){
                    drakePlayer.pause(context);
                } else {
                    drakePlayer.start(context);
                }
                break;
            case CreateNotification.ACTION_NEXT:
                try {
                    drakePlayer.next(context);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }

    }

}
