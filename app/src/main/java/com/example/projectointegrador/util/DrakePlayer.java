package com.example.projectointegrador.util;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.IBinder;

import androidx.annotation.Nullable;

import java.util.List;

public class DrakePlayer extends Service {

    private static List<String> trackList;
    private static MediaPlayer mediaPlayer;
    private static DrakePlayer instance;
    private static final String HOLA = "hola";

    public static DrakePlayer getInstance() {
        if (instance == null) {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            instance = new DrakePlayer();
        }

        return instance;
    }

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        return START_STICKY;

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        stopSelf();
    }
}
