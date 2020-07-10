package com.example.projectointegrador.util;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.example.projectointegrador.model.Track;

import java.io.IOException;
import java.util.ArrayList;

public class DrakePlayer extends Service {

    private static MediaPlayer mediaPlayer;
    private static DrakePlayer instance;
    private ArrayList<Track> trackList;
    private Track trackActual;

    private DrakePlayer() {
    }

    public static DrakePlayer getInstance() {
        if (instance == null) {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            instance = new DrakePlayer();
        }

        return instance;
    }

    public ArrayList<Track> getTrackList() {
        return trackList;
    }

    public void setTrackList(ArrayList<Track> trackList) {
        this.trackList = trackList;
    }

    public Track getTrackActual() {
        return trackActual;
    }

    public void setTrackActual(Track trackActual) {
        this.trackActual = trackActual;
    }

    public void start(){
        mediaPlayer.start();
    }

    public void pause(){
        mediaPlayer.pause();
    }

    public void stop(){
        mediaPlayer.stop();
    }

    public void prepararTrackParaReproduccion(Context context, int ordenTrackEnLista) throws IOException {
        mediaPlayer.reset();
        Track trackAPreparar = trackList.get(ordenTrackEnLista);
        mediaPlayer.setDataSource(context, Uri.parse(trackAPreparar.getPreview()));
        mediaPlayer.prepareAsync();

    }

    public void setPlayerInicio(Context context, ArrayList<Track> trackList, int position) throws IOException {
        setTrackList(trackList);
        prepararTrackParaReproduccion(context, position);
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                start();
            }
        });
    }

    public void setPlayerTemaNuevo (Context context, int posicionNueva) throws IOException {
        mediaPlayer.reset();
        prepararTrackParaReproduccion(context, posicionNueva);
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                start();
            }
        });

    }

    public void next(Context context) throws IOException {
        if(trackList.indexOf(trackActual) < trackList.size() - 1){
            Track trackNuevo = trackList.get(trackList.indexOf(trackActual) + 1);
            setPlayerTemaNuevo(context, trackList.indexOf(trackNuevo));
        }
    }

    public void prev(Context context) throws IOException {
        if(trackList.indexOf(trackActual) > 0){
            Track trackNuevo = trackList.get(trackList.indexOf(trackActual) - 1);
            setPlayerTemaNuevo(context, trackList.indexOf(trackNuevo));
        }
    }

    public void shuffle(){

    }

    public void repeat(boolean repeat){
        mediaPlayer.setLooping(repeat);
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
