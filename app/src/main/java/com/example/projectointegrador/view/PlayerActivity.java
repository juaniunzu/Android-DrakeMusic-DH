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
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.projectointegrador.R;
import com.example.projectointegrador.databinding.ActivityPlayerBinding;
import com.example.projectointegrador.model.Track;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PlayerActivity extends AppCompatActivity implements PlayerFragment.PlayerFragmentListener {

    public static final String KEY_TRACK = "track";
    public static final String KEY_LISTA = "lista";

    private ViewPager viewPager;
    private Toolbar toolbar;
    private MediaPlayer audioPlayer;
    private ArrayList<Track> trackArrayList;
    private ActivityPlayerBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPlayerBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        viewPager = binding.activityPlayerViewPager;
        toolbar = binding.activityPlayerToolbar;

        setSupportActionBar(toolbar);

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

        audioPlayer = new MediaPlayer();
        audioPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        prepararTrackParaReproduccion(viewPager.getCurrentItem());
        audioPlayer.start();


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
    public void onClickPlay(ImageView botonPlay, ImageView botonPause) {
        if(audioPlayer == null){
            audioPlayer = new MediaPlayer();
            audioPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            prepararTrackParaReproduccion(viewPager.getCurrentItem());
        }
        audioPlayer.start();
        botonPlay.setVisibility(View.GONE);
        botonPause.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClickPause(ImageView botonPause, ImageView botonPlay) {
        audioPlayer.pause();
        botonPause.setVisibility(View.GONE);
        botonPlay.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClickNext() {

    }

    @Override
    public void onClickPrevious() {

    }

    @Override
    public void onClickShuffle(ImageView boton) {

    }

    @Override
    public void onClickRepeat(ImageView boton) {

    }

    @Override
    public void onClickAddFavorite(ImageView boton) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //audioPlayer.release();
    }
}
