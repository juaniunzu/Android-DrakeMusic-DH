package com.example.projectointegrador.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.palette.graphics.Palette;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.request.target.CustomViewTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.projectointegrador.R;
import com.example.projectointegrador.databinding.FragmentPlayerBinding;
import com.example.projectointegrador.model.Track;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class PlayerFragment extends Fragment {

    public static final String KEY_DETAIL_TRACK = "track";
    private FragmentPlayerBinding binding;
    private ImageView fragmentPlayerImageView;
    private FloatingActionButton fragmentPlayerButtonNext;
    private FloatingActionButton fragmentPlayerButtonPlay;
    private FloatingActionButton fragmentPlayerButtonPrevious;
    private FloatingActionButton fragmentPlayerButtonRepeat;
    private FloatingActionButton fragmentPlayerButtonShuffle;
    private ImageView fragmentPlayerButtonAddFavorite;
    private TextView fragmentPlayerTextViewArtista;
    private TextView fragmentPlayerTextViewNombre;
    private Bitmap bitmap;


    /**
     * Nuevo constructor. Setea el fragment segun el {@param track} parametro
     * @return
     */
    public static PlayerFragment crearPlayerFragment(Track track){
        PlayerFragment fragment = new PlayerFragment();
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
        final Track trackRecibido = (Track) desdeMain.getSerializable(KEY_DETAIL_TRACK);

        setViewResources(trackRecibido);

        setFragmentBackground(view, trackRecibido.getAlbum().getCover());

        return view;
    }

    /**
     * setea el background del fragment con el color dominante de la imagen de tapa del disco, en gradiente hacia negro, de arriba hacia abajo
     * @param view
     * @param url
     */
    private void setFragmentBackground(final View view, String url) {
        Glide.with(getContext()).asBitmap().load(url).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(@NonNull final Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                Palette.from(resource).generate(new Palette.PaletteAsyncListener() {
                    @Override
                    public void onGenerated(@Nullable Palette palette) {
                        int dominantColor = palette.getDominantColor(resource.getPixel(0, 0));

                        GradientDrawable gd = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, new int[]{dominantColor, 0xFF000000});
                        gd.setCornerRadius(0f);

                        view.setBackground(gd);
                    }
                });
            }
        });
    }



    private void setViewResources(Track trackRecibido) {
        Glide.with(getContext()).load(trackRecibido.getAlbum().getCover()).into(fragmentPlayerImageView);
        fragmentPlayerTextViewArtista.setText(trackRecibido.getArtist().getName());
        fragmentPlayerTextViewNombre.setText(trackRecibido.getTitle());
    }

    private void findViews() {
        fragmentPlayerImageView = binding.fragmentPlayerImageView;
        fragmentPlayerButtonNext = binding.fragmentPlayerButtonNext;
        fragmentPlayerButtonPlay = binding.fragmentPlayerButtonPlay;
        fragmentPlayerButtonPrevious = binding.fragmentPlayerButtonPrevious;
        fragmentPlayerButtonRepeat = binding.fragmentPlayerButtonRepeat;
        fragmentPlayerButtonShuffle = binding.fragmentPlayerButtonShuffle;
        fragmentPlayerButtonAddFavorite = binding.fragmentPlayerButtonAddFavorite;
        fragmentPlayerTextViewArtista = binding.fragmentPlayerTextViewArtista;
        fragmentPlayerTextViewNombre = binding.fragmentPlayerTextViewNombre;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}
