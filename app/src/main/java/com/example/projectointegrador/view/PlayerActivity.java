package com.example.projectointegrador.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.projectointegrador.R;
import com.example.projectointegrador.controller.TrackController;
import com.example.projectointegrador.databinding.ActivityPlayerBinding;
import com.example.projectointegrador.model.Track;
import com.example.projectointegrador.util.ResultListener;
import com.example.projectointegrador.view.adapter.TrackSearchAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PlayerActivity extends AppCompatActivity implements PlayerFragment.PlayerFragmentListener {

    public static final String KEY_TRACK = "track";
    public static final String KEY_LISTA = "lista";

    private ViewPager viewPager;
    private Toolbar toolbar;
    private MediaPlayer audioPlayer;
    private ArrayList<Track> trackArrayList;
    private ActivityPlayerBinding binding;
    private Runnable runnable;
    private Handler handler;
    private SeekBar seekBar;
    private FirebaseUser firebaseUser;
    private FirebaseAuth firebaseAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPlayerBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);


        viewPager = binding.activityPlayerViewPager;
        toolbar = binding.activityPlayerToolbar;

        setSupportActionBar(toolbar);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
            supportActionBar.setTitle("");
        }

        Intent desdeMain = getIntent();
        Bundle datosDesdeMain = desdeMain.getExtras();
        Track trackClickeado = (Track) datosDesdeMain.getSerializable(KEY_TRACK);
        trackArrayList = (ArrayList<Track>) datosDesdeMain.getSerializable(KEY_LISTA);
        List<Fragment> listaFragments = generarFragments(trackArrayList);

        Integer indice = trackArrayList.indexOf(trackClickeado);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), listaFragments);

        viewPager.setAdapter(viewPagerAdapter);

        viewPager.setCurrentItem(indice);

        handler = new Handler();


        audioPlayer = new MediaPlayer();
        audioPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        prepararTrackParaReproduccion(viewPager.getCurrentItem());
        audioPlayer.start();
        agregarTrackAUltimosReproducidos(trackArrayList.get(viewPager.getCurrentItem()));


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {


            }

            @Override
            public void onPageSelected(int position) {
                if(audioPlayer != null){
                    if(audioPlayer.isPlaying()){
                        audioPlayer.stop();
                    }
                    audioPlayer.reset();
                    audioPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    prepararTrackParaReproduccion(position);
                    audioPlayer.start();
                    agregarTrackAUltimosReproducidos(trackArrayList.get(position));
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });



    }

    private void prepararTrackParaReproduccion(Integer ordenTrackEnLista){
        Track track = this.trackArrayList.get(ordenTrackEnLista);
        try {
            audioPlayer.setDataSource(this, Uri.parse(track.getPreview()));
            audioPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private List<Fragment> generarFragments(List<Track> listaDeTracks){
        List<Fragment> listaADevolver = new ArrayList<>();
        for (Track track : listaDeTracks) {
            Fragment fragment = PlayerFragment.crearPlayerFragment(track, this);
            listaADevolver.add(fragment);
        }
        return listaADevolver;
    }

    private void pegarFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.activityPlayerFragmentContainer, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            onBackPressed();
        }
        return true;
    }

    @Override
    public void onClickPlay(ToggleButton boton) {
        if(!boton.isChecked()){
            if(audioPlayer == null){
                audioPlayer = new MediaPlayer();
                audioPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                prepararTrackParaReproduccion(viewPager.getCurrentItem());
            }
            audioPlayer.start();
            boton.setBackground(getDrawable(R.drawable.ic_pause_circle_filled_black_24dp));
        } else {
            audioPlayer.pause();
            boton.setBackground(getDrawable(R.drawable.ic_play_circle_filled_black_24dp));
        }
    }

    @Override
    public void onClickNext() {
        int fragmentActual = viewPager.getCurrentItem();
        viewPager.setCurrentItem(fragmentActual + 1);
    }

    @Override
    public void onClickPrevious() {
        int fragmentActual = viewPager.getCurrentItem();
        viewPager.setCurrentItem(fragmentActual - 1);
    }

    @Override
    public void onClickShuffle(ToggleButton boton) {

        if(boton.isChecked()){
            boton.setBackground(getDrawable(R.drawable.ic_shuffle_accent_24dp));
            final int cantTemas = trackArrayList.size();
            for (int i = 0; i < cantTemas; i++) {
                audioPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        Random r = new Random();
                        int indiceTemaNuevo = r.nextInt(cantTemas);
                        viewPager.setCurrentItem(indiceTemaNuevo);
                    }
                });
            }
        } else {
            boton.setBackground(getDrawable(R.drawable.ic_shuffle_black_24dp));
            audioPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    viewPager.setCurrentItem(viewPager.getCurrentItem());
                }
            });
        }

    }

    @Override
    public void onClickRepeat(ToggleButton boton) {
        if(boton.isChecked()){
            audioPlayer.setLooping(true);
            boton.setBackground(getDrawable(R.drawable.ic_repeat_accent_24dp));
        } else {
            audioPlayer.setLooping(false);
            boton.setBackground(getDrawable(R.drawable.ic_repeat_black_24dp));
        }
    }

    @Override
    public void onClickAddTrackFavorite(Track track) {
        TrackController trackController = new TrackController();
        trackController.agregarTrackAFavoritos(track, firebaseUser, new ResultListener<Track>() {
            @Override
            public void finish(Track resultado) {
                Toast.makeText(PlayerActivity.this, "Track agregado a Favoritos!", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void seekBar(SeekBar seekBar) {
        //programar seekbar
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        audioPlayer.release();
    }

    private void agregarTrackAUltimosReproducidos(Track track){
        TrackController trackController = new TrackController();
        trackController.agregarTrackAUltimosReproducidos(track, firebaseUser, new ResultListener<Track>() {
            @Override
            public void finish(Track resultado) {
                Toast.makeText(PlayerActivity.this, "Track agregado", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
