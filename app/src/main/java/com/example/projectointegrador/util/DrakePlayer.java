package com.example.projectointegrador.util;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.widget.SeekBar;

import androidx.annotation.Nullable;

import com.example.projectointegrador.R;
import com.example.projectointegrador.model.Track;
import com.example.projectointegrador.view.PlayerActivity;
import com.example.projectointegrador.view.notification.CreateNotification;

import java.io.IOException;
import java.util.ArrayList;

public class DrakePlayer extends Service {

    private static MediaPlayer mediaPlayer;
    private static DrakePlayer instance;
    private ArrayList<Track> trackList;
    private Track trackActual;

    public DrakePlayer() {
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

    public void start(SeekBar seekBar){
        mediaPlayer.start();
        changeSeekbar(seekBar);
    }

    public void start(Context context){
        mediaPlayer.start();
        CreateNotification.createNotification(context, trackActual,
                R.drawable.ic_pause_circle_filled_black_24dp,
                trackList.indexOf(trackActual), trackList.size() - 1);
    }

    public void pause(Context context){
        mediaPlayer.pause();
        CreateNotification.createNotification(context, trackActual,
                R.drawable.ic_play_circle_filled_black_24dp,
                trackList.indexOf(trackActual), trackList.size() - 1);
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

    public void setPlayerInicio(SeekBar seekBar, Context context, ArrayList<Track> trackList, Track trackClickeado) throws IOException {
        setTrackList(trackList);
        setTrackActual(trackClickeado);
        prepararTrackParaReproduccion(context, trackList.indexOf(trackClickeado));
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                start(seekBar);
                changeSeekbar(seekBar);
            }
        });
        CreateNotification.createNotification(context, trackClickeado,
                R.drawable.ic_pause_circle_filled_black_24dp,
                trackList.indexOf(trackClickeado), trackList.size() - 1);
    }

    public void setPlayerInicio(Context context, ArrayList<Track> trackList, Track trackClickeado) throws IOException {
        setTrackList(trackList);
        setTrackActual(trackClickeado);
        prepararTrackParaReproduccion(context, trackList.indexOf(trackClickeado));
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                start(context);
            }
        });
        CreateNotification.createNotification(context, trackClickeado,
                R.drawable.ic_pause_circle_filled_black_24dp,
                trackList.indexOf(trackClickeado), trackList.size() - 1);
    }

    public void setTemaSiguienteEnLista(SeekBar seekBar, Context context){
        if(trackList.indexOf(trackActual) < trackList.size() - 1){
            Track trackNuevo = trackList.get(trackList.indexOf(trackActual) + 1);
            trackActual = trackNuevo;
            try {
                setPlayerTemaNuevo(seekBar, context, trackList.indexOf(trackNuevo));
                CreateNotification.createNotification(context, trackActual,
                        R.drawable.ic_pause_circle_filled_black_24dp,
                        trackList.indexOf(trackActual), trackList.size() - 1);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void setTemaSiguienteEnLista(Context context){
        if(trackList.indexOf(trackActual) < trackList.size() - 1){
            Track trackNuevo = trackList.get(trackList.indexOf(trackActual) + 1);
            trackActual = trackNuevo;
            try {
                setPlayerTemaNuevo(context, trackList.indexOf(trackNuevo));
                CreateNotification.createNotification(context, trackActual,
                        R.drawable.ic_pause_circle_filled_black_24dp,
                        trackList.indexOf(trackActual), trackList.size() - 1);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void setPlayerTemaNuevo (SeekBar seekBar, Context context, int posicionNueva) throws IOException {
        mediaPlayer.reset();
        prepararTrackParaReproduccion(context, posicionNueva);
        setTrackActual(trackList.get(posicionNueva));
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                start(seekBar);
                changeSeekbar(seekBar);
            }
        });
        CreateNotification.createNotification(context, trackActual,
                R.drawable.ic_pause_circle_filled_black_24dp,
                posicionNueva, trackList.size() - 1);

    }

    public void setPlayerTemaNuevo (Context context, int posicionNueva) throws IOException {
        mediaPlayer.reset();
        prepararTrackParaReproduccion(context, posicionNueva);
        setTrackActual(trackList.get(posicionNueva));
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                start(context);
            }
        });
        CreateNotification.createNotification(context, trackActual,
                R.drawable.ic_pause_circle_filled_black_24dp,
                posicionNueva, trackList.size() - 1);
    }

    private void changeSeekbar(SeekBar seekBar) {
        try {
            seekBar.setProgress(mediaPlayer.getCurrentPosition());
            if (mediaPlayer.isPlaying()) {
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        changeSeekbar(seekBar);
                    }
                };
                new Handler().postDelayed(runnable, 100);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void next(SeekBar seekBar, Context context) throws IOException {
        if(trackList.indexOf(trackActual) < trackList.size() - 1){
            Track trackNuevo = trackList.get(trackList.indexOf(trackActual) + 1);
            trackActual = trackNuevo;
            try {
                setPlayerTemaNuevo(seekBar, context, trackList.indexOf(trackActual));
                CreateNotification.createNotification(context, trackActual,
                        R.drawable.ic_pause_circle_filled_black_24dp,
                        trackList.indexOf(trackActual), trackList.size() - 1);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void next(Context context) throws IOException {
        if(trackList.indexOf(trackActual) < trackList.size() - 1){
            Track trackNuevo = trackList.get(trackList.indexOf(trackActual) + 1);
            trackActual = trackNuevo;
            try {
                setPlayerTemaNuevo(context, trackList.indexOf(trackNuevo));
                CreateNotification.createNotification(context, trackActual,
                        R.drawable.ic_pause_circle_filled_black_24dp,
                        trackList.indexOf(trackActual), trackList.size() - 1);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void prev(SeekBar seekBar, Context context) throws IOException {
        if(trackList.indexOf(trackActual) > 0){
            Track trackNuevo = trackList.get(trackList.indexOf(trackActual) - 1);
            trackActual = trackNuevo;
            try {
                setPlayerTemaNuevo(seekBar, context, trackList.indexOf(trackNuevo));
                trackActual = trackNuevo;
                CreateNotification.createNotification(context, trackActual,
                        R.drawable.ic_pause_circle_filled_black_24dp,
                        trackList.indexOf(trackActual), trackList.size() - 1);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void prev(Context context) throws IOException {
        if(trackList.indexOf(trackActual) > 0){
            Track trackNuevo = trackList.get(trackList.indexOf(trackActual) - 1);
            trackActual = trackNuevo;
            try {
                setPlayerTemaNuevo(context, trackList.indexOf(trackNuevo));
                CreateNotification.createNotification(context, trackActual,
                        R.drawable.ic_pause_circle_filled_black_24dp,
                        trackList.indexOf(trackActual), trackList.size() - 1);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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
