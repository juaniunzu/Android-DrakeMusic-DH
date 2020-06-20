package com.example.projectointegrador.view;

import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.bumptech.glide.Glide;
import com.example.projectointegrador.databinding.FragmentPlayerBinding;
import com.example.projectointegrador.model.Track;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


import java.io.IOException;

import static com.example.projectointegrador.util.Utils.setFragmentBackground;


public class PlayerFragment extends Fragment {

    public static final String KEY_DETAIL_TRACK = "track";
    private FragmentPlayerBinding binding;
    private ImageView fragmentPlayerImageView;
    private ImageView fragmentPlayerButtonNext;
    private ToggleButton fragmentPlayerButtonPlay;
    private ImageView fragmentPlayerButtonPrevious;
    private ToggleButton fragmentPlayerButtonRepeat;
    private ToggleButton fragmentPlayerButtonShuffle;
    private ImageView fragmentPlayerButtonAddFavorite;
    private TextView fragmentPlayerTextViewArtista;
    private TextView fragmentPlayerTextViewNombre;
    private PlayerFragmentListener listener;
    private Track trackRecibido;
    private SeekBar seekBar;



    /**
     * Nuevo constructor. Setea el fragment segun el {@param track} parametro
     * @return
     */
    public static PlayerFragment crearPlayerFragment(Track track, PlayerFragmentListener listener){
        PlayerFragment fragment = new PlayerFragment();
        fragment.setListener(listener);
        Bundle datosDeTrack = new Bundle();
        datosDeTrack.putSerializable(KEY_DETAIL_TRACK, track);
        fragment.setArguments(datosDeTrack);
        return fragment;
    }

    public PlayerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentPlayerBinding.inflate(inflater, container, false);

        findViews();

        final View view = binding.getRoot();

        Bundle desdeMain = getArguments();
        trackRecibido = (Track) desdeMain.getSerializable(KEY_DETAIL_TRACK);

        setViewResources(trackRecibido);

        setFragmentBackground(getContext(), view, trackRecibido.getAlbum().getCover());

        //fragmentPlayerButtonPlay.setVisibility(View.GONE);

        fragmentPlayerButtonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClickPlay(fragmentPlayerButtonPlay);
            }
        });

        fragmentPlayerButtonAddFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClickAddFavorite(fragmentPlayerButtonAddFavorite);
            }
        });

        fragmentPlayerButtonRepeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClickRepeat(fragmentPlayerButtonRepeat);
            }
        });

        fragmentPlayerButtonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClickNext();
            }
        });

        fragmentPlayerButtonPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClickPrevious();
            }
        });

        fragmentPlayerButtonShuffle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClickShuffle(fragmentPlayerButtonShuffle);
            }
        });


        return view;
    }

    public void setListener(PlayerFragmentListener listener) {
        this.listener = listener;
    }

    public Track getTrackRecibido() {
        return trackRecibido;
    }

    private void setViewResources(Track trackRecibido) {
        Glide.with(getContext()).load(trackRecibido.getAlbum().getCover()).into(fragmentPlayerImageView);
        fragmentPlayerTextViewArtista.setText(trackRecibido.getArtist().getName());
        fragmentPlayerTextViewNombre.setText(trackRecibido.getTitle());
    }

    private void findViews() {
        seekBar = binding.fragmentPlayerSeekbar;
        fragmentPlayerImageView = binding.fragmentPlayerImageView;
        fragmentPlayerButtonAddFavorite = binding.fragmentPlayerButtonAddFavorite;
        fragmentPlayerTextViewArtista = binding.fragmentPlayerTextViewArtista;
        fragmentPlayerTextViewNombre = binding.fragmentPlayerTextViewNombre;
        fragmentPlayerButtonPlay = binding.fragmentPlayerButtonPlay;
        fragmentPlayerButtonNext = binding.fragmentPlayerButtonNext;
        fragmentPlayerButtonPrevious = binding.fragmentPlayerButtonPrevious;
        fragmentPlayerButtonRepeat = binding.fragmentPlayerButtonRepeat;
        fragmentPlayerButtonShuffle = binding.fragmentPlayerButtonShuffle;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }

    public interface PlayerFragmentListener{
        void onClickPlay(ToggleButton boton);
        void onClickNext();
        void onClickPrevious();
        void onClickShuffle(ToggleButton boton);
        void onClickRepeat(ToggleButton boton);
        void onClickAddFavorite(ImageView boton);
    }
}
