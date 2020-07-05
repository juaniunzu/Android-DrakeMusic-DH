package com.example.projectointegrador.util;

import android.media.AudioManager;
import android.media.MediaPlayer;

public class DrakePlayer {

    private static MediaPlayer mediaPlayer;
    private static DrakePlayer instance;

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
}
